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
    String testName;

    public void addListSelectionListeners(JList jList1, JList jList2, JTextArea testDescriptionTextArea, JTextArea testDescriptionTextArea1) {
        jList1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    testName = (String) jList1.getSelectedValue();
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
                    testName = (String) jList2.getSelectedValue();
                    if (testName != null) {
                        updateTestDescription(testDescriptionTextArea1, testName);
                    }
                }
            }
        });
    }

    public void loadTests(JList jList1, JList jList2) {
        DataBase db = new DataBase();
        DefaultListModel listModel = new DefaultListModel();
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

    public void updateTestDescription(JTextArea textArea, String testName) {
        DataBase db = new DataBase();
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
