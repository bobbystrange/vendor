package org.dreamcat.vendor.excel.core;

import org.dreamcat.vendor.excel.content.IExcelContent;
import org.dreamcat.vendor.excel.style.ExcelFont;
import org.dreamcat.vendor.excel.style.ExcelHyperLink;
import org.dreamcat.vendor.excel.style.ExcelStyle;

/**
 * Create by tuke on 2020/7/22
 */
public interface IExcelCell {

    int getRowIndex();

    int getColumnIndex();

    default int getRowSpan() {
        return 1;
    }

    default void setRowSpan(int i) {
        throw new UnsupportedOperationException();
    }

    default int getColumnSpan() {
        return 1;
    }

    default void setColumnSpan(int i) {
        throw new UnsupportedOperationException();
    }

    IExcelContent getContent();

    default ExcelStyle getStyle() {
        return null;
    }

    default ExcelFont getFont() {
        return null;
    }

    default ExcelHyperLink getHyperLink() {
        return null;
    }

    default boolean hasMergedRegion() {
        return getRowSpan() > 1 || getColumnSpan() > 1;
    }

}
