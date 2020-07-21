package org.dreamcat.vendor.weixin.platform.query;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.dreamcat.vendor.weixin.platform.core.ComponentAppid;

/**
 * Create by tuke on 2019-05-27
 */
@Getter
@Setter
public class ApiQueryAuthQuery extends ComponentAppid {
    @SerializedName("authorization_code")
    private String authorizationCode;
}
