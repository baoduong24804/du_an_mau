/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSys.DAO;

import EduSys.Entity.NhanVien;
import EduSys.Utils.Auth;
import EduSys.Utils.JDBCHelper;
import EduSys.Utils.MsgBox;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import org.apache.log4j.PropertyConfigurator;

public class NhanVienDAO {
public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(NhanVienDAO.class);
    public void insert(NhanVien model) {
        PropertyConfigurator.configure("src/EduSys/Log/log4j.properties");
        String sql = "INSERT INTO NhanVien (MaNV, HoTen, MatKhau,Gmail,  VaiTro) VALUES (?,?, ?, ?, ?)";
        JDBCHelper.executeUpdate(sql,
                model.getMaNV(),
                model.getHoTen(),
                model.getMatKhau(),
                model.getGmail(),
                model.isVaiTro());
        MsgBox.alert(null, "Thêm thành công");
         logger.info("Tài khoản " + "[" + Auth.user.getMaNV() + "] " + "thêm nhân viên "+"("+model.getMaNV()+")");
    }

    public void update(NhanVien model) {
        PropertyConfigurator.configure("src/EduSys/Log/log4j.properties");
        String sql = "UPDATE NhanVien SET HoTen=?, MatKhau=?, Gmail=?,  VaiTro=? WHERE MaNV=?";
        JDBCHelper.executeUpdate(sql,
                model.getHoTen(),
                model.getMatKhau(),
                model.getGmail(),
                model.isVaiTro(),
                model.getMaNV());
        MsgBox.alert(null, "Sửa thành công");
logger.info("Tài khoản " + "[" + Auth.user.getMaNV() + "] " + "sửa nhân viên "+"("+model.getMaNV()+")");
    }

    public void forgotpass(int rdpass, String manv) {
        String sql = "UPDATE NhanVien SET MatKhau=? WHERE MaNV=?";
        JDBCHelper.executeUpdate(sql,
                rdpass,
                    manv
        );

    }

    public void delete(String MaNV) {
        PropertyConfigurator.configure("src/EduSys/Log/log4j.properties");
        String sql = "DELETE FROM NhanVien WHERE MaNV=?";
        JDBCHelper.executeUpdate(sql, MaNV);
        MsgBox.alert(null, "Xóa thành công");
        logger.info("Tài khoản " + "[" + Auth.user.getMaNV() + "] " + "xóa nhân viên "+"("+MaNV+")");
    }

    public List<NhanVien> select() {
        String sql = "SELECT * FROM NhanVien";
        return select(sql);
    }

    public NhanVien findById(String manv) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV=?";
        List<NhanVien> list = select(sql, manv);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<NhanVien> select(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    NhanVien model = readFromResultSet(rs);
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

    private NhanVien readFromResultSet(ResultSet rs) throws SQLException {
        NhanVien model = new NhanVien();
        model.setMaNV(rs.getString("MaNV"));
        model.setHoTen(rs.getString("HoTen"));
        model.setMatKhau(rs.getString("MatKhau"));
        model.setGmail(rs.getString("Gmail"));
        model.setVaiTro(rs.getBoolean("VaiTro"));
        return model;
    }

}
