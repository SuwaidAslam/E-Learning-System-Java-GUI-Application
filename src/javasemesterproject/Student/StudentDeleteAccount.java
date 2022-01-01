package javasemesterproject.Student;

import javasemesterproject.DBConnection;
import javax.swing.JOptionPane;

public class StudentDeleteAccount{
    int input;
    public StudentDeleteAccount(){

            input = JOptionPane.showConfirmDialog(null, "Do you want to proceed?", "Select an Option...",
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if(input == 0){
                try{
                    DBConnection c1 = new DBConnection();

                    String q = "Delete From Student Where stdID ='" + StudentLogin.currentStudentID + "'";

                    int x = c1.s.executeUpdate(q);
                    if(x == 0){
                        JOptionPane.showMessageDialog(null, "Got some error");
                    }else{
                        JOptionPane.showMessageDialog(null, "Account Deleted Successfully");
                        
                    }
            }catch(Exception e){
                e.printStackTrace();
                }
            }
        
        }
    public static void main(String[] args) {
        new StudentDeleteAccount();
    }
}