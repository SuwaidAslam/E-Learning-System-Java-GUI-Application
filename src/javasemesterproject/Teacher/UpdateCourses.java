package javasemesterproject.Teacher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javasemesterproject.DBConnection;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;


public class UpdateCourses extends JFrame implements ActionListener{
    JLabel title;
    JPanel middlePanel;
    JComboBox courseCb;
    JTextArea courseDescription, courseContent;
    JScrollPane scroll, scroll2;
    JButton updateBtn;
    public UpdateCourses() {
        super("Update Course");
        setLayout(new BorderLayout());
        
        title = new JLabel("Update Course", JLabel.CENTER);
        title.setFont(title.getFont().deriveFont (22.0f));
        title.setBackground(Color.LIGHT_GRAY);
        title.setForeground(Color.BLACK);
        title.setOpaque(true);
        add(title, BorderLayout.NORTH);
        
        middlePanel = new JPanel();
        middlePanel.setLayout(null);
        add(middlePanel, BorderLayout.CENTER);
        
        courseCb = new JComboBox(this.getMyCourses());
        courseCb.setSelectedIndex(-1);
        courseCb.setBounds(200, 30, 140, 28);
        courseCb.addActionListener(this);
        middlePanel.add(courseCb);
        
        JLabel courseDescriptionLbl = new JLabel("Course Description");
        courseDescriptionLbl.setFont(new Font(Font.SERIF,Font.BOLD, 16));
        courseDescriptionLbl.setBounds(80, 100, 140, 28);
        courseDescriptionLbl.setHorizontalAlignment(JLabel.LEFT);
        middlePanel.add(courseDescriptionLbl);
        
        courseDescription = new JTextArea();
        courseDescription.setLineWrap(true);
        courseDescription.setWrapStyleWord(true);
        scroll = new JScrollPane(courseDescription);
        scroll.setBounds(150, 130, 460, 100);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        courseDescription.setEditable(true);
        middlePanel.add(scroll);
        
        
        JLabel courseContentLbl = new JLabel("Course Content");
        courseContentLbl.setFont(new Font(Font.SERIF,Font.BOLD, 16));
        courseContentLbl.setBounds(80, 235, 140, 28);
        courseContentLbl.setHorizontalAlignment(JLabel.LEFT);
        middlePanel.add(courseContentLbl);
        
        courseContent = new JTextArea();
        courseContent.setLineWrap(true);
        courseContent.setWrapStyleWord(true);
        scroll2 = new JScrollPane(courseContent);
        scroll2.setBounds(10, 270, 685, 150);
        scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        courseContent.setEditable(true);
        middlePanel.add(scroll2);
        
        updateBtn = new JButton("Update");
        updateBtn.setFont(new Font(Font.SERIF,Font.BOLD, 15));
        updateBtn.setHorizontalAlignment(JButton.CENTER);
        updateBtn.addActionListener(this);
        add(updateBtn, BorderLayout.SOUTH);
        
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(720,520);
        setLocation(375,145);
        setVisible(true);
    }

    private String[] getMyCourses(){
        String data[] = null;
    try{
        DBConnection c1 = new DBConnection();
        String q1  = "Select Course_ID, Name From Courses Where teacherID = '"+ TeacherLogin.currentTeacherID +"'";
        ResultSet rs = c1.s.executeQuery(q1);
        int rowCount = 0;
        while (rs.next())
            rowCount++;
        data = new String[rowCount];
        int row = 0;
        rs.beforeFirst();
        while (rs.next()) {
            data[row] = rs.getString("Name");
            row++;
        }
    }catch(Exception e){
        e.printStackTrace();
        }
        return data;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == courseCb){
            Object selected = courseCb.getSelectedItem();
            String courseName = selected.toString();
            try{
                DBConnection c1 = new DBConnection();
                String q1  = "Select Description, Content From Courses As C"
                        + " Where C.Name = '"+ courseName +"' And C.teacherID = '"+ TeacherLogin.currentTeacherID +"'";
                ResultSet rs = c1.s.executeQuery(q1); 
                rs.next();
                courseDescription.setText(rs.getString("Description"));
                courseContent.setText(rs.getString("Content"));
                
            }catch(Exception exception){
                exception.printStackTrace();
            }
        }
        else if(e.getSource() == updateBtn){
            Object selected = courseCb.getSelectedItem();
            String courseName = selected.toString();
            String courseDescriptionStr = courseDescription.getText();
            String courseContentStr = courseContent.getText();
            try{
                DBConnection c1 = new DBConnection();
                String q1  = "Update Courses Set Description = '"+ courseDescriptionStr +"', Content = '"+ courseContentStr +"'"
                        + " Where Name = '"+ courseName +"'";
                
                int x = c1.s.executeUpdate(q1);
                if(x == 0){
                    JOptionPane.showMessageDialog(null, "Got some error");
                }else{
                    JOptionPane.showMessageDialog(null, "Course Updated Successfully");
                    setVisible(false);
                }
            }catch(Exception exc){
                JOptionPane.showMessageDialog(null, "This Course is Already being Offered By other Teacher!");
                exc.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new UpdateCourses();
    }
    
}
