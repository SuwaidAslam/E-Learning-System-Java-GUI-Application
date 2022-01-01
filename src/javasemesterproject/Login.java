package javasemesterproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javasemesterproject.Admin.AdminLogin;
import javasemesterproject.Student.StudentLogin;
import javasemesterproject.Teacher.TeacherLogin;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Login extends JFrame implements ActionListener{
     JPanel panel;
     JButton studentLoginButton, teacherLoginButton, adminLoginButton;
     JLabel title;
    public Login(){
        super("Login");
        setSize(360,260);
        setLocation(550,365);
        setLayout(new BorderLayout());
        
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("javasemesterproject/icons/systemIcon.png"));
        setIconImage(icon.getImage());
        
        panel = new JPanel();
        panel.setLayout(null);
        
        title = new JLabel("Login Here", JLabel.CENTER);
        title.setFont(title.getFont().deriveFont (22.0f));
        title.setBackground(Color.LIGHT_GRAY);
        title.setForeground(Color.BLACK);
        title.setOpaque(true);
        
        studentLoginButton = new  JButton("Student Login");
        studentLoginButton.addActionListener((ActionListener) this);
        studentLoginButton.setHorizontalAlignment(JButton.CENTER);
        studentLoginButton.setBounds(115, 15, 120, 40);
        
        teacherLoginButton = new  JButton("Teacher Login");
        teacherLoginButton.addActionListener((ActionListener) this);
        teacherLoginButton.setHorizontalAlignment(JButton.CENTER);
        teacherLoginButton.setBounds(115, 75, 120, 40);
        
        adminLoginButton = new  JButton("Admin Login");
        adminLoginButton.addActionListener((ActionListener) this);
        adminLoginButton.setHorizontalAlignment(JButton.CENTER);
        adminLoginButton.setBounds(115, 135, 120, 40);
        
        // add to panel
        panel.add(studentLoginButton);
        panel.add(teacherLoginButton);
        panel.add(adminLoginButton);
        
        // Add to Frame
        add(title, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
     @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == studentLoginButton){
            setVisible(false);
            new StudentLogin();
        }
        else if(ae.getSource() == teacherLoginButton){
            setVisible(false);
            new TeacherLogin();
        }
        else if(ae.getSource() == adminLoginButton){
            setVisible(false);
            new AdminLogin();
        }
    }
    public static void main(String[] args) {
        new Login();
    }
}
