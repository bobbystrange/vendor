package org.dreamcat.vendor.excel.wrapped;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.dreamcat.common.bean.BeanListUtil;
import org.dreamcat.vendor.excel.content.ExcelUnionContent;
import org.dreamcat.vendor.excel.content.IExcelContent;
import org.dreamcat.vendor.excel.core.IExcelCell;
import org.dreamcat.vendor.excel.core.IExcelSheet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Create by tuke on 2020/7/22
 */
@Data
@AllArgsConstructor
public class WrappedListSheet<T> implements IExcelSheet {
    private String name;
    private List<T> list;

    public WrappedListSheet(String name) {
        this.name = name;
        this.list = new ArrayList<>(0);
    }

    @NotNull
    @Override
    public Iterator<IExcelCell> iterator() {
        return this.new Iter();
    }

    @Getter
    private class Iter extends ExcelUnionContent implements Iterator<IExcelCell>, IExcelCell {
        final int rowSize;
        final int columnSize;
        transient List<Object> row;
        transient int rowIndex;
        transient int columnIndex;

        private Iter() {
            if (WrappedListSheet.this.list.isEmpty()) {
                this.rowSize = 0;
                this.columnSize = -1;
            } else {
                this.row = BeanListUtil.toList(WrappedListSheet.this.list.get(0));
                this.rowSize = WrappedListSheet.this.list.size();
                this.columnSize = row.size();
            }
            this.rowIndex = 0;
            this.columnIndex = -1;
        }

        @Override
        public IExcelContent getContent() {
            return this;
        }

        @Override
        public boolean hasNext() {
            return columnSize != -1 && (rowIndex < rowSize - 1 || columnIndex < columnSize - 1);
        }

        @Override
        public IExcelCell next() {
            columnIndex++;
            if (columnIndex >= columnSize) {
                columnIndex = 0;
                rowIndex++;
                row = BeanListUtil.toList(WrappedListSheet.this.list.get(rowIndex));
            }

            var value = row.get(columnIndex);
            if (value instanceof Number) {
                var number = (Number) value;
                this.setNumericContent(number.doubleValue());
            } else if (value instanceof Boolean) {
                var bool = (Boolean) value;
                this.setBooleanContent(bool);
            } else {
                this.setStringContent(String.valueOf(value));
            }
            return this;
        }
    }
}
