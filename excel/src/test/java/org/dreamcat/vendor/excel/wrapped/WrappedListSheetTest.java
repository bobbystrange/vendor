package org.dreamcat.vendor.excel.wrapped;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dreamcat.vendor.excel.core.ExcelWorkbook;
import org.junit.Test;

import java.util.Date;

import static org.dreamcat.common.util.RandomUtil.*;

/**
 * Create by tuke on 2020/7/22
 */
public class WrappedListSheetTest {
    @Test
    public void exportWrappedListSheet() throws Exception {
        var sheet = new WrappedListSheet<Pojo>("Sheet One");
        for (int i = 0; i < 1000; i++) {
            sheet.getList().add(new Pojo(randi(10), rand(), null, new Date(), choose72(6)));
            if (randi(100) == 1) break;
        }

        var book = new ExcelWorkbook<WrappedListSheet<Pojo>>();
        book.getSheets().add(sheet);
        book.writeTo("/Users/tuke/Downloads/book.xlsx");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Pojo {
        int a;
        double b;
        Long c;
        Date d;
        String s;
    }
}
