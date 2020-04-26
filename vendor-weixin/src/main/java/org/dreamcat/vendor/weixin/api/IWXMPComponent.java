package org.dreamcat.vendor.weixin.api;

import org.dreamcat.vendor.weixin.mp.query.CodeQuery;
import org.dreamcat.vendor.weixin.mp.query.Oauth2AccessTokenQuery;
import org.dreamcat.vendor.weixin.mp.query.RefreshOauth2AccessTokenQuery;
import org.dreamcat.vendor.weixin.mp.query.SendTemplateMessageQuery;
import org.dreamcat.vendor.weixin.mp.view.GetAllPrivateTemplateView;
import org.dreamcat.vendor.weixin.mp.view.GetIndustryView;
import org.dreamcat.vendor.weixin.mp.view.Oauth2AccessTokenView;
import org.dreamcat.vendor.weixin.mp.view.SendTemplateMessageView;

/**
 * Create by tuke on 2019-05-27
 */
public interface IWXMPComponent {

    // 代公众号发起网页授权
    // 第一步：请求CODE
    // 在确保微信公众账号拥有授权作用域（scope参数）的权限的前提下
    // （一般而言，已微信认证的服务号拥有snsapi_base和snsapi_userinfo），使用微信客户端打开以下链接
    // https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE&component_appid=component_appid#wechat_redirect
    // 用户允许授权后，将会重定向到redirect_uri的网址上，并且带上code, state以及appid
    //redirect_uri?code=CODE&state=STATE&appid=APPID
    // 若用户禁止授权，则重定向后不会带上code参数，仅会带上state参数
    //redirect_uri?state=STATE
    String getCodeUrl(CodeQuery query);

    // 第二步：通过code换取access_token
    // 由于安全方面的考虑，对访问该链接的客户端有IP白名单的要求
    // https://api.weixin.qq.com/sns/oauth2/component/access_token?appid=APPID&code=CODE&grant_type=authorization_code&component_appid=COMPONENT_APPID&component_access_token=COMPONENT_ACCESS_TOKENhttps://api.weixin.qq.com/sns/oauth2/component/access_token?appid=APPID&code=CODE&grant_type=authorization_code&component_appid=COMPONENT_APPID&component_access_token=COMPONENT_ACCESS_TOKEN
    Oauth2AccessTokenView getOauth2AccessToken(Oauth2AccessTokenQuery query, String componentAccessToken);

    // 第三步：刷新access_token（如果需要）
    // 由于access_token拥有较短的有效期，当access_token超时后，可以使用refresh_token进行刷新，
    // refresh_token拥有较长的有效期（30天），当refresh_token失效的后，需要用户重新授权
    // https://api.weixin.qq.com/sns/oauth2/component/refresh_token?appid=APPID&grant_type=refresh_token&component_appid=COMPONENT_APPID&component_access_token=COMPONENT_ACCESS_TOKEN&refresh_token=REFRESH_TOKEN
    Oauth2AccessTokenView refreshOauth2AccessToken(RefreshOauth2AccessTokenQuery query, String componentAccessToken);

    // 模板消息接口
    // 获取帐号设置的行业信息。可登录微信公众平台，在公众号后台中查看行业信息
    // GET https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN
    GetIndustryView getIndustry(String accessToken);

    // GET https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN
    // 获取已添加至帐号下所有模板列表，可在微信公众平台后台中查看模板列表信息
    GetAllPrivateTemplateView getAllPrivateTemplate(String accessToken);

    // 发送模板消息
    // POST https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN
    SendTemplateMessageView sendTemplateMessage(SendTemplateMessageQuery query, String accessToken);

}
