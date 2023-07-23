import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StatusRowRenderer extends DefaultTableCellRenderer {
    private int statusColumnIndex;

    public StatusRowRenderer(int statusColumnIndex) {
        this.statusColumnIndex = statusColumnIndex;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        boolean statusValue = (boolean) table.getValueAt(row, statusColumnIndex);
        if (statusValue) {
            cellComponent.setBackground(Color.GREEN);
        } else {
            cellComponent.setBackground(table.getBackground());
        }

        return cellComponent;
    }
}
