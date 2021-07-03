package org.dreamcat.vendor.weixin.api;

import org.dreamcat.vendor.weixin.common.CommonView;
import org.dreamcat.vendor.weixin.platform.accept.AuthorizationNotifyParameter;
import org.dreamcat.vendor.weixin.platform.accept.AuthorizationNotifyView;
import org.dreamcat.vendor.weixin.platform.query.ApiAuthorizerTokenQuery;
import org.dreamcat.vendor.weixin.platform.query.ApiComponentTokenQuery;
import org.dreamcat.vendor.weixin.platform.query.ApiCreatePreAuthCodeQuery;
import org.dreamcat.vendor.weixin.platform.query.ApiGetAuthorizerInfoQuery;
import org.dreamcat.vendor.weixin.platform.query.ApiGetAuthorizerOptionQuery;
import org.dreamcat.vendor.weixin.platform.query.ApiQueryAuthQuery;
import org.dreamcat.vendor.weixin.platform.query.ApiSetAuthorizerOptionQuery;
import org.dreamcat.vendor.weixin.platform.query.BindComponentQuery;
import org.dreamcat.vendor.weixin.platform.query.LoginPageQuery;
import org.dreamcat.vendor.weixin.platform.view.ApiAuthorizerTokenView;
import org.dreamcat.vendor.weixin.platform.view.ApiComponentTokenView;
import org.dreamcat.vendor.weixin.platform.view.ApiCreatePreauthcodeView;
import org.dreamcat.vendor.weixin.platform.view.ApiGetAuthorizerInfoView;
import org.dreamcat.vendor.weixin.platform.view.ApiGetAuthorizerOptionView;
import org.dreamcat.vendor.weixin.platform.view.ApiQueryAuthView;
import org.dreamcat.vendor.weixin.platform.view.ApiSetAuthorizerOptionView;
import org.dreamcat.vendor.weixin.util.aes.AesException;

/**
 * Create by tuke on 2019-05-24
 * <p>
 * 推送 component_verify_ticket 和授权相关通知
 *
 * @see org.dreamcat.vendor.weixin.platform.accept.AuthorizationNotifyView
 */
public interface IWXPlatformComponent {

    // 1、推送component_verify_ticket
    // 微信服务器 每隔10分钟会向第三方的消息接收地址推送一次component_verify_ticket
    // 第三方平台方在收到ticket推送后也需进行解密，接收到后必须直接返回字符串success
    AuthorizationNotifyView decryptMessage(
            String msgSignature, long timestamp, long nonce, String postData) throws AesException;

    default AuthorizationNotifyView decryptMessage(
            AuthorizationNotifyParameter parameter, String postData) throws AesException {
        return decryptMessage(parameter.getMessageSignature(), parameter.getTimestamp(), parameter.getNonce(),
                postData);
    }

    // 2、获取第三方平台component_access_token
    // POST https://api.weixin.qq.com/cgi-bin/component/api_component_token
    ApiComponentTokenView getApiComponentToken(ApiComponentTokenQuery query);

    // 3、获取预授权码pre_auth_code
    // POST https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=xxx
    ApiCreatePreauthcodeView getPreAuthCode(
            ApiCreatePreAuthCodeQuery query, String componentAccessToken);

    // 根据预授权码获取服务号或小程序的授权码，用于后续步骤的接口调用
    // 程序或者公众号授权给第三方平台
    // 授权流程完成后，授权页会自动跳转进入回调URI，并在URL参数中返回授权码和过期时间
    // redirect_url?auth_code=xxx&expires_in=600

    // 方式一：授权注册页面扫码授权
    // https://mp.weixin.qq.com/cgi-bin/componentloginpage?component\_appid=xxxx&pre\_auth\_code=xxxxx&redirect\_uri=xxxx&auth\_type=xxx
    String getLoginPageUrl(LoginPageQuery query);

    // 方式二：点击移动端链接快速授权
    // https://mp.weixin.qq.com/safe/bindcomponent?action=bindcomponent&auth_type=3&no_scan=1&component_appid=xxxx&pre_auth_code=xxxxx&redirect_uri=xxxx&auth_type=xxx&biz_appid=xxxx#wechat_redirect
    String getBindComponentUrl(BindComponentQuery query);

    // 4、使用授权码换取公众号或小程序的接口调用凭据和授权信息
    // POST https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=xxxx
    ApiQueryAuthView getApiQueryAuth(ApiQueryAuthQuery query, String componentAccessToken);

    // 5、获取（刷新）授权公众号或小程序的接口调用凭据（令牌）
    // POST https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=xxxxx
    ApiAuthorizerTokenView getApiAuthorizerToken(ApiAuthorizerTokenQuery query, String componentAccessToken);

    // 6、获取授权方的帐号基本信息
    // POST https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token=xxxx
    ApiGetAuthorizerInfoView getApiGetAuthorizerInfo(ApiGetAuthorizerInfoQuery query, String componentAccessToken);

    // 7、获取授权方的选项设置信息
    // POST https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_option?component_access_token=xxxx
    ApiGetAuthorizerOptionView getApiGetAuthorizerOption(ApiGetAuthorizerOptionQuery query,
            String componentAccessToken);

    // 8、设置授权方的选项信息
    // POST https://api.weixin.qq.com/cgi-bin/component/api_set_authorizer_option?component_access_token=xxxx
    // location_report(地理位置上报选项)	0无上报, 1进入会话时上报, 2每5s上报
    // voice_recognize（语音识别开关选项）	0关闭语音识别, 1开启语音识别
    // customer_service（多客服开关选项）	0关闭多客服, 1开启多客服
    ApiSetAuthorizerOptionView getApiSetAuthorizerOption(ApiSetAuthorizerOptionQuery query,
            String componentAccessToken);

    // 9、推送授权相关通知
    // 当公众号对第三方平台进行授权、取消授权、更新授权后，
    // 微信服务器会向第三方平台方的授权事件接收URL（创建第三方平台时填写）推送相关通知。
    // 第三方平台方在收到授权相关通知后也需进行解密，接收到后之后只需直接返回字符串success


    // 代公众号调用接口调用次数清零API的权限
    // 每个公众号每个月有10次清零机会，包括在微信公众平台上的清零以及调用API进行清零
    // POST https://api.weixin.qq.com/cgi-bin/clear_quota?access_token=ACCESS_TOKEN
    CommonView clearQuota(String appId, String accessToken);

    // 第三方平台对其所有API调用次数清零（只与第三方平台相关，与公众号无关，接口如api_component_token）
    // POST https://api.weixin.qq.com/cgi-bin/component/clear_quota?component_access_token=COMPONENT_ACCESS_TOKEN
    CommonView clearComponentQuota(String appId, String componentAccessToken);


}
