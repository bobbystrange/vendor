package org.dreamcat.vendor.weixin.platform.view;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.dreamcat.vendor.weixin.platform.core.AuthorizerInfo;

/**
 * Create by tuke on 2019-05-27
 */
@Data
public class ApiGetAuthorizerInfoView {
    @SerializedName("authorizer_info")
    private AuthorizerInfo authorizerInfo;
}
