package org.dreamcat.vendor.weixin.platform.view;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Create by tuke on 2019-05-27
 */
@Data
public class ApiCreatePreauthcodeView {

    @SerializedName("pre_auth_code")
    private String preAuthCode;
    // 600 sec.
    @SerializedName("expires_in")
    private int expiresIn;
}
