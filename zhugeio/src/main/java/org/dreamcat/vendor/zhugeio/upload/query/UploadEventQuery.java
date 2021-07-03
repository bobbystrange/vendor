package org.dreamcat.vendor.zhugeio.upload.query;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Create by tuke on 2020/10/22
 */
@Data
public class UploadEventQuery {

    @SerializedName("ak")
    private String appKey;
    @SerializedName("dt")
    private String dataType;
    /**
     * 平台信息: js为js、android为and、iOS为ios (必填，字符串)
     */
    @SerializedName("pl")
    private String platform = Platform.JS.getValue();
    /**
     * 实时调试: 1为开启实时调试, 0或缺少此字段为关闭(非必填，数字)
     */
    private int debug = 0;
    /**
     * ip信息，会解析出地域信息(非必填，字符串)
     */
    private String ip;
    @SerializedName("pr")
    private PushRequst pushRequst;
    /**
     * dt为 evt 时，设备ID(匿名用户必填，字符串)
     */
    @SerializedName("usr")
    private User uesr;

    @Data
    public static class PushRequst {

        /**
         * 事件时间(unixtime)(必填,单位是毫秒，数字)
         */
        @SerializedName("$ct")
        private Long ct;
        /**
         * 事件名称(必填，字符串)
         */
        @SerializedName("$eid")
        private String eid;
        /**
         * 用户id(实名用户必填，字符串)
         */
        @SerializedName("$cuid")
        private String cuid;

        /// 非必填

        /**
         * 会话ID(非必填，数字)
         */
        @SerializedName("$sid")
        private String sid;
        /**
         * 来源域名（非必填，字符串）
         */
        @SerializedName("$referrer_domain")
        private String referrer_domain;
        /**
         * 当前url(非必填，字符串)
         */
        @SerializedName("$url")
        private String url;
        /**
         * 来源网址(非必填，字符串)
         */
        @SerializedName("$ref")
        private String ref;
        /**
         * 版本(非必填，字符串)
         */
        @SerializedName("$vn")
        private String vn;
        /**
         * 渠道(非必填，字符串)
         */
        @SerializedName("$cn")
        private String cn;
        /**
         * 运营商(非必填，数字，见页尾参考值)
         */
        @SerializedName("$cr")
        private String cr;
        /**
         * 系统(非必填，字符串)
         */
        @SerializedName("$os")
        private String os;
        /**
         * 系统版本(非必填，数字)
         */
        @SerializedName("$ov")
        private String ov;

        /// dt 为 abp 时必填

        /**
         * 商品价格(必填，数值型属性不要带引号)
         */
        @SerializedName("$price")
        private Double price;
        /**
         * 商品ID(必填，数字、字符串)
         */
        @SerializedName("$productID")
        private String productId;
        /**
         * 商品数量(必填，数值型属性不要带引号)
         */
        @SerializedName("$productQuantity")
        private Long productQuantity;
        /**
         * 商品名称(必填，数字、字符串)
         */
        @SerializedName("$revenueType")
        private Double revenueType;

        /// dt 为 pl 时必填

        /**
         * 设备ID(必填，字符串)
         */
        private User usr;
    }

    @Data
    public static class User {

        private String did;
    }

    @Getter
    @RequiredArgsConstructor
    public enum DataType {
        // 事件行为数据
        EVT("evt"),
        // 用户信息，identify（必填）
        USR("usr"),
        // 设备信息完善
        PL("pl"),
        // 收入分析数据采集
        ABP("abp");
        private final String value;

        @Override
        public String toString() {
            return value;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum Platform {
        JS("js"),
        Android("and"),
        IOS("ios");
        private final String value;

        @Override
        public String toString() {
            return value;
        }
    }
}
