/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package pd1_rulevics_25;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author klasv
 */
public class UserTest {

    public UserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of login method, of class User.
     */
    @Test
    public void testLogin() throws SQLException {
        System.out.println("login(String username, String password) method of class User test");
        ResultSet rs;

        rs = User.login("hina", "123");
        assertEquals(true, rs.next());

        rs = User.login("hina123", "123");
        assertEquals(true, rs.next());

        rs = User.login("Mister Test", "SUPERPASSWORD123");
        assertEquals(false, rs.next());

        rs = User.login("ALI$$A", "darzu513");
        assertEquals(true, rs.next());

        rs = User.login("westerd", "apelsin!2#4%6");
        assertEquals(false, rs.next());

        rs = User.login("tigovad", "12345");
        assertEquals(true, rs.next());

        rs = User.login("EllaFreya", "123456789");
        assertEquals(true, rs.next());

        rs = User.login("HINA", "123");
        assertEquals(false, rs.next());
    }

}
