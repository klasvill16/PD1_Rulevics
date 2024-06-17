package pd1_rulevics_25;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class Teacher extends User {
    
    public Teacher(String name, String surname, String username, String password, String usertype) {
        super(name, surname, username, password, usertype);
    }
    
    public Teacher(String name, String username, String password, String usertype){
        super(name, username, password, usertype);
    }
    
    public void populateMarksTable(JTable JTable) {
        DataBase db = new DataBase();
        ResultSet rs = null, rs2 = null;
        PreparedStatement pst = null, pst2 = null;

        try {
            Connection con = db.connect();

            String sql = "SELECT UserName, TestName, Mark FROM marks";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) JTable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String userName = rs.getString("UserName");
                String testName = rs.getString("TestName");
                String mark = rs.getString("Mark");

                sql = "SELECT FirstName, LastName FROM users WHERE UserName=? ";
                pst2 = con.prepareStatement(sql);
                pst2.setString(1, userName);
                rs2 = pst2.executeQuery(); 
                while (rs2.next()) {
                    String firstName = rs2.getString("FirstName");
                    String lastName = rs2.getString("LastName");
                    model.addRow(new Object[]{firstName, lastName, testName, mark});
                }

                rs2.close(); 
                pst2.close(); 
            }

            JTable.setModel(model);
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void saveNewMarks(JTable JTable){
        DataBase db = new DataBase();
        Connection con = null;
        PreparedStatement pst = null;
        boolean hasInvalidMarks = false;
        Logu_Redaktors loguRedaktors = new Logu_Redaktors();
        
        try {
            con = db.connect();

            DefaultTableModel model = (DefaultTableModel) JTable.getModel();
            int rowCount = model.getRowCount();

            String sql = "UPDATE marks SET Mark=? WHERE UserName=? AND TestName=?";
            pst = con.prepareStatement(sql);

            for (int i = 0; i < rowCount; i++) {
                String mark = (String) model.getValueAt(i, 3);
                try {
                    int markValue = Integer.parseInt(mark);
                    if (markValue < 1 || markValue > 10) {
                        hasInvalidMarks = true;
                        String firstName = (String) model.getValueAt(i, 0);
                        String lastName = (String) model.getValueAt(i, 1);
                        loguRedaktors.showError("Nepareizs vērtējums par lietotāju - \"" + firstName + " " + lastName + "\".");
                    }
                } catch (NumberFormatException e) {
                    hasInvalidMarks = true;
                    String firstName = (String) model.getValueAt(i, 0);
                    String lastName = (String) model.getValueAt(i, 1);
                    loguRedaktors.showError("Nepareizs vērtējums par lietotāju - \"" + firstName + " " + lastName + "\".");
                }
            }
            
            if (!hasInvalidMarks) {
                for (int i = 0; i < rowCount; i++) {
                    String firstName = (String) model.getValueAt(i, 0);
                    String lastName = (String) model.getValueAt(i, 1);
                    String testName = (String) model.getValueAt(i, 2);
                    String mark = (String) model.getValueAt(i, 3);

                    String userName = loguRedaktors.getUserName(con, firstName, lastName);

                    pst.setString(1, mark);
                    pst.setString(2, userName);
                    pst.setString(3, testName);

                    pst.executeUpdate();

                }
                loguRedaktors.showSuccess("Jūs esat veiksmīgi nomainījuši atzīmes!");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        populateMarksTable(JTable);
    }
    
    public void deleteMark(JTable JTable) {
        Logu_Redaktors loguRedaktors = new Logu_Redaktors();
        int selectedRow = JTable.getSelectedRow();
        
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) JTable.getModel();
            String firstName = (String) model.getValueAt(selectedRow, 0);
            String lastName = (String) model.getValueAt(selectedRow, 1);
            String testName = (String) model.getValueAt(selectedRow, 2);

            DataBase db = new DataBase();
            Connection con = null;
            PreparedStatement pst = null;

            try {
                con = db.connect();
                String userName = loguRedaktors.getUserName(con, firstName, lastName);

                String sql = "DELETE FROM marks WHERE UserName=? AND TestName=?";
                pst = con.prepareStatement(sql);
                pst.setString(1, userName);
                pst.setString(2, testName);
                pst.executeUpdate();

                model.removeRow(selectedRow);
                loguRedaktors.showSuccess("Vērtējums ir veiksmīgi izdzēsts!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } 
        } else {
            loguRedaktors.showInfo("Nav izvēlēta neviena rinda!");
        }
    }
    
    public void deleteTest(JList<String> jList2, JTextArea TestDescriptionTextArea1) {
        String selectedTest = jList2.getSelectedValue();
        Logu_Redaktors loguRedaktors = new Logu_Redaktors(); 
        
        if (selectedTest != null) {
            DataBase db = new DataBase();
            Connection con = null;
            PreparedStatement pst = null;
            try {
                con = db.connect();
                String sql = "DELETE FROM test WHERE TestName = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, selectedTest);
                pst.executeUpdate();
                con.close();
                DefaultListModel<String> model = (DefaultListModel<String>) jList2.getModel();
                model.removeElement(selectedTest);
                loguRedaktors.showSuccess("Tests ir veiksmīgi izdzēsts!");
                TestDescriptionTextArea1.setText("");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }else{
            loguRedaktors.showInfo("Tests nav izvelēts!");
        }
    }
    
    public void deleteStudent(JTable JTable) {
        Logu_Redaktors loguRedaktors = new Logu_Redaktors();
        int selectedRow = JTable.getSelectedRow();

        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) JTable.getModel();
            String userName = (String) model.getValueAt(selectedRow, 2);

            DataBase db = new DataBase();
            Connection con = null;
            PreparedStatement pst = null, pst2 = null;

            try {
                con = db.connect();
                String deleteMarksSql = "DELETE FROM marks WHERE UserName=?";
                pst = con.prepareStatement(deleteMarksSql);
                pst.setString(1, userName);
                pst.executeUpdate();
                pst.close();
                
                String deleteUserSql = "DELETE FROM users WHERE UserName=? ";
                pst2 = con.prepareStatement(deleteUserSql);
                pst2.setString(1, userName);
                pst2.executeUpdate();
                pst2.close();
                
                loguRedaktors.showSuccess("Lietotājs ir veiksmīgi izdzēsts!");
                model.removeRow(selectedRow);
            } catch (Exception e) {
                
            } 
        } else {
            loguRedaktors.showInfo("Nav izvēlēta neviena rinda!");
        }
        
    }
    
    public void populateStudentTable(JTable JTable) {
        DataBase db = new DataBase();
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            Connection con = db.connect();

            String sql = "SELECT FirstName, LastName, UserName, Password, UserType FROM users";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) JTable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String userName = rs.getString("UserName");
                String password = rs.getString("Password");
                String userType = rs.getString("UserType");
                if(userType.equals("teacher")){
                    continue;
                }else{
                    model.addRow(new Object[]{firstName, lastName, userName, password});
                }
            }

            JTable.setModel(model);
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void saveNewStudent(JTable jTable) {
        DataBase db = new DataBase();
        Connection con = null;
        PreparedStatement pst = null;
        Logu_Redaktors loguRedaktors = new Logu_Redaktors();
        
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        int rowCount = model.getRowCount();

        try {
            con = db.connect();
            con.setAutoCommit(false);  

            for (int i = 0; i < rowCount; i++) {
                String firstName = (String) model.getValueAt(i, 0);
                String lastName = (String) model.getValueAt(i, 1);
                String userName = (String) model.getValueAt(i, 2);
                String password = (String) model.getValueAt(i, 3);

                String checkSql = "SELECT COUNT(*) FROM users WHERE UserName=?";
                pst = con.prepareStatement(checkSql);
                pst.setString(1, userName);
                ResultSet rs = pst.executeQuery();
                rs.next();
                int count = rs.getInt(1);
                pst.close();

                if (count > 0) {
                    String updateSql = "UPDATE users SET FirstName=?, LastName=?, Password=?, UserType=? WHERE UserName=?";
                    pst = con.prepareStatement(updateSql);
                    pst.setString(1, firstName);
                    pst.setString(2, lastName);
                    pst.setString(3, password);
                    pst.setString(4, "student");
                    pst.setString(5, userName);
                    pst.executeUpdate();
                    pst.close();
                } else {
                    String insertSql = "INSERT INTO users (FirstName, LastName, UserName, Password, UserType) VALUES (?, ?, ?, ?, ?)";
                    pst = con.prepareStatement(insertSql);
                    pst.setString(1, firstName);
                    pst.setString(2, lastName);
                    pst.setString(3, userName);
                    pst.setString(4, password);
                    pst.setString(5, "student");
                    pst.executeUpdate();
                    pst.close();
                }
            }

            con.commit();  
            con.setAutoCommit(true);

            loguRedaktors.showSuccess("Izmaiņas veiksmīgi saglabātas!");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}