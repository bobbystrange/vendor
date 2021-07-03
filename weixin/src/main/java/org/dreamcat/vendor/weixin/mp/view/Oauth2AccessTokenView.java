package org.dreamcat.vendor.weixin.mp.view;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.dreamcat.vendor.weixin.common.CommonView;

/**
 * Create by tuke on 2019-05-28
 */
@Getter
@Setter
public class Oauth2AccessTokenView extends CommonView {

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("expires_in")
    private int expiresIn;
    @SerializedName("refresh_token")
    private String refreshToken;
    private String openid;
    // 用户授权的作用域，使用逗号（,）分隔
    private String scope;
}
