package org.dreamcat.vendor.excel.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.dreamcat.vendor.excel.content.IExcelContent;
import org.dreamcat.vendor.excel.style.ExcelFont;
import org.dreamcat.vendor.excel.style.ExcelHyperLink;
import org.dreamcat.vendor.excel.style.ExcelStyle;

/**
 * Create by tuke on 2020/7/21
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ExcelRichCell extends ExcelCell {
    protected ExcelStyle style;
    protected ExcelFont font;
    protected ExcelHyperLink hyperLink;

    public ExcelRichCell(
            IExcelContent content, int rowIndex, int columnIndex) {
        super(content, rowIndex, columnIndex);
    }

    public ExcelRichCell(
            IExcelContent content, int rowIndex, int columnIndex,
            int rowSpan, int columnSpan) {
        super(content, rowIndex, columnIndex, rowSpan, columnSpan);
    }
}
