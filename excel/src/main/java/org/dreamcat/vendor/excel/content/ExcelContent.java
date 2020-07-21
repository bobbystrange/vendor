package org.dreamcat.vendor.excel.content;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Create by tuke on 2020/7/21
 */
public abstract class ExcelContent {
    public static ExcelContent from(Cell cell) {
        var type = cell.getCellType();
        return switch (type) {
            case BLANK, STRING -> new ExcelStringContent(cell.getStringCellValue());
            case BOOLEAN -> new ExcelBooleanContent(cell.getBooleanCellValue());
            case NUMERIC -> new ExcelNumericContent(cell.getNumericCellValue());
            case FORMULA -> new ExcelFormulaContent(cell.getCellFormula());
            default -> new ExcelStringContent();
        };
    }

    abstract String getText();

    public void fill(Cell cell) {
        cell.setCellValue(getText());
    }

    @Override
    public String toString() {
        return getText();
    }
}
