package org.dreamcat.vendor.weixin.mp.view;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

/**
 * Create by tuke on 2019-05-28
 */
public class GetAllPrivateTemplateView {

    @SerializedName("template_list")
    private List<Template> templates;

    @Data
    public static class Template {

        @SerializedName("template_id")
        private String templateId;
        private String title;
        @SerializedName("primary_industry")
        private String primaryIndustry;
        // 模板所属行业的二级行业
        @SerializedName("deputy_industry")
        private String deputyIndustry;
        // 模板内容
        private String content;
        private String example;
    }
}
