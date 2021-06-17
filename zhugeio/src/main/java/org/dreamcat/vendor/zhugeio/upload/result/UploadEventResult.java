package org.dreamcat.vendor.zhugeio.upload.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by tuke on 2020/10/22
 *
 * success but with warn
 * {
 *     "return_code":0,
 *     "return_message":"success",
 *     "warn_did":"did is null, will have some impact on statistics"
 * }
 */
@Data
public class UploadEventResult {
    private static final String RETURN_CODE = "return_code";
    private static final String RETURN_MESSAGE = "return_message";
    private static final String WARN_PREFIX = "warn_";

    /**
     * 0:成功；小于0：失败
     */
    @SerializedName(RETURN_CODE)
    private double code;
    @SerializedName(RETURN_MESSAGE)
    private String message;
    /**
     * warn_*	    警告、提示信息
     */
    private transient Map<String, String> warnings;

    public static UploadEventResult from(Map<String, Object> map) {
        UploadEventResult result = new UploadEventResult();
        double code = (Double) map.get(RETURN_CODE);
        String message = (String) map.get(RETURN_MESSAGE);
        result.setCode(code);
        result.setMessage(message);
        map.forEach((k, v) -> {
            if (!k.startsWith(WARN_PREFIX)) return;
            k = k.substring(WARN_PREFIX.length());
            Map<String, String> warnings = result.getWarnings();
            if (warnings == null) {
                warnings = new HashMap<>();
                result.setWarnings(warnings);
            }
            if (v != null) warnings.put(k, v.toString());
        });
        return result;
    }
 }
