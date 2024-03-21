/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSys.DAO;

import EduSys.Entity.HocVien;
import EduSys.Utils.Auth;
import EduSys.Utils.JDBCHelper;
import EduSys.Utils.MsgBox;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import org.apache.log4j.PropertyConfigurator;

public class HocVienDAO {
public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(HocVienDAO.class);
    public void insert(HocVien model) {
        PropertyConfigurator.configure("src/EduSys/Log/log4j.properties");
        String sql = "INSERT INTO HocVien(MaKH, MaNH, Diem) VALUES(?, ?, ?)";
        JDBCHelper.executeUpdate(sql,
                model.getMaKH(),
                model.getMaNH(),
                model.getDiem());
        //   MsgBox.alert(null, "Thêm thành công");
        logger.info("Tài khoản " + "[" + Auth.user.getMaNV() + "] " + "thêm học viên có mã người học "+"("+model.getMaNH()+")");
    }

    public void update(HocVien model) {
        PropertyConfigurator.configure("src/EduSys/Log/log4j.properties");
        String sql = "UPDATE HocVien SET Diem=? WHERE MaHV=?";
        JDBCHelper.executeUpdate(sql,
                model.getDiem(),
                model.getMaHV());
        //   MsgBox.alert(null, "Sửa thành công");
        logger.info("Tài khoản " + "[" + Auth.user.getMaNV() + "] " + "sữa học viên có mã người học "+"("+model.getMaNH()+")");
    }

    public void delete(Integer MaHV) {
        PropertyConfigurator.configure("src/EduSys/Log/log4j.properties");
        String sql = "DELETE FROM HocVien WHERE MaHV=?";
        JDBCHelper.executeUpdate(sql, MaHV);
     //   MsgBox.alert(null, "Xóa thành công");
     logger.info("Tài khoản " + "[" + Auth.user.getMaNV() + "] " + "xóa học viên "+MaHV);
    }

    public List<HocVien> select() {
        String sql = "SELECT * FROM HocVien";
        return select(sql);
    }

    public HocVien findById(Integer mahv) {
        String sql = "SELECT * FROM HocVien WHERE MaHV=?";
        List<HocVien> list = select(sql, mahv);
        return list.size() > 0 ? list.get(0) : null;
    }

    public HocVien findChuaHoc(Integer mahv) {
        String sql = "SELECT * FROM HocVien WHERE MaHV=?";
        List<HocVien> list = select(sql, mahv);
        return list.size() > 0 ? list.get(0) : null;
    }

    public HocVien findByMAKH(Integer mahv) {
        String sql = "SELECT * FROM HocVien WHERE MaKH=?";
        List<HocVien> list = select(sql, mahv);
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<HocVien> selectByMaKH(Integer makh) {
        String sql = "SELECT * FROM HocVien where MAKH = ?";
        return select(sql,makh);
    }

    public HocVien findByMaNH(String manh) {
        String sql = "SELECT * FROM HocVien WHERE MaNH=?";
        List<HocVien> list = select(sql, manh);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<HocVien> select(String sql, Object... args) {
        List<HocVien> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    HocVien model = readFromResultSet(rs);
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

    private HocVien readFromResultSet(ResultSet rs) throws SQLException {
        HocVien model = new HocVien();
        model.setMaHV(rs.getInt("MaHV"));
        model.setMaKH(rs.getInt("MaKH"));
        model.setMaNH(rs.getString("MaNH"));
        model.setDiem(rs.getDouble("Diem"));
        return model;
    }
}
