package org.dreamcat.vendor.excel.content;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Create by tuke on 2020/7/21
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ExcelStringContent extends ExcelContent {
    private String value;

    @Override
    public String getText() {
        return value;
    }
}
