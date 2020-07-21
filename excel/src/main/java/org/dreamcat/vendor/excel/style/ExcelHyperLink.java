package org.dreamcat.vendor.excel.style;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Workbook;
import org.dreamcat.vendor.excel.core.ExcelCell;
import org.dreamcat.vendor.excel.core.ExcelRichCell;

/**
 * Create by tuke on 2020/7/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelHyperLink {
    private HyperlinkType type;
    private String address;
    private String label;

    public static ExcelHyperLink from(Hyperlink hyperlink) {
        var link = new ExcelHyperLink();
        link.setType(hyperlink.getType());
        link.setAddress(hyperlink.getAddress());
        link.setLabel(hyperlink.getLabel());
        return link;
    }

    public void fill(ExcelCell excelCell, Workbook workbook, Cell cell) {
        var creationHelper = workbook.getCreationHelper();
        var link = creationHelper.createHyperlink(type);
        link.setAddress(address);
        if (label != null) link.setLabel(label);

        if (excelCell instanceof ExcelRichCell) {
            var richCell = (ExcelRichCell) excelCell;
            link.setFirstRow(richCell.getRowIndex());
            link.setLastRow(richCell.getRowIndex() + richCell.getRowSpan() - 1);
            link.setFirstColumn(richCell.getColumnIndex());
            link.setLastColumn(richCell.getColumnIndex() + richCell.getColumnSpan() - 1);
        }
        cell.setHyperlink(link);
    }

}
