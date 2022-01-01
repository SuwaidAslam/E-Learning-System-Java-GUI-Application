package javasemesterproject.Teacher;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javasemesterproject.DBConnection;
import javasemesterproject.Main;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class TeacherLogin extends JFrame implements ActionListener{
    JLabel l1,l2;
    JTextField t1;
    JPasswordField t2;
    JButton b1,b2;
    public static int currentTeacherID;
    public TeacherLogin(){
        super("Teacher Login");
        
        setLayout(null);

        l1 = new JLabel("Username");
        l1.setBounds(40,20,100,30);
        add(l1);
        
        l2 = new JLabel("Password");
        l2.setBounds(40,70,100,30);
        add(l2);
 
        t1=new JTextField();
        t1.setBounds(150,20,150,30);
        add(t1);

        t2=new JPasswordField();
        t2.setBounds(150,70,150,30);
        add(t2);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("javasemesterproject/icons/second.jpg"));
        Image i2 = i1.getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT);
        ImageIcon i3 =  new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(350,20,150,150);
        add(l3);


        b1 = new JButton("Login");
        b1.setBounds(40,140,120,30);
        b1.setFont(new Font("serif",Font.BOLD,15));
        b1.addActionListener(this);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        add(b1);

        b2=new JButton("Cancel");
        b2.setBounds(180,140,120,30);
        b2.setFont(new Font("serif",Font.BOLD,15));
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        add(b2);
        b2.addActionListener(this);
        
        getContentPane().setBackground(Color.WHITE);
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600,300);
        setLocation(420,365);
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == b1){
            try{
                DBConnection c1 = new DBConnection();
                String u = t1.getText();
                String v = String.valueOf(t2.getPassword());

                String q = "select * from Teacher where username='"+u+"' and password='"+v+"'";

                ResultSet rs = c1.s.executeQuery(q);
                if(rs.next()){
                    JOptionPane.showMessageDialog(null, "login Successfull");
                    currentTeacherID = Integer.parseInt(rs.getString("teacherID"));
                    setVisible(false);
                    new Teacher();
                    Main.main.dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Invalid login");
                }
        }catch(HeadlessException | NumberFormatException | SQLException e){
            e.printStackTrace();
            }
        }
        else if(ae.getSource() == b2){
            dispose();
        }
    }
    public static void main(String[] args) {
        new TeacherLogin();
    }
}
