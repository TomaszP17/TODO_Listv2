import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class UpperPanel extends JPanel {
    private JTable todoTable;
    private String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private String username = "system";
    private String password = "root";
    public UpperPanel(DefaultTableModel todoTableModel) {

        JLabel countRecordLabel = new JLabel("TODO elements: " + todoTableModel.getRowCount());
        add(countRecordLabel);

        //adding a new task
        JButton addRecordButton = new JButton("Add task");
        addRecordButton.addActionListener(e -> {
            JPanel inputPanel = new JPanel(new GridLayout(3, 2));
            JTextField taskField = new JTextField(20);
            JComboBox<Boolean> statusField = new JComboBox<>();
            statusField.addItem(false);
            statusField.addItem(true);
            JComboBox<Priority> priorityField = new JComboBox<>();
            priorityField.addItem(Priority.NOT_IMPORTANT);
            priorityField.addItem(Priority.IMPORTANT);
            priorityField.addItem(Priority.VERY_IMPORTANT);

            inputPanel.add(new JLabel("Task:"));
            inputPanel.add(taskField);
            inputPanel.add(new JLabel("Status:"));
            inputPanel.add(statusField);
            inputPanel.add(new JLabel("Priority: "));
            inputPanel.add(priorityField);

            int option = JOptionPane.showConfirmDialog(UpperPanel.this, inputPanel, "Enter New Task Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                String task = taskField.getText();
                boolean status = (boolean) statusField.getSelectedItem();
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
                JComboBox<Status> isDone = new JComboBox<>();
                JComboBox<Priority> priorityBox = new JComboBox<>();

                isDone.addItem(Status.FALSE);
                isDone.addItem(Status.TRUE);

                priorityBox.addItem(Priority.NOT_IMPORTANT);
                priorityBox.addItem(Priority.IMPORTANT);
                priorityBox.addItem(Priority.VERY_IMPORTANT);

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

                    todoTableModel.setValueAt(editedTask, selectedItem, 1);
                    todoTableModel.setValueAt(editedIsDoneStatus, selectedItem, 2);
                    todoTableModel.setValueAt(editedPriority, selectedItem, 3);
                }
            }
        });
        add(editTaskButton);

        JButton loadDataButton = new JButton("Load data");
        loadDataButton.addActionListener(e -> {
            //clear actual todoTabel
            todoTableModel.setRowCount(0);

            try {
                Connection connection = DriverManager.getConnection(url, username, password);

                String sqlQuery = "SELECT * FROM TASK";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery);

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String task = resultSet.getString("TASK");
                    boolean isDone = resultSet.getBoolean("ISDONE");
                    Priority priority = null;
                    String dbPriority = resultSet.getString("PRIORITY");
                    switch (dbPriority){
                        case "Not important" -> priority = Priority.NOT_IMPORTANT;
                        case "Important" -> priority = Priority.IMPORTANT;
                        case "Very important" -> priority = Priority.VERY_IMPORTANT;
                    }
                    LocalDate data = resultSet.getDate("DATA").toLocalDate();

                    System.out.println(id + " " + task + " " + isDone + " " + priority + " " + data);

                    Object[] rowData = {id, task, isDone, priority, data};
                    todoTableModel.addRow(rowData);

                }

                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException event) {
                event.printStackTrace();
            }
        });
        add(loadDataButton);

        JButton updateDataButton = new JButton("Update data");

        updateDataButton.addActionListener(e -> {
            try {

                Connection connection = DriverManager.getConnection(url, username, password);

                String deleteQuery = "DELETE FROM TASK";
                Statement deleteStatement = connection.createStatement();
                deleteStatement.executeUpdate(deleteQuery);
                deleteStatement.close();

                String insertQuery = "INSERT INTO TASK (ID, TASK, ISDONE, PRIORITY, DATA) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

                for (int i = 0; i < todoTableModel.getRowCount(); i++) {
                    int id = (int) todoTableModel.getValueAt(i, 0);
                    String task = (String) todoTableModel.getValueAt(i, 1);
                    boolean isDone = (boolean) todoTableModel.getValueAt(i, 2);
                    String priority = todoTableModel.getValueAt(i, 3).toString();
                    LocalDate data = (LocalDate) todoTableModel.getValueAt(i, 4);

                    // Mapujemy wartości do parametrów w zapytaniu SQL
                    insertStatement.setInt(1, id);
                    insertStatement.setString(2, task);
                    insertStatement.setBoolean(3, isDone);
                    insertStatement.setString(4, priority);
                    insertStatement.setDate(5, java.sql.Date.valueOf(data));

                    // Wykonaj zapytanie INSERT dla aktualnego wiersza
                    insertStatement.executeUpdate();
                }
                insertStatement.close();
                connection.close();
            } catch (SQLException event) {
                event.printStackTrace();
            }
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
