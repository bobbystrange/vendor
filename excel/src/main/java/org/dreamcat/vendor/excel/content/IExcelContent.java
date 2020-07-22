package org.dreamcat.vendor.excel.content;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Create by tuke on 2020/7/21
 */
public interface IExcelContent {

    static IExcelContent from(Cell cell) {
        var type = cell.getCellType();
        return switch (type) {
            case STRING -> new ExcelStringContent(cell.getStringCellValue());
            case NUMERIC -> new ExcelNumericContent(cell.getNumericCellValue());
            case BOOLEAN -> new ExcelBooleanContent(cell.getBooleanCellValue());
            case FORMULA -> new ExcelFormulaContent(cell.getCellFormula());
            default -> new ExcelStringContent();
        };
    }

    void fill(Cell cell);

}
