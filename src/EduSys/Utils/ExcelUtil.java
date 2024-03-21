/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSys.Utils;

import EduSys.DAO.NguoiHocDAO;
import EduSys.DAO.ThongKeDAO;
import EduSys.Entity.KhoaHoc;
import EduSys.Entity.NguoiHoc;
import EduSys.UI.ThongKeJPanel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 *
 * @author Ha Thanh Liem
 */
public class ExcelUtil {

    public static Workbook printBangDiemKhoaHocToExcel(javax.swing.JTable tblBangDiem,
            javax.swing.JComboBox<String> cbKhoaHoc, ThongKeDAO tkDAO) throws FileNotFoundException, IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Bảng điểm");
        KhoaHoc kh = (KhoaHoc) cbKhoaHoc.getSelectedItem();
        int rownum = 0;
        Cell cell = null;
        Row row = null;
        HSSFCellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(rownum);
        //MaNH
        cell = row.createCell(0, CellType.NUMERIC);
        cell.setCellValue("Mã KH");
        cell.setCellStyle(style);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Mã NH");
        cell.setCellStyle(style);

        //Họ Tên
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Họ và tên");
        cell.setCellStyle(style);

        //Điểm 
        cell = row.createCell(3, CellType.NUMERIC);
        cell.setCellValue("Điểm");
        cell.setCellStyle(style);

