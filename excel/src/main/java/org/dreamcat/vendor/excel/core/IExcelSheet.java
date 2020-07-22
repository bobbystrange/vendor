package org.dreamcat.vendor.excel.core;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Create by tuke on 2020/7/22
 */
public interface IExcelSheet extends Iterable<IExcelCell> {
    String getName();

    default void fill(Workbook workbook, Sheet sheet, CellStyle defaultStyle, Font defaultFont) {
        for (var excelCell : this) {
            int ri = excelCell.getRowIndex();
            int ci = excelCell.getColumnIndex();
            var cellContent = excelCell.getContent();
            var cellFont = excelCell.getFont();
            var cellStyle = excelCell.getStyle();
            var cellLink = excelCell.getHyperLink();

            if (excelCell.hasMergedRegion()) {
                int rs = excelCell.getRowSpan();
                int cs = excelCell.getColumnSpan();
                if (rs > 1 || cs > 1) {
                    sheet.addMergedRegion(new CellRangeAddress(
                            ri, ri + rs - 1, ci, ci + cs - 1));
                }
            }

            var row = sheet.getRow(ri);
            if (row == null) {
                row = sheet.createRow(ri);
            }
            var cell = row.createCell(ci);

            cellContent.fill(cell);
            if (cellLink != null) {
                cellLink.fill(excelCell, workbook, cell);
            }

            if (cellStyle == null && cellFont == null) {
                cell.setCellStyle(defaultStyle);
            } else {
                var style = workbook.createCellStyle();

                // cellStyle == null && cellFont != null
                if (cellStyle == null) {
                    var font = workbook.createFont();
                    cellFont.fill(font);
                    style.setFont(font);
                }
                // cellStyle != null
                else if (cellFont == null) {
                    cellStyle.fill(style, defaultFont);
                }
                // cellStyle != null && cellFont != null
                else {
                    var font = workbook.createFont();
                    cellFont.fill(font);
                    cellStyle.fill(style, font);
                }

                cell.setCellStyle(style);
            }
        }
    }
}
