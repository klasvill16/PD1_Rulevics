package pd1_rulevics_25;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    String name;
    String surname;
    String username;
    String password;
    String usertype;

    public User(String name, String surname, String username, String password, String usertype) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.usertype = usertype;
    }

    public User(String name, String username, String password, String usertype) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.usertype = usertype;
    }

    public static ResultSet login(String username, String password) {
        DataBase db = new DataBase();
        ResultSet rs = null;
        PreparedStatement pst = null;
        Logu_Redaktors loguredaktors = new Logu_Redaktors();

        try {
            Connection con = db.connect();
            if (con == null) {
                loguredaktors.showInfo("Jūs neesat pieslēgts datubāzei!");
            }
            String sql = "SELECT * FROM users WHERE UserName=? AND Password=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);

            return pst.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
