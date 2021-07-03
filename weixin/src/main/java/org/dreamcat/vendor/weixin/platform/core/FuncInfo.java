package org.dreamcat.vendor.weixin.platform.core;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Create by tuke on 2019-05-27
 */
@Data
public class FuncInfo {

    @SerializedName("funcscope_category")
    private FuncScopeCategory funcScopeCategory;

    @Data
    public static class FuncScopeCategory {

        public int id;
    }
}
