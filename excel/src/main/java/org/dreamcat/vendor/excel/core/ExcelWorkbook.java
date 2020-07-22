package org.dreamcat.vendor.excel.core;

import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dreamcat.vendor.excel.style.ExcelFont;
import org.dreamcat.vendor.excel.style.ExcelStyle;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Create by tuke on 2020/7/21
 */
@Data
public class ExcelWorkbook<T extends IExcelSheet> implements IExcelWorkbook<T> {
    private final List<T> sheets;
    private ExcelStyle defaultStyle;
    private ExcelFont defaultFont;

    public ExcelWorkbook() {
        this.sheets = new LinkedList<>();
    }

    public static ExcelWorkbook<ExcelSheet> from(File file) throws IOException, InvalidFormatException {
        return from(new XSSFWorkbook(file));
    }

    public static ExcelWorkbook<ExcelSheet> from2003(File file) throws IOException {
        return from(new HSSFWorkbook(new POIFSFileSystem(file, true)));
    }

    public static <T extends Workbook> ExcelWorkbook<ExcelSheet> from(T workbook) {
        var book = new ExcelWorkbook<ExcelSheet>();

        int sheetNum = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetNum; i++) {
            var sheet = workbook.getSheetAt(i);
            book.sheets.add(ExcelSheet.from(workbook, sheet));
        }
        return book;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return sheets.iterator();
    }

}
