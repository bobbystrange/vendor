package org.dreamcat.vendor.excel.wrapped;

import lombok.Getter;
import org.dreamcat.common.bean.BeanListUtil;
import org.dreamcat.vendor.excel.content.ExcelUnionContent;
import org.dreamcat.vendor.excel.content.IExcelContent;
import org.dreamcat.vendor.excel.core.IExcelCell;
import org.dreamcat.vendor.excel.core.IExcelSheet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Create by tuke on 2020/7/22
 */
@Getter
@SuppressWarnings({"rawtypes", "unchecked"})
public class WrappedListSheet implements IExcelSheet {
    private final String name;
    // [Cell..., T1, Cell..., T2]
    private final List schemes;

    public WrappedListSheet(String name) {
        this.name = name;
        this.schemes = new ArrayList<>(0);
    }

    public void add(Object row) {
        schemes.add(row);
    }

    public void addAll(Collection scheme) {
        schemes.addAll(scheme);
    }

    public void add(IExcelCell cell) {
        schemes.add(cell);
    }

    @NotNull
    @Override
    public Iterator<IExcelCell> iterator() {
        return this.new Iter();
    }

    @Getter
    private class Iter extends ExcelUnionContent implements Iterator<IExcelCell>, IExcelCell {
        // as row index offset since row based structure
        int offset;
        int schemeSize;
        transient int schemeIndex;

        transient List row;
        int columnSize;
        transient int columnIndex;

        // only not null if scheme is a IExcelCell
        transient IExcelCell cell;
        transient IExcelCell nextCell;
        transient int maxRowOffset;

        private Iter() {
            offset = 0;
            if (schemes.isEmpty()) {
                clear(true);
                return;
            }
            schemeSize = schemes.size();
            schemeIndex = 0;
            columnIndex = -1;

            setRow(schemes.get(0));
            if (nextCell != null) {
                return;
            }

            if (row.isEmpty()) {
                clear(true);
                return;
            }
            columnSize = row.size();
        }

        @Override
        public IExcelContent getContent() {
            return this;
        }

        @Override
        public int getRowIndex() {
            if (cell != null) {
                return cell.getRowIndex() + offset;
            } else {
                return offset;
            }
        }

        @Override
        public int getColumnIndex() {
            if (cell != null) {
                return cell.getColumnIndex();
            } else {
                return columnIndex;
            }
        }

        @Override
        public int getRowSpan() {
            if (cell != null) {
                return cell.getRowSpan();
            }
            return 1;
        }

        @Override
        public int getColumnSpan() {
            if (cell != null) {
                return cell.getColumnSpan();
            }
            return 1;
        }

        @Override
        public boolean hasNext() {
            // empty schemes
            if (columnSize == -1) return false;
            // reach all schemes
            if (schemeIndex >= schemeSize) return false;
            // has cells
            if (nextCell != null) return true;

            return columnIndex < columnSize - 1 || schemeIndex < schemeSize - 1;
        }

        @Override
        public IExcelCell next() {
            // in cell case scheme
            if (nextCell != null) {
                // move magical cursor for cells
                cell = nextCell;
                maxRowOffset = Math.max(cell.getRowIndex() + cell.getRowSpan(), maxRowOffset);
                schemeIndex++;
                if (schemeIndex < schemeSize) {
                    setRow(schemes.get(schemeIndex));
                } else {
                    nextCell = null;
                }

                setRawContent(cell.getContent());
                return this;
            }

            // if next element is a POJO
            if (cell != null) {
                // clear cell bits
                cell = null;
                offset += maxRowOffset;
            }

            // move magical cursor
            columnIndex++;
            if (columnIndex >= columnSize) {
                schemeIndex++;
                setRow(schemes.get(schemeIndex));
                // move offset
                offset++;
                columnIndex = 0;
            }

            // set content
            var value = row.get(columnIndex);
            if (value instanceof Number) {
                var number = (Number) value;
                setNumericContent(number.doubleValue());
            } else if (value instanceof Boolean) {
                var bool = (Boolean) value;
                setBooleanContent(bool);
            } else {
                setStringContent(String.valueOf(value));
            }
            return this;
        }

        // Note that it makes hasNext() return false
        private void clear(boolean initial) {
            schemeSize = 0;
            columnSize = -1;
            if (initial) return;

            row = null;
            cell = null;
            nextCell = null;
        }

        private void setRow(Object rawRow) {
            if (rawRow instanceof IExcelCell) {
                nextCell = (IExcelCell) rawRow;
                row = null;
            } else if (rawRow instanceof List) {
                row = (List) (rawRow);
                columnSize = row.size();
                nextCell = null;
            } else {
                row = BeanListUtil.toList(rawRow);
                columnSize = row.size();
                nextCell = null;
            }
        }

    }
}
