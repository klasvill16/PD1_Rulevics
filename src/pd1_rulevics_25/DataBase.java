/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pd1_rulevics_25;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author klasv
 */
public class DataBase {

    public Connection connect() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/TestDB", "root", "28974363");
            //root is username of db and 28974363 is a password
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return con;
    }
}
