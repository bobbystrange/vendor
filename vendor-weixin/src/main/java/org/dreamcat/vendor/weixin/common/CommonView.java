package org.dreamcat.vendor.weixin.common;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * Create by tuke on 2019-05-27
 */
@Getter
@Setter
public class CommonView {
    //  { "errcode":0, "errmsg":"ok" }
    @SerializedName("errcode")
    private int errorCode;
    @SerializedName("errmsg")
    private String errorMessage;
}
