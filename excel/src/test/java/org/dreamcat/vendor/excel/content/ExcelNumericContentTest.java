package org.dreamcat.vendor.excel.content;

import org.junit.Test;

/**
 * Create by tuke on 2020/7/21
 */
public class ExcelNumericContentTest {

    @Test
    public void formatExcelNumericContent() {
        double n;
        for (int i = 1; i <= 102400; i += 16) {
            System.out.printf("%.8g\n", (double) i);
            n = Math.random() * i * i;
            System.out.printf("%.8g\n", n);
            n = Math.random() * i * i;
            System.out.printf("%.8g\n", n);
        }


    }
}
