package javasemesterproject.Teacher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javasemesterproject.DBConnection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ViewMyCourses extends JFrame implements ActionListener{
    JLabel title;
    JScrollPane scroll;
    JTable table;
    DefaultTableModel model;
    String[][] data;
    String[] columnNames;
    JButton deleteButton;
    public ViewMyCourses(){
        super("View My Courses");
        setLayout(new BorderLayout());
        
        title = new JLabel("View My Courses", JLabel.CENTER);
        title.setFont(title.getFont().deriveFont (22.0f));
        title.setBackground(Color.LIGHT_GRAY);
        title.setForeground(Color.BLACK);
        title.setOpaque(true);
        add(title, BorderLayout.NORTH);
        
        
        this.getMyCourses();
        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);
        JScrollPane pane = new JScrollPane(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setRowHeight(30);
        pane.setBounds(20, 150, 550, 200);
        add(pane, BorderLayout.CENTER);
        
        deleteButton = new JButton("Delete");
        deleteButton.setHorizontalAlignment(JButton.CENTER);
        deleteButton.addActionListener(this);
        deleteButton.setToolTipText( "Click to Delete Selected Course." );
        deleteButton.setPreferredSize(new Dimension(20, 50));
        add(deleteButton, BorderLayout.SOUTH);
       
        
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,360);
        setLocation(420,260);
        setVisible(true);
    }
    private void getMyCourses(){
        try{
            DBConnection c1 = new DBConnection();
            String q1  = "Select Course_ID, Name From Courses Where teacherID = '"+ TeacherLogin.currentTeacherID +"'";
            ResultSet rs = c1.s.executeQuery(q1); 
            ResultSetMetaData rsmd = rs.getMetaData();
            int    columnCount  =  rsmd.getColumnCount();
            int    rowCount = 0;
            while (rs.next())
                rowCount++;
            // The column count starts from 1
            columnNames = new String[columnCount];
            int CIndex = 1;
            for(int in = 0 ; in < columnCount; in++) {
              columnNames[in]  = rsmd.getColumnName(CIndex);
              CIndex++;
            }
            data = new String[rowCount][columnCount];
            int row = 0;
            rs.beforeFirst();
            while (rs.next()) {
                for (int c = 0 ; c < columnCount; c++) {
                  data[row][c] = rs.getString(columnNames[c]);
                }
                row++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == deleteButton){
            // check for selected row first
            if(table.getSelectedRow() != -1) {
                int rowNum = table.getSelectedRow();
                // 0 is the Column index which is ID
                String course_ID = model.getValueAt(rowNum,0).toString();
                
                try{
                    DBConnection c1 = new DBConnection();
                    String q = "Delete From Courses Where course_ID ='"+ course_ID +"'";
                    int x = c1.s.executeUpdate(q); 
                    if(x == 0){
                        JOptionPane.showMessageDialog(null, "Error Occured");
                    }else{
                         JOptionPane.showMessageDialog(null, "Selected Course deleted successfully");
                         dispose();
                         new ViewMyCourses();
                    }
                }
                catch(HeadlessException | SQLException exception){
                    exception.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        new ViewMyCourses();
    }
}