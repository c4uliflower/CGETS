package CGETS;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class Events7 extends JFrame {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/dbase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private DefaultTableModel model;
    private JTable table;
    private JButton removeButton;

    public Events7() {
    	loadCustomFont();
    	getContentPane().setBackground(Color.LIGHT_GRAY);
        setTitle("Events");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        //Load the icon image
        ImageIcon icon = new ImageIcon("C:\\Users\\PC01\\eclipse-workspace\\kean\\src\\CGETS\\362305135_3609898249334879_8434680016085167769_n.png");
        setIconImage(icon.getImage());

        model = new DefaultTableModel();
        // Replace the line where you create the JTable with the following code
        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells uneditable
            }
        };
        table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        table.setBackground(Color.BLACK);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoCreateRowSorter(true);
        // Create a custom cell renderer to set the text color to green
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setForeground(Color.GREEN);
        table.setDefaultRenderer(Object.class, renderer);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setViewportBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
        scrollPane.setBounds(10, 60, 1164, 408);
        scrollPane.getViewport().setBackground(Color.BLACK);
        getContentPane().setLayout(null);
        getContentPane().add(scrollPane);
        // Create a bevel border for the back button
        Border bevelBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY);
        
                		JButton addButton = new JButton("Add");
                		addButton.setBackground(new Color(192, 192, 192));
                		addButton.setBounds(860, 489, 119, 50);
                		addButton.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
                		addButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
                		getContentPane().add(addButton);
                
                        JButton removeButton = new JButton("Remove");
                        removeButton.setBackground(new Color(192, 192, 192));
                        removeButton.setBounds(1016, 489, 119, 50);
                        removeButton.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
                        removeButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
                        removeButton.setEnabled(false);
                        getContentPane().add(removeButton);
                        
                        JButton backButton = new JButton("Back");
                        backButton.setBackground(new Color(192, 192, 192));
                        backButton.setBounds(50, 489, 119, 50);
                        backButton.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
                        backButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
                        getContentPane().add(backButton);
                        
                        JLabel lblNewLabel = new JLabel("Events");
                        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        lblNewLabel.setBounds(10, 11, 200, 40);
                        lblNewLabel.setFont(new Font("PixelMplus10", Font.PLAIN, 28));
                        lblNewLabel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
                        getContentPane().add(lblNewLabel);
                        
                        JLabel lblNewLabel_1 = new JLabel("");
                        lblNewLabel_1.setBounds(0, 0, 1184, 561);
                        lblNewLabel_1.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
                        getContentPane().add(lblNewLabel_1);
                        
                        backButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                            	Main7 main7 = new Main7();
                                main7.setVisible(true);
                                setVisible(false);
                            }
                        });
                        removeButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                removeRow();
                            }
                        });
                        addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addRow();
                    		}
                        });
                        
                     // Add a ListSelectionListener to the table to enable/disable removeButton based on row selection
                        ListSelectionModel selectionModel = table.getSelectionModel();
                        selectionModel.addListSelectionListener(new ListSelectionListener() {
                            @Override
                            public void valueChanged(ListSelectionEvent e) {
                                boolean enableRemoveButton = !selectionModel.isSelectionEmpty();
                                removeButton.setEnabled(enableRemoveButton);
                            }
                        });

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
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM events");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                model.addColumn(metaData.getColumnName(columnIndex));
            }

            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getObject(i);
                }
                model.addRow(rowData);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    

    private void addRow() {
        // Create JTextFields for user input
        JTextField nameField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField timeField = new JTextField();
        JTextField materialsField = new JTextField();
        JTextField locationField = new JTextField();

        // Create an array of components to display in the JOptionPane
        Object[] message = {
                "Name:", nameField,
                "Date:", dateField,
                "Time:", timeField,
                "Materials/Equipments:", materialsField,
                "Location:", locationField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Enter New Event Details", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            // Create an array to store user input
            Object[] rowData = new Object[model.getColumnCount()];

            // Set the values in the array
            rowData[0] = nameField.getText();
            rowData[1] = dateField.getText();
            rowData[2] = timeField.getText();
            rowData[3] = materialsField.getText();
            rowData[4] = locationField.getText();

            // Add the row to the table model
            model.addRow(rowData);
            
            // Insert a log statement into the database
            insertLogToDatabase("P.R.O. has added an event:" + nameField.getText());

            // Save the data to the database
            saveToDatabase(nameField.getText(), dateField.getText(), timeField.getText(), materialsField.getText(), locationField.getText());
        }
    }

    
    private void removeRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Retrieve data for the selected row
            String name = (String) model.getValueAt(selectedRow, 0);
            String date = (String) model.getValueAt(selectedRow, 1);
            String time = (String) model.getValueAt(selectedRow, 2);
            String materials = (String) model.getValueAt(selectedRow, 3);
            String location = (String) model.getValueAt(selectedRow, 4);

            // Remove the row from the table model
            model.removeRow(selectedRow);

            // Remove the row from the database
            removeFromDatabase(name, date, time, materials, location);
        	// Insert a log statement into the database
            insertLogToDatabase("P.R.O. has removed an event: " + name);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to remove.");
        }
    }
    
    private void saveToDatabase(String name, String date, String time, String materials, String location) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);

            String query = "INSERT INTO events (Name, Date, Time, Materials, Location) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, date);
                preparedStatement.setString(3, time);
                preparedStatement.setString(4, materials);
                preparedStatement.setString(5, location);

                preparedStatement.executeUpdate();
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void removeFromDatabase(String name, String date, String time, String materials, String location) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);

            String query = "DELETE FROM events WHERE Name = ? AND Date = ? AND Time = ? AND Materials = ? AND Location = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, date);
                preparedStatement.setString(3, time);
                preparedStatement.setString(4, materials);
                preparedStatement.setString(5, location);

                preparedStatement.executeUpdate();
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void insertLogToDatabase(String logStatement) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);

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
                new Events7().setVisible(true);
            }
        });
    }
}