package org.dreamcat.vendor.weixin.platform.view;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Create by tuke on 2019-05-27
 */
@Data
public class ApiComponentTokenView {

    @SerializedName("component_access_token")
    private String componentAccessToken;
    // 7200 sec.
    @SerializedName("expires_in")
    private int expiresIn;

}
