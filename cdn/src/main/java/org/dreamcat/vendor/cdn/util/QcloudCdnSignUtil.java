package org.dreamcat.vendor.cdn.util;

import org.dreamcat.common.crypto.SignUtil;
import org.dreamcat.common.util.UrlUtil;

import java.util.Map;

/**
 * Create by tuke on 2019-03-31
 */
public class QcloudCdnSignUtil {

    private static final String QCLOUD_API_CDN_URL = "cdn.api.qcloud.com/v2/index.php";

    public static void signAndPut(String method, Map<String, Object> queryMap, String secretKey) {
        String sign = sign(method, queryMap, secretKey);

        switch (method.toUpperCase()) {
            case "HEAD":
            case "GET":
                queryMap.put("Signature", sign);
                queryMap.entrySet().forEach(entry -> {
                    entry.setValue(UrlUtil.encodeUrl(entry.getValue().toString()));
                });
                break;
            case "POST":
            case "PUT":
            case "DELETE":
            case "PATCH":
                queryMap.put("Signature", UrlUtil.encodeUrl(sign));
            default:
                throw new IllegalArgumentException(String.format("HTTP method '%s' is not supported", method));
        }
    }

    public static String sign(String method, Map<String, Object> queryMap, String secretKey) {
        String paramString = UrlUtil.toSortedQueryString(
                queryMap,
                it -> it.replace('_', '.'),
                Object::toString);
        String signatureString = method.toUpperCase() + QCLOUD_API_CDN_URL + "?" + paramString;
        return SignUtil.hs1Base64(signatureString, secretKey);
    }

}
