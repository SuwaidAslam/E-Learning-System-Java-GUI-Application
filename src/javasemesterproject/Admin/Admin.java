package javasemesterproject.Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javasemesterproject.DBConnection;
import javasemesterproject.Main;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Admin extends JFrame implements ActionListener, WindowStateListener{
    JPanel sidePanel, rightPanel, buttonsPanel;
    JButton manageAccount, viewProfileBtn, logoutBtn;
    JLabel label, backgroundPic;
    JButton b1,b2, b3, b4, b5, b6, b7, b8, b9;
    JLabel usericon, lblUsername;
    BufferedImage bufferedImage = null;
    public Admin(){
        super("Admin Module");
        setSize(1280,720);
        setLocation(35,30);
        setLayout(new BorderLayout());
        
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("javasemesterproject/icons/systemIcon.png"));
        setIconImage(icon.getImage());
      
        sidePanel = new JPanel();
        sidePanel.setLayout(null);
        sidePanel.setBackground(new Color(0, 26, 195));
        Dimension sidePanelSize = new Dimension(180, 720);
        sidePanel.setPreferredSize(sidePanelSize);
        add(sidePanel, BorderLayout.WEST);
        
        
        //get Name and Profile from database
        String firstName = null,lastName = null;
        byte[] bytImage = null;
        try{
            DBConnection c1 = new DBConnection();
            
            PreparedStatement ps = c1.c.prepareStatement("select * from Admin where Adminid = '"+ AdminLogin.currentAdminID +"'");
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                firstName = rs.getString("fname");
                lastName = rs.getString("lname");
                //get image as byte
                bytImage = rs.getBytes("picture");
            }else{
                JOptionPane.showMessageDialog(null, "Not Found");
            }
            ps.close();
        }catch(HeadlessException | NumberFormatException | SQLException e){
            e.printStackTrace();
        }
        
        //sidePanel Code           
        if(bytImage != null){
           InputStream is = new ByteArrayInputStream(bytImage);
            try {
                bufferedImage = ImageIO.read(is);
                usericon = new JLabel(resizeImage(bufferedImage));
            } catch (IOException ex) {
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            usericon = new JLabel(new ImageIcon(ClassLoader.getSystemResource("javasemesterproject/icons/uploadPicIcon.png")));
        }

        usericon.setBounds(38, 5, 96, 96);
        usericon.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        sidePanel.add(usericon);
       
        
        lblUsername = new JLabel();
        lblUsername.setFont(new Font(Font.SERIF,Font.BOLD, 20));
        lblUsername.setForeground(new Color(45,255,3));
        lblUsername.setBounds(20, 98, 150, 40);
        lblUsername.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        lblUsername.setText(firstName + " " + lastName);
        sidePanel.add(lblUsername);
        
        viewProfileBtn = new JButton("View Profile");
        viewProfileBtn.setFont(new Font(Font.SERIF,Font.BOLD, 13));
        viewProfileBtn.setBackground(Color.BLACK);
        viewProfileBtn.setForeground(Color.WHITE);
        viewProfileBtn.setBounds(30, 150, 120, 28);
        viewProfileBtn.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        viewProfileBtn.addActionListener((ActionListener) this);
        sidePanel.add(viewProfileBtn);
        
        
        logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font(Font.SERIF,Font.BOLD, 13));
        logoutBtn.setBackground(Color.BLACK);
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setBounds(30, 600, 120, 28);
        logoutBtn.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        logoutBtn.addActionListener((ActionListener) this);
        sidePanel.add(logoutBtn);
        
        //rightPanel Code
        rightPanel = new JPanel(new BorderLayout());
        add(rightPanel, BorderLayout.CENTER);
       
        
        JLabel mainTitle = new JLabel("Admin Module");
        mainTitle.setHorizontalAlignment(JLabel.CENTER);
        mainTitle.setFont(new Font(Font.SERIF,Font.BOLD, 50));
        mainTitle.setBackground(Color.BLACK);
        mainTitle.setForeground(Color.WHITE);
        mainTitle.setOpaque(true);
        rightPanel.add(mainTitle, BorderLayout.NORTH);
        
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(null);
        rightPanel.add(buttonsPanel, BorderLayout.CENTER);
        
        JLabel buttonSectionTitle = new JLabel("My Account");
        buttonSectionTitle.setFont(new Font(Font.SERIF,Font.BOLD, 25));
        buttonSectionTitle.setForeground(Color.BLACK);
        buttonSectionTitle.setHorizontalAlignment(JLabel.LEFT);
        buttonSectionTitle.setBounds(6,6,150,50);
        buttonsPanel.add(buttonSectionTitle);
       
        // 64 is the size of Button Icon https://icons8.com/
        
        b1 = new JButton("Manage Account");
        b1.setIcon(new ImageIcon(ClassLoader.getSystemResource("javasemesterproject/icons/ManageAccount.png")));
        b1.setBounds(250,60,130,90);
        b1.setHorizontalTextPosition(JButton.CENTER);
        b1.setVerticalTextPosition(JButton.BOTTOM);
        b1.addActionListener((ActionListener) this);
        buttonsPanel.add(b1);
        
        b2 = new JButton("Delete Account");
        b2.setIcon(new ImageIcon(ClassLoader.getSystemResource("javasemesterproject/icons/DeleteAccount.png")));
        b2.setBounds(410,60,130,90);
        b2.setHorizontalTextPosition(JButton.CENTER);
        b2.setVerticalTextPosition(JButton.BOTTOM);
        b2.addActionListener((ActionListener) this);
        buttonsPanel.add(b2);
        
        // gap to 160 Horizontally
        b3 = new JButton("View Account");
        b3.setIcon(new ImageIcon(ClassLoader.getSystemResource("javasemesterproject/icons/viewAccount.png")));
        b3.setBounds(570,60,130,90);
        b3.setHorizontalTextPosition(JButton.CENTER);
        b3.setVerticalTextPosition(JButton.BOTTOM);
        b3.addActionListener((ActionListener) this);
        buttonsPanel.add(b3);
        
        
        // second Row of Buttons
        JLabel buttonSectionTitle2 = new JLabel("Admin Operations");
        buttonSectionTitle2.setFont(new Font(Font.SERIF,Font.BOLD, 25));
        buttonSectionTitle2.setForeground(Color.BLACK);
        buttonSectionTitle2.setHorizontalAlignment(JLabel.LEFT);
        buttonSectionTitle2.setBounds(6,150,210,50);
        buttonsPanel.add(buttonSectionTitle2);
       
        // 64 is the size of Button Icon https://icons8.com/
        
        b4 = new JButton("Add Subject");
        b4.setIcon(new ImageIcon(ClassLoader.getSystemResource("javasemesterproject/icons/addSubject.png")));
        b4.setBounds(250,210,130,90);
        b4.setHorizontalTextPosition(JButton.CENTER);
        b4.setVerticalTextPosition(JButton.BOTTOM);
        b4.addActionListener((ActionListener) this);
        buttonsPanel.add(b4);
        
        b5 = new JButton("Delete Subject");
        b5.setIcon(new ImageIcon(ClassLoader.getSystemResource("javasemesterproject/icons/deleteSubject.png")));
        b5.setBounds(410,210,130,90);
        b5.setHorizontalTextPosition(JButton.CENTER);
        b5.setVerticalTextPosition(JButton.BOTTOM);
        b5.addActionListener((ActionListener) this);
        buttonsPanel.add(b5);
        
        // gap to 160 Horizontally
        b6 = new JButton("View Students");
        b6.setIcon(new ImageIcon(ClassLoader.getSystemResource("javasemesterproject/icons/viewStudents.png")));
        b6.setBounds(570,210,130,90);
        b6.setHorizontalTextPosition(JButton.CENTER);
        b6.setVerticalTextPosition(JButton.BOTTOM);
        b6.addActionListener((ActionListener) this);
        buttonsPanel.add(b6);
        
        b7 = new JButton("View Teachers");
        b7.setIcon(new ImageIcon(ClassLoader.getSystemResource("javasemesterproject/icons/viewTeachers.png")));
        b7.setBounds(250,320,130,90);
        b7.setHorizontalTextPosition(JButton.CENTER);
        b7.setVerticalTextPosition(JButton.BOTTOM);
        b7.addActionListener((ActionListener) this);
        buttonsPanel.add(b7);
        
        b8 = new JButton("View Courses");
        b8.setIcon(new ImageIcon(ClassLoader.getSystemResource("javasemesterproject/icons/viewCourses.png")));
        b8.setBounds(410,320,130,90);
        b8.setHorizontalTextPosition(JButton.CENTER);
        b8.setVerticalTextPosition(JButton.BOTTOM);
        b8.addActionListener((ActionListener) this);
        buttonsPanel.add(b8);
        
        
        b9 = new JButton("Add New Admin");
        b9.setIcon(new ImageIcon(ClassLoader.getSystemResource("javasemesterproject/icons/addNewAdmin.png")));
        b9.setBounds(570,320,130,90);
        b9.setHorizontalTextPosition(JButton.CENTER);
        b9.setVerticalTextPosition(JButton.BOTTOM);
        b9.addActionListener((ActionListener) this);
        buttonsPanel.add(b9);

        setResizable(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }
    // This code use to resize image to fit lable
    public ImageIcon resizeImage(BufferedImage bufferedImage){
        int width = bufferedImage.getWidth();
        BufferedImage circleBuffer = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = circleBuffer.createGraphics();
        g2.setClip(new Ellipse2D.Float(0, 0, width, width));
        g2.drawImage(bufferedImage, 0, 0, width, width, null);
        ImageIcon icon = new ImageIcon(circleBuffer);
        Image i2 = icon.getImage().getScaledInstance(96 ,96 ,Image.SCALE_DEFAULT);
        ImageIcon i3 =  new ImageIcon(i2);
        return i3;
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == b1){
            new AdminManageAccount();
        }
        else if(ae.getSource() == b2){
            AdminDeleteAccount delete = new AdminDeleteAccount();
            if(delete.input == 0){
                dispose();
                new Main();
            }
        }
        else if(ae.getSource() == b4){
            new AddSubject();
        }
        else if(ae.getSource() == b5){
            new DeleteSubject();
        }
        else if(ae.getSource() == b3){
            new AdminAccountDetails();
        }
        else if(ae.getSource() == viewProfileBtn){
            JOptionPane.showMessageDialog(this, new ImageIcon(bufferedImage), "Profile Picture", JOptionPane.PLAIN_MESSAGE);
        }
        else if(ae.getSource() == logoutBtn){
            JOptionPane.showMessageDialog(null, "Loggin Out...");
            new Main();
            dispose();
        }
        else if(ae.getSource() == b6){
            new ViewStudents();
        }
        else if(ae.getSource() == b7){
            new ViewTeachers();
        }
        else if(ae.getSource() == b8){
            new ViewCourses();
        }
        else if(ae.getSource() == b9){
            new AdminSignup();
        }
        
    }
    @Override
    public void windowStateChanged(WindowEvent e) {
        // normal state
        if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.NORMAL){
           logoutBtn.setLocation(30, 600);
        }
        // maximized
        else if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH){
            logoutBtn.setLocation(30, this.getHeight() - 120);
        }
    }
}
