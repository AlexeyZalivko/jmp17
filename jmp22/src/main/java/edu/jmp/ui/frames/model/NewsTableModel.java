package edu.jmp.ui.frames.model;

import edu.jmp.ui.frames.data.NewsDisplayData;

import javax.swing.event.TableModelListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alex on 12.03.17.
 */
public class NewsTableModel implements javax.swing.table.TableModel {

    public static final String EMPTY_STRING = "";
    private List<NewsDisplayData> data = new ArrayList<>();
    private static List<String> columnsHeader = Arrays.asList("Author", "Caption", "Text");

    public NewsTableModel(final List<NewsDisplayData> data) {
        if (data != null) {
            this.data.addAll(data);
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(final int columnIndex) {
        return columnsHeader.get(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        final NewsDisplayData data = this.data.get(rowIndex);
        switch (columnIndex){
            case 0: {
                return data.getAuthor().getName();
            }
            case 1: {
                return data.getCaption();
            }
            case 2: {
                return data.getText();
            }
            default: {
                return EMPTY_STRING;
            }
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(final TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(final TableModelListener l) {

    }
}
