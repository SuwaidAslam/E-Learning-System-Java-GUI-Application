package javasemesterproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javasemesterproject.Student.StudentSignup;
import javasemesterproject.Teacher.TeacherSignup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Signup extends JFrame implements ActionListener{
    JPanel panel;
     JButton studentSignUpButton, teacherSignUpButton;
     JLabel title;
    public Signup(){
        super("Signup");
        setSize(360,220);
        setLocation(550,365);
        setLayout(new BorderLayout());
        
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("javasemesterproject/icons/systemIcon.png"));
        setIconImage(icon.getImage());
        
        panel = new JPanel();
        panel.setLayout(null);
        
        title = new JLabel("SignUp Here", JLabel.CENTER);
        title.setBackground(Color.LIGHT_GRAY);
        title.setForeground(Color.BLACK);
        title.setOpaque(true);
        title.setFont(title.getFont ().deriveFont (22.0f));
        
        
        studentSignUpButton = new  JButton("Student SignUp");
        studentSignUpButton.addActionListener((ActionListener) this);
        studentSignUpButton.setHorizontalAlignment(JButton.CENTER);
        studentSignUpButton.setBounds(115, 15, 130, 40);
        
        teacherSignUpButton = new  JButton("Teacher SignUp");
        teacherSignUpButton.addActionListener((ActionListener) this);
        teacherSignUpButton.setHorizontalAlignment(JButton.CENTER);
        teacherSignUpButton.setBounds(115, 95, 130, 40);

        
        
        // add to panel
        panel.add(studentSignUpButton);
        panel.add(teacherSignUpButton);
        
        // Add to Frame
        add(title, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
         
        // Settings for the frame
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
     @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == studentSignUpButton){
            setVisible(false);
            new StudentSignup();
        }
        else if(ae.getSource() == teacherSignUpButton){
            setVisible(false);
            new TeacherSignup();
        }
    }
    public static void main(String[] args) {
        new Signup();
    }
}
