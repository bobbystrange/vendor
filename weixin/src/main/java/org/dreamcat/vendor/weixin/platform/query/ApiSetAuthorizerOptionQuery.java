package org.dreamcat.vendor.weixin.platform.query;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.dreamcat.vendor.weixin.platform.core.ComponentAuthorizerPair;

/**
 * Create by tuke on 2019-05-27
 */
@Getter
@Setter
public class ApiSetAuthorizerOptionQuery extends ComponentAuthorizerPair {

    @SerializedName("option_name")
    private String optionName;
    @SerializedName("option_value")
    private int optionValue;
}
