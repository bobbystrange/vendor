package org.dreamcat.vendor.excel.util;

import org.dreamcat.vendor.excel.annotation.XlsSheet;
import org.dreamcat.vendor.excel.core.IExcelCell;

/**
 * Create by tuke on 2020/7/22
 */
public class XlsBuilder {

    public static <T> IExcelCell parse(Class<T> clazz) {
        var annotations = clazz.getDeclaredAnnotations();
        for (var annotation : annotations) {
            if (annotation instanceof XlsSheet) {
                var xlsSheet = (XlsSheet) annotation;

            }
        }
        return null;

    }
}
