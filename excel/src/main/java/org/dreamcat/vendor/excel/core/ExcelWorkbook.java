package org.dreamcat.vendor.excel.core;

import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dreamcat.common.util.ObjectUtil;
import org.dreamcat.vendor.excel.style.ExcelFont;
import org.dreamcat.vendor.excel.style.ExcelStyle;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Create by tuke on 2020/7/21
 */
@Data
public class ExcelWorkbook {
    private final List<ExcelSheet> sheets;
    private ExcelStyle defaultStyle;
    private ExcelFont defaultFont;

    public ExcelWorkbook() {
        this.sheets = new LinkedList<>();
    }

    public static ExcelWorkbook from(File file) throws IOException, InvalidFormatException {
        return from(new XSSFWorkbook(file));
    }

    public static ExcelWorkbook from2003(File file) throws IOException, InvalidFormatException {
        return from(new HSSFWorkbook(new POIFSFileSystem(file, true)));
    }

    public static <T extends Workbook> ExcelWorkbook from(T workbook) {
        var book = new ExcelWorkbook();

        int sheetNum = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetNum; i++) {
            var sheet = workbook.getSheetAt(i);
            book.sheets.add(ExcelSheet.from(workbook, sheet));
        }
        return book;
    }

    public void writeTo(String newFile) throws IOException {
        writeTo(new File(newFile));
    }

    public void writeTo(File newFile) throws IOException {
        try (var ostream = new FileOutputStream(newFile)) {
            writeTo(ostream);
        }
    }

    public void writeTo(OutputStream ostream) throws IOException {
        try (var workbook = toWorkbook()) {
            workbook.write(ostream);
        }
    }

    public void writeTo2003(String newFile) throws IOException {
        writeTo2003(new File(newFile));
    }

    public void writeTo2003(File newFile) throws IOException {
        try (var ostream = new FileOutputStream(newFile)) {
            writeTo2003(ostream);
        }
    }

    public void writeTo2003(OutputStream ostream) throws IOException {
        try (var workbook = toWorkbook2003()) {
            workbook.write(ostream);
        }
    }

    public byte[] toByteArray() throws IOException {
        try (var ostream = new ByteArrayOutputStream();
             var workbook = toWorkbook()) {
            workbook.write(ostream);
            return ostream.toByteArray();
        }
    }

    public byte[] toByteArray2003() throws IOException {
        try (var ostream = new ByteArrayOutputStream();
             var workbook = toWorkbook2003()) {
            workbook.write(ostream);
            return ostream.toByteArray();
        }
    }

    public XSSFWorkbook toWorkbook() {
        return toWorkbook(new XSSFWorkbook());
    }

    public HSSFWorkbook toWorkbook2003() {
        return toWorkbook(new HSSFWorkbook());
    }

    private <T extends Workbook> T toWorkbook(T workbook) {
        if (ObjectUtil.isEmpty(sheets)) return workbook;

        var font = workbook.createFont();
        if (defaultFont != null) defaultFont.fill(font);

        var style = workbook.createCellStyle();
        if (defaultStyle != null) defaultStyle.fill(style, font);

        for (var excelSheet : sheets) {
            Sheet sheet = workbook.createSheet(excelSheet.getName());
            excelSheet.fill(workbook, sheet, style, font);
        }

        return workbook;
    }

}
