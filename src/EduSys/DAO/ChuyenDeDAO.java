/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSys.DAO;

import EduSys.Entity.ChuyenDe;
import EduSys.Utils.Auth;
import EduSys.Utils.JDBCHelper;
import EduSys.Utils.MsgBox;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import org.apache.log4j.PropertyConfigurator;

public class ChuyenDeDAO {

    public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ChuyenDeDAO.class);

    public void insert(ChuyenDe model) {
        PropertyConfigurator.configure("src/EduSys/Log/log4j.properties");
        String sql = "INSERT INTO ChuyenDe (MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa) VALUES (?, ?, ?, ?, ?, ?)";
        JDBCHelper.executeUpdate(sql,
                model.getMaCD(),
                model.getTenCD(),
                model.getHocPhi(),
                model.getThoiLuong(),
                model.getHinh(),
                model.getMoTa());
        MsgBox.alert(null, "Thêm thành công");
         logger.info("Tài khoản " + "[" + Auth.user.getMaNV() + "] " + "thêm chuyên đề "+"("+model.getMaCD()+")");
    }

    public void update(ChuyenDe model) {
        PropertyConfigurator.configure("src/EduSys/Log/log4j.properties");
        String sql = "UPDATE ChuyenDe SET TenCD=?, HocPhi=?, ThoiLuong=?, Hinh=?, MoTa=? WHERE MaCD=?";
        JDBCHelper.executeUpdate(sql,
                model.getTenCD(),
                model.getHocPhi(),
                model.getThoiLuong(),
                model.getHinh(),
                model.getMoTa(),
                model.getMaCD());
        MsgBox.alert(null, "Sửa thành công");
        logger.info("Tài khoản " + "[" + Auth.user.getMaNV() + "] " + "cập nhật chuyên đề "+"("+model.getMaCD()+")");
    }

    public void delete(String MaCD) {
        PropertyConfigurator.configure("src/EduSys/Log/log4j.properties");
        String sql = "DELETE FROM ChuyenDe WHERE MaCD=?";
        JDBCHelper.executeUpdate(sql, MaCD);
        MsgBox.alert(null, "Xóa thành công");
        logger.info("Tài khoản " + "[" + Auth.user.getMaNV() + "] " + "xóa chuyên đề "+"("+MaCD+")");
    }

    public List<ChuyenDe> select() {
        String sql = "SELECT * FROM ChuyenDe";
        return select(sql);
    }

    public ChuyenDe findById(String macd) {
        String sql = "SELECT * FROM ChuyenDe WHERE MaCD=?";
        List<ChuyenDe> list = select(sql, macd);
        return list.size() > 0 ? list.get(0) : null;
    }

    public ChuyenDe findByName(String tench) {
        String sql = "SELECT * FROM ChuyenDe WHERE TenCD=?";
        List<ChuyenDe> list = select(sql, tench);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<ChuyenDe> select(String sql, Object... args) {
        List<ChuyenDe> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    ChuyenDe model = readFromResultSet(rs);
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

    private ChuyenDe readFromResultSet(ResultSet rs) throws SQLException {
        ChuyenDe model = new ChuyenDe();
        model.setMaCD(rs.getString("MaCD"));
        model.setHinh(rs.getString("Hinh"));
        model.setHocPhi(rs.getDouble("HocPhi"));
        model.setMoTa(rs.getString("MoTa"));
        model.setTenCD(rs.getString("TenCD"));
        model.setThoiLuong(rs.getInt("ThoiLuong"));
        return model;
    }
}
