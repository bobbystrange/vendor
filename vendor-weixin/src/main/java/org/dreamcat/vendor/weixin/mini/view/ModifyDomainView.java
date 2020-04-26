package org.dreamcat.vendor.weixin.mini.view;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.dreamcat.vendor.weixin.common.CommonView;

import java.util.List;

/**
 * Create by tuke on 2019-05-28
 */
@Getter
@Setter
public class ModifyDomainView extends CommonView {

    @SerializedName("requestdomain")
    private List<String> requestDomains;
    @SerializedName("wsrequestdomain")
    private List<String> wsrequestDomains;
    @SerializedName("uploaddomain")
    private List<String> uploadDomains;
    @SerializedName("downloaddomain")
    private List<String> downloadDomains;
}
