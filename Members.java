package CGETS;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.sql.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

public class Members extends JFrame {
    private JTextField studentIdField, nameField, contactField, yearField;
    private JButton addButton, removeButton, btnBack, addToArchivesButton;
    private JTable table;
    private DefaultTableModel tableModel;

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/dbase";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public Members() {
    	loadCustomFont();
    	getContentPane().setBackground(Color.LIGHT_GRAY);
        initializeUI();
        setupListeners();
        loadDataFromDatabase();
    }

    private void initializeUI() {
        setTitle("Members");
        setSize(1200, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Load the icon image
        ImageIcon icon = new ImageIcon("C:\\Users\\PC01\\eclipse-workspace\\kean\\src\\CGETS\\362305135_3609898249334879_8434680016085167769_n.png");
        setIconImage(icon.getImage());

        // Set up table and its model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Student_ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Contact_No");
        tableModel.addColumn("Year_Level");

        table = new JTable(tableModel);
        table.setDefaultEditor(Object.class, null); // Set cells to be non-editable
        table.setBackground(Color.BLACK);
        table.setForeground(Color.GREEN);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(290, 11, 884, 450);
        scrollPane.setViewportBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
        scrollPane.getViewport().setBackground(Color.BLACK);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        table.getTableHeader().setReorderingAllowed(false);
        getContentPane().setLayout(null);
        getContentPane().add(scrollPane);
        JLabel label = new JLabel("Student_ID");
        label.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
        label.setBounds(10, 148, 110, 40);
        getContentPane().add(label);
        
                // Set up components
                studentIdField = new JTextField(10);
                studentIdField.setBounds(130, 156, 150, 30);
                studentIdField.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
                getContentPane().add(studentIdField);
                JLabel label_1 = new JLabel("Name");
                label_1.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
                label_1.setBounds(10, 192, 110, 40);
                getContentPane().add(label_1);
                nameField = new JTextField(20);
                nameField.setBounds(130, 197, 150, 30);
                nameField.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
                getContentPane().add(nameField);
                JLabel label_2 = new JLabel("Contact_No");
                label_2.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
                label_2.setBounds(10, 235, 110, 30);
                getContentPane().add(label_2);
                contactField = new JTextField(15);
                contactField.setBounds(130, 238, 150, 30);
                contactField.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
                getContentPane().add(contactField);
                JLabel label_3 = new JLabel("Year_Level");
                label_3.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
                label_3.setBounds(10, 276, 110, 30);
                getContentPane().add(label_3);
                yearField = new JTextField(5);
                yearField.setBounds(130, 279, 150, 30);
                yearField.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
                getContentPane().add(yearField);
                
                        btnBack = new JButton("Back");
                        btnBack.setBackground(new Color(192, 192, 192));
                        btnBack.setBounds(50, 489, 119, 50);
                        btnBack.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
                		btnBack.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
                        getContentPane().add(btnBack);
                        
                                addButton = new JButton("Add");
                                addButton.setBounds(860, 489, 119, 50);
                                addButton.setBackground(new Color(192, 192, 192));
                                addButton.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
                        		addButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
                                getContentPane().add(addButton);
                                
                                removeButton = new JButton("Remove");
                                removeButton.setBackground(new Color(192, 192, 192));
                                removeButton.setBounds(1016, 489, 119, 50);
                                removeButton.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
                        		removeButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
                        		removeButton.setEnabled(false);
                                getContentPane().add(removeButton);
                                
                                JLabel lblNewLabel = new JLabel("Members");
                                lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                lblNewLabel.setBounds(10, 11, 200, 40);
                                lblNewLabel.setFont(new Font("PixelMplus10", Font.PLAIN, 28));
                                lblNewLabel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
                                getContentPane().add(lblNewLabel);
                                
                                JLabel lblNewLabel_1 = new JLabel("");
                                lblNewLabel_1.setBounds(0, 0, 1184, 561);
                                lblNewLabel_1.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
                                getContentPane().add(lblNewLabel_1);
                               
                                // Add to Archives button
                                addToArchivesButton = new JButton("Add to Archives");
                                addToArchivesButton.setBounds(665, 489, 150, 50);
                                addToArchivesButton.setBackground(new Color(192, 192, 192));
                                addToArchivesButton.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
                                addToArchivesButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
                                getContentPane().add(addToArchivesButton);
    }
    
    private void loadCustomFont() {
        try {
            // Load the custom font from the project's resources (assuming it's in the "fonts" folder)
            InputStream is = getClass().getResourceAsStream("/CGETS/PixelMplus10-Regular_web.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is);

            // Register the font with the GraphicsEnvironment
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

        } catch (Exception e) {
            e.printStackTrace();
        }
   	}

    private void setupListeners() {
        addButton.addActionListener(e -> addData());
        removeButton.addActionListener(e -> removeData());
		addToArchivesButton.addActionListener(e -> addToArchives());
        btnBack.addActionListener(e -> {
            Main main = new Main();
            main.setVisible(true);
            setVisible(false);
        });

        table.getSelectionModel().addListSelectionListener(e -> handleRowSelection());

        /**addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!table.contains(e.getPoint())) {
                    table.clearSelection();
                    handleRowSelection();
                }
            }
        });**/

        ((AbstractDocument) studentIdField.getDocument()).setDocumentFilter(new NumericFilter());
        ((AbstractDocument) contactField.getDocument()).setDocumentFilter(new NumericFilter());
        ((AbstractDocument) yearField.getDocument()).setDocumentFilter(new NumericFilter());
    }

    private void handleRowSelection() {
        int selectedRowCount = table.getSelectedRowCount();
        removeButton.setEnabled(selectedRowCount > 0);
    }

    private void loadDataFromDatabase() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM members";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String id = resultSet.getString("Student_ID");
                    String name = resultSet.getString("Name");
                    String contact = resultSet.getString("Contact_No");
                    String year = resultSet.getString("Year_Level");

                    Object[] rowData = {id, name, contact, year};
                    tableModel.addRow(rowData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error loading data from the database: " + e.getMessage());
        }
    }

    private void addData() {
        String id = studentIdField.getText();
        String name = nameField.getText();
        String contact = contactField.getText();
        String year = yearField.getText();

        if (id.isEmpty() || name.isEmpty() || contact.isEmpty() || year.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Object[] rowData = {id, name, contact, year};
        tableModel.addRow(rowData);
        
        insertLogToDatabase("President has added a member: " + name);

        saveDataToDatabase(id, name, contact, year);
        
        // Clear the text fields
        studentIdField.setText("");
        nameField.setText("");
        contactField.setText("");
        yearField.setText("");
    }


    private void removeData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to remove", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String idToRemove = (String) table.getValueAt(selectedRow, 0);
        String nameToRemove = (String) table.getValueAt(selectedRow, 1);
        tableModel.removeRow(selectedRow);
        insertLogToDatabase("President has removed a member: " + nameToRemove);
        removeDataFromDatabase(idToRemove);
    }

    private void saveDataToDatabase(String id, String name, String contact, String year) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            connection.setAutoCommit(false); 
            String query = "INSERT INTO members (Student_ID, Name, Contact_No, Year_Level) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, contact);
                preparedStatement.setString(4, year);
                preparedStatement.executeUpdate();
            }
            connection.commit(); // Commit the changes to the database
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error saving data to the database: " + e.getMessage());
        }
    }

    private void removeDataFromDatabase(String id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "DELETE FROM members WHERE Student_ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Members().setVisible(true));
    }

    private static class NumericFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string.matches("\\d+")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text.matches("\\d+")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
    
    private void addToArchives() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to add to archives", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = (String) table.getValueAt(selectedRow, 0);
        String name = (String) table.getValueAt(selectedRow, 1);
        String contact = (String) table.getValueAt(selectedRow, 2);
        String year = (String) table.getValueAt(selectedRow, 3);

        // Add the selected row to the archives database
        addToArchivesDatabase(id, name, contact, year);
        
        insertLogToDatabase("President moved to archives:" +name);

        // Remove the selected row from the current database
        tableModel.removeRow(selectedRow);
    }
    
    private void addToArchivesDatabase(String id, String name, String contact, String year) {
        // Use the JDBC URL, username, and password for the archives database
        String archivesURL = "jdbc:mysql://localhost:3306/archives";
        String archivesUSER = "root";
        String archivesPASSWORD = "";
        
        // Declare the Connection variable outside the try block
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(archivesURL, archivesUSER, archivesPASSWORD);
            // Start a transaction for atomicity
            connection.setAutoCommit(false);

            // Insert into the archives table
            String insertQuery = "INSERT INTO archive (Student_ID, Name, Contact_No, Year_Level) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, id);
                insertStatement.setString(2, name);
                insertStatement.setString(3, contact);
                insertStatement.setString(4, year);
                insertStatement.executeUpdate();
            }

            // Commit the changes to the archives database
            connection.commit();

            // Now, update the original table by removing the row
            removeDataFromDatabase(id);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error adding to archives database: " + ex.getMessage());
            // Rollback the transaction in case of an error
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            // Close the connection in the finally block to ensure it's always closed
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void insertLogToDatabase(String logStatement) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

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