package javasemesterproject.Admin;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javasemesterproject.DBConnection;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AdminSignup extends JFrame implements ActionListener, FocusListener{
    JPanel contentPane;
    JTextField firstname, lastname, email, username;
    JPasswordField passwordField;
    JButton registerButton, uploadPicBtn;
    JRadioButton maleRB, femaleRB;
    ButtonGroup radioBtns;
    JLabel fnameValidation, LnameValidation, emailValidation, userNameValidation, passwordValidation, profilePicLbl;
    FileInputStream fis = null;
    File f = null;
    public AdminSignup() {
        super("Admin SignUp");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1014,515);
        setLocation(210,110);
        setResizable(false);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        profilePicLbl = new JLabel();
        profilePicLbl.setIcon(new ImageIcon (ClassLoader.getSystemResource("javasemesterproject/icons/uploadPicIcon.png")));
        profilePicLbl.setBounds(456, 18, 96, 96);
        contentPane.add(profilePicLbl);
        
        uploadPicBtn = new JButton("Upload");
        uploadPicBtn.setBounds(470, 120, 75, 23);
        uploadPicBtn.addActionListener(this);
        contentPane.add(uploadPicBtn);

        JLabel lblName = new JLabel("First name");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblName.setBounds(58, 152, 99, 43);
        contentPane.add(lblName);

        JLabel lblNewLabel = new JLabel("Last name");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel.setBounds(58, 243, 110, 29);
        contentPane.add(lblNewLabel);

        JLabel lblEmailAddress = new JLabel("Email\r\n address");
        lblEmailAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblEmailAddress.setBounds(58, 324, 124, 36);
        contentPane.add(lblEmailAddress);
        
        fnameValidation = new JLabel();
        firstname = new JTextField();
        firstname.setFont(new Font("Tahoma", Font.PLAIN, 32));
        firstname.addFocusListener(this);
        firstname.setBounds(214, 151, 228, 50);
        fnameValidation.setBounds(214, 205, 150, 10);
        contentPane.add(fnameValidation);
        contentPane.add(firstname);
        firstname.setColumns(10);

        LnameValidation = new JLabel();
        lastname = new JTextField();
        lastname.setFont(new Font("Tahoma", Font.PLAIN, 32));
        lastname.setBounds(214, 235, 228, 50);
        lastname.addFocusListener(this);
        LnameValidation.setBounds(214, 289, 150, 10);
        contentPane.add(LnameValidation);
        contentPane.add(lastname);
        lastname.setColumns(10);

        emailValidation = new JLabel();
        email = new JTextField();
        email.setFont(new Font("Tahoma", Font.PLAIN, 32));
        email.setBounds(214, 320, 228, 50);
        email.addFocusListener(this);
        emailValidation.setBounds(214, 374, 150, 10);
        contentPane.add(emailValidation);
        contentPane.add(email);
        email.setColumns(10);

        userNameValidation = new JLabel();
        username = new JTextField();
        username.setFont(new Font("Tahoma", Font.PLAIN, 32));
        username.setBounds(707, 151, 228, 50);
        username.addFocusListener(this);
        userNameValidation.setBounds(707, 205, 150, 10);
        contentPane.add(userNameValidation);
        contentPane.add(username);
        username.setColumns(10);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblUsername.setBounds(542, 159, 99, 29);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblPassword.setBounds(542, 245, 99, 24);
        contentPane.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordField.setBounds(707, 235, 228, 50);
        contentPane.add(passwordField);
        
        JLabel genderLbl = new JLabel("Gender");
        genderLbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
        genderLbl.setBounds(542, 331, 99, 24);
        contentPane.add(genderLbl);
        
        maleRB = new JRadioButton("Male");
        femaleRB = new JRadioButton("Female");
        radioBtns = new ButtonGroup();

        maleRB.setFont(new Font("Tahoma", Font.PLAIN, 25));
        maleRB.setBounds(707, 321, 110, 50);
        maleRB.setActionCommand("Male");
        contentPane.add(maleRB);
        
        femaleRB.setFont(new Font("Tahoma", Font.PLAIN, 25));
        femaleRB.setBounds(825, 321, 120, 50);
        femaleRB.setActionCommand("Female");
        contentPane.add(femaleRB);
        
        radioBtns.add(maleRB);
        radioBtns.add(femaleRB);

        registerButton = new JButton("Register");
        registerButton.setBounds(410, 400, 228, 60);
        registerButton.addActionListener((ActionListener) this);
        contentPane.add(registerButton);
        
        setVisible(true);
    }
    // This code use to resize image to fit lable
    public ImageIcon resizeImage(String imagePath){
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File(imagePath));
        } catch (IOException ex) {
            Logger.getLogger(AdminSignup.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        if(ae.getSource() == uploadPicBtn){
            String fname = null;
            JFileChooser fchoser = new JFileChooser();
            fchoser.showOpenDialog(null);
            f = fchoser.getSelectedFile();
            fname=f.getAbsolutePath();
            ImageIcon micon=new ImageIcon(fname);
            
            try {
                File image=new File(fname);
                fis = new FileInputStream(image);
                profilePicLbl.setIcon(resizeImage(fname));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        else{
            String firstName = firstname.getText();
            String lastName = lastname.getText();
            String emailId = email.getText();
            String userName = username.getText();
            String password = String.valueOf(passwordField.getPassword());
            String genderStr = "";
            if(radioBtns.getSelection() != null)
                genderStr = radioBtns.getSelection().getActionCommand();
            String msg = "" + firstName;
            if(fnameValidation.getText().isEmpty() && 
                    LnameValidation.getText().isEmpty() && 
                    emailValidation.getText().isEmpty() && 
                    userNameValidation.getText().isEmpty()){
                if(firstName.isEmpty() || lastName.isEmpty() || emailId.isEmpty() || userName.isEmpty() || password.isEmpty()
                    || genderStr.isEmpty() || this.f == null || this.fis == null){
                JOptionPane.showMessageDialog(null, "Please Fill All Fields !");
                }
                else{
                    DBConnection c1 = new DBConnection();
                    try{
                        PreparedStatement ps = c1.c.prepareStatement("Insert into Admin (fname, lname, Email_ID, username, password, Gender, picture) "
                                + "values(?,?,?,?,?,?,?)");
                        ps.setString(1, firstName);
                        ps.setString(2, lastName);
                        ps.setString(3, emailId);
                        ps.setString(4, userName);
                        ps.setString(5, password);
                        ps.setString(6, genderStr);
                        ps.setBinaryStream(7,(InputStream)fis,(int)f.length());
                        int x =  ps.executeUpdate();
                        ps.close();
                        if(x == 0){
                            JOptionPane.showMessageDialog(null, "This User already exist");
                        }else{
                            JOptionPane.showMessageDialog(null, "Welcome, "+ msg + " Your account is successfully created."
                                    + "You can Now Log into your Account.");
                            setVisible(false);
                            new AdminLogin();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    finally{
                        c1.Close();
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Please Fill accurate Info !");
            }
        }
    }
    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource() == firstname)
            fnameValidation.setText("");
        else if(e.getSource() == lastname)
            LnameValidation.setText("");
        else if(e.getSource() == email)
            emailValidation.setText("");
        else if(e.getSource() == username)
            userNameValidation.setText("");
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(e.getSource() == firstname){
            String fName = firstname.getText();
            if(fName.isEmpty()){
                fnameValidation.setText("Enter First Name");
            }
            else{
                boolean valid = fName.matches("[A-Z][a-z]*");
                if(!valid)
                    fnameValidation.setText("Invalid First Name");
                else
                    fnameValidation.setText("");
            }
        }
        else if(e.getSource() == lastname){
            String LName = lastname.getText();
            if(LName.isEmpty()){
                LnameValidation.setText("Enter Last Name");
            }
            else{
                boolean valid = LName.matches("[A-Z][a-z]*");
                if(!valid)
                    LnameValidation.setText("Invalid Last Name");
                else
                    LnameValidation.setText("");
            }
        }
        else if(e.getSource() == email){
            String emailTxt = email.getText();
            if(emailTxt.isEmpty()){
                emailValidation.setText("Enter Email");
            }
            else{
                boolean valid = emailTxt.matches("[\\w]+@[\\w]+\\.[a-zA-Z]{2,3}");
                if(!valid)
                    emailValidation.setText("Invalid Email");
                else
                    emailValidation.setText("");
            }
        }
        else if(e.getSource() == username){
            String usernameTxt = username.getText();
            if(usernameTxt.isEmpty()){
                userNameValidation.setText("Enter UserName");
            }
            else{
                boolean valid = usernameTxt.matches("\\b[a-zA-Z][a-zA-Z0-9\\-._]{3,}\\b");
                if(!valid)
                    userNameValidation.setText("Invalid UserName");
                else
                    userNameValidation.setText("");
            }
        }
    }
    public static void main(String[] args) {
        new AdminSignup();
    }

}
