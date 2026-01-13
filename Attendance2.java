package CGETS;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Attendance2 extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton removeColumnButton;

    public Attendance2() {
    	getContentPane().setBackground(Color.LIGHT_GRAY);
    	loadCustomFont();
        // Set up the JFrame
        setTitle("Attendance");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        //Load the icon image
        ImageIcon icon = new ImageIcon("C:\\Users\\PC01\\eclipse-workspace\\kean\\src\\CGETS\\362305135_3609898249334879_8434680016085167769_n.png");
        setIconImage(icon.getImage());
        
        // Set up the table model and table
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make "Name" and "Year_Level" columns not editable
                return !(getColumnName(column).equals("Name") || getColumnName(column).equals("Year_Level"));
            }
        };
        
        getContentPane().setLayout(null);
        table = new JTable(tableModel);
        table.setForeground(Color.GREEN);
        table.setBackground(Color.BLACK);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoCreateRowSorter(true);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setViewportBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
        scrollPane.setBounds(10, 60, 1164, 408);
        scrollPane.getViewport().setBackground(Color.BLACK);
        getContentPane().add(scrollPane);
        
        // Add a TableModelListener to detect changes in the table
        tableModel.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            // Check if the cell data is updated
            if (row != -1 && column != -1) {
                Object data = tableModel.getValueAt(row, column);
                updateCellInDatabase(row, column, data);
            }
        });
        
        // Create components
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(192, 192, 192));
        backButton.setBounds(50, 489, 119, 50);
        backButton.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
        backButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
        getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add code to handle the back button action
            	Main2 main2 = new Main2();
            	main2.setVisible(true);
            	setVisible(false);
            }
        });

        // Add button for adding a new column
        JButton addColumnButton = new JButton("Add");
        addColumnButton.setBackground(new Color(192, 192, 192));
        addColumnButton.setBounds(860, 489, 119, 50);
        addColumnButton.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
        addColumnButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
        getContentPane().add(addColumnButton);
        addColumnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt for a new column name
                String columnName = JOptionPane.showInputDialog("Enter Existing Event: ");

                if (columnName != null && !columnName.trim().isEmpty()) {
                    // Add the column to the table model
                    tableModel.addColumn(columnName);
                    insertLogToDatabase("Treasurer added an event to attendance:" +columnName);
                    // Update the database with the new column
                    updateDatabase(columnName);
                }
            }
        });
        
     // Add a button for removing a column
        JButton removeColumnButton = new JButton("Remove");
        removeColumnButton.setBackground(new Color(192, 192, 192));
        removeColumnButton.setBounds(1016, 489, 119, 50);
        removeColumnButton.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
        removeColumnButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
        removeColumnButton.setEnabled(false); // Disable the button initially
        getContentPane().add(removeColumnButton);
        removeColumnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedColumn = table.getSelectedColumn();

                if (selectedColumn != -1) {
                    // Get the column name to be removed
                    String columnName = tableModel.getColumnName(selectedColumn);

                    // Remove the column from the table model and JTable
                    removeColumn(selectedColumn);
                    insertLogToDatabase("Treasurer removed an event to attendance:" +columnName);
                    // Update the database to remove the column
                    removeColumnFromDatabase(columnName);
                } else {
                    JOptionPane.showMessageDialog(Attendance2.this, "Select an Event to remove", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
    	// Add a ListSelectionListener to the table selection model
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                removeColumnButton.setEnabled(selectedRow != -1); // Enable the button if a row is selected
            }
        });
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setBounds(0, 0, 1184, 561);
        lblNewLabel_1.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
        getContentPane().add(lblNewLabel_1);
        
        JLabel lblNewLabel = new JLabel("Attendance");
        lblNewLabel.setBackground(new Color(255, 255, 255));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(10, 11, 200, 40);
        lblNewLabel.setFont(new Font("PixelMplus10", Font.PLAIN, 28));
        lblNewLabel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
        getContentPane().add(lblNewLabel);

        // Add the remove button to the JFrame
        getContentPane().add(removeColumnButton);

        // Add the button to the JFrame
        getContentPane().add(addColumnButton);

        // Connect to the database and populate the table
        connectToDatabase();
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

    private void connectToDatabase() {
        Connection connection = null;

        try {
            // Replace these values with your database connection details
            String url = "jdbc:mysql://localhost:3306/dbase";
            String user = "root";
            String password = "";

            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            connection = DriverManager.getConnection(url, user, password);

            // Fetch data from the database and populate the table
            // Assuming there are two columns: "Name" and "Year_Level"
            // You should modify this based on your actual database structure
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM attendance");
            ResultSet resultSet = preparedStatement.executeQuery();

            // Get the metadata to determine the number of columns
            int columnCount = resultSet.getMetaData().getColumnCount();

            // Clear existing columns in the table model
            tableModel.setColumnCount(0);

            // Add column names to the table model
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(resultSet.getMetaData().getColumnName(i));
            }

            // Add data to the table model
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];

                // Initialize rowData with default values
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = ""; // You can use any default value here
                }

                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getObject(i);
                }
                tableModel.addRow(rowData);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Set the table model and refresh the table outside the try-catch-finally block
        table.setModel(tableModel);
        table.revalidate();
        table.repaint();
    }
    
    private void removeColumn(int columnIndex) {
        // Create a new DefaultTableModel without the selected column
        DefaultTableModel newModel = new DefaultTableModel();
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            if (i != columnIndex) {
                newModel.addColumn(tableModel.getColumnName(i));
            }
        }

        // Copy data from the old model to the new model
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            Object[] rowData = new Object[newModel.getColumnCount()];
            int destCol = 0;
            for (int srcCol = 0; srcCol < tableModel.getColumnCount(); srcCol++) {
                if (srcCol != columnIndex) {
                    rowData[destCol] = tableModel.getValueAt(row, srcCol);
                    destCol++;
                }
            }
            newModel.addRow(rowData);
        }

        // Set the new model to the JTable
        table.setModel(newModel);
        tableModel = newModel;
    }
    
    private void removeColumnFromDatabase(String columnName) {
        Connection connection = null;

        try {
            // Replace these values with your database connection details
            String url = "jdbc:mysql://localhost:3306/dbase";
            String user = "root";
            String password = "";

            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            connection = DriverManager.getConnection(url, user, password);

            // Remove the column from the database table
            String alterTableQuery = "ALTER TABLE attendance DROP COLUMN " + columnName;
            PreparedStatement preparedStatement = connection.prepareStatement(alterTableQuery);
            preparedStatement.executeUpdate();

            // Close the connection
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error removing the column from the database", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void updateCellInDatabase(int row, int column, Object data) {
        Connection connection = null;

        try {
            // Replace these values with your database connection details
            String url = "jdbc:mysql://localhost:3306/dbase";
            String user = "root";
            String password = "";

            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            connection = DriverManager.getConnection(url, user, password);

            // Update the corresponding record in the database
            String columnName = tableModel.getColumnName(column);
            String updateQuery = "UPDATE attendance SET " + columnName + " = ? WHERE Name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            // Use setObject to handle various data types
            preparedStatement.setObject(1, data);
            preparedStatement.setString(2, (String) tableModel.getValueAt(row, 0)); // Assuming Name is in the first column

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating the database", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateDatabase(String columnName) {
        try {
            // Replace these values with your database connection details
            String url = "jdbc:mysql://localhost:3306/dbase";
            String user = "root";
            String password = "";

            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            Connection connection = DriverManager.getConnection(url, user, password);

            // Add the new column to the database table
            String alterTableQuery = "ALTER TABLE attendance ADD COLUMN " + columnName + " VARCHAR(255)";
            PreparedStatement preparedStatement = connection.prepareStatement(alterTableQuery);
            preparedStatement.executeUpdate();

            // Close the connection
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating the database", "Error", JOptionPane.ERROR_MESSAGE);
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
                new Attendance2().setVisible(true);
            }
        });
    }
}
