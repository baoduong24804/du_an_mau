/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSys.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author anhba
 */
public class KTValidate {

    public static boolean kiemTraMa(String input, int num) {
        if (input.trim().length() != num) {
            return false;
        }
        return true;
    }

    public static boolean kiemTraTrongVaDoDai(String input, int s) {
        if (input.trim().isEmpty() || input.trim().length() < s) {
            return false;
        }
        return true;
    }

    public static boolean kiemTraTrong(String input) {
        if (input.trim().isEmpty()) {
            return false;
        }
        return true;
    }

//    public static String kiemTraNgayKH(String txtNgayKhaiGiangKhoaHoc) {
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        try {
//            LocalDate ngayKhaiGiang = LocalDate.parse(txtNgayKhaiGiangKhoaHoc.trim(), formatter);
//            LocalDate ngayTao = LocalDate.parse(txtNgayKhaiGiangKhoaHoc.trim(), formatter);
//
//            if (ngayKhaiGiang.isBefore(ngayTao)) {
//                return "Nhập đúng định dạng và \nngày khai giảng phải lớn hơn ngày tạo";
//            }
//        } catch (Exception e) {
//
//            return "Nhập đúng định dạng và \nngày khai giảng phải lớn hơn ngày tạo";
//        }
//        return "";
//    }

    public static boolean kiemTraDiemKH(String input) {
        try {
            Double a = Double.parseDouble(input);
            if ((a < 0 || a > 10)) {
                if (a == -1.0) {
                    return true;
                }
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean kiemTraKiTuAlphabet(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean kiemTraGmail(String input) {
        if (!input.contains("@")) {
            return false;
        }

        return true;
    }

    public static boolean kiemTraSDT(String input) {
        // Kiểm tra từng ký tự, xem có phải là số không
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        if (input.length() < 9) {
            return false;
        }

        return true;
    }

    public static boolean kiemTraCoTren16(String input) {
        try {
            String[] parts = input.trim().split("/");
            if (parts.length < 3) {
                return false;
            }
            Calendar calendar = Calendar.getInstance();
            int nam = Integer.parseInt(parts[2].toString());
            int namht = calendar.get(Calendar.YEAR);
         //   System.out.println(parts[2]);
            if ((parts[2].length() < 4) || (namht - nam) < 15) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
    
       public static boolean kiemTraNgayTruocVaSau(String input, String input2) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate ngayKhaiGiang = LocalDate.parse(input.trim(), formatter);
            LocalDate ngayTao = LocalDate.parse(input2.trim(), formatter);

            if (ngayKhaiGiang.isBefore(ngayTao)) {
                return false;
            }
        } catch (Exception e) {

            return false;
        }
        return true;
    }

    public static boolean kiemTraNgaySinh(String dateString) {
        // Định dạng ngày/tháng/năm (dd/MM/yyyy)
        if (dateString.trim().isEmpty()) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        String[] parts = dateString.trim().split("/");
        if (parts.length < 3) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        int nam = Integer.parseInt(parts[2].toString());
        int namht = calendar.get(Calendar.YEAR);
        if ((parts[2].length() < 4) || (namht - nam) < 15) {
            return false;
        }
        try {
            // Parse chuỗi thành Date
            Date date = dateFormat.parse(dateString);

            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}
