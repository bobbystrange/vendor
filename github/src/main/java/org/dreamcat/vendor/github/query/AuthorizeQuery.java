package org.dreamcat.vendor.github.query;

import com.google.gson.annotations.SerializedName;
import java.util.Set;
import lombok.Data;

/**
 * Create by tuke on 2020/6/5
 */
@Data
public class AuthorizeQuery {

    @SerializedName("redirect_uri")
    private String redirectUri;

    // Suggests a specific account to use for signing in and authorizing the app.
    private String login;

    // A space-delimited list of scopes
    private Set<String> scope;

    // An unguessable random string. It is used to protect against cross-site request forgery attacks.
    private String state;

    // Whether or not unauthenticated users will be offered an option to sign up for GitHub during the OAuth flow. The default is true. Use false when a policy prohibits signups.
    @SerializedName("allow_signup")
    private boolean allowSignup;
}
