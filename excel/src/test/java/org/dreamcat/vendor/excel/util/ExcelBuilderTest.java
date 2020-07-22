package org.dreamcat.vendor.excel.util;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Font;
import org.junit.Test;

import static org.dreamcat.vendor.excel.util.ExcelBuilder.sheet;
import static org.dreamcat.vendor.excel.util.ExcelBuilder.term;

/**
 * Create by tuke on 2020/7/22
 */
public class ExcelBuilderTest {
    @Test
    public void exportExcelBuilder() throws Exception {
        var book = sheet("sheet1")
                .cell(term("A1:B2"), 0, 0, 2, 2)
                .richCell(term("C1:C2"), 0, 2, 2, 1)
                .bold().height(365).underline().finish()
                .richCell(term("D1:D2"), 0, 3, 2, 1)
                .italic().color(Font.COLOR_RED).richStyle().borderBottom(BorderStyle.DOUBLE).finish().finish()
                .cell(term("A1:B2"), 2, 0, 1, 2)
                .cell(term("C3"), 2, 2)
                .cell(term("D3"), 2, 3)
                .cell(term("A4:B4"), 3, 0, 1, 2)
                .cell(term("C4"), 3, 2)
                .cell(term("D4"), 3, 3)
                .finish();
        book.writeTo("/Users/tuke/Downloads/book.xlsx");
    }
}
