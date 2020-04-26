package org.dreamcat.vendor.weixin.platform.accept;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dreamcat.common.hc.xstream.XStreamCDATA;

/**
 * Create by tuke on 2019-05-27
 */
@Data
@XStreamAlias("xml")
public class AuthorizationNotifyView {
    @XStreamCDATA
    @XStreamAlias("AppId")
    public String appId;
    @XStreamAlias("CreateTime")
    public long createTime;
    @XStreamCDATA
    @XStreamAlias("InfoType")
    public String infoType;
    @XStreamCDATA
    @XStreamAlias("AuthorizerAppid")
    private String authorizerAppId;

    // 取消授权无以下字段

    // 授权更新通知，授权成功通知，则有以下四项
    @XStreamCDATA
    @XStreamAlias("AuthorizationCode")
    private String authorizationCode;
    @XStreamCDATA
    @XStreamAlias("AuthorizationCodeExpiredTime")
    private Long authorizationCodeExpiredTime;
    @XStreamCDATA
    @XStreamAlias("PreAuthCode")
    private String preAuthCode;

    // InfoType 为 component_verify_ticket，则有以下一项
    @XStreamCDATA
    @XStreamAlias("ComponentVerifyTicket")
    private String componentVerifyTicket;

    @RequiredArgsConstructor
    public enum InfoType {
        COMPONENT_VERIFY_TICKET("component_verify_ticket"),
        UNAUTHORIZED("unauthorized"),
        UPDATE_AUTHORIZED("updateauthorized"),
        AUTHORIZED("authorized");

        @Getter
        private final String value;

        @Override
        public String toString() {
            return value;
        }
    }


}
