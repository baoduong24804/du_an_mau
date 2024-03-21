/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSys.Utils;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MsgBox {

    public static void alert(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Hệ thống quản lý đào tạo",
                JOptionPane.INFORMATION_MESSAGE);
    }

        public static void alertRed(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, new JLabel("<html><font color='red'>"+message+"</font></html>"), "Hệ thống quản lý đào tạo",
                JOptionPane.ERROR_MESSAGE);
    }

    public static boolean confirm(Component parent, String message) {
        int result = JOptionPane.showConfirmDialog(parent, message, "Hệ thống quản lý đào tạo",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    public static String prompt(Component parent, String message) {
        return JOptionPane.showInputDialog(parent, message, "Hệ thống quản lý đào tạo",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
