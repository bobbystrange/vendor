package org.dreamcat.vendor.excel.core;

import lombok.Data;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.dreamcat.common.util.ObjectUtil;
import org.dreamcat.vendor.excel.content.ExcelContent;
import org.dreamcat.vendor.excel.style.ExcelFont;
import org.dreamcat.vendor.excel.style.ExcelHyperLink;
import org.dreamcat.vendor.excel.style.ExcelStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Create by tuke on 2020/7/20
 */
@Data
public class ExcelSheet {
    private final String name;
    private final List<ExcelCell> cells;

    public ExcelSheet(String name) {
        this.name = name;
        this.cells = new ArrayList<>();
    }

    public static ExcelSheet from(Workbook workbook, Sheet sheet) {
        var excelSheet = new ExcelSheet(sheet.getSheetName());

        var cells = excelSheet.getCells();
        int rowNum = sheet.getPhysicalNumberOfRows();
        Map<Integer, Map<Integer, ExcelCell>> cellMap = new TreeMap<>();
        for (int i = 0; i < rowNum; i++) {
            var row = sheet.getRow(i);
            if (row == null) continue;

            int start = row.getFirstCellNum();
            if (start == -1) continue;
            int end = row.getLastCellNum();

            for (int j = start; j < end; j++) {
                var cell = row.getCell(j);
                if (cell == null) continue;
                var content = ExcelContent.from(cell);

                ExcelCell excelCell = null;
                var hyperlink = cell.getHyperlink();
                var style = cell.getCellStyle();

                if (hyperlink != null || style != null) {
                    var richCell = new ExcelRichCell(content, i, j);
                    if (hyperlink != null) {
                        richCell.setHyperLink(ExcelHyperLink.from(hyperlink));
                    }
                    if (style != null) {
                        richCell.setStyle(ExcelStyle.from(style));
                        richCell.setFont(ExcelFont.from(workbook, style));
                    }

                    excelCell = richCell;
                }

                if (excelCell == null) {
                    excelCell = new ExcelCell(content, i, j);
                }

                cells.add(excelCell);
                cellMap.computeIfAbsent(i, it -> new TreeMap<>())
                        .put(j, excelCell);
            }
        }

        int numMergedRegions = sheet.getNumMergedRegions();
        if (numMergedRegions == 0) return excelSheet;

        for (var cell : cells) {
            var leftCell = getLeftCell(cell, cellMap);
            if (leftCell != null) {
                leftCell.setColumnSpan(cell.getColumnIndex() - leftCell.getColumnIndex());
            }
            var topCell = getTopCell(cell, cellMap);
            if (topCell != null) {
                topCell.setRowSpan(cell.getRowIndex() - topCell.getRowIndex());
            }
        }

        // Note merge region for the last cell
        if (!cells.isEmpty()) {
            var lastCell = cells.get(cells.size() - 1);
            var addresses = sheet.getMergedRegion(numMergedRegions - 1);
            int ri = addresses.getFirstRow();
            int ci = addresses.getFirstColumn();
            if (lastCell.getRowIndex() == ri &&
                    lastCell.getColumnIndex() == ci) {
                lastCell.setRowSpan(addresses.getLastRow() - ri);
                lastCell.setColumnSpan(addresses.getLastColumn() - ci);
            }
        }

        return excelSheet;
    }

    private static ExcelCell getLeftCell(ExcelCell cell, Map<Integer, Map<Integer, ExcelCell>> map) {
        int ri = cell.getRowIndex();
        int ci = cell.getColumnIndex();
        ExcelCell excelCell;
        while (--ci >= 0) {
            excelCell = map.getOrDefault(ri, Collections.emptyMap()).get(ci);
            if (excelCell != null) return excelCell;
        }
        return null;
    }

    private static ExcelCell getTopCell(ExcelCell cell, Map<Integer, Map<Integer, ExcelCell>> map) {
        int ri = cell.getRowIndex();
        int ci = cell.getColumnIndex();
        ExcelCell excelCell;
        while (--ri >= 0) {
            excelCell = map.getOrDefault(ri, Collections.emptyMap()).get(ci);
            if (excelCell != null) return excelCell;
        }
        return null;
    }

    public void fill(Workbook workbook, Sheet sheet, CellStyle defaultStyle, Font defaultFont) {
        if (ObjectUtil.isEmpty(cells)) return;

        for (var excelCell : cells) {
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
