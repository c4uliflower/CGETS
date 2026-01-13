package CGETS;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class Clearance extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton backButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Clearance frame = new Clearance();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Clearance() {
    	loadCustomFont();
    	getContentPane().setBackground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 600);
        setTitle("Clearance");
        setResizable(false);

        // Load the icon image
        ImageIcon icon = new ImageIcon("C:\\Users\\PC01\\eclipse-workspace\\kean\\src\\CGETS\\362305135_3609898249334879_8434680016085167769_n.png");
        setIconImage(icon.getImage());

        // Initialize tableModel with column names
        tableModel = new DefaultTableModel() {
            // Override the isCellEditable method to make "Name" and "Student ID" columns uneditable
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Only allow editing in the "Clearance" column
            }
        };
        tableModel.addColumn("Student_ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Clearance");

        // Initialize JTable with the DefaultTableModel
        table = new JTable(tableModel);
        table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        table.setBackground(Color.BLACK);
        table.setForeground(Color.GREEN);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoCreateRowSorter(true);  // Enable row sorting
        // Add a table model listener to capture changes in the table
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                // Check if the modification is in the "Clearance" column
                if (column == 2) {
                    Object data = tableModel.getValueAt(row, column);
                    if (data != null) {
                        saveDataToDatabase();
                    }
                }
            }
        });

        // Add a mouse listener to the table to handle double-click events in the "Clearance" column
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Double-click
                    int column = table.columnAtPoint(e.getPoint());
                    int row = table.rowAtPoint(e.getPoint());
                    if (column == 2) { // Assuming "Clearance" is the third column (index 2)
                        editClearanceValue(row, column);
                    }
                }
            }
        });

        // Add a focus listener to clear the selection when focus is lost
        table.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                table.clearSelection();
            }
        });
        getContentPane().setLayout(null);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setViewportBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
        scrollPane.setBounds(224, 11, 950, 539);
        scrollPane.getViewport().setBackground(Color.BLACK);
        getContentPane().add(scrollPane);
        
        JLabel lblNewLabel = new JLabel("Clearance");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(10, 11, 200, 40);
        lblNewLabel.setFont(new Font("PixelMplus10", Font.PLAIN, 28));
        lblNewLabel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
        getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setBounds(0, 0, 1184, 561);
        lblNewLabel_1.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
        getContentPane().add(lblNewLabel_1);
        
                // Initialize Back button
		        JButton backButton = new JButton("Back");
		        backButton.setBackground(new Color(192, 192, 192));
		        backButton.setBounds(50, 489, 119, 50);
		        backButton.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
		        backButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
		        getContentPane().add(backButton);
                getContentPane().add(backButton);
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Main main = new Main();
                        main.setVisible(true);
                        setVisible(false);
                    }
                });
        loadDataFromDatabase();
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

    private void editClearanceValue(int row, int column) {
        if (column == 2) { // Check for "Clearance" column
            String currentValue = table.getValueAt(row, column).toString();
            // Save changes to the database immediately after updating the table
            saveDataToDatabase();
        }
    }

    private void saveDataToDatabase() {
        // Update the database with the modified values in the table
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbase", "root", "")) {
            connection.setAutoCommit(false);

            String updateQuery = "UPDATE clearance SET clearance = ? WHERE Student_ID = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                for (int row = 0; row < table.getRowCount(); row++) {
                    String studentId = table.getValueAt(row, 0).toString();
                    String clearanceValue = table.getValueAt(row, 2).toString();

                    updateStatement.setString(1, clearanceValue);
                    updateStatement.setString(2, studentId);
                    updateStatement.executeUpdate();
                }

                // Commit changes
                connection.commit();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromDatabase() {
        // Populate the table with data from the database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbase", "root", "")) {
            String query = "SELECT Student_ID, Name, clearance FROM clearance";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("Student_ID");
                    String name = resultSet.getString("Name");
                    String clearance = resultSet.getString("Clearance");

                    // Add a row to the table with data from the database
                    tableModel.addRow(new Object[]{id, name, clearance});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
