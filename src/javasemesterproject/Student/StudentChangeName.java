package javasemesterproject.Student;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javasemesterproject.DBConnection;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class StudentChangeName extends JFrame implements ActionListener{
    JLabel newFNamelbl;
    JLabel newLNamelbl;
    JTextField newFName;
    JTextField newLName;
    JButton updateNameButton;
    public StudentChangeName(){
            newFNamelbl = new JLabel("First Name");
            newFNamelbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
            newFNamelbl.setBounds(10, 10, 150, 30);
            add(newFNamelbl);
            

            newFName = new JTextField();
            newFName.setFont(new Font("Tahoma", Font.PLAIN, 22));
            newFName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            newFName.setBounds(150, 10, 150, 40);
            add(newFName);
            newFName.setColumns(20);
            
            newLNamelbl = new JLabel("Last Name");
            newLNamelbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
            newLNamelbl.setBounds(10, 80, 150, 30);
            add(newLNamelbl);
            

            newLName = new JTextField();
            newLName.setFont(new Font("Tahoma", Font.PLAIN, 22));
            newLName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            newLName.setBounds(150, 80, 150, 40);
            add(newLName);
            newLName.setColumns(20);
            
            updateNameButton = new JButton("Update");
            updateNameButton.setBounds(80, 140, 150, 30);
            updateNameButton.addActionListener((ActionListener) this);
            add(updateNameButton);
            
//            ChangePassFrame.setResizable(false);
            setLayout(null);
            setSize(320,220);
            setLocation(500,320);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == updateNameButton){
            String FirstName = newFName.getText();
            String LastName = newLName.getText();
            try{
                DBConnection c1 = new DBConnection();

                String q = "update Student SET fname = '"+ FirstName +"', lname = '"+ LastName +"'"
                        + "Where stdID ='" + StudentLogin.currentStudentID + "'";

                int x = c1.s.executeUpdate(q);
                if(x == 0){
                    JOptionPane.showMessageDialog(null, "Got some error");
                }else{
                    JOptionPane.showMessageDialog(null, "Your Name Updated Successfully");
                    setVisible(false);
                }
            }catch(Exception e){
                e.printStackTrace();
                }
        }
    }
    public static void main(String[] args) {
        new StudentChangeName();
    }
}