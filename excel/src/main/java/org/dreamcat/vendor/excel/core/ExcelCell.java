package org.dreamcat.vendor.excel.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dreamcat.vendor.excel.content.ExcelContent;
import org.dreamcat.vendor.excel.style.ExcelFont;
import org.dreamcat.vendor.excel.style.ExcelHyperLink;
import org.dreamcat.vendor.excel.style.ExcelStyle;

/**
 * Create by tuke on 2020/7/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelCell {
    protected ExcelContent content;
    protected int rowIndex;
    protected int columnIndex;
    protected int rowSpan;
    protected int columnSpan;

    public ExcelCell(ExcelContent content, int rowIndex, int columnIndex) {
        this(content, rowIndex, columnIndex, 1, 1);
    }

    public ExcelStyle getStyle() {
        return null;
    }

    public ExcelFont getFont() {
        return null;
    }

    public ExcelHyperLink getHyperLink() {
        return null;
    }

    public boolean hasMergedRegion() {
        return rowSpan > 1 || columnSpan > 1;
    }

}
