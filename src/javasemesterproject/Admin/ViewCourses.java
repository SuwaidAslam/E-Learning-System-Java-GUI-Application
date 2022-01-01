package javasemesterproject.Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javasemesterproject.DBConnection;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ViewCourses extends JFrame implements ActionListener{
    String columnNames[];
    String[][] data;
    JTable table;
    DefaultTableModel model;
    JLabel title, cbLbl;
    JComboBox filterBySubjectCb;
    JPanel middlePanel;
    public ViewCourses(){
        super("View Courses");
        setLayout(new BorderLayout());
        
        title = new JLabel("Courses", JLabel.CENTER);
        title.setFont(title.getFont().deriveFont (25.0f));
        title.setBackground(Color.LIGHT_GRAY);
        title.setForeground(Color.BLACK);
        title.setOpaque(true);
        add(title, BorderLayout.NORTH);
        
        
        middlePanel = new JPanel();
        middlePanel.setLayout(null);
        add(middlePanel, BorderLayout.CENTER);
        
        
        cbLbl = new JLabel("Filter By Subject");
        cbLbl.setFont(new Font(Font.SERIF,Font.BOLD, 16));
        cbLbl.setBounds(80, 25, 120, 28);
        cbLbl.setHorizontalAlignment(JLabel.LEFT);
        middlePanel.add(cbLbl);
        
        filterBySubjectCb = new JComboBox(this.getSubjects());
        filterBySubjectCb.setBounds(200, 25, 140, 28);
        filterBySubjectCb.addActionListener(this);
        middlePanel.add(filterBySubjectCb);
        
        this.getCourses("");
        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(20, 95, 550, 245);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setRowHeight(40);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        middlePanel.add(pane);
        
  
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(600,450);
        setLocation(420,260);
        setVisible(true);
    }
    private String[] getSubjects(){
        String[] subjects = null;
        try{
            DBConnection c1 = new DBConnection();
            String q = "select Name From Subjects";
            
            ResultSet rs = c1.s.executeQuery(q); 
            int    rowCount = 0;
            while (rs.next())
                rowCount++;
            subjects = new String[rowCount+1];
            subjects[0] = "";
            int row = 1;
            rs.beforeFirst();
            while (rs.next()) {
                subjects[row] = rs.getString("Name");
                row++;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return subjects;
    }
    private void getCourses(String subjectName){
        DBConnection c1 = new DBConnection();
        try{
            String q = "select C.Name As Course, CONCAT(T.fname, ' ', T.lname) As Teacher_Name, T.Email_ID As Teacher_Email"
                    + " from Courses As C"
                    + " Inner Join Teacher As T ON T.teacherID = C.teacherID"
                    + " Where C.Subject_ID = (select Subject_ID From Subjects Where Name = '"+ subjectName +"')";
            if(subjectName.isEmpty()){
                q = "select C.Name As Course, CONCAT(T.fname, ' ', T.lname) As Teacher_Name, T.Email_ID As Teacher_Email"
                    + " from Courses As C"
                    + " Inner Join Teacher As T ON T.teacherID = C.teacherID";
            }
            
            ResultSet rs = c1.s.executeQuery(q);
            ResultSetMetaData rsmd = rs.getMetaData();
            int    columnCount  =  rsmd.getColumnCount();
            int    rowCount = 0;
            while (rs.next())
                rowCount++;
            // The column count starts from 1
            columnNames = new String[columnCount];
            int CIndex = 1;
            for(int in = 0 ; in < columnCount; in++) {
              columnNames[in]  = rsmd.getColumnLabel(CIndex);
              CIndex++;
            }
            data = new String[rowCount][columnCount];
            int row = 0;
            rs.beforeFirst();
 
            while (rs.next()) {
                int col = 0;
                for (int c = 1 ; c < columnCount+1 ; c++) {
                  data[row][col] = rs.getString(c);
                  col++;
                }
                row++;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            c1.Close();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == filterBySubjectCb){
            // to Clear all records from JTable
            model.setRowCount(0);
            // to Clear all Columns from JTable
            model.setColumnCount(0);
            Object selected = filterBySubjectCb.getSelectedItem();
            String subjectName = selected.toString();
            this.getCourses(subjectName);
            for(int i=0; i< columnNames.length; i++)
                model.addColumn(columnNames[i]);
            for(int r=0; r< data.length; r++)
                model.addRow(data[r]);
        }
    }
    public static void main(String[] args) {
        new ViewCourses();
    }
 
}