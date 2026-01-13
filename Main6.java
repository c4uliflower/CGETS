package CGETS;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;

public class Main6 extends JFrame {
    private JPanel contentPane;
    private Image backgroundImage;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main6 frame = new Main6();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public Main6() {
    	loadCustomFont();
        initializeUI();
        setupListeners();
    	setTitle("CGATES Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        
        //Load the icon image
        ImageIcon icon = new ImageIcon("C:\\Users\\PC01\\eclipse-workspace\\kean\\src\\CGETS\\362305135_3609898249334879_8434680016085167769_n.png");
        setIconImage(icon.getImage());
        
        //IMAGE USED
    	ImageIcon Iconevent = new ImageIcon("C:\\Users\\PC01\\eclipse-workspace\\kean\\src\\CGETS\\events.png");
    	ImageIcon Iconmember = new ImageIcon("C:\\Users\\PC01\\eclipse-workspace\\kean\\src\\CGETS\\members.png");
    	ImageIcon Iconfees = new ImageIcon("C:\\Users\\PC01\\eclipse-workspace\\kean\\src\\CGETS\\fees.png");
    	ImageIcon Iconarchive = new ImageIcon("C:\\Users\\PC01\\eclipse-workspace\\kean\\src\\CGETS\\archive.png");
    	ImageIcon Iconattendance = new ImageIcon("C:\\Users\\PC01\\eclipse-workspace\\kean\\src\\CGETS\\attendance.png");
    	ImageIcon Iconexit = new ImageIcon("C:\\Users\\PC01\\eclipse-workspace\\kean\\src\\CGETS\\exit.png");

        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("C:\\Users\\PC01\\eclipse-workspace\\kean\\src\\CGETS\\background2.png");
                Image backgroundImage = img.getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnNewButton = new JButton("EVENTS");
        btnNewButton = new JButton();
        btnNewButton.setBounds(558, 100, 175, 175);
        btnNewButton.setOpaque(false);
        btnNewButton.setContentAreaFilled(false);
        btnNewButton.setBorderPainted(false);
        btnNewButton.setFocusable(false);
        btnNewButton.setIcon(Iconevent);
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Events6 events6 = new Events6();
        		events6.setVisible(true);
        		setVisible(false);
        	}
        });
        btnNewButton.setForeground(Color.BLACK);
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        contentPane.add(btnNewButton);

        JButton btnNewButton_3 = new JButton("MEMBERS");
        btnNewButton_3 = new JButton();
        btnNewButton_3.setBounds(821, 100, 175, 175);
        btnNewButton_3.setOpaque(false);
        btnNewButton_3.setContentAreaFilled(false);
        btnNewButton_3.setBorderPainted(false);
        btnNewButton_3.setFocusable(false);
        btnNewButton_3.setIcon(Iconmember);
        btnNewButton_3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Members6 members6 = new Members6();
        		members6.setVisible(true);
        		setVisible(false);
        	}
        });
        btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 11));
        contentPane.add(btnNewButton_3);
        
        JButton btnNewButton_4 = new JButton("ARCHIVE\r\n");
        btnNewButton_4 = new JButton();
        btnNewButton_4.setBounds(558, 304, 175, 175);
        btnNewButton_4.setOpaque(false);
        btnNewButton_4.setContentAreaFilled(false);
        btnNewButton_4.setBorderPainted(false);
        btnNewButton_4.setFocusable(false);
        btnNewButton_4.setIcon(Iconarchive);
        btnNewButton_4.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Archives6 archives6 = new Archives6();
        		archives6.setVisible(true);
        		setVisible(false);
        	}
        });
        btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 11));
        contentPane.add(btnNewButton_4);
        
        JButton btnNewButton_5 = new JButton("EXIT\r\n");
        btnNewButton_5 = new JButton();
        btnNewButton_5.setText("Logout");
        btnNewButton_5.setBounds(410, 520, 150, 30);
        btnNewButton_5.setBackground(new Color(0, 128, 0));
        btnNewButton_5.setForeground(Color.WHITE);
        btnNewButton_5.setFont(new Font("PixelMplus10", Font.PLAIN, 16));
        btnNewButton_5.setHorizontalAlignment(SwingConstants.CENTER);
        btnNewButton_5.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        contentPane.add(btnNewButton_5);
        btnNewButton_5.addActionListener(e -> {
            // Show a confirmation dialog
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Confirmation", JOptionPane.YES_NO_OPTION);

            // Check the user's choice
            if (result == JOptionPane.YES_OPTION) {
                goToLogin();
            } else {

            }
        });
        
        JButton btnNewButton_6 = new JButton("Attendance\r\n");
        btnNewButton_6 = new JButton();
        btnNewButton_6.setBounds(821, 304, 175, 175);
        btnNewButton_6.setOpaque(false);
        btnNewButton_6.setContentAreaFilled(false);
        btnNewButton_6.setBorderPainted(false);
        btnNewButton_6.setFocusable(false);
        btnNewButton_6.setIcon(Iconattendance);
        btnNewButton_6.setFont(new Font("PixelMplus10", Font.BOLD, 11));
        contentPane.add(btnNewButton_6);
        
        btnNewButton_6.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Attendance6 attendance6 = new Attendance6();
        		attendance6.setVisible(true);
        		setVisible(false);
        	}
        });
        
        JLabel lblNewLabel = new JLabel("Welcome to the");
        lblNewLabel.setBackground(Color.WHITE);
        lblNewLabel.setFont(new Font("PixelMplus10", Font.BOLD, 25));
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setBounds(75, 260, 284, 75);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("CGATES Organizational");
        lblNewLabel_1.setFont(new Font("PixelMplus10", Font.BOLD, 25));
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setBounds(75, 310, 334, 75);
        contentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Management System");
        lblNewLabel_2.setFont(new Font("PixelMplus10", Font.BOLD, 25));
        lblNewLabel_2.setForeground(Color.WHITE);
        lblNewLabel_2.setBounds(75, 360, 283, 75);
        contentPane.add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Copyright © 2023 Group 3. All rights reserved.");
        lblNewLabel_3.setFont(new Font("PixelMplus10", Font.PLAIN, 10));
        lblNewLabel_3.setForeground(Color.WHITE);
        lblNewLabel_3.setBounds(940, 490, 253, 35);
        contentPane.add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("");
        lblNewLabel_4.setBounds(50, 149, 100, 100);
        contentPane.add(lblNewLabel_4);
        
        JLabel lblNewLabel_5 = new JLabel("");
        lblNewLabel_5.setIcon(new ImageIcon(Main.class.getResource("/CGETS/370270937_2430242113812399_6246499789382381799_n.png")));
        lblNewLabel_5.setBounds(20, 60, 250, 250);
        contentPane.add(lblNewLabel_5);
       	}
    
    private void goToLogin() {
    	Login login = new Login();
    	login.setVisible(true);
    	setVisible(false);
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
		// TODO Auto-generated method stub
		
	}

	private void initializeUI() {
		// TODO Auto-generated method stub
		
	}
}