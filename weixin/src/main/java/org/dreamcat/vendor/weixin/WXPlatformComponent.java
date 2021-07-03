package org.dreamcat.vendor.weixin;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.dreamcat.common.hc.okhttp.OkHttpUtil;
import org.dreamcat.common.hc.okhttp.RetrofitUtil;
import org.dreamcat.common.hc.xstream.XStreamUtil;
import org.dreamcat.common.util.UrlUtil;
import org.dreamcat.common.x.bean.BeanMapUtil;
import org.dreamcat.vendor.weixin.api.IWXPlatformComponent;
import org.dreamcat.vendor.weixin.common.AppId;
import org.dreamcat.vendor.weixin.common.CommonView;
import org.dreamcat.vendor.weixin.platform.WXPlatformRequest;
import org.dreamcat.vendor.weixin.platform.accept.AuthorizationNotifyView;
import org.dreamcat.vendor.weixin.platform.core.ComponentAppPair;
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
import org.dreamcat.vendor.weixin.util.FastCopyUtil;
import org.dreamcat.vendor.weixin.util.aes.AesException;
import org.dreamcat.vendor.weixin.util.aes.WXBizMsgCrypt;

/**
 * Create by tuke on 2019-05-24
 */
@Slf4j
public class WXPlatformComponent extends ComponentAppPair implements IWXPlatformComponent {

    private final WXPlatformRequest wxJsonRequest;
    // 使用第三方平台申请时的接收消息的加密symmetric_key（也称为EncodingAESKey）来进行加密
    // EncodingAESKey长度固定为43个字符，从a-z,A-Z,0-9共62个字符中选取。
    // 建议公众账号保存当前的和上一次的EncodingAESKey，
    // 若当前EncodingAESKey解密失败，则尝试用上一次的EncodingAESKey的解密。
    // 回包时，用哪个Key解密成功，则用此Key加密对应的回包。
    private String token;
    private String encodingAesKey;

    public WXPlatformComponent() {
        this(OkHttpUtil.newClient());
    }

    public WXPlatformComponent(OkHttpClient client) {
        wxJsonRequest = RetrofitUtil.getInstance4Json(
                WXPlatformRequest.BASE_URL, client).create(WXPlatformRequest.class);
    }

    public WXPlatformComponent(
            String componentAppId, String componentAppSecret, String token, String encodingAesKey) {
        this();
        this.componentAppId = componentAppId;
        this.componentAppSecret = componentAppSecret;
        this.token = token;
        this.encodingAesKey = encodingAesKey;
    }

    @Override
    public AuthorizationNotifyView decryptMessage(
            String msgSignature, long timestamp, long nonce, String postData)
            throws AesException {
        WXBizMsgCrypt crypt = new WXBizMsgCrypt(token, encodingAesKey, componentAppId);
        String xml = crypt.decryptMsg(msgSignature, timestamp + "", nonce + "", postData);
        log.info("decrypted message:\t" + xml);
        return XStreamUtil.fromXML(xml, AuthorizationNotifyView.class);
    }

    @Override
    public ApiComponentTokenView getApiComponentToken(ApiComponentTokenQuery query) {
        FastCopyUtil.copy(this, query);
        return RetrofitUtil.unwrap(wxJsonRequest.getApiComponentToken(query));
    }

    @Override
    public ApiCreatePreauthcodeView getPreAuthCode(
            ApiCreatePreAuthCodeQuery query, String componentAccessToken) {
        FastCopyUtil.copy(this, query);
        return RetrofitUtil.unwrap(wxJsonRequest.getPreAuthCode(query, componentAccessToken));
    }

    @Override
    public String getLoginPageUrl(LoginPageQuery query) {
        return UrlUtil.concatUrl(
                WXPlatformRequest.COMPONENT_LOGIN_PAGE,
                BeanMapUtil.toMap(query));
    }

    @Override
    public String getBindComponentUrl(BindComponentQuery query) {
        return UrlUtil.concatUrl(
                WXPlatformRequest.COMPONENT_LOGIN_PAGE,
                BeanMapUtil.toMap(query));
    }

    @Override
    public ApiQueryAuthView getApiQueryAuth(ApiQueryAuthQuery query, String componentAccessToken) {
        FastCopyUtil.copy(this, query);
        return RetrofitUtil.unwrap(wxJsonRequest.getApiQueryAuth(query, componentAccessToken));
    }

    @Override
    public ApiAuthorizerTokenView getApiAuthorizerToken(ApiAuthorizerTokenQuery query, String componentAccessToken) {
        FastCopyUtil.copy(this, query);
        return RetrofitUtil.unwrap(wxJsonRequest.getApiAuthorizerToken(query, componentAccessToken));
    }

    @Override
    public ApiGetAuthorizerInfoView getApiGetAuthorizerInfo(ApiGetAuthorizerInfoQuery query,
            String componentAccessToken) {
        FastCopyUtil.copy(this, query);
        return RetrofitUtil.unwrap(wxJsonRequest.getApiGetAuthorizerInfo(query, componentAccessToken));
    }

    @Override
    public ApiGetAuthorizerOptionView getApiGetAuthorizerOption(ApiGetAuthorizerOptionQuery query,
            String componentAccessToken) {
        FastCopyUtil.copy(this, query);
        return RetrofitUtil.unwrap(wxJsonRequest.getApiGetAuthorizerOption(query, componentAccessToken));
    }

    @Override
    public ApiSetAuthorizerOptionView getApiSetAuthorizerOption(ApiSetAuthorizerOptionQuery query,
            String componentAccessToken) {
        FastCopyUtil.copy(this, query);
        return RetrofitUtil.unwrap(wxJsonRequest.getApiSetAuthorizerOption(query, componentAccessToken));
    }

    @Override
    public CommonView clearQuota(String appId, String accessToken) {
        return RetrofitUtil.unwrap(wxJsonRequest.clearQuota(new AppId(appId), accessToken));
    }

    @Override
    public CommonView clearComponentQuota(String appId, String componentAccessToken) {
        return RetrofitUtil.unwrap(wxJsonRequest.clearQuota(new AppId(appId), componentAccessToken));
    }

}
