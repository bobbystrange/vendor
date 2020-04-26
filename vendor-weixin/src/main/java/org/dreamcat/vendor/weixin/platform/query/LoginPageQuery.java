package org.dreamcat.vendor.weixin.platform.query;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.dreamcat.vendor.weixin.platform.core.ComponentAppid;

/**
 * Create by tuke on 2019-05-24
 */
@Getter
@Setter
public class LoginPageQuery extends ComponentAppid {
    @SerializedName("pre_auth")
    private String preAuth;
    @SerializedName("redirect_uri")
    private String redirectUri;
    // 要授权的帐号类型，
    //    1则商户扫码后，手机端仅展示公众号、
    //    2表示仅展示小程序，
    //    3表示公众号和小程序都展示。
    // 如果为未制定，则默认小程序和公众号都展示。
    @SerializedName("auth_type")
    private Integer authType = 3;
}
