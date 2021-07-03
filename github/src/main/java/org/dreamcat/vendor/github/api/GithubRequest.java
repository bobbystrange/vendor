package org.dreamcat.vendor.github.api;

import java.util.Map;
import org.dreamcat.vendor.github.view.Oauth2AccessTokenView;
import org.dreamcat.vendor.github.view.UserView;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Create by tuke on 2020/6/5
 */
public interface GithubRequest {

    @Headers({"Accept: application/json"})
    @POST("https://github.com/login/oauth/access_token")
    Call<Oauth2AccessTokenView> postOauth2AccessToken(
            @QueryMap Map<String, String> queryMap);

    @GET("https://api.github.com/user")
    Call<UserView> getUser(@Header("Authorization") String accessToken);

}
