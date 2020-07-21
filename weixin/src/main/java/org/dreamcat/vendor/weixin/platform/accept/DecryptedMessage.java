package org.dreamcat.vendor.weixin.platform.accept;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * Create by tuke on 2019-05-27
 */
@Data
@XStreamAlias("xml")
public class DecryptedMessage {
    @XStreamAlias("ToUserName")
    private String toUserName;

    @XStreamAlias("FromUserName")
    private String fromUserName;

    @XStreamAlias("CreateTime")
    private String createTime;

    @XStreamAlias("MsgType")
    private String messageType;

    @XStreamAlias("Content")
    private String content;

    @XStreamAlias("MsgId")
    private String messageId;
}
