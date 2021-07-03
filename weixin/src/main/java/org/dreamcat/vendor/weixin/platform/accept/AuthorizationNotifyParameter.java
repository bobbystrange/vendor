package org.dreamcat.vendor.weixin.platform.accept;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Create by tuke on 2019-05-27
 */
@Data
public class AuthorizationNotifyParameter {

    public long timestamp;
    public long nonce;
    @SerializedName("encrypt_type")
    public String encryptType = "aes";
    @SerializedName("msg_signature")
    public String messageSignature;
}
