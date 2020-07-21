package org.dreamcat.vendor.cdn.storage;

import com.google.gson.JsonElement;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.meta.InsertOnly;
import com.qcloud.cos.request.DelFileRequest;
import com.qcloud.cos.request.DelFolderRequest;
import com.qcloud.cos.request.GetFileLocalRequest;
import com.qcloud.cos.request.ListFolderRequest;
import com.qcloud.cos.request.StatFileRequest;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.dreamcat.common.hc.gson.GsonUtil;
import org.dreamcat.common.hc.okhttp.OkHttpUtil;
import org.dreamcat.vendor.cdn.util.QcloudCdnSignUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Create by tuke on 2019-01-17
 */
@RequiredArgsConstructor
@Slf4j
public class QcloudCosService {
    private static final String QCLOUD_API_CDN_URL = "cdn.api.qcloud.com/v2/index.php";
    private final long appId;
    private final String secretId;
    private final String secretKey;
    private final COSClient cosClient;
    private int retry = 3;

    // builder pattren
    public static Builder builder(long appId, String secretId, String secretKey) {
        return new Builder(appId, secretId, secretKey);
    }

    public boolean exist(String bucket, String cosPath) {
        StatFileRequest request = new StatFileRequest(bucket, cosPath);
        String json = cosClient.statFile(request);

        log.debug("invoking qcloud cos: exist {} {}", bucket, cosPath);
        log.debug(json);

        try {
            JsonElement root = GsonUtil.toJsonElement(json);
            Integer code = GsonUtil.getInt(root, "code");
            return code != null && code == 0;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return false;
    }

    // over write
    public boolean upload(String bucket, String cosPath, String localPath) {
        long startTime = System.currentTimeMillis();
        boolean success = false;
        for (int i = 0; i < retry; i++) {
            UploadFileRequest uploadFileRequest = new UploadFileRequest(
                    bucket, cosPath, localPath);
            uploadFileRequest.setInsertOnly(InsertOnly.OVER_WRITE);

            long time = System.currentTimeMillis();
            String json = cosClient.uploadFile(uploadFileRequest);
            time = System.currentTimeMillis() - time;
            log.debug("invoking qcloud cos: upload {} {}", bucket, cosPath);
            log.debug(json);
            try {
                JsonElement root = GsonUtil.toJsonElement(json);
                Integer code = GsonUtil.getInt(root, "code");
                String message = GsonUtil.getString(root, "message");
                if (code != null && code == 0) {
                    success = true;
                    log.debug("invoked qcloud cos upload success: time={}&bucket={}&cosPath={}", time, bucket, cosPath);
                    break;
                }

                if (i == retry - 1) {
                    log.error("Upload {} to qcloud-cos failed {} times, abort.",
                            cosPath, retry);
                    break;
                }

                log.warn("Upload {} to qcloud-cos failed, code={}, retrying after 0.2 second..", cosPath, code);
                if (message != null) log.warn("{}", message);
                try {
                    Thread.sleep(200L);
                } catch (Exception ex) {
                    log.warn(ex.getMessage(), ex);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return success;
    }

    public boolean download(String bucket, String cosPath, String localPath) {
        boolean success = false;
        GetFileLocalRequest getFileLocalRequest = new GetFileLocalRequest(
                bucket, cosPath, localPath);
        getFileLocalRequest.setUseCDN(false);

        for (int i = 0; i < retry; ++i) {
            long time = System.currentTimeMillis();
            String json = this.cosClient.getFileLocal(getFileLocalRequest);
            time = System.currentTimeMillis() - time;
            log.debug("invoking qcloud cos: download {} {}", bucket, cosPath);
            log.debug(json);
            try {
                JsonElement root = GsonUtil.toJsonElement(json);
                Integer code = GsonUtil.getInt(root, "code");
                String message = GsonUtil.getString(root, "message");
                if (code != null && code == 0) {
                    success = true;
                    log.debug("invoked qcloud cos download success: time={}&bucket={}&cosPath={}", time, bucket, cosPath);
                    break;
                }

                if (i == retry - 1) {
                    log.error("Download {} from qcloud-cos failed {} times, abort.",
                            cosPath, retry);
                    break;
                }

                log.warn("Download {} from qcloud-cos failed, code={}, retrying after 0.2 second..", cosPath, code);
                if (message != null) log.warn("{}", message);
                try {
                    Thread.sleep(200L);
                } catch (Exception ex) {
                    log.warn(ex.getMessage(), ex);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return success;
    }

    public boolean delete(String bucket, String cosPath) {
        DelFileRequest fileRequest = new DelFileRequest(bucket, cosPath);
        String json = cosClient.delFile(fileRequest);
        log.debug("invoking qcloud cos: delete {} {}", bucket, cosPath);
        log.debug(json);
        try {
            JsonElement root = GsonUtil.toJsonElement(json);
            Integer code = GsonUtil.getInt(root, "code");
            return code != null && code == 0;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public boolean deleteFolder(String bucket, String cosPath) {
        DelFolderRequest request = new DelFolderRequest(bucket, cosPath);
        String json = cosClient.delFolder(request);

        try {
            JsonElement root = GsonUtil.toJsonElement(json);
            Integer code = GsonUtil.getInt(root, "code");
            return code != null && code == 0;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public boolean listFolder(String bucket, String cosPath) {
        ListFolderRequest request = new ListFolderRequest(bucket, cosPath);
        String json = cosClient.listFolder(request);

        try {
            JsonElement root = GsonUtil.toJsonElement(json);
            Integer code = GsonUtil.getInt(root, "code");
            if (code != null && code == 0) {
                String data = GsonUtil.getString(root, "data");
                log.info("{}", data);
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;

    }

    // 默认情况下，每一个账号每日可刷新 URL 10000条，每次最多可提交1000条
    // 提交的 URL 必须以http://或https://开头。
    // https://cloud.tencent.com/document/product/228/3946
    public boolean refreshCdnUrl(String accessToken, List<String> urlList) {
        String joinedUrlListString = String.join(
                "\n", urlList.toArray(new String[0]));

        String authorization = String.format(
                "Bearer %s", accessToken);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", authorization);

        int size = urlList.size();
        List<Map<String, Object>> content = new ArrayList<>(size);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("Action", "RefreshCdnUrl");
            item.put("SecretId", secretId);
            item.put("Timestamp", new Date().getTime());
            item.put("Nonce", random.nextLong());
            item.put("urls." + i, urlList.get(i));
            QcloudCdnSignUtil.signAndPut("POST", item, secretKey);

            content.add(item);
        }
        String json = GsonUtil.toJson(content);

        try {
            Response response = OkHttpUtil.postJSON(QCLOUD_API_CDN_URL, json, headers, null);
            ResponseBody body = response.body();
            String bodyString = "body is null";
            if (body != null) {
                bodyString = body.string();
                body.close();
            }
            log.info(bodyString);
            if (response.code() == 200) {
                Integer code = GsonUtil.getInt(bodyString, "code");
                return code == 0;
            }
            return false;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @RequiredArgsConstructor
    public static class Builder {
        private final long appId;
        private final String secretId;
        private final String secretKey;

        private String region;
        // milliseccond
        private int connectionTimeout = 0;
        // milliseccond
        private int socketTimeout = 0;

        private int retry;

        public Builder region(String region) {
            this.region = region;
            return this;
        }

        public Builder connectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public Builder socketTimeout(int socketTimeout) {
            this.socketTimeout = socketTimeout;
            return this;
        }

        public Builder retry(int retry) {
            this.retry = retry;
            return this;
        }


        public QcloudCosService build() {
            ClientConfig clientConfig = new ClientConfig();
            if (region != null) clientConfig.setRegion(region);
            if (connectionTimeout > 0) clientConfig.setConnectionTimeout(connectionTimeout);
            if (socketTimeout > 0) clientConfig.setSocketTimeout(socketTimeout);
            COSClient cosClient = new COSClient(clientConfig,
                    new Credentials(appId, secretId, secretKey));

            QcloudCosService target = new QcloudCosService(appId, secretId, secretKey, cosClient);

            if (retry > 0) target.retry = retry;
            return target;
        }
    }

}
