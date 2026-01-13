package CGETS;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class Login extends JFrame {

    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JComboBox<String> comboBoxUsertype;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Login() {
    	getContentPane().setForeground(Color.WHITE);
    	getContentPane().setBackground(Color.BLACK);
    	setTitle("CGATES Management System");
    	setResizable(false);
    	
    	// Load the custom font
        loadCustomFont();
    	
    	ImageIcon icon = new ImageIcon("C:\\Users\\PC01\\eclipse-workspace\\kean\\src\\CGETS\\302481053_569613294897707_4526454162707963317_n.jpg");
        setIconImage(icon.getImage());
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setUndecorated(true); 
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        getRootPane().setBorder(null);
        
        
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setForeground(Color.GREEN);
        lblUsername.setBackground(Color.BLACK);
        lblUsername.setFont(new Font("PixelMplus10", Font.PLAIN, 11));
        lblUsername.setBounds(40, 100, 74, 14);
        lblUsername.setOpaque(true);
        getContentPane().add(lblUsername);

        textFieldUsername = new JTextField();
        textFieldUsername.setForeground(Color.BLACK);
        textFieldUsername.setBackground(Color.WHITE);
        textFieldUsername.setBounds(154, 97, 150, 20);
        getContentPane().add(textFieldUsername);
        textFieldUsername.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("x");
        lblNewLabel_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        lblNewLabel_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setFont(new Font("PixelMplus10", Font.BOLD, 15));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(360, 3, 46, 22);
        getContentPane().add(lblNewLabel_1);
               
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setForeground(Color.GREEN);
        lblPassword.setBackground(Color.BLACK);
        lblPassword.setFont(new Font("PixelMplus10", Font.PLAIN, 11));
        lblPassword.setBounds(40, 150, 74, 14);
        lblPassword.setOpaque(true);
        getContentPane().add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setForeground(Color.BLACK);
        passwordField.setBackground(Color.WHITE);
        passwordField.setBounds(154, 147, 150, 20);
        getContentPane().add(passwordField);
        
        JLabel lblNewLabel = new JLabel("LOGIN");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("PixelMplus10", Font.BOLD, 25));
        lblNewLabel.setToolTipText("");
        lblNewLabel.setBounds(144, 21, 120, 53);
        getContentPane().add(lblNewLabel);

        JLabel lblUsertype = new JLabel("Position:");
        lblUsertype.setForeground(Color.GREEN);
        lblUsertype.setBackground(Color.BLACK);
        lblUsertype.setFont(new Font("PixelMplus10", Font.PLAIN, 11));
        lblUsertype.setBounds(40, 200, 74, 14);
        lblUsertype.setOpaque(true);
        getContentPane().add(lblUsertype);

        comboBoxUsertype = new JComboBox<>();
        comboBoxUsertype.setFont(new Font("PixelMplus10", Font.ITALIC, 11));
        comboBoxUsertype.setForeground(Color.WHITE);
        comboBoxUsertype.setBackground(Color.BLACK);
        comboBoxUsertype.addItem("None");
        comboBoxUsertype.addItem("President");
        comboBoxUsertype.addItem("Vice President Internal");
        comboBoxUsertype.addItem("Vice President External");
        comboBoxUsertype.addItem("Secretary");
        comboBoxUsertype.addItem("Treasurer");
        comboBoxUsertype.addItem("Auditor");
        comboBoxUsertype.addItem("P.R.O.");
        comboBoxUsertype.setBounds(154, 197, 150, 20);
        getContentPane().add(comboBoxUsertype);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(0, 128, 0));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("PixelMplus10", Font.PLAIN, 11));
        btnLogin.setHorizontalAlignment(SwingConstants.CENTER);
        btnLogin.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnLogin.addActionListener(e -> {
            String username = textFieldUsername.getText();
            String password = new String(passwordField.getPassword());
            String usertype = comboBoxUsertype.getSelectedItem().toString();

            if (authenticateUser(username, password, usertype)) {
                goToMain();
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect Username, Password, or Position");
            }
        });
        btnLogin.setBounds(275, 250, 89, 23);
        getContentPane().add(btnLogin);
        Image img = new ImageIcon(this.getClass().getResource("302481053_569613294897707_4526454162707963317_n.jpg")).getImage();
        int newWidth = 480;  // Set the desired width
        int newHeight = 480; // Set the desired height
        Image resizedImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
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


    private void goToMain() {
        String selectedUserType = comboBoxUsertype.getSelectedItem().toString();

        switch (selectedUserType) {
            case "Treasurer":
                openMain2();
                break;
            case "President":
            	openMain();
            	break;
            case "Vice President Internal":
            	openMain3();
            	break;
            case "Vice President External":
            	openMain4();
            	break;
            case "Secretary":
            	openMain5();
            	break;
            case "Auditor":
            	openMain6();
            	break;
            case "P.R.O.":
            	openMain7();
            	break;
            default:
                openMain();
        }

        setVisible(false); // Hide the login page
    }

    private void openMain7() {
    	Main7 main7 = new Main7();
        main7.setVisible(true);
    }

	private void openMain6() {
    	Main6 main6 = new Main6();
        main6.setVisible(true);
    }

	private void openMain5() {
    	Main5 main5 = new Main5();
        main5.setVisible(true);
    }

	private void openMain4() {
    	Main4 main4 = new Main4();
        main4.setVisible(true);
    }

	private void openMain2() {
        Main2 main2 = new Main2();
        main2.setVisible(true);
    }	

    private void openMain() {
        Main main = new Main();
        main.setVisible(true);
    }
    
    private void openMain3() {
        Main3 main3 = new Main3();
        main3.setVisible(true);
    }


	private boolean authenticateUser(String username, String password, String usertype) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbase", "root", "");

            String query = "SELECT * FROM users WHERE username=? AND password=? AND usertype=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, usertype);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // If there is a matching record, authentication is successful

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

