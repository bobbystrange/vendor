package org.dreamcat.vendor.weixin.util;

import org.dreamcat.vendor.weixin.platform.core.ComponentAppPair;
import org.dreamcat.vendor.weixin.platform.core.ComponentAppid;

/**
 * Create by tuke on 2019-05-27
 */
public class FastCopyUtil {

    public static void copy(ComponentAppid appid1, ComponentAppid appid2) {
        appid2.setComponentAppId(appid1.getComponentAppId());
    }

    public static void copy(ComponentAppPair pair1, ComponentAppPair pair2) {
        pair2.setComponentAppId(pair1.getComponentAppId());
        pair2.setComponentAppSecret(pair1.getComponentAppSecret());
    }
}
