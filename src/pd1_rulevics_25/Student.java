package pd1_rulevics_25;

//
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ButtonGroup;

public class Student extends User {

    public Student(String name, String surname, String username, String password, String usertype) {
        super(name, surname, username, password, usertype);
    }

    public Student(String name, String username, String password, String usertype) {
        super(name, username, password, usertype);
    }

    public void displayMarks(JTable jTable) {
        DataBase db = new DataBase();
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            Connection con = db.connect();

            String sql = "SELECT TestName, Mark FROM marks WHERE UserName=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, this.username);
            rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) jTable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String testName = rs.getString("TestName");
                String mark = rs.getString("Mark");
                model.addRow(new Object[]{testName, mark});
            }

            jTable.setModel(model);
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void displayTest(String testName, JLabel TestNameLabel, JLabel[] questionLabels, JRadioButton[][] answerButtons) {
        DataBase db = new DataBase();
        ResultSet rs = null;
        PreparedStatement pst = null;

        if (testName == null) {
            Logu_Redaktors loguRedaktors = new Logu_Redaktors();
            loguRedaktors.showInfo("Tests nav izvelēts!");
            return;
        }

        try {
            Connection con = db.connect();
            TestNameLabel.setText(testName);

            String sql = "SELECT QuestionText, AnswerFirst, AnswerSecond, AnswerThird FROM test WHERE TestName=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, testName);
            rs = pst.executeQuery();

            int questionIndex = 0;

            while (rs.next() && questionIndex < 5) {
                String questionText = rs.getString("QuestionText");
                String answerFirst = rs.getString("AnswerFirst");
                String answerSecond = rs.getString("AnswerSecond");
                String answerThird = rs.getString("AnswerThird");

                questionLabels[questionIndex].setText(questionText);
                answerButtons[questionIndex][0].setText(answerFirst);
                answerButtons[questionIndex][1].setText(answerSecond);
                answerButtons[questionIndex][2].setText(answerThird);

                questionIndex++;
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
    public float[] submitTest(ButtonGroup[] answerGroups, String testName, User currentUser) {
        DataBase db = new DataBase();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int pointsForTest = 0; 
        float markForTest = 0, percentsForTest = 0;
        int i = 0;
        Logu_Redaktors loguRedaktors = new Logu_Redaktors();

        try {
            Connection con = db.connect();
            String sql = "SELECT AnswerRight FROM test WHERE TestName=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, testName);
            rs = pst.executeQuery();
            while (rs.next() && i < 5) {
                String answerRight = rs.getString("AnswerRight");
                if (loguRedaktors.getSelectedButtonText(answerGroups[i]) == null) {
                    pointsForTest += 0;
                } else if (loguRedaktors.getSelectedButtonText(answerGroups[i]).equals(answerRight)) {
                    pointsForTest++;
                }
                i++;
            }
            percentsForTest = ((float) pointsForTest / 5) * 100;

            switch (pointsForTest) {
                case 0: {
                    markForTest = 1;
                    break;
                }
                case 1: {
                    markForTest = 2;
                    break;
                }
                case 2: {
                    markForTest = 4;
                    break;
                }
                case 3: {
                    markForTest = 6;
                    break;
                }
                case 4: {
                    markForTest = 8;
                    break;
                }
                default: {
                    markForTest = 10;
                }
            }
            
            String sqlCheck = "SELECT * FROM marks WHERE UserName=? AND TestName=? ";
            pst = con.prepareStatement(sqlCheck);
            pst.setString(1, currentUser.username);
            pst.setString(2, testName);
            rs = pst.executeQuery();

            if (rs.next()) {
                return null;
            } else {
                sql = "INSERT INTO marks VALUES(?, ?, ?)";
                pst = con.prepareStatement(sql);
                pst.setString(1, currentUser.username);
                pst.setString(2, testName);
                pst.setString(3, Integer.toString((int)markForTest));
                pst.executeUpdate();
                
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new float[] {percentsForTest, markForTest};
    }
}