        //Xếp loại
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Xếp loại");
        cell.setCellStyle(style);
        // 
        if (kh != null) {
            List<Object[]> list = tkDAO.getBangDiem(kh.getMaKH());

            //DATA 
            for (int i = 0; i < list.size(); i++) {
                rownum++;
                row = sheet.createRow(rownum);

                //ID Student
                cell = row.createCell(0, CellType.NUMERIC);
                cell.setCellValue((Integer) tblBangDiem.getValueAt(i, 0));

                cell = row.createCell(1, CellType.STRING);
//                cell.setCellValue((String) tblBangDiem.getValueAt(i, 1));
                Object value = tblBangDiem.getValueAt(i, 1);
                if (value != null) {
                    if (value instanceof String) {
                        cell.setCellValue((String) value);
                    } else if (value instanceof Number) {
                        cell.setCellValue(((Number) value).doubleValue());
                    }
                }
                //FULL NAME
                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue((String) tblBangDiem.getValueAt(i, 2));

                //POINT
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue((Double) tblBangDiem.getValueAt(i, 3));

                //CLASSIFICATION
                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue((String) tblBangDiem.getValueAt(i, 4));

            }
        }
        return workbook;

    }

    public static void chooseDirectoryToSave(Workbook workbook) {
        JFileChooser choose = new JFileChooser();
        int x = choose.showSaveDialog(null);
        if (x == JFileChooser.APPROVE_OPTION) {
            try {
                String file = choose.getSelectedFile().getAbsolutePath().toString();
                FileOutputStream outFile = new FileOutputStream(file+".xlsx");
                workbook.write(outFile);
                workbook.close();
                outFile.close();
                MsgBox.alert(null, "Xuất tệp Excel thành công!");
            } catch (IOException ex) {
                Logger.getLogger(ThongKeJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static Workbook printNguoiHocToExcel(javax.swing.JTable tblBangDiem, ThongKeDAO dao) throws FileNotFoundException, IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Người học");

        //    KhoaHoc kh = (KhoaHoc) cbKhoaHoc.getSelectedItem()
        List<Object[]> list = dao.getNguoiHoc();

        int rownum = 0;
        Cell cell = null;
        Row row = null;
        // 
        HSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rownum);

        //MaNH
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Năm");
        cell.setCellStyle(style);

        //Họ Tên
        cell = row.createCell(1, CellType.NUMERIC);
        cell.setCellValue("Số người học");
        cell.setCellStyle(style);

        //Điểm 
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Đầu tiên");
        cell.setCellStyle(style);

        //Xếp loại
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Sau cùng");
        cell.setCellStyle(style);

        //DATA 
        for (int i = 0; i < list.size(); i++) {
            rownum++;
            row = sheet.createRow(rownum);

            //ID Student
            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue((Integer) tblBangDiem.getValueAt(i, 0));

            //FULL NAME
            cell = row.createCell(1, CellType.NUMERIC);
            cell.setCellValue((Integer) tblBangDiem.getValueAt(i, 1));

            //POINT
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue((String) tblBangDiem.getValueAt(i, 2).toString());

            //CLASSIFICATION
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue((String) tblBangDiem.getValueAt(i, 3).toString());

        }

        return workbook;

    }

    public static Workbook printTongHopDiemToExcel(javax.swing.JTable tblBangDiem, ThongKeDAO tkDAO) throws FileNotFoundException, IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Tổng hợp điểm");

        List<Object[]> list = tkDAO.getDiemTheoChuyenDe();

        int rownum = 0;
        Cell cell = null;
        Row row = null;
        // 
        HSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rownum);

        //MaNH
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Chuyên đề");
        cell.setCellStyle(style);

        //Họ Tên
        cell = row.createCell(1, CellType.NUMERIC);
        cell.setCellValue("Tổng số học viên");
        cell.setCellStyle(style);

        //Điểm 
        cell = row.createCell(2, CellType.NUMERIC);
        cell.setCellValue("Điểm thấp nhất");
        cell.setCellStyle(style);

        //Xếp loại
        cell = row.createCell(3, CellType.NUMERIC);
        cell.setCellValue("Điểm cao nhất");
        cell.setCellStyle(style);

        cell = row.createCell(4, CellType.NUMERIC);
        cell.setCellValue("Điểm trung bình");
        cell.setCellStyle(style);

        //DATA 
        for (int i = 0; i < list.size(); i++) {
            rownum++;
            row = sheet.createRow(rownum);

            //ID Student
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue((String) tblBangDiem.getValueAt(i, 0));

            //FULL NAME
            cell = row.createCell(1, CellType.NUMERIC);
            cell.setCellValue((Integer) tblBangDiem.getValueAt(i, 1));

            //POINT
            cell = row.createCell(2, CellType.NUMERIC);
            cell.setCellValue((Double) tblBangDiem.getValueAt(i, 2));

            //CLASSIFICATION
            cell = row.createCell(3, CellType.NUMERIC);
            cell.setCellValue((Double) tblBangDiem.getValueAt(i, 3));

            cell = row.createCell(4, CellType.NUMERIC);
            cell.setCellValue((Double) tblBangDiem.getValueAt(i, 4));

        }

        return workbook;

    }

    public static Workbook printDoanhThuToExcel(javax.swing.JTable tblBangDiem,
            javax.swing.JComboBox<String> cbKhoaHoc, ThongKeDAO tkDAO) throws FileNotFoundException, IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Doanh thu");
        int rownum = 0;
        Cell cell = null;
        Row row = null;
        HSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rownum);

        //MaNH
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Chuyên đề");
        cell.setCellStyle(style);

        //Họ Tên
        cell = row.createCell(1, CellType.NUMERIC);
        cell.setCellValue("Số khóa");
        cell.setCellStyle(style);

        //Điểm 
        cell = row.createCell(2, CellType.NUMERIC);
        cell.setCellValue("Số HV");
        cell.setCellStyle(style);

        //Xếp loại
        cell = row.createCell(3, CellType.NUMERIC);
        cell.setCellValue("Doanh thu");
        cell.setCellStyle(style);

        cell = row.createCell(4, CellType.NUMERIC);
        cell.setCellValue("Học phí thấp nhất");
        cell.setCellStyle(style);

        cell = row.createCell(5, CellType.NUMERIC);
        cell.setCellValue("Học phí cao nhất");
        cell.setCellStyle(style);

        cell = row.createCell(6, CellType.NUMERIC);
        cell.setCellValue("Học phí trung bình");
        cell.setCellStyle(style);

        if (cbKhoaHoc.getSelectedItem() != null) {
            int nam = Integer.parseInt(cbKhoaHoc.getSelectedItem().toString());

            List<Object[]> list = tkDAO.getDoanhThu(nam);

            // 
            //DATA 
            for (int i = 0; i < list.size(); i++) {
                rownum++;
                row = sheet.createRow(rownum);

                //ID Student
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue((String) tblBangDiem.getValueAt(i, 0));

                //FULL NAME
                cell = row.createCell(1, CellType.NUMERIC);
                cell.setCellValue((Integer) tblBangDiem.getValueAt(i, 1));

                //POINT
                cell = row.createCell(2, CellType.NUMERIC);
                cell.setCellValue((Integer) tblBangDiem.getValueAt(i, 2));

                //CLASSIFICATION
                cell = row.createCell(3, CellType.NUMERIC);
                cell.setCellValue((Double) tblBangDiem.getValueAt(i, 3));

                cell = row.createCell(4, CellType.NUMERIC);
                cell.setCellValue((Double) tblBangDiem.getValueAt(i, 4));

                cell = row.createCell(5, CellType.NUMERIC);
                cell.setCellValue((Double) tblBangDiem.getValueAt(i, 5));

                cell = row.createCell(6, CellType.NUMERIC);
                cell.setCellValue((Double) tblBangDiem.getValueAt(i, 6));

            }
        }
        return workbook;

    }

    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    public static JFileChooser fileChooser;

    public static File chonfile() {
        File excelFile = null;
        fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            excelFile = XImage.saveExel(file);

        }
        return excelFile;
    }

    public static File chonFileExcelImportNguoiHoc() {
        File excelFile = null;
        fileChooser = new JFileChooser();

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            excelFile = XImage.saveExel(file); // lưu hình vào thư mục logos

        }
        return excelFile;
    }

    public static void importNguoiHocFromExcel(File excelFile, NguoiHocDAO nhdao) {
        NguoiHoc nguoiHoc = new NguoiHoc();
        try {
            FileInputStream file = new FileInputStream(excelFile);

            //Create Workbook instance holding reference to .xlsx file
            HSSFWorkbook workbook = new HSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();//Skip the header row
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                NguoiHoc nh = nhdao.findById(row.getCell(0).getStringCellValue());
                if (nh != null) {
                    return;
                }
                nguoiHoc.setMaNH(row.getCell(0).getStringCellValue());
                nguoiHoc.setHoTen(row.getCell(1).getStringCellValue());
                if (row.getCell(2).getStringCellValue().equals("Nam")) {
                    nguoiHoc.setGioiTinh(true);
                } else {
                    nguoiHoc.setGioiTinh(false);
                }

                nguoiHoc.setNgaySinh(XDate.toDate(row.getCell(3).getStringCellValue(),
                        "MM/dd/yyyy"));
                nguoiHoc.setDienThoai(String.valueOf(row.getCell(4).getStringCellValue()));
                nguoiHoc.setEmail(row.getCell(5).getStringCellValue());
                nguoiHoc.setGhiChu("");
                nguoiHoc.setMaNV(Auth.user.getMaNV());
                nguoiHoc.setNgayDK(new Date());

                nhdao.insertFromExcel(nguoiHoc);

            }
            file.close();
            //  this.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
