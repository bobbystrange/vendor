package org.dreamcat.vendor.weixin.platform;

import org.dreamcat.vendor.weixin.common.AppId;
import org.dreamcat.vendor.weixin.common.CommonView;
import org.dreamcat.vendor.weixin.platform.query.ApiAuthorizerTokenQuery;
import org.dreamcat.vendor.weixin.platform.query.ApiComponentTokenQuery;
import org.dreamcat.vendor.weixin.platform.query.ApiCreatePreAuthCodeQuery;
import org.dreamcat.vendor.weixin.platform.query.ApiGetAuthorizerInfoQuery;
import org.dreamcat.vendor.weixin.platform.query.ApiGetAuthorizerOptionQuery;
import org.dreamcat.vendor.weixin.platform.query.ApiQueryAuthQuery;
import org.dreamcat.vendor.weixin.platform.query.ApiSetAuthorizerOptionQuery;
import org.dreamcat.vendor.weixin.platform.view.ApiAuthorizerTokenView;
import org.dreamcat.vendor.weixin.platform.view.ApiComponentTokenView;
import org.dreamcat.vendor.weixin.platform.view.ApiCreatePreauthcodeView;
import org.dreamcat.vendor.weixin.platform.view.ApiGetAuthorizerInfoView;
import org.dreamcat.vendor.weixin.platform.view.ApiGetAuthorizerOptionView;
import org.dreamcat.vendor.weixin.platform.view.ApiQueryAuthView;
import org.dreamcat.vendor.weixin.platform.view.ApiSetAuthorizerOptionView;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Create by tuke on 2019-05-24
 */
public interface WXPlatformRequest {

    String BASE_URL = "https://api.weixin.qq.com";
    String COMPONENT_LOGIN_PAGE = "https://mp.weixin.qq.com/cgi-bin/componentloginpage";

    @POST("/api_component_token")
    Call<ApiComponentTokenView> getApiComponentToken(@Body ApiComponentTokenQuery query);

    @POST("/api_create_preauthcode")
    Call<ApiCreatePreauthcodeView> getPreAuthCode(
            @Body ApiCreatePreAuthCodeQuery query,
            @Query("component_access_token") String componentAccessToken);

    @POST("/api_query_auth")
    Call<ApiQueryAuthView> getApiQueryAuth(
            @Body ApiQueryAuthQuery query,
            @Query("component_access_token") String componentAccessToken);

    @POST("/api_authorizer_token")
    Call<ApiAuthorizerTokenView> getApiAuthorizerToken(
            @Body ApiAuthorizerTokenQuery query,
            @Query("component_access_token") String componentAccessToken);

    @POST("/api_get_authorizer_info")
    Call<ApiGetAuthorizerInfoView> getApiGetAuthorizerInfo(
            @Body ApiGetAuthorizerInfoQuery query,
            @Query("component_access_token") String componentAccessToken);

    @POST("/api_get_authorizer_option")
    Call<ApiGetAuthorizerOptionView> getApiGetAuthorizerOption(
            @Body ApiGetAuthorizerOptionQuery query,
            @Query("component_access_token") String componentAccessToken);

    @POST("/api_set_authorizer_option")
    Call<ApiSetAuthorizerOptionView> getApiSetAuthorizerOption(
            @Body ApiSetAuthorizerOptionQuery query,
            @Query("component_access_token") String componentAccessToken);

    @POST("/cgi-bin/clear_quota")
    Call<CommonView> clearQuota(
            @Body AppId appId, @Query("access_token") String accessToken);

    @POST("/cgi-bin/component/clear_quota")
    Call<CommonView> clearComponentQuota(
            @Body AppId appId, @Query("component_access_token") String componentAccessToken);

}
