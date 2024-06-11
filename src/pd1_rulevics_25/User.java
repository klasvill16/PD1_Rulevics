package pd1_rulevics_25;



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
    
}
