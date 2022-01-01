package javasemesterproject.Student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javasemesterproject.DBConnection;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ViewParticipants extends JFrame implements ActionListener{
    JLabel title, courseCbLbl;
    JComboBox  coursesCb;
    JPanel middlePanel;
    JScrollPane scroll;
    JTable table;
    DefaultTableModel model;
    JButton contactBtn;
    public ViewParticipants(){
        super("View Particpants");
        setLayout(new BorderLayout());
        
        title = new JLabel("Particpants", JLabel.CENTER);
        title.setFont(title.getFont().deriveFont (22.0f));
        title.setBackground(Color.LIGHT_GRAY);
        title.setForeground(Color.BLACK);
        title.setOpaque(true);
        add(title, BorderLayout.NORTH);
        
        middlePanel = new JPanel();
        middlePanel.setLayout(null);
        add(middlePanel, BorderLayout.CENTER);
        
        
        courseCbLbl = new JLabel("Select Course");
        courseCbLbl.setFont(new Font(Font.SERIF,Font.BOLD, 16));
        courseCbLbl.setBounds(80, 50, 120, 28);
        courseCbLbl.setHorizontalAlignment(JLabel.LEFT);
        middlePanel.add(courseCbLbl);
        

        coursesCb = new JComboBox(this.getMyCourses());
        coursesCb.setSelectedIndex(-1);
        coursesCb.setBounds(200, 50, 140, 28);
        coursesCb.addActionListener(this);
        middlePanel.add(coursesCb);
        
        
        model = new DefaultTableModel();
        table = new JTable(model);
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);
        JScrollPane pane = new JScrollPane(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
        public void valueChanged(ListSelectionEvent event) {
            String role = table.getValueAt(table.getSelectedRow(), 4).toString();
            if(role == "Teacher")
                contactBtn.setEnabled(false);
            else
                contactBtn.setEnabled(true);
        }
    });
        pane.setBounds(20, 95, 550, 245);
        middlePanel.add(pane);
       
        contactBtn = new JButton("Contact");
        contactBtn.setToolTipText( "Click to Message the selected Participant." );
        contactBtn.setPreferredSize(new Dimension(50, 40));
        contactBtn.addActionListener(this);
        add(contactBtn, BorderLayout.SOUTH);
        
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600,450);
        setLocation(420,260);
        setVisible(true);
    }
    private String[] getMyCourses(){
        String[] Course_Names = null;
        try{
            DBConnection c1 = new DBConnection();
            String q = "Select C.Name From Courses As C"
                        + " Inner Join Enrollments As E ON E.course_ID = C.course_ID"
                        + " Where E.stdID = '"+ StudentLogin.currentStudentID+"'";
            
            ResultSet rs = c1.s.executeQuery(q);
            int rowCount = 0;
            while(rs.next())
                rowCount++;
            Course_Names = new String[rowCount];
            rs.beforeFirst();
            int i=0;
            while(rs.next()){
                Course_Names[i] = rs.getString("Name");
                i++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return Course_Names;
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == coursesCb){
            // to Clear all records from JTable
            model.setRowCount(0);
            // to Clear all Columns from JTable
            model.setColumnCount(0);
            Object selected = coursesCb.getSelectedItem();
            String courseName = selected.toString();
            try{
                DBConnection c1 = new DBConnection();
                String q = "Select S.stdID, S.fname, S.lname, S.Email_ID, S.Last_Login"
                        + " From Student As S"
                        + " Inner Join Enrollments As E ON E.stdID = S.stdID"
                        + " Where E.course_ID = (select course_ID From Courses Where Name = '"+ courseName +"')"
                        + " And S.stdID <> '"+ StudentLogin.currentStudentID +"'";

                ResultSet rs = c1.s.executeQuery(q);
                int StdrowCount = 0;
                String[][] Studentdata;
                while (rs.next())
                    StdrowCount++;
                Studentdata = new String[StdrowCount][5];
                rs.beforeFirst();
                int rowCount = 0;
                while (rs.next()) {
                    Studentdata[rowCount][0] = rs.getString(1);
                    String fname = rs.getString(2);
                    String lname = rs.getString(3);
                    Studentdata[rowCount][1] = fname + " " +lname;
                    Studentdata[rowCount][2] = rs.getString(4);
                    Studentdata[rowCount][3] = rs.getString(5);
                    Studentdata[rowCount][4] = "Student";
                    rowCount++;
                }
                
                String q2 = "Select T.teacherID, T.fname, T.lname, T.Email_ID, T.Last_Login"
                        + " From Teacher As T"
                        + " Inner Join Courses As C ON C.teacherID = T.teacherID"
                        + " Where C.course_ID = (select course_ID From Courses Where Name = '"+ courseName +"')";
                
                ResultSet rs2 = c1.s.executeQuery(q2);
                rs2.next();
                String teacherData[][] = new String[1][5];
                teacherData[0][0] = rs2.getString(1);
                String fnameTeacher = rs2.getString(2);
                String lnameTeacher = rs2.getString(3);
                teacherData[0][1] = fnameTeacher + " " +lnameTeacher;
                teacherData[0][2] = rs2.getString(4);
                teacherData[0][3] = rs2.getString(5);
                teacherData[0][4] = "Teacher";
                
                String[] columnNames = {"ID", "Name", "Email", "Last Login", "Role"};
                for(int c = 0; c < columnNames.length; c++)
                    model.addColumn(columnNames[c]);
                
                model.addRow(teacherData[0]);
                
                for(int r = 0; r < Studentdata.length ; r++){
                         model.addRow(Studentdata[r]);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(ae.getSource() == table){
            
        }
        else if(ae.getSource() == contactBtn){
            // check for selected row first
            if(table.getSelectedRow() != -1) {
                int rowNum = table.getSelectedRow();
                // 0 is the Column index which is ID
                int to_id = Integer.parseInt(model.getValueAt(rowNum,0).toString());
                String to_role = model.getValueAt(rowNum, 4).toString();
                String toName = model.getValueAt(rowNum, 1).toString();
                new Message(StudentLogin.currentStudentID, toName, to_id);
            }
        }
    }
    public static void main(String[] args) {
        new ViewParticipants();
    }
}