/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSys.DAO;

import EduSys.Entity.KhoaHoc;
import EduSys.Utils.Auth;
import EduSys.Utils.JDBCHelper;
import EduSys.Utils.MsgBox;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import org.apache.log4j.PropertyConfigurator;

public class KhoaHocDAO {
public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(KhoaHocDAO.class);
    public void insert(KhoaHoc model) {
        PropertyConfigurator.configure("src/EduSys/Log/log4j.properties");
        String sql
                = "INSERT INTO KhoaHoc (MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV) VALUES (?, ?, ?, ?, ?,?)";
        JDBCHelper.executeUpdate(sql,
                model.getMaCD(),
                model.getHocPhi(),
                model.getThoiLuong(),
                model.getNgayKG(),
                model.getGhiChu(),
                model.getMaNV());
        MsgBox.alert(null, "Thêm thành công");
        logger.info("Tài khoản " + "[" + Auth.user.getMaNV() + "] " + "thêm khóa học "+"("+model.getMaKH()+")");
    }

    public void update(KhoaHoc model) {
        PropertyConfigurator.configure("src/EduSys/Log/log4j.properties");
        String sql
                = "UPDATE KhoaHoc SET MaCD=?, HocPhi=?, ThoiLuong=?, NgayKG=?, GhiChu=?, MaNV=? WHERE MaKH = ?";
        JDBCHelper.executeUpdate(sql,
                model.getMaCD(),
                model.getHocPhi(),
                model.getThoiLuong(),
                model.getNgayKG(),
                model.getGhiChu(),
                model.getMaNV(),
                model.getMaKH());
        MsgBox.alert(null, "Sửa thành công");
        logger.info("Tài khoản " + "[" + Auth.user.getMaNV() + "] " + "sữa khóa học "+"("+model.getMaKH()+")");
    }

    public void delete(Integer MaKH) {
        PropertyConfigurator.configure("src/EduSys/Log/log4j.properties");
        String sql = "DELETE FROM KhoaHoc WHERE MaKH=?";
        JDBCHelper.executeUpdate(sql, MaKH);
        MsgBox.alert(null, "Xóa thành công");
        logger.info("Tài khoản " + "[" + Auth.user.getMaNV() + "] " + "xóa khóa học "+"("+MaKH+")");
    }

    public List<KhoaHoc> select() {
        String sql = "SELECT * FROM KhoaHoc";
        return select(sql);
    }

    public List<KhoaHoc> selectbyMacd(String cd) {
        String sql = "SELECT * FROM KhoaHoc where MaCD = ?";
        return select(sql, cd);
    }

    public KhoaHoc findById(Integer makh) {
        String sql = "SELECT * FROM KhoaHoc WHERE MaKH=?";
        List<KhoaHoc> list = select(sql, makh);
        return list.size() > 0 ? list.get(0) : null;
    }

//    public List<KhoaHoc> selectByChuyenDe(String macd) {
//        String sql = "SELECT * FROM KhoaHoc WHERE MaCD=?";
//        return this.select(sql, macd);
//    }

    public KhoaHoc findById2(String macd) {
        String sql = "SELECT * FROM KhoaHoc WHERE MaCD=?";
        List<KhoaHoc> list = select(sql, macd);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<KhoaHoc> select(String sql, Object... args) {
        List<KhoaHoc> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    KhoaHoc model = readFromResultSet(rs);
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

    private KhoaHoc readFromResultSet(ResultSet rs) throws SQLException {
        KhoaHoc model = new KhoaHoc();
        model.setMaKH(rs.getInt("MaKH"));
        model.setHocPhi(rs.getDouble("HocPhi"));
        model.setThoiLuong(rs.getInt("ThoiLuong"));
        model.setNgayKG(rs.getDate("NgayKG"));
        model.setGhiChu(rs.getString("GhiChu"));
        model.setMaNV(rs.getString("MaNV"));
        model.setNgayTao(rs.getDate("NgayTao"));
        model.setMaCD(rs.getString("MaCD"));
        return model;
    }
}
