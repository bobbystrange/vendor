package org.dreamcat.vendor.cdn.storage;

import lombok.extern.slf4j.Slf4j;
import main.java.com.UpYun;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.dreamcat.common.crypto.MD5Util;
import org.dreamcat.common.hc.gson.GsonUtil;
import org.dreamcat.common.hc.okhttp.OkHttpUtil;
import org.dreamcat.common.util.DateUtil;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by tuke on 2019-01-18
 */
@Slf4j
public class UpyunService {
    private static final String UPYUN_PURGE = "http://purge.upyun.com/purge/";
    private static final String UPYUN_API_PURGE = "https://api.upyun.com/purge/";
    private final Map<String, UpYun> cache = new HashMap<>();
    private final String username;
    private final String password;
    private int retry = 3;
    // seccond
    private int timeout = 0;
    private boolean debug = false;

    private UpyunService(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // builder pattren
    public static Builder builder(String username, String password) {
        return new Builder(username, password);
    }

    public boolean exist(String bucket, String path) {
        return upyun(bucket).getFileInfo(path) != null;
    }

    public boolean upload(String bucket, String path, File file) {
        UpYun upyun = upyun(bucket);
        boolean success = false;
        for (int i = 0; i < retry; i++) {
            try {
                upyun.setContentMD5(UpYun.md5(file));
                success = upyun.writeFile(path, file, true);
                if (success) break;

                if (i == retry - 1) {
                    log.error("Upload {} to upyun failed {} times, abort.", path, retry);
                    break;
                }

                log.warn("Upload {} to upyun failed, retrying after 0.2 second...", path);
                try {
                    Thread.sleep(200L);
                } catch (Exception ex) {
                    log.warn(ex.getMessage(), ex);
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                break;
            }

        }
        return success;
    }

    public boolean download(String bucket, String path, File localFile) {
        UpYun upyun = upyun(bucket);
        boolean success = false;
        for (int i = 0; i < retry; i++) {
            success = upyun.readFile(path, localFile);
            if (success) break;

            if (i == retry - 1) {
                log.error("Download {} from upyun failed {} times, abort.", path, retry);
                break;
            }

            log.warn("Download {} from upyun failed, retrying after 0.2 second...", path);
            try {
                Thread.sleep(200L);
            } catch (Exception ex) {
                log.warn(ex.getMessage(), ex);
            }
        }
        return success;
    }

    public boolean delete(String bucket, String path) {
        UpYun upyun = upyun(bucket);
        boolean result;
        for (int i = 0; i < retry; i++) {
            result = upyun.deleteFile(path);
            if (result) return true;
        }
        return false;
    }

    // https://api.upyun.com/doc#/api/operation/cache/POST%20%2Fpurge
    public boolean refresh(String accessToken, List<String> urlList) {
        String joinedUrlListString = String.join(
                "\n", urlList.toArray(new String[0]));

        String authorization = String.format(
                "Bearer %s", accessToken);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", authorization);

        Map<String, Object> content = new HashMap<>();
        content.put("urls", joinedUrlListString);
        String json = GsonUtil.toJson(content);

        try {
            Response response = OkHttpUtil.postJSON(UPYUN_API_PURGE, json, headers, null);
            ResponseBody body = response.body();
            String bodyString = "body is null";
            if (body != null) {
                bodyString = body.string();
                body.close();
            }
            log.info(bodyString);
            if (response.code() == 200) {
                return !bodyString.contains("error_code");
            }
            return false;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public boolean refreshByPurge(String bucket, List<String> urlList) {
        String joinedUrlListString = String.join(
                "\n", urlList.toArray(new String[0]));
        String dateString = DateUtil.toGMTString(new Date());
        String md5encodedPassword = MD5Util.md5Hex(password);
        String sign = String.format(
                "%s&%s&%s&%s", joinedUrlListString, bucket,
                dateString, md5encodedPassword);
        sign = MD5Util.md5Hex(sign);

        String authorization = String.format(
                "UpYun %s:%s:%s", bucket, username, sign);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", authorization);
        headers.put("Date", dateString);
        Map<String, String> form = new HashMap<>();
        form.put("purge", joinedUrlListString);

        try {
            Response response = OkHttpUtil.postForm(UPYUN_PURGE, form, headers, null);
            ResponseBody body = response.body();
            String bodyString = "body is null";
            if (body != null) {
                bodyString = body.string();
                body.close();
            }
            log.info(bodyString);
            if (response.code() == 200) {
                return !bodyString.contains("invalid_domain_of_url");
            }
            return false;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    private UpYun upyun(String bucket) {
        UpYun upyun = cache.get(bucket);
        if (upyun == null) {
            upyun = new UpYun(bucket, username, password);
            if (timeout > 0) upyun.setTimeout(timeout);
            upyun.setDebug(debug);
            cache.put(bucket, upyun);
        }
        return upyun;
    }

    public static class Builder {
        private final UpyunService service;

        private Builder(String username, String password) {
            service = new UpyunService(username, password);
        }

        public Builder timeout(int timeout) {
            service.timeout = timeout;
            return this;
        }

        public Builder debug(boolean debug) {
            service.debug = debug;
            return this;
        }

        public Builder retry(int retry) {
            service.retry = retry;
            return this;
        }

        public UpyunService build() {
            return service;
        }
    }

}
