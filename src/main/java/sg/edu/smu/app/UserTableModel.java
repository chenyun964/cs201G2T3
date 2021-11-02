package sg.edu.smu.app;

import javax.swing.table.AbstractTableModel;

public class UserTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 5044877015250409328L;

    private String[] header;
    private Object[][] data;

    public UserTableModel(Object[][] data, String[] header) {
        this.header = header;
        this.data = data;
    }

    public void setData(Object[][] data) {
        this.data = data;
        fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return super.getColumnClass(column);
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public String getColumnName(int column) {
        return header[column];
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return data[row][column];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 0;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        data[row][column] = value;
    }
}
