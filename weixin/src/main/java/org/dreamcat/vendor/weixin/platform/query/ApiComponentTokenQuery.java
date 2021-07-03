package org.dreamcat.vendor.weixin.platform.query;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.dreamcat.vendor.weixin.platform.core.ComponentAppPair;

/**
 * Create by tuke on 2019-05-27
 */
@Getter
@Setter
public class ApiComponentTokenQuery extends ComponentAppPair {

    @SerializedName("component_verify_ticket")
    private String componentVerifyTicket;
}
