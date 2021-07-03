package org.dreamcat.vendor.weixin.mp.query;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * Create by tuke on 2019-05-28
 */
@Getter
@Setter
public class CodeQuery {

    @SerializedName("appid")
    protected String appId;
    @SerializedName("redirect_uri")
    private String redirectUri;
    //@SerializedName("response_type")
    //private String responseType = "code";
    @SerializedName("component_appid")
    private String componentAppId;
    // 授权作用域，拥有多个作用域用逗号（,）分隔
    private String scope;
    // 重定向后会带上state参数，开发者可以填写任意参数值，最多128字节
    private String state;
}
