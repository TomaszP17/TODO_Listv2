import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class TodoApp extends JFrame {
    public TodoApp() {
        setTitle("TODO List");

        String imagePath = "C:\\Users\\Tomasz\\Desktop\\PORTFOLIO\\TODOList\\todo\\resources\\todo2PNG.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        setIconImage(imageIcon.getImage());

        LocalDate localDate = LocalDate.now();
        Record[] todoItems = {
                new Record(1, "skonczyc ten program", true,Priority.NOT_IMPORTANT, localDate),
                new Record(2, "kupic litr wodki", true, Priority.IMPORTANT, localDate),
                new Record(3, "po robocie pojsc na impreze", true, Priority.VERY_IMPORTANT, localDate)
        };

        DefaultTableModel todoTableModel = new DefaultTableModel();
        todoTableModel.addColumn(Category.ID);
        todoTableModel.addColumn(Category.TASK);
        todoTableModel.addColumn(Category.IS_DONE);
        todoTableModel.addColumn(Category.PRIORITY);
        todoTableModel.addColumn(Category.DATA);

        for (Record item : todoItems) {
            Object[] rowData = {item.getIdRecord(), item.getContent(), item.isDone(), item.getPriority(), item.getCreateDate()};
            todoTableModel.addRow(rowData);
        }

        JTable todoTable = new JTable(todoTableModel);
        todoTable.setBackground(Color.lightGray);
        todoTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        todoTable.getColumnModel().getColumn(1).setPreferredWidth(1000);
        todoTable.getColumnModel().getColumn(2).setPreferredWidth(20);
        todoTable.getColumnModel().getColumn(3).setPreferredWidth(20);
        todoTable.getColumnModel().getColumn(4).setPreferredWidth(20);

        todoTable.setDefaultRenderer(Object.class, new StatusRowRenderer(2));

        todoTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(todoTable);
        add(scrollPane, BorderLayout.CENTER);

        UpperPanel upperPanel = new UpperPanel(todoTableModel);
        upperPanel.setTodoTable(todoTable);
        add(upperPanel, BorderLayout.NORTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1920, 1080));
        pack();
        setVisible(true);
    }
}
