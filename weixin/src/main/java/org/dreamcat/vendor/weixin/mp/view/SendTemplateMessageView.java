package org.dreamcat.vendor.weixin.mp.view;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.dreamcat.vendor.weixin.common.CommonView;

/**
 * Create by tuke on 2019-05-28
 */
@Getter
@Setter
public class SendTemplateMessageView extends CommonView {

    @SerializedName("msgid")
    private long messageId;
}
