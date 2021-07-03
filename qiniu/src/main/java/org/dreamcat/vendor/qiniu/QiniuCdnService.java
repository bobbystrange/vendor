package org.dreamcat.vendor.qiniu;

import com.qiniu.cdn.CdnManager;
import com.qiniu.cdn.CdnResult;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.hc.gson.GsonUtil;

/**
 * Create by tuke on 2019-01-18
 */
@Slf4j
public class QiniuCdnService {

    private final Auth auth;
    private final BucketManager bucketManager;
    private final UploadManager uploadManager;

    private final CdnManager cdnManager;

    private int retry = 3;

    private QiniuCdnService(Auth auth) {
        Configuration cfg = new Configuration(Region.autoRegion());

        this.auth = auth;
        this.bucketManager = new BucketManager(auth, cfg);
        uploadManager = new UploadManager(cfg);
        cdnManager = new CdnManager(auth);
    }

    // builder pattren
    public static Builder builder(String accessKey, String secretKey) {
        return new Builder(accessKey, secretKey);
    }

    public boolean upload(String bucket, String key, String localFile) {
        boolean success = false;
        delete(bucket, key);

        String token = auth.uploadToken(bucket, key);
        for (int i = 0; i < retry; i++) {
            try {
                log.debug("invoking qiniu cdn: upload {} {}", bucket, key);
                Response response = uploadManager.put(localFile, key, token);
                log.debug(response.bodyString());
                success = true;
                break;
            } catch (QiniuException e) {
                log.info(e.response.error, e);
                if (i == retry - 1) {
                    break;
                }
            }
        }
        return success;
    }

    public boolean delete(String bucket, String key) {
        for (int i = 0; i < retry; i++) {
            try {
                log.debug("invoking qiniu cdn: delete {} {}", bucket, key);
                bucketManager.delete(bucket, key);
                return true;
            } catch (QiniuException e) {
                log.error(e.response.error);
            }
        }
        return false;
    }

    public String fileHash(String bucket, String filename) {
        FileInfo fileInfo = fileInfo(bucket, filename);
        if (fileInfo == null) return null;
        return fileInfo.hash;
    }

    public long fileSize(String bucket, String filename) {
        FileInfo fileInfo = fileInfo(bucket, filename);
        if (fileInfo == null) return -1;
        return fileInfo.fsize;
    }

    public FileInfo fileInfo(String bucket, String filename) {
        try {
            return bucketManager.stat(bucket, filename);
        } catch (QiniuException e) {
            log.info(e.getMessage(), e);
            return null;
        }
    }

    // less than 100
    public boolean refresh(List<String> refreshUrls) {
        String[] urls = refreshUrls.toArray(new String[0]);
        for (int i = 0; i < retry; i++) {
            try {
                CdnResult.RefreshResult result = cdnManager.refreshUrls(urls);
                log.debug(GsonUtil.toJson(result));
                return true;
            } catch (QiniuException e) {
                log.error(e.response.error, e);
            }
        }
        return false;
    }

    public static class Builder {

        private final QiniuCdnService service;

        private Builder(String accessKey, String secretKey) {
            Auth auth = Auth.create(accessKey, secretKey);
            this.service = new QiniuCdnService(auth);
        }

        public Builder retry(int retry) {
            service.retry = retry;
            return this;
        }

        public QiniuCdnService build() {
            return service;
        }
    }
}
