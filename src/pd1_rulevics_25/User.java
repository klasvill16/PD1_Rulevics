package pd1_rulevics_25;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;



public class User {
    String name;
    String surname;
    String username;
    String password;
    String usertype;

    public User(String name, String surname, String username, String password, String userType) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.usertype = userType;
    }
    
    public User(String name, String username, String password, String userType) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.usertype = userType;
    }
    
}
