package CGETS;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Payments2 extends JFrame {

    private static final String URL = "jdbc:mysql://localhost:3306/dbase";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private JButton backButton;
    private JTable paymentTable;
    private JButton removeColumnButton;

    public Payments2() {
    	loadCustomFont();
    	getContentPane().setBackground(Color.LIGHT_GRAY);
        // Set up the JFrame
        setTitle("Payments");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        //Load the icon image
        ImageIcon icon = new ImageIcon("C:\\Users\\PC01\\eclipse-workspace\\kean\\src\\CGETS\\362305135_3609898249334879_8434680016085167769_n.png");
        setIconImage(icon.getImage());

        // Create components
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(192, 192, 192));
        backButton.setBounds(50, 489, 119, 50);
        backButton.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
        backButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
        getContentPane().add(backButton);
        
     // Add a new button for adding a column
        JButton addColumnButton = new JButton("Add");
        addColumnButton.setBackground(new Color(192, 192, 192));
        addColumnButton.setBounds(860, 489, 119, 50);
        addColumnButton.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
        addColumnButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
        getContentPane().add(addColumnButton);

        // Add action listener for the new button
        addColumnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user for the new column name
                String columnName = JOptionPane.showInputDialog(Payments2.this, "Enter New Fee:");

                if (columnName != null && !columnName.isEmpty()) {
                    // Add the new column to the database
                    addColumnToDatabase(columnName);

                    // Update the JTable to include the new column
                    DefaultTableModel model = (DefaultTableModel) paymentTable.getModel();
                    model.addColumn(columnName);
                    insertLogToDatabase("Treasurer removed a new fee:" +columnName);
                    // Notify the table that the structure has changed
                    model.fireTableStructureChanged();
                }
            }
        });
        
     // Add a new button for removing a column
        JButton removeColumnButton = new JButton("Remove");
        removeColumnButton.setBackground(new Color(192, 192, 192));
        removeColumnButton.setBounds(1016, 489, 119, 50);
        removeColumnButton.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
        removeColumnButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
        removeColumnButton.setEnabled(false); // Initially disabled
        getContentPane().add(removeColumnButton);

        // Add action listener for the remove button
        removeColumnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected column index
                int selectedColumn = paymentTable.getSelectedColumn();

                // Make sure a column is selected
                if (selectedColumn != -1) {
                    // Get the column name
                    String columnName = paymentTable.getColumnName(selectedColumn);

                    // Remove the column from the database
                    removeColumnFromDatabase(columnName);

                    // Update the JTable to remove the selected column
                    DefaultTableModel model = (DefaultTableModel) paymentTable.getModel();
                    model.setColumnCount(model.getColumnCount() - 1);

                    // Notify the table that the structure has changed
                    model.fireTableStructureChanged();
                    insertLogToDatabase("Treasurer removed a new fee:" +columnName);
                } else {
                    JOptionPane.showMessageDialog(Payments2.this, "Please select a column to remove.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setBounds(0, 0, 1184, 561);
        lblNewLabel_1.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
        getContentPane().add(lblNewLabel_1);
        
        JLabel lblNewLabel = new JLabel("Fees");
        lblNewLabel.setBackground(new Color(255, 255, 255));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(10, 11, 200, 40);
        lblNewLabel.setFont(new Font("PixelMplus10", Font.PLAIN, 28));
        lblNewLabel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
        getContentPane().add(lblNewLabel);
        
        getContentPane().setLayout(null);
        paymentTable = new JTable();
        paymentTable.setBackground(Color.BLACK);
        paymentTable.setForeground(Color.GREEN);
        paymentTable.getTableHeader().setReorderingAllowed(false);
        paymentTable.setAutoCreateRowSorter(true);  // Enable row sorting
        paymentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = paymentTable.getSelectedRow();
                removeColumnButton.setEnabled(selectedRow != -1);
            }
        });
        JScrollPane scrollPane = new JScrollPane(paymentTable);
        scrollPane.setViewportBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
        scrollPane.setBounds(10, 60, 1164, 408);
        scrollPane.getViewport().setBackground(Color.BLACK);
        getContentPane().add(scrollPane);
        getContentPane().add(backButton);

        // Set up the action listener for the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add code to handle the back button action
            	Main2 main2 = new Main2();
            	main2.setVisible(true);
            	setVisible(false);
            }
        });
        
        // Add table model listener to capture changes and save to the database
        paymentTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    saveChangesToDatabase();
                }
            }
        });
 
     // Add focus listener to save changes when the table loses focus
        paymentTable.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                saveChangesToDatabase();
            }
        });

        // Fetch data from the database and populate the JTable
        fetchAndPopulateData();

        // Add key listener to save changes when Enter key is pressed
        paymentTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    saveChangesToDatabase();
                }
            }
        });
        
     // Set the JFrame visibility to true
        setVisible(true);
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
    
    // Add this method to alter the database schema and add a new column
    private void addColumnToDatabase(String columnName) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Build SQL query to add a new column to the table
            String alterTableQuery = "ALTER TABLE payments ADD COLUMN " + columnName + " VARCHAR(255)";
            PreparedStatement alterTableStatement = connection.prepareStatement(alterTableQuery);

            // Execute the query
            alterTableStatement.executeUpdate();

            // Close resources
            alterTableStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Add this method to remove the selected column from the database
    private void removeColumnFromDatabase(String columnName) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Build SQL query to remove the column from the table
            String alterTableQuery = "ALTER TABLE payments DROP COLUMN " + columnName;
            PreparedStatement alterTableStatement = connection.prepareStatement(alterTableQuery);

            // Execute the query
            alterTableStatement.executeUpdate();

            // Close resources
            alterTableStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void fetchAndPopulateData() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String query = "SELECT * FROM payments";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Create a DefaultTableModel with editable columns and set it to the JTable
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Make "Student ID," "Name," and "Contact No." columns non-editable
                    return !(column == 0 || column == 1 || column == 2);
                }
            };
            paymentTable.setModel(model);

            // Get column names
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            // Get data from the result set and add it to the table model
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getObject(i);
                }
                model.addRow(rowData);
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveChangesToDatabase() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            DefaultTableModel model = (DefaultTableModel) paymentTable.getModel();
            int rowCount = model.getRowCount();
            int columnCount = model.getColumnCount();

            for (int row = 0; row < rowCount; row++) {
                StringBuilder updateQuery = new StringBuilder("UPDATE payments SET ");
                for (int col = 3; col < columnCount; col++) {
                    updateQuery.append(model.getColumnName(col)).append(" = ?, ");
                }
                updateQuery.deleteCharAt(updateQuery.length() - 1); // Remove trailing comma
                updateQuery.deleteCharAt(updateQuery.length() - 1); // Remove trailing space
                updateQuery.append(" WHERE Student_ID = ?");

                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery.toString());

                // Set parameter values for the update query
                for (int col = 3; col < columnCount; col++) {
                    preparedStatement.setObject(col - 2, model.getValueAt(row, col));
                }
                preparedStatement.setObject(columnCount - 2, model.getValueAt(row, 0)); // StudentID

                // Execute the update query
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }

            // Close the connection
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
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Payments2();
            }
        });
    }
}
