package org.dreamcat.vendor.github.query;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Create by tuke on 2020/6/5
 */
@Data
public class Oauth2AccessTokenQuery {

    @SerializedName("redirect_uri")
    private String redirectUri;

    // The unguessable random string you provided in Step 1.
    private String state;
}
