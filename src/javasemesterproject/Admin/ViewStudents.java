package javasemesterproject.Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javasemesterproject.DBConnection;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ViewStudents extends JFrame implements ActionListener{
    String columnNames[];
    Object[][] data;
    JTable table;
    JButton deleteButton;
    DefaultTableModel model;
    JLabel title;
    public ViewStudents(){
        super("View Students");
        setLayout(new BorderLayout());
        
        title = new JLabel("Students", JLabel.CENTER);
        title.setFont(title.getFont().deriveFont (25.0f));
        title.setBackground(Color.LIGHT_GRAY);
        title.setForeground(Color.BLACK);
        title.setOpaque(true);
        add(title, BorderLayout.NORTH);
        
        this.getStudents();
        model = new DefaultTableModel(data, columnNames){
        @Override
        public Class<?> getColumnClass(int column) {
            switch(column) {
                case 9: return ImageIcon.class;
                default: return Object.class;
            }
        }
        };
        table = new JTable(model);
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);
        JScrollPane pane = new JScrollPane(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setRowHeight(96);
        add(pane, BorderLayout.CENTER);
        
        deleteButton = new JButton("Delete");
        deleteButton.setHorizontalAlignment(JButton.CENTER);
        deleteButton.addActionListener(this);
        deleteButton.setToolTipText( "Click to Delete Selected Record." );
        deleteButton.setPreferredSize(new Dimension(20, 50));
        add(deleteButton, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000,500);
        setLocation(250,200);
        setVisible(true);
    }
    private void getStudents(){
        DBConnection c1 = new DBConnection();
        try{
            String q = "select * from Student";
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
              columnNames[in]  = rsmd.getColumnName(CIndex);
              CIndex++;
            }
            data = new Object[rowCount][columnCount];
            int row = 0;
            rs.beforeFirst();
            while (rs.next()) {
                byte[] bytImage = null;
                for (int c = 0 ; c < columnCount; c++) {
                    if(columnNames[c].equalsIgnoreCase("picture")){
                        bytImage = rs.getBytes(columnNames[c]);
                        data[row][c] =  getImageIcon(bytImage);
                    }
                    else
                        data[row][c] = rs.getString(columnNames[c]);
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
    private ImageIcon getImageIcon(byte[] byteImage){
        InputStream is = new ByteArrayInputStream(byteImage);
        BufferedImage bufferedImage = null;
         try {
             bufferedImage = ImageIO.read(is);
         } catch (IOException ex) {
             Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
         }
         
        ImageIcon icon = new ImageIcon(bufferedImage);
        Image i2 = icon.getImage().getScaledInstance(96 ,96 ,Image.SCALE_DEFAULT);
        ImageIcon i3 =  new ImageIcon(i2);
         return i3;
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == deleteButton){
            // check for selected row first
            if(table.getSelectedRow() != -1) {
                int rowNum = table.getSelectedRow();
                // 0 is the Column index which is ID
                String id = model.getValueAt(rowNum,0).toString();
                
                DBConnection c1 = new DBConnection();
                try{
                    String q = "Delete From Student Where stdID ='"+id+"'";
                    int x = c1.s.executeUpdate(q); 
                    if(x == 0){
                        JOptionPane.showMessageDialog(null, "Error Occured");
                    }else{
                         JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
                         dispose();
                         new ViewStudents();
                    }
                }
                catch(HeadlessException | SQLException exception){
                    exception.printStackTrace();
                }
                finally{
                    c1.Close();
                }
            }
        }
    }
    public static void main(String[] args) {
        new ViewStudents();
    }
 
}