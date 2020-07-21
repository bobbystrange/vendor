package org.dreamcat.vendor.weixin.platform.core;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Create by tuke on 2019-05-27
 */
@Data
public class BusinessInfo {
    @SerializedName("open_store")
    private int openStore;
    @SerializedName("open_scan")
    private int openScan;
    @SerializedName("open_pay")
    private int openPay;
    @SerializedName("open_card")
    private int openCard;
    @SerializedName("open_shake")
    private int openShake;
}
