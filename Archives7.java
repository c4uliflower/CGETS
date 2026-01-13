package CGETS;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.sql.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Archives7 extends JFrame {

    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JTable table;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Archives7 frame = new Archives7();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Archives7() {
        try {
            initialize();
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        loadCustomFont();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Archives");
        setResizable(false);

        ImageIcon icon = new ImageIcon("C:\\Users\\PC01\\eclipse-workspace\\kean\\src\\CGETS\\362305135_3609898249334879_8434680016085167769_n.png");
        setIconImage(icon.getImage());

        contentPane = new JPanel();
        contentPane.setBackground(Color.LIGHT_GRAY);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(224, 11, 950, 539);
        scrollPane.setViewportBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
        scrollPane.getViewport().setBackground(Color.BLACK);
        contentPane.add(scrollPane);

        table = new JTable();
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoCreateRowSorter(true);
        scrollPane.setViewportView(table);
        table.setBackground(Color.black);
        table.setForeground(Color.green);
        table.getTableHeader().setReorderingAllowed(false);

        JLabel lblNewLabel = new JLabel("Archives");
        lblNewLabel.setBounds(10, 11, 200, 40);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("PixelMplus10", Font.PLAIN, 28));
        lblNewLabel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
        contentPane.add(lblNewLabel);
        		
        
        // Create the return button
        JButton returnButton = new JButton("Return");
        contentPane.add(returnButton);
        returnButton.setBackground(new Color(192, 192, 192));
        returnButton.setBounds(50, 425, 119, 50);
        returnButton.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
        returnButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
        returnButton.addActionListener(e -> returnToMembers());
        
        
                JButton backButton = new JButton("Back");
                contentPane.add(backButton);
                backButton.setBackground(new Color(192, 192, 192));
                backButton.setBounds(50, 489, 119, 50);
                backButton.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
                backButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
                backButton.addActionListener(e -> {
                    Main7 main7 = new Main7();
                    main7.setVisible(true);
                    setVisible(false);
                });
                
                        JButton removeButton = new JButton("Remove");
                        contentPane.add(removeButton);
                        removeButton.setBackground(new Color(192, 192, 192));
                        removeButton.setBounds(50, 360, 119, 50);
                        removeButton.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
                        removeButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
                        removeButton.setEnabled(false);  // Set initially disabled
                        removeButton.addActionListener(e -> {
                            int selectedRow = table.getSelectedRow();
                            if (selectedRow != -1) {
                                DefaultTableModel model = (DefaultTableModel) table.getModel();
                                String name = (String) model.getValueAt(selectedRow, 1);
                                Object primaryKey = model.getValueAt(selectedRow, 0);
                                removeRowFromDatabase(primaryKey);
                                model.removeRow(selectedRow);
                                insertLogToDatabase("P.R.O. removed from archives: " + name);
                            } else {
                                JOptionPane.showMessageDialog(removeButton, "Please select a row to remove.");
                            }
                        });

                        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                            @Override
                            public void valueChanged(ListSelectionEvent e) {
                                if (!e.getValueIsAdjusting()) {
                                    // The user has finished selecting
                                    int selectedRow = table.getSelectedRow();
                                    removeButton.setEnabled(selectedRow != -1);
                                }
                            }
                        });
        // Explicitly set the size after adding components
        setSize(1200, 600);
        setLocationRelativeTo(null);
    }

    private void loadCustomFont() {
        try {
            InputStream is = getClass().getResourceAsStream("/CGETS/PixelMplus10-Regular_web.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try {
            String url = "jdbc:mysql://localhost:3306/archives";
            String user = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, user, password);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM archive");

            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make all cells uneditable
                }
            };

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                model.addColumn(metaData.getColumnName(column));
            }

            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getObject(i);
                }
                model.addRow(rowData);
            }

            table.setModel(model);

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void returnToMembers() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] rowData = new Object[model.getColumnCount()];
            for (int i = 0; i < rowData.length; i++) {
                rowData[i] = model.getValueAt(selectedRow, i);
            }

            // Add the selected row to the "members" database
            addToMembersDatabase(rowData);
            String name = (String) rowData[1];

            // Remove the row from the "archives" database
            Object primaryKey = model.getValueAt(selectedRow, 0);
            removeRowFromDatabase(primaryKey);

            // Remove the row from the table
            model.removeRow(selectedRow);
            insertLogToDatabase("P.R.O. returned to members: " + name);
        } else {
            Component returnButton = null;
			JOptionPane.showMessageDialog(returnButton, "Please select a row to return.");
        }
    }

    private void addToMembersDatabase(Object[] rowData) {
        try {
            String url = "jdbc:mysql://localhost:3306/dbase";
            String user = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, user, password);

            String insertQuery = "INSERT INTO members (Student_ID, Name, Contact_No, Year_Level) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                for (int i = 0; i < rowData.length; i++) {
                    preparedStatement.setObject(i + 1, rowData[i]);
                }
                preparedStatement.executeUpdate();
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void removeRowFromDatabase(Object primaryKey) {
        try {
            String url = "jdbc:mysql://localhost:3306/archives";
            String user = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, user, password);

            String deleteQuery = "DELETE FROM archive WHERE Student_ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setObject(1, primaryKey);
                preparedStatement.executeUpdate();
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void returnRowToMembers(Object[] rowData) {
        try {
            String url = "jdbc:mysql://localhost:3306/dbase"; // Change the database name if needed
            String user = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, user, password);

            String insertQuery = "INSERT INTO members (Student_ID, Name, Contact_No, Year_Level) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                for (int i = 0; i < rowData.length; i++) {
                    preparedStatement.setObject(i + 1, rowData[i]);
                }
                preparedStatement.executeUpdate();
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void insertLogToDatabase(String logStatement) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/dbase"; // Change the database name if needed
            String user = "root";
            String password = "";
            
            Connection connection = DriverManager.getConnection(url, user, password);

            String query = "INSERT INTO track (Activity) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, logStatement);

                preparedStatement.executeUpdate();
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
