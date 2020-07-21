package org.dreamcat.vendor.weixin.platform.view;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Create by tuke on 2019-05-27
 */
@Data
public class ApiGetAuthorizerOptionView {
    @SerializedName("authorizer_appid")
    private String authorizerAppId;
    @SerializedName("option_name")
    private String optionName;
    @SerializedName("option_value")
    private int optionValue;
}
