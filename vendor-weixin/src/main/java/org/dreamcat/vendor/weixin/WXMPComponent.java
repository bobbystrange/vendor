package org.dreamcat.vendor.weixin;

import com.google.gson.annotations.SerializedName;
import org.dreamcat.common.bean.BeanMapUtil;
import org.dreamcat.common.hc.okhttp.RetrofitUtil;
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

    private WXMPRequest wxmpRequest;

    @Override
    public String getCodeUrl(CodeQuery query) {
        Map<String, String> queryMap = BeanMapUtil.toProps(query, SerializedName.class);
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
        Map<String, String> queryMap = BeanMapUtil.toProps(query);
        queryMap.put("grant_type", "authorization_code");
        return RetrofitUtil.unwrap(wxmpRequest.getOauth2AccessToken(queryMap));
    }

    @Override
    public Oauth2AccessTokenView refreshOauth2AccessToken(RefreshOauth2AccessTokenQuery query, String componentAccessToken) {
        Map<String, String> queryMap = BeanMapUtil.toProps(query, SerializedName.class);
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
