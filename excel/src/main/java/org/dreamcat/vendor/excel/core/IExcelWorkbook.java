package org.dreamcat.vendor.excel.core;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dreamcat.vendor.excel.style.ExcelFont;
import org.dreamcat.vendor.excel.style.ExcelStyle;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Create by tuke on 2020/7/22
 */
public interface IExcelWorkbook<T extends IExcelSheet> extends Iterable<T> {

    ExcelStyle getDefaultStyle();

    ExcelFont getDefaultFont();

    default XSSFWorkbook toWorkbook() {
        return toWorkbook(new XSSFWorkbook());
    }

    default HSSFWorkbook toWorkbook2003() {
        return toWorkbook(new HSSFWorkbook());
    }

    default <W extends Workbook> W toWorkbook(W workbook) {
        var font = workbook.createFont();
        var defaultFont = getDefaultFont();
        if (defaultFont != null) defaultFont.fill(font);

        var style = workbook.createCellStyle();
        var defaultStyle = getDefaultStyle();
        if (defaultStyle != null) defaultStyle.fill(style, font);

        for (var excelSheet : this) {
            Sheet sheet = workbook.createSheet(excelSheet.getName());
            excelSheet.fill(workbook, sheet, style, font);
        }

        return workbook;
    }

    default void writeTo(String newFile) throws IOException {
        writeTo(new File(newFile));
    }

    default void writeTo(File newFile) throws IOException {
        try (var ostream = new FileOutputStream(newFile)) {
            writeTo(ostream);
        }
    }

    default void writeTo(OutputStream ostream) throws IOException {
        try (var workbook = toWorkbook()) {
            workbook.write(ostream);
        }
    }

    default void writeTo2003(String newFile) throws IOException {
        writeTo2003(new File(newFile));
    }

    default void writeTo2003(File newFile) throws IOException {
        try (var ostream = new FileOutputStream(newFile)) {
            writeTo2003(ostream);
        }
    }

    default void writeTo2003(OutputStream ostream) throws IOException {
        try (var workbook = toWorkbook2003()) {
            workbook.write(ostream);
        }
    }

    default byte[] toByteArray() throws IOException {
        try (var ostream = new ByteArrayOutputStream();
             var workbook = toWorkbook()) {
            workbook.write(ostream);
            return ostream.toByteArray();
        }
    }

    default byte[] toByteArray2003() throws IOException {
        try (var ostream = new ByteArrayOutputStream();
             var workbook = toWorkbook2003()) {
            workbook.write(ostream);
            return ostream.toByteArray();
        }
    }

}
