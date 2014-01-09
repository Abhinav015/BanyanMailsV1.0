package helper;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {

    private Object[][] data;
    private String[] columns;

    public MyTableModel(Object[][] data, String[] columns) {
        this.data = data;
        this.columns = columns;
    }

    public Class getColumnClass(int columnIndex) {
        return data[0][columnIndex].getClass();
    }

    public int getColumnCount() {
        return columns.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 3) {
            data[rowIndex][columnIndex] = aValue;

            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 3) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public String  getColumnName(int col) {
       return columns[col];
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }
}
