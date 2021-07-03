package org.dreamcat.vendor.weixin.mp;

import java.util.Map;
import org.dreamcat.vendor.weixin.mp.query.SendTemplateMessageQuery;
import org.dreamcat.vendor.weixin.mp.view.GetAllPrivateTemplateView;
import org.dreamcat.vendor.weixin.mp.view.GetIndustryView;
import org.dreamcat.vendor.weixin.mp.view.Oauth2AccessTokenView;
import org.dreamcat.vendor.weixin.mp.view.SendTemplateMessageView;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Create by tuke on 2019-05-27
 */
public interface WXMPRequest {

    String BASE_URL = "https://api.weixin.qq.com";

    @POST("/sns/oauth2/component/access_token")
    Call<Oauth2AccessTokenView> getOauth2AccessToken(
            @QueryMap Map<String, String> query);

    @GET("/cgi-bin/template/get_industry")
    Call<GetIndustryView> getIndustry(@Query("access_token") String accessToken);

    @GET("/cgi-bin/template/get_all_private_template")
    Call<GetAllPrivateTemplateView> getAllPrivateTemplate(@Query("access_token") String accessToken);

    @POST("/cgi-bin/message/template/send")
    Call<SendTemplateMessageView> sendTemplateMessage(
            @Body SendTemplateMessageQuery query, @Query("access_token") String accessToken);
}
