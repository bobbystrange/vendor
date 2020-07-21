package org.dreamcat.vendor.excel.content;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Create by tuke on 2020/7/21
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ExcelNumericContent extends ExcelContent {
    private double value;

    @Override
    public String getText() {
        long round = Math.round(value);
        if (Math.abs(value - round) < Double.MIN_NORMAL) {
            return Long.toString(round);
        }
        if (value > -1000 && value < 10000) {
            return String.format("%.6g", value);
        }
        return String.format("%.8g", value);
    }

}
