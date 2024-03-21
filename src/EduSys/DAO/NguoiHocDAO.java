/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSys.DAO;

import EduSys.Entity.NguoiHoc;
import EduSys.Utils.Auth;
import EduSys.Utils.JDBCHelper;
import EduSys.Utils.MsgBox;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import org.apache.log4j.PropertyConfigurator;

public class NguoiHocDAO {

    public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(HocVienDAO.class);

    public static void insert(NguoiHoc model) {
        PropertyConfigurator.configure("src/EduSys/Log/log4j.properties");
        String sql = "INSERT INTO NguoiHoc (MaNH, HoTen, NgaySinh, GioiTinh, "
                + "DienThoai, Email, GhiChu, MaNV)VALUES( ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?)";
        JDBCHelper.executeUpdate(sql,
                model.getMaNH(),
                model.getHoTen(),
                model.getNgaySinh(),
                model.isGioiTinh(),
                model.getDienThoai(),
                model.getEmail(),
                model.getGhiChu(),
                model.getMaNV());
        MsgBox.alert(null, "Thêm thành công");
        logger.info("Tài khoản " + "[" + Auth.user.getMaNV() + "] " + "thêm người học "+"("+model.getMaNH()+")");
    }

    public static void insertFromExcel(NguoiHoc model) {

        String sql = "INSERT INTO NguoiHoc (MaNH, HoTen, NgaySinh, GioiTinh, "
                + "DienThoai, Email, GhiChu, MaNV)VALUES( ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?)";
        JDBCHelper.executeUpdate(sql,
                model.getMaNH(),
                model.getHoTen(),
                model.getNgaySinh(),
                model.isGioiTinh(),
                model.getDienThoai(),
                model.getEmail(),
                model.getGhiChu(),
                model.getMaNV());
        //  MsgBox.alert(null, "Thêm thành công");

    }

    public static void update(NguoiHoc model) {
      //  System.out.println(model.getNgaySinh());
        PropertyConfigurator.configure("src/EduSys/Log/log4j.properties");
        String sql
                = "UPDATE NguoiHoc SET HoTen=?, NgaySinh=?, GioiTinh=?, DienThoai=?, Email=?, GhiChu=?,MaNV =  ? WHERE  MaNH =  ?";
        JDBCHelper.executeUpdate(sql,
                model.getHoTen(),
                model.getNgaySinh(),
                model.isGioiTinh(),
                model.getDienThoai(),
                model.getEmail(),
                model.getGhiChu(),
                model.getMaNV(),
                model.getMaNH());
        MsgBox.alert(null, "Sửa thành công");
              logger.info("Tài khoản " + "[" + Auth.user.getMaNV() + "] " + "sửa người học "+"("+model.getMaNH()+")");
    }

    public static void delete(String id) {
        String sql = "DELETE FROM NguoiHoc WHERE MaNH=?";
        JDBCHelper.executeUpdate(sql, id);
        MsgBox.alert(null, "Xóa thành công");
              logger.info("Tài khoản " + "[" + Auth.user.getMaNV() + "] " + "xóa người học "+"("+id+")");
    }

    public List<NguoiHoc> select() {
        String sql = "SELECT * FROM NguoiHoc";
        return select(sql);
    }

    public List<NguoiHoc> selectdscohoc() {
        String sql = "select * from NguoiHoc where NguoiHoc.MaNH in (select HocVien.MaNH from HocVien )";
        return select(sql);
    }

    public List<NguoiHoc> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM NguoiHoc WHERE HoTen LIKE ?";
        return select(sql, "%" + keyword + "%");
    }

    public List<NguoiHoc> selectByCourse(Integer makh) {
        String sql = "SELECT * FROM NguoiHoc WHERE MaNH NOT IN (SELECT MaNH FROM HocVien WHERE MaKH=?)";
        return select(sql, makh);
    }

    public NguoiHoc findById(String manh) {
        String sql = "SELECT * FROM NguoiHoc WHERE MaNH=?";
        List<NguoiHoc> list = select(sql, manh);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<NguoiHoc> select(String sql, Object... args) {
        List<NguoiHoc> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    NguoiHoc model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {

                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private NguoiHoc readFromResultSet(ResultSet rs) throws SQLException {
        NguoiHoc model = new NguoiHoc();
        model.setMaNH(rs.getString("MaNH"));
        model.setHoTen(rs.getString("HoTen"));
        model.setNgaySinh(rs.getDate("NgaySinh"));
        model.setGioiTinh(rs.getBoolean("GioiTinh"));
        model.setDienThoai(rs.getString("DienThoai"));
        model.setEmail(rs.getString("Email"));
        model.setGhiChu(rs.getString("GhiChu"));
        model.setMaNV(rs.getString("MaNV"));
        model.setNgayDK(rs.getDate("NgayDK"));
        return model;
    }
}
