/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EduSys.UI;

import EduSys.Entity.ChuyenDe;

/**
 *
 * @author anhba
 */
public class NewClass {

    String name = "";

    public static void main(String[] args) {
        NewClass a = new NewClass();
        System.out.println( a.toString());
    }

    @Override
    public String toString() {
        String str2 = this.name;
        String str1 = str2;
        if (str2 == null) {
            str2 = getClass().getName();
            int i = str2.lastIndexOf('.');
            str1 = str2;
            if (i != -1) {
                str1 = str2.substring(i + 1);
            }
        }
        return str1;
    }
}
