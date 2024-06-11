/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package pd1_rulevics_25;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextArea;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author klasv
 */
public class TeacherTest {
    
    public TeacherTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of Teacher(String name, String username, String password, String usertype) constructor, of class Teacher.
     */
    @Test
    public void testTeacherCreation(){
        System.out.println("Teacher(String name, String username, String password, String usertype) constructor of class Teacher test");
        Teacher teacher = new Teacher("John", "mcLovin", "password", "teacher");

        assertEquals("John", teacher.name);
        assertEquals("mcLovin", teacher.username);
        assertEquals("password", teacher.password);
        assertEquals("teacher", teacher.usertype);
        
        Teacher teacher2 = new Teacher("Ivan", "ivan", "QWERTY", "teacher");

        assertEquals("Ivan", teacher2.name);
        assertEquals("ivan", teacher2.username);
        assertEquals("QWERTY", teacher2.password);
        assertEquals("teacher", teacher2.usertype);
        
        Teacher teacher3 = new Teacher("Kilian", "KMBP88", "123456", "teacher");

        assertEquals("Kilian", teacher3.name);
        assertEquals("KMBP88", teacher3.username);
        assertEquals("123456", teacher3.password);
        assertEquals("teacher", teacher3.usertype);
        
        Teacher teacher4 = new Teacher("Jose", "JOSE2004", "pas123456789", "teacher");

        assertEquals("Jose", teacher4.name);
        assertEquals("JOSE2004", teacher4.username);
        assertEquals("pas123456789", teacher4.password);
        assertEquals("teacher", teacher4.usertype);
    }
    
}
