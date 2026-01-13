package CGETS;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

public class Startup extends JFrame {

    public Startup() {
        // Set up the main frame
    	loadCustomFont();
        setTitle("CGATES Management System");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setResizable(false);
        
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("C:\\Users\\PC01\\eclipse-workspace\\kean\\src\\CGETS\\385533456_1381751519434186_8138988876347881573_n (1).png");
                Image backgroundImage = img.getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                
                ImageIcon icon = new ImageIcon("C:\\Users\\PC01\\eclipse-workspace\\kean\\src\\CGETS\\362305135_3609898249334879_8434680016085167769_n.png");
                setIconImage(icon.getImage());
            }
        };
        mainPanel.setBounds(0, 0, 1200, 600);

        // Create a button and add an ActionListener
        JButton proceedButton = new JButton("");
        proceedButton.setForeground(Color.BLACK);
        proceedButton.setFont(new Font("PixelMplus10", Font.PLAIN, 18));
        proceedButton.setBackground(Color.WHITE);
        proceedButton.setBounds(500, 420, 195, 25);
        proceedButton.setOpaque(false);
        proceedButton.setContentAreaFilled(true);
        proceedButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call a method to proceed to the main page
                goToLogin();
            }
        });
        getContentPane().setLayout(null);
        mainPanel.setLayout(null);

        // Add the button to the center of the panel
        mainPanel.add(proceedButton);

        // Add the panel to the frame
        getContentPane().add(mainPanel);

        // Make the frame visible
        setVisible(true);
    }
    

    // Method to proceed to the login page
    private void goToLogin() {
        // Assuming you have a MainPage class
        Login login = new Login(); // Pass the reference to this frame
        login.setVisible(true);
        setVisible(false); // Hide the startup page
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

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Startup();
            }
        });
    }
}