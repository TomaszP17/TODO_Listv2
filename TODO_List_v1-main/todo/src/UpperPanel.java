import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class UpperPanel extends JPanel {
    private final DefaultTableModel todoTableModel;
    private JTable todoTable;

    public UpperPanel(DefaultTableModel todoTableModel) {
        this.todoTableModel = todoTableModel;

        JLabel countRecordLabel = new JLabel("TODO elements: " + todoTableModel.getRowCount());
        add(countRecordLabel);

        JButton addRecordButton = new JButton("Add task");
        addRecordButton.addActionListener(e -> {
            JPanel inputPanel = new JPanel(new GridLayout(3, 2));
            JTextField taskField = new JTextField(20);
            JTextField statusField = new JTextField(5);

            JPopupMenu priorityPopupMenu = new JPopupMenu();
            JMenuItem lowPriorityItem = new JMenuItem("Not important");
            JMenuItem mediumPriorityItem = new JMenuItem("Important");
            JMenuItem highPriorityItem = new JMenuItem("Very important");

            priorityPopupMenu.add(lowPriorityItem);
            priorityPopupMenu.add(mediumPriorityItem);
            priorityPopupMenu.add(highPriorityItem);

            JTextField priorityField = new JTextField(5);
            priorityField.setEditable(false);
            priorityField.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    priorityPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            });

            lowPriorityItem.addActionListener(evt -> {
                priorityField.setText("Not important");
                priorityPopupMenu.setVisible(false);
            });

            mediumPriorityItem.addActionListener(evt -> {
                priorityField.setText("Important");
                priorityPopupMenu.setVisible(false);
            });

            highPriorityItem.addActionListener(evt -> {
                priorityField.setText("Very important");
                priorityPopupMenu.setVisible(false);
            });

            inputPanel.add(new JLabel("Task:"));
            inputPanel.add(taskField);
            inputPanel.add(new JLabel("Status (true/false):"));
            inputPanel.add(statusField);
            inputPanel.add(new JLabel("Priority: "));
            inputPanel.add(priorityField);

            int option = JOptionPane.showConfirmDialog(UpperPanel.this, inputPanel, "Enter New Task Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                String task = taskField.getText();
                String statusText = statusField.getText();
                boolean status = Boolean.parseBoolean(statusText);
                String priority = priorityField.getText();
                LocalDate localDate = LocalDate.now();
                Object[] rowData = {todoTableModel.getRowCount() + 1, task, status, priority, localDate};
                todoTableModel.addRow(rowData);
                countRecordLabel.setText("TODO elements: " + todoTableModel.getRowCount());
            }
        });
        add(addRecordButton);

        JButton deleteRecordButton = new JButton("Delete task");
        deleteRecordButton.addActionListener(e -> {
            int selectedIndex = getSelectedRowIndex();
            if (selectedIndex != -1) {
                todoTableModel.removeRow(selectedIndex);
                countRecordLabel.setText("TODO elements: " + todoTableModel.getRowCount());
            } else {
                JOptionPane.showMessageDialog(UpperPanel.this, "Select a task to delete.", "No Task Selected", JOptionPane.WARNING_MESSAGE);
            }
        });
        add(deleteRecordButton);

        JButton editTaskButton = new JButton("Edit task");
        editTaskButton.addActionListener(e -> {
            int selectedItem = getSelectedRowIndex();
            if (selectedItem != -1) {
                String editedElement = JOptionPane.showInputDialog(UpperPanel.this, "Enter edited task:");
                if (editedElement != null && !editedElement.isEmpty()) {
                    todoTableModel.setValueAt(editedElement, selectedItem, 1);
                }
            }
        });
        add(editTaskButton);

        JButton completedTasksButton = new JButton("Filter tasks");
        completedTasksButton.addActionListener(e -> {
            System.out.println("Clicked!");
        });
        add(completedTasksButton);
    }

    public int getSelectedRowIndex() {
        if (todoTable != null) {
            return todoTable.getSelectedRow();
        } else {
            return -1;
        }
    }
    public void setTodoTable(JTable todoTable) {
        this.todoTable = todoTable;
    }
}
