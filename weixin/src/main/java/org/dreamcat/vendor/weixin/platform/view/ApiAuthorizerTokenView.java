package org.dreamcat.vendor.weixin.platform.view;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Create by tuke on 2019-05-27
 */
@Data
public class ApiAuthorizerTokenView {

    @SerializedName("authorizer_access_token")
    private String authorizerAccessToken;
    // 有效期，为2小时
    @SerializedName("expires_in")
    private int expiresIn;
    @SerializedName("authorizer_refresh_token")
    private String authorizerRefreshToken;
}
