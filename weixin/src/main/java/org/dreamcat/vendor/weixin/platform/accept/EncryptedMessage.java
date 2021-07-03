package org.dreamcat.vendor.weixin.platform.accept;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.dreamcat.common.hc.xstream.XStreamCDATA;

/**
 * Create by tuke on 2019-05-27
 */
@Data
@XStreamAlias("xml")
public class EncryptedMessage {

    // 为公众号的原始ID
    @XStreamCDATA
    @XStreamAlias("ToUserName")
    private String toUserName;
    // 微信服务器发送给服务自身的事件推送（如取消授权通知，Ticket推送等）。
    // 此时，消息XML体中没有ToUserName字段，而是AppId字段，即公众号服务的AppId
    @XStreamAlias("AppId")
    private String appId;
    @XStreamAlias("Encrypt")
    private String encrypt;

    // todo
    // https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277
}
