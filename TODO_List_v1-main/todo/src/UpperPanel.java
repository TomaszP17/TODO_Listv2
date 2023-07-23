import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class UpperPanel extends JPanel {
    private JTable todoTable;
    public UpperPanel(DefaultTableModel todoTableModel) {

        JLabel countRecordLabel = new JLabel("TODO elements: " + todoTableModel.getRowCount());
        add(countRecordLabel);

        //adding a new task
        JButton addRecordButton = new JButton("Add task");
        addRecordButton.addActionListener(e -> {
            JPanel inputPanel = new JPanel(new GridLayout(3, 2));
            JTextField taskField = new JTextField(20);
            JComboBox<String> statusField = new JComboBox<>();
            statusField.addItem("true");
            statusField.addItem("false");
            JComboBox<String> priorityField = new JComboBox<>();
            priorityField.addItem("Not important");
            priorityField.addItem("Important");
            priorityField.addItem("Very important");

            inputPanel.add(new JLabel("Task:"));
            inputPanel.add(taskField);
            inputPanel.add(new JLabel("Status (true/false):"));
            inputPanel.add(statusField);
            inputPanel.add(new JLabel("Priority: "));
            inputPanel.add(priorityField);

            int option = JOptionPane.showConfirmDialog(UpperPanel.this, inputPanel, "Enter New Task Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                String task = taskField.getText();
                String statusText = String.valueOf(statusField.getSelectedIndex());
                boolean status = Boolean.parseBoolean(statusText);
                String priority = String.valueOf(priorityField.getSelectedItem());
                LocalDate localDate = LocalDate.now();
                Object[] rowData = {todoTableModel.getRowCount() + 1, task, status, priority, localDate};
                todoTableModel.addRow(rowData);
                countRecordLabel.setText("TODO elements: " + todoTableModel.getRowCount());
            }
        });
        add(addRecordButton);
        //deleting task
        JButton deleteRecordButton = new JButton("Delete task");
        deleteRecordButton.addActionListener(e -> {
            int selectedIndex = getSelectedRowIndex();
            if (selectedIndex != -1) {
                todoTableModel.removeRow(selectedIndex);
                countRecordLabel.setText("TODO elements: " + todoTableModel.getRowCount());

                for(int i = 0; i < todoTableModel.getRowCount(); i++){
                    todoTableModel.setValueAt(i+1, i, 0);
                }

            } else {
                JOptionPane.showMessageDialog(UpperPanel.this, "Select a task to delete.", "No Task Selected", JOptionPane.WARNING_MESSAGE);
            }
        });
        add(deleteRecordButton);
        //editing an existing task
        JButton editTaskButton = new JButton("Edit task");
        editTaskButton.addActionListener(e -> {
            int selectedItem = getSelectedRowIndex();
            if (selectedItem != -1) {

                JPanel editingPanel = new JPanel(new GridLayout(3, 2));
                JTextField taskField = new JTextField(20);
                JComboBox<String> isDone = new JComboBox<>();
                JComboBox<String> priorityBox = new JComboBox<>();

                isDone.addItem("false");
                isDone.addItem("true");

                priorityBox.addItem("Not important");
                priorityBox.addItem("Important");
                priorityBox.addItem("Very important");

                editingPanel.add(new JLabel("Task: "));
                editingPanel.add(taskField);
                editingPanel.add(new JLabel("is Done: "));
                editingPanel.add(isDone);
                editingPanel.add(new JLabel("Priority: "));
                editingPanel.add(priorityBox);

                int option = JOptionPane.showConfirmDialog(UpperPanel.this, editingPanel, "Edit Task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (option == JOptionPane.OK_OPTION) {
                    String editedTask = taskField.getText();
                    String editedIsDoneValue = isDone.getSelectedItem().toString();
                    boolean editedIsDoneStatus = Boolean.parseBoolean(editedIsDoneValue);
                    String editedPriority = priorityBox.getSelectedItem().toString();

                    // Aktualizacja wartości dla wybranego wiersza w tabeli
                    todoTableModel.setValueAt(editedTask, selectedItem, 1);
                    todoTableModel.setValueAt(editedIsDoneStatus, selectedItem, 2);
                    todoTableModel.setValueAt(editedPriority, selectedItem, 3);
                }
            }
        });
        add(editTaskButton);

        JButton loadDataButton = new JButton("Load data");
        loadDataButton.addActionListener(e -> {

        });
        add(loadDataButton);

        //updating table in DB
        JButton updateDataButton = new JButton("Update data");
        updateDataButton.addActionListener(e -> {

        });
        add(updateDataButton);
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
