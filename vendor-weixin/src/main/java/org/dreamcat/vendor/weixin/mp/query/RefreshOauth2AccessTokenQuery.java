package org.dreamcat.vendor.weixin.mp.query;

import com.google.gson.annotations.SerializedName;

/**
 * Create by tuke on 2019-05-28
 */
public class RefreshOauth2AccessTokenQuery {
    @SerializedName("appid")
    protected String appId;
    private String code;
    //@SerializedName("grant_type")
    //private String grantType = "authorization_code";
    @SerializedName("component_appid")
    private String componentAppId;
    @SerializedName("refresh_token")
    private String refreshToken;
}
