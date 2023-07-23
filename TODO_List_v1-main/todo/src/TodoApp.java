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
                new Record(1, "skonczyc ten program", true, 1, localDate),
                new Record(2, "kupic litr wodki", true, 2, localDate),
                new Record(3, "po robocie pojsc na impreze", true, 3, localDate)
        };
        // Tworzymy i ustawiamy model listy
        DefaultTableModel todoTableModel = new DefaultTableModel();
        todoTableModel.addColumn("ID");
        todoTableModel.addColumn("TASK");
        todoTableModel.addColumn("IS DONE");
        todoTableModel.addColumn("PRIORITY");
        todoTableModel.addColumn("DATA");

        //DODAWANIE DO JTABLEMODEL
        for (Record item : todoItems) {
            Object[] rowData = {item.getIdRecord(), item.getContent(), item.isDone(), item.getPriority(), item.getCreateDate()};
            todoTableModel.addRow(rowData);
        }

        // Tworzymy JTable na podstawie modelu
        JTable todoTable = new JTable(todoTableModel);
        todoTable.setBackground(Color.lightGray);
        todoTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        todoTable.getColumnModel().getColumn(1).setPreferredWidth(70);
        todoTable.getColumnModel().getColumn(2).setPreferredWidth(40);

        JScrollPane scrollPane = new JScrollPane(todoTable);
        add(scrollPane, BorderLayout.CENTER);

        // Przekazujemy referencję do modelu listy do klasy UpperPanel
        UpperPanel upperPanel = new UpperPanel(todoTableModel);
        upperPanel.setTodoTable(todoTable);
        add(upperPanel, BorderLayout.NORTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 600));
        pack();
        setVisible(true);
    }
}
