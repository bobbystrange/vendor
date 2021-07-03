package org.dreamcat.vendor.weixin.mp.query;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Create by tuke on 2019-05-28
 */
@Data
public class SendTemplateMessageQuery {

    // 接收者openid
    @SerializedName("touser")
    private String toUser;
    @SerializedName("template_id")
    private String templateId;
    // 模板跳转链接（海外帐号没有跳转能力）
    private String url;
    // 跳小程序所需数据，不需跳小程序可不用传该数据
    @SerializedName("miniprogram")
    private MiniProgram miniProgram;
    // 模板数据
    private Data data;
    // 模板内容字体颜色，不填默认为黑色
    private String color;

    @Data
    public static class MiniProgram {

        @SerializedName("appid")
        private String appId;
        // 所需跳转到小程序的具体页面路径，支持带参数,
        // （示例index?foo=bar），要求该小程序已发布，暂不支持小游戏
        @SerializedName("pagepath")
        private String pagePath;
    }


}
