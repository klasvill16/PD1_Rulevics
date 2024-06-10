/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pd1_rulevics_25;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Test {
    public String testName = null;
    
    private DefaultListModel listModel;
    private JList<String> jList1;
    private JList<String> jList2;
    private JTextArea testDescriptionTextArea;
    private JTextArea testDescriptionTextArea1;
    private DataBase db;

    public Test(DefaultListModel listModel, JList<String> jList1, JList<String> jList2, JTextArea testDescriptionTextArea, JTextArea testDescriptionTextArea1) {        
        this.listModel = listModel;
        this.jList1 = jList1;
        this.jList2 = jList2;
        this.testDescriptionTextArea = testDescriptionTextArea;
        this.testDescriptionTextArea1 = testDescriptionTextArea1;
        this.db = new DataBase();

        addListSelectionListeners();
        loadTests();
    }

    private void addListSelectionListeners() {
        jList1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    testName = jList1.getSelectedValue();
                    if (testName != null) {
                        updateTestDescription(testDescriptionTextArea, testName);
                    }
                }
            }
        });

        jList2.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    testName = jList2.getSelectedValue();
                    if (testName != null) {
                        updateTestDescription(testDescriptionTextArea1, testName);
                    }
                }
            }
        });
    }

    private void loadTests() {
        try {
            Connection con = db.connect();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM test");

            Set<String> uniqueTestNames = new HashSet<>();

            while (rs.next()) {
                String testName = rs.getString("TestName");
                if (uniqueTestNames.add(testName)) {
                    listModel.addElement(testName);
                }
            }
            jList1.setModel(listModel);
            jList2.setModel(listModel);
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateTestDescription(JTextArea textArea, String testName) {
        try {
            Connection con = db.connect();
            String sql = "SELECT TestDescription FROM test WHERE TestName=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, testName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String testDescription = rs.getString("TestDescription");
                textArea.setText(testDescription);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
