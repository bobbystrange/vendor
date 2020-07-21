package org.dreamcat.vendor.oauth2.github;

import com.google.gson.annotations.SerializedName;
import org.dreamcat.common.bean.BeanMapUtil;
import org.dreamcat.common.hc.okhttp.RetrofitUtil;
import org.dreamcat.common.util.ObjectUtil;
import org.dreamcat.common.util.UrlUtil;
import org.dreamcat.vendor.oauth2.github.api.GithubRequest;
import org.dreamcat.vendor.oauth2.github.api.IGithubComponent;
import org.dreamcat.vendor.oauth2.github.query.AuthorizeQuery;
import org.dreamcat.vendor.oauth2.github.query.Oauth2AccessTokenQuery;
import org.dreamcat.vendor.oauth2.github.view.Oauth2AccessTokenView;
import org.dreamcat.vendor.oauth2.github.view.UserView;

import java.util.Map;
import java.util.Set;

/**
 * Create by tuke on 2020/6/5
 */
public class GithubComponent implements IGithubComponent {
    private final GithubRequest githubRequest;
    // The client ID you received from GitHub when you registered.
    private final String cilentId;
    // The client secret you received from GitHub for your GitHub App.
    private final String clientSecret;

    public GithubComponent(String cilentId, String clientSecret) {
        this.cilentId = cilentId;
        this.clientSecret = clientSecret;
        this.githubRequest = RetrofitUtil.getInstance4Json().create(GithubRequest.class);
    }

    // 1. Request a user's GitHub identity
    // GET https://github.com/login/oauth/authorize
    @SuppressWarnings("unchecked")
    @Override
    public String concatAuthorizeUrl(AuthorizeQuery query) {
        Map<String, Object> queryMap = BeanMapUtil.toMap(query, SerializedName.class);
        queryMap.put("client_id", cilentId);
        Set<String> scope = (Set<String>) queryMap.get("scope");
        if (ObjectUtil.isEmpty(scope)) {
            queryMap.put("scope", null);
        } else {
            queryMap.put("scope", String.join(" ", scope));
        }
        return UrlUtil.concatUrl("https://github.com/login/oauth2/authorize", queryMap);
    }

    // 2. Users are redirected back to your site by GitHub
    // POST https://github.com/login/oauth/access_token
    @Override
    public Oauth2AccessTokenView postOauth2AccessToken(Oauth2AccessTokenQuery query, String code) {
        Map<String, String> queryMap = BeanMapUtil.toProps(query);
        queryMap.put("client_id", cilentId);
        queryMap.put("client_secret", clientSecret);
        queryMap.put("code", code);
        return RetrofitUtil.unwrap(githubRequest.postOauth2AccessToken(queryMap));
    }

    // 3. Use the access token to access the API
    // Authorization: token OAUTH-TOKEN
    // GET https://api.github.com/user
    public UserView getUser(String accessToken, String tokenType) {
        return RetrofitUtil.unwrap(githubRequest.getUser(tokenType + " " + accessToken));
    }

}
