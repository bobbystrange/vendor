package org.dreamcat.vendor.weixin.platform.core;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * Create by tuke on 2019-05-27
 */
@Getter
@Setter
public class ComponentAppPair extends ComponentAppid {

    @SerializedName("component_appsecret")
    protected String componentAppSecret;
}
