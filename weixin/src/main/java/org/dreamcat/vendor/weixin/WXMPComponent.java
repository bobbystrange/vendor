package org.dreamcat.vendor.weixin;

import org.dreamcat.common.hc.gson.GsonUtil;
import org.dreamcat.common.hc.okhttp.RetrofitUtil;
import org.dreamcat.common.util.CollectionUtil;
import org.dreamcat.common.util.UrlUtil;
import org.dreamcat.vendor.weixin.api.IWXMPComponent;
import org.dreamcat.vendor.weixin.mp.WXMPRequest;
import org.dreamcat.vendor.weixin.mp.query.CodeQuery;
import org.dreamcat.vendor.weixin.mp.query.Oauth2AccessTokenQuery;
import org.dreamcat.vendor.weixin.mp.query.RefreshOauth2AccessTokenQuery;
import org.dreamcat.vendor.weixin.mp.query.SendTemplateMessageQuery;
import org.dreamcat.vendor.weixin.mp.view.GetAllPrivateTemplateView;
import org.dreamcat.vendor.weixin.mp.view.GetIndustryView;
import org.dreamcat.vendor.weixin.mp.view.Oauth2AccessTokenView;
import org.dreamcat.vendor.weixin.mp.view.SendTemplateMessageView;

import java.util.Map;

/**
 * Create by tuke on 2019-05-27
 */
public class WXMPComponent implements IWXMPComponent {
    private final WXMPRequest wxmpRequest;

    public WXMPComponent() {
        this.wxmpRequest = RetrofitUtil.getInstance4Json(
                WXMPRequest.BASE_URL).create(WXMPRequest.class);
    }

    @Override
    public String concatCodeUrl(CodeQuery query) {
        Map<String, Object> queryMap = GsonUtil.toMap(query);
        queryMap.put("response_type", "code");

        return UrlUtil.concatUrl(
                "https://open.weixin.qq.com/connect/oauth2/authorize",
                queryMap,
                "wechat_redirect",
                true
        );
    }

    @Override
    public Oauth2AccessTokenView getOauth2AccessToken(Oauth2AccessTokenQuery query, String componentAccessToken) {
        Map<String, String> queryMap = CollectionUtil.toProps(GsonUtil.toMap(query));
        queryMap.put("grant_type", "authorization_code");
        return RetrofitUtil.unwrap(wxmpRequest.getOauth2AccessToken(queryMap));
    }

    @Override
    public Oauth2AccessTokenView refreshOauth2AccessToken(RefreshOauth2AccessTokenQuery query, String componentAccessToken) {
        Map<String, String> queryMap = CollectionUtil.toProps(GsonUtil.toMap(query));
        queryMap.put("component_access_token", componentAccessToken);
        return RetrofitUtil.unwrap(wxmpRequest.getOauth2AccessToken(queryMap));
    }

    @Override
    public GetIndustryView getIndustry(String accessToken) {
        return RetrofitUtil.unwrap(wxmpRequest.getIndustry(accessToken));
    }

    @Override
    public GetAllPrivateTemplateView getAllPrivateTemplate(String accessToken) {
        return RetrofitUtil.unwrap(wxmpRequest.getAllPrivateTemplate(accessToken));
    }

    @Override
    public SendTemplateMessageView sendTemplateMessage(SendTemplateMessageQuery query, String accessToken) {
        return RetrofitUtil.unwrap(wxmpRequest.sendTemplateMessage(query, accessToken));
    }
}
