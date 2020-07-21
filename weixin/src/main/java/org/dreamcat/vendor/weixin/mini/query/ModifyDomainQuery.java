package org.dreamcat.vendor.weixin.mini.query;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * Create by tuke on 2019-05-28
 */
@Data
public class ModifyDomainQuery {
    // add添加, delete删除, set覆盖, get获取。当参数是get时不需要填四个域名字段
    private String action;
    @SerializedName("requestdomain")
    private List<String> requestDomains;
    @SerializedName("wsrequestdomain")
    private List<String> wsrequestDomains;
    @SerializedName("uploaddomain")
    private List<String> uploadDomains;
    @SerializedName("downloaddomain")
    private List<String> downloadDomains;
}
