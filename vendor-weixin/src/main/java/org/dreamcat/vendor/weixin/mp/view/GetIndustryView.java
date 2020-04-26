package org.dreamcat.vendor.weixin.mp.view;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Create by tuke on 2019-05-28
 */
@Data
public class GetIndustryView {
    @SerializedName("primary_industry")
    private Industry primaryIndustry;
    @SerializedName("secondary_industry")
    private Industry secondaryIndustry;

    @Data
    public static class Industry {
        private String firstClass;
        private String secondClass;
    }
}
