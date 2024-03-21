/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSys.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class XDate {

    static final SimpleDateFormat DATE_FORMATER = new SimpleDateFormat("dd/MM/yyyy");

    public static Date toDate(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            DATE_FORMATER.applyPattern(pattern);
            return format.parse(date);
        } catch (ParseException ex) {

            throw new RuntimeException(ex);
        }
    }

    public static Date toDate2(String date_dd_MM_yyyy_String, String pattern) {

        try {
           
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date1 = originalFormat.parse(date_dd_MM_yyyy_String);

            SimpleDateFormat newFormat = new SimpleDateFormat(pattern);
          
            
            
            String newDateString = newFormat.format(date1);
            DATE_FORMATER.applyPattern(pattern);
            return newFormat.parse(newDateString);
        } catch (ParseException ex) {

            throw new RuntimeException(ex);
        }
    }

    public static String toString(Date date, String... pattern) {

        if (pattern.length > 0) {
            DATE_FORMATER.applyPattern(pattern[0]);
        }
        try {
            if (date == null) {
                date = XDate.now();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return DATE_FORMATER.format(date);
    }

    public static String ChuyenNgay(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if (date == null) {
            return formatter.format(XDate.now());
        }
        String strDate = formatter.format(date);
        return strDate;
    }

//    /**
//     * Lấy thời gian hiện tại
//     *
//     * @return Date kết quả
//     */
    public static Date now() {

        return new Date();
    }

    public static String ChuyenChuoiBiNguoc(String nam, String dinhDang) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dinhDang);
        try {
            // Chuyển chuỗi thành đối tượng Date
            Date date = dateFormat.parse(nam);

            // Định dạng lại Date theo định dạng mong muốn
            SimpleDateFormat desiredFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = desiredFormat.format(date);

            return formattedDate;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public static String ChuyenNgaySangChuoi(String nam) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (nam == null) {
            return formatter.format(XDate.now());
        }
        try {
            // Chuyển chuỗi thành đối tượng Date
            Date date = formatter.parse(nam);

            // Định dạng lại Date theo định dạng mong muốn
            SimpleDateFormat desiredFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = desiredFormat.format(date);

            return formattedDate;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

//    /**
//     * Bổ sung số ngày vào thời gian
//     *
//     * @param date thời gian hiện có
//     * @param days số ngày cần bổ sung váo date
//     * @return Date kết quả
//     */
    public static Date addDays(Date date, int days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }

//    /**
//     * Bổ sung số ngày vào thời gian hiện hành
//     *
//     * @param days số ngày cần bổ sung vào thời gian hiện tại
//     * @return Date kết quả
//     */
    public static Date add(int days) {
        Date now = XDate.now();
        now.setTime(now.getTime() + days * 24 * 60 * 60 * 1000);
        return now;
    }
}
