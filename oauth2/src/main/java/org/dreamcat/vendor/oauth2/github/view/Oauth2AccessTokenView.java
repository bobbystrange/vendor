package org.dreamcat.vendor.oauth2.github.view;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Create by tuke on 2020/6/5
 */
@Data
public class Oauth2AccessTokenView {
    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String token_type;

    // A comma-delimited list of scopes
    private String scope;

}
