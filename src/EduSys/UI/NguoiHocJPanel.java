/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package EduSys.UI;

import EduSys.DAO.NguoiHocDAO;
import EduSys.Entity.NguoiHoc;
import EduSys.Utils.Auth;
import EduSys.Utils.ExcelUtil;
import EduSys.Utils.KTValidate;
import EduSys.Utils.MsgBox;
import EduSys.Utils.XDate;
import java.awt.HeadlessException;
import java.io.File;
import java.util.List;
import java.util.Locale;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author anhba
 */
public class NguoiHocJPanel extends javax.swing.JPanel {

    DefaultTableModel model = new DefaultTableModel(new Object[]{"STT", "Mã NH", "Họ và tên", "Giới tính", "Ngày sinh", "Điện thoại", "Email", "Mã NV", "Ngày ĐK"}, 0);

    /**
     * Creates new form NguoiHocJPanel
     */
    public NguoiHocJPanel() {
        initComponents();
        tbl.setModel(model);
        loadData();
        setStatus(true);
        chinhDoDaiCot();

        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                timKiem(); //  throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                timKiem();  //   throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //  timKiem();  //   throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }
    int index = 0;
    NguoiHocDAO nhdao = new NguoiHocDAO();

    void chinhDoDaiCot() {
        TableColumnModel columnModel2 = tbl.getColumnModel();
        TableColumn column2 = columnModel2.getColumn(0);
        column2.setPreferredWidth(40);

        TableColumnModel columnModel = tbl.getColumnModel();
        TableColumn column = columnModel.getColumn(2);
        column.setPreferredWidth(150);

        TableColumnModel columnModel3 = tbl.getColumnModel();
        TableColumn column3 = columnModel3.getColumn(3);
        column3.setPreferredWidth(60);

        TableColumnModel columnModel5 = tbl.getColumnModel();
        TableColumn column5 = columnModel5.getColumn(4);
        column5.setPreferredWidth(80);

        TableColumnModel columnModel6 = tbl.getColumnModel();
        TableColumn column6 = columnModel6.getColumn(5);
        column6.setPreferredWidth(90);

        TableColumnModel columnModel4 = tbl.getColumnModel();
        TableColumn column4 = columnModel4.getColumn(6);
        column4.setPreferredWidth(150);

        TableColumnModel columnModel7 = tbl.getColumnModel();
        TableColumn column7 = columnModel7.getColumn(7);
        column7.setPreferredWidth(50);

    }

    void loadData() {
        int stt = 0;
        model.setRowCount(0);
        try {
            for (NguoiHoc nh : nhdao.select()) {
                model.addRow(new Object[]{++stt, nh.getMaNH(), nh.getHoTen(), nh.isGioiTinh() ? "Nam" : "Nữ", XDate.ChuyenNgaySangChuoi(nh.getNgaySinh().toString()), nh.getDienThoai(), nh.getEmail(), nh.getMaNV(), XDate.ChuyenNgaySangChuoi(nh.getNgayDK().toString())});
            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(null, "Lấy dữ liệu thất bại");
        }

    }

    void timKiem() {
        int stt = 0;
        String keyword = txtTimKiem.getText();
        List<NguoiHoc> list = nhdao.selectByKeyword(keyword);
//            if (list.size() <= 0) {
//             //   MsgBox.alert(this, "Không tìm thấy");
//                List<NguoiHoc> list2 = nhdao.selectByKeyword("");
//                model.setRowCount(0);
//                for (NguoiHoc nh : list2) {
//                    model.addRow(new Object[]{++stt, nh.getMaNH(), nh.getHoTen(), nh.isGioiTinh() ? "Nam" : "Nữ", XDate.ChuyenNgaySangChuoi(nh.getNgaySinh().toString()), nh.getDienThoai(), nh.getEmail(), nh.getMaNV(), XDate.ChuyenNgaySangChuoi(nh.getNgayDK().toString())});
//                }
//                return;
//            }
        model.setRowCount(0);
        for (NguoiHoc nh : list) {
            model.addRow(new Object[]{++stt, nh.getMaNH(), nh.getHoTen(), nh.isGioiTinh() ? "Nam" : "Nữ", XDate.ChuyenNgaySangChuoi(nh.getNgaySinh().toString()), nh.getDienThoai(), nh.getEmail(), nh.getMaNV(), XDate.ChuyenNgaySangChuoi(nh.getNgayDK().toString())});
        }
    }

    void insert() {
   //     System.out.println("Ngay:" + XDate.ChuyenNgay(txtNgaySinh.getDate()));

        // Nam/ thang /ngay
        NguoiHoc nh = nhdao.findById(txtMaNH.getText().trim());
        if (nh != null) {
            MsgBox.alert(this, "Đã có mã người học: " + txtMaNH.getText().trim());
            return;
        }

        if (txtEmail.getText().trim().isEmpty() || txtHoTen.getText().trim().isEmpty() || txtMaNH.getText().trim().isEmpty()
                || txtNgaySinh.toString().trim().isEmpty() || txtSDT.getText().trim().isEmpty()) {
            MsgBox.alertRed(null, "Vui lòng điền đầy đủ thông tin");
            return;
        }
        if (!KTValidate.kiemTraMa(txtMaNH.getText().trim(), 7)) {
            MsgBox.alertRed(null, "Mã học viên phải đúng 7 kí tự");
            return;
        }
        if (!KTValidate.kiemTraKiTuAlphabet(txtHoTen.getText().trim())) {
            MsgBox.alertRed(null, "Họ và tên chỉ được nhập chữ cái và khoảng trắng");
            return;
        }

        if (!KTValidate.kiemTraSDT(txtSDT.getText().trim())) {
            MsgBox.alertRed(null, "SDT phải từ 10 số");
            return;
        }

        try {
            XDate.ChuyenNgay(txtNgaySinh.getDate());
        } catch (Exception e) {
            MsgBox.alertRed(null, "Ngày phải đúng định dạng và phải từ 16 tuổi");
            return;
        }

        if (!KTValidate.kiemTraCoTren16(XDate.ChuyenNgay(txtNgaySinh.getDate()))) {
            MsgBox.alertRed(null, "Ngày phải đúng định dạng và phải từ 16 tuổi");
            return;
        }
        if (!KTValidate.kiemTraGmail(txtEmail.getText().trim())) {
            MsgBox.alertRed(null, "Gmail không đúng định dạng");
            return;
        }

        NguoiHoc model = getModel();
        try {
            nhdao.insert(model);
            this.loadData();
            this.clear();
            //  MsgBox.alert(this, "Thêm người học thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Thêm người học thất bại!");
        }
    }

    void update() {
        if (txtEmail.getText().trim().isEmpty() || txtHoTen.getText().trim().isEmpty() || txtMaNH.getText().trim().isEmpty()
                || txtNgaySinh.toString().trim().isEmpty() || txtSDT.getText().trim().isEmpty()) {
            MsgBox.alertRed(null, "Vui lòng điền đầy đủ thông tin");
            return;
        }
        if (!KTValidate.kiemTraMa(txtMaNH.getText().trim(), 7)) {
            MsgBox.alertRed(null, "Mã học viên phải đúng 7 kí tự");
            return;
        }
        if (!KTValidate.kiemTraKiTuAlphabet(txtHoTen.getText().trim())) {
            MsgBox.alertRed(null, "Họ và tên chỉ được nhập chữ cái và khoảng trắng");
            return;
        }

        if (!KTValidate.kiemTraSDT(txtSDT.getText().trim())) {
            MsgBox.alertRed(null, "SDT phải từ 10 số");
            return;
        }

        try {
            XDate.ChuyenNgay(txtNgaySinh.getDate());
        } catch (Exception e) {
            MsgBox.alertRed(null, "Ngày phải đúng định dạng và phải từ 16 tuổi");
            return;
        }

        if (!KTValidate.kiemTraCoTren16(XDate.ChuyenNgay(txtNgaySinh.getDate()))) {
            MsgBox.alertRed(null, "Ngày phải đúng định dạng và phải từ 16 tuổi");
            return;
        }
        if (!KTValidate.kiemTraGmail(txtEmail.getText().trim())) {
            MsgBox.alertRed(null, "Gmail không đúng định dạng");
            return;
        }

        NguoiHoc model = getModel();
        try {
            nhdao.update(model);
            this.loadData();
            this.clear();
            //    MsgBox.alert(this, "Cập nhật người học thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Cập nhật người học thất bại!");
        }
    }

    void delete() {
        if (MsgBox.confirm(this, "Bạn thực sự muốn xóa người học này?")) {
            String manh = txtMaNH.getText();
            try {
                nhdao.delete(manh);
                this.loadData();
                this.clear();
                //    MsgBox.alert(this, "Xóa người học thành công!");
            } catch (HeadlessException e) {
                e.printStackTrace();
                MsgBox.alert(this, "Xóa người học thất bại!");
            }
        }
    }

    void clear() {

        NguoiHoc model = new NguoiHoc();
        model.setMaNV(Auth.user.getMaNV());
        model.setNgaySinh(XDate.now());
        model.setNgayDK(XDate.now());
        this.setModel(model);
        this.setStatus(true);
    }

    void edit() {
        try {
            String manh = (String) tbl.getValueAt(this.index, 1);
            NguoiHoc model = nhdao.findById(manh);
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setModel(NguoiHoc model
    ) {
        txtMaNH.setText(model.getMaNH());
        txtHoTen.setText(model.getHoTen());
        cboGioiTinh.setSelectedIndex(model.isGioiTinh() ? 0 : 1);
        txtSDT.setText(model.getDienThoai());
        txtEmail.setText(model.getEmail());
        txtGhiChu.setText(model.getGhiChu());
        txtNgaySinh.setDate(model.getNgaySinh());

    }

    NguoiHoc getModel() {
        NguoiHoc model = new NguoiHoc();
        model.setMaNH(txtMaNH.getText().trim());
        model.setHoTen(txtHoTen.getText().trim());
        model.setGioiTinh(cboGioiTinh.getSelectedIndex() == 0);
        String date = XDate.ChuyenNgay(txtNgaySinh.getDate());
        model.setNgaySinh(XDate.toDate(date, "dd/MM/yyyy"));
        model.setDienThoai(txtSDT.getText().trim());
        model.setEmail(txtEmail.getText().trim());
        model.setGhiChu(txtGhiChu.getText().trim());
        model.setMaNV(Auth.user.getMaNV().trim());
        model.setNgayDK(XDate.now());
        return model;
    }

    void setStatus(boolean insertable
    ) {
        txtMaNH.setEditable(insertable);

        txtMaNH.setEditable(insertable);
        btnThem.setEnabled(insertable);
        btnSua.setEnabled(!insertable);
        btnXoa.setEnabled(!insertable);
        boolean first = this.index > 0;
        boolean last = this.index < tbl.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPrev.setEnabled(!insertable && first);
        btnLast.setEnabled(!insertable && last);
        btnNext.setEnabled(!insertable && last);
        if (!Auth.isManager()) {
            btnXoa.setEnabled(false);
        }
    }

    private void first() {
        this.index = 0;
        this.edit();
        this.btnMoi.setEnabled(true);
    }

    private void prev() {
        this.index--;
        this.edit();
        this.btnMoi.setEnabled(true);
    }

    private void next() {
        this.index++;
        this.edit();

    }

    private void last() {
        this.index = tbl.getRowCount() - 1;
        this.edit();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaNH = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cboGioiTinh = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(684, 504));

        jTabbedPane1.setFont(jTabbedPane1.getFont().deriveFont(jTabbedPane1.getFont().getSize()+2f));

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+2f));
        jLabel2.setText("Mã người học");

        jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getSize()+2f));
        jLabel3.setText("Họ và tên");

        jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getSize()+2f));
        jLabel4.setText("Giới tính");

        cboGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getSize()+2f));
        jLabel5.setText("Ngày sinh");

        jLabel6.setFont(jLabel6.getFont().deriveFont(jLabel6.getFont().getSize()+2f));
        jLabel6.setText("Điện thoại");

        jLabel7.setFont(jLabel7.getFont().deriveFont(jLabel7.getFont().getSize()+2f));
        jLabel7.setText("Địa chỉ Email (vidu@gmail.com)");

        jLabel8.setFont(jLabel8.getFont().deriveFont(jLabel8.getFont().getSize()+2f));
        jLabel8.setText("Ghi chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        btnThem.setFont(btnThem.getFont().deriveFont(btnThem.getFont().getSize()+2f));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setFont(btnSua.getFont().deriveFont(btnSua.getFont().getSize()+2f));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setFont(btnXoa.getFont().deriveFont(btnXoa.getFont().getSize()+2f));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setFont(btnMoi.getFont().deriveFont(btnMoi.getFont().getSize()+2f));
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        jButton2.setText("Đọc Excel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2)
                                .addComponent(txtMaNH)
                                .addComponent(jLabel3)
                                .addComponent(txtHoTen)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6)
                                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(36, 36, 36)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel7)
                                                .addComponent(jLabel5)
                                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addComponent(jLabel8)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMaNH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEmail)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThem)
                            .addComponent(btnSua)
                            .addComponent(btnXoa)
                            .addComponent(btnFirst)
                            .addComponent(btnPrev)
                            .addComponent(btnNext)
                            .addComponent(btnLast)
                            .addComponent(btnMoi)
                            .addComponent(jButton2))
                        .addGap(39, 39, 39))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        txtNgaySinh.setDateFormatString("dd/MM/yyyy");
        txtNgaySinh.setLocale(new Locale("vi", "VN"));

        jTabbedPane1.addTab("Quản lý", jPanel1);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã NH", "Họ và tên", "Giới tính", "Ngày sinh", "Điện thoại", "Email", "Mã NV", "Ngày ĐK"
            }
        ));
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tbl);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(146, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh sách", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 689, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane1)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane1)
                    .addContainerGap()))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() >= 1) {
            this.index = tbl.rowAtPoint(evt.getPoint());
            if (this.index >= 0) {
                this.edit();
                // System.out.println("hi");
                jTabbedPane1.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tblMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        File file = ExcelUtil.chonFileExcelImportNguoiHoc();
        if (file == null) {
            return;
        } else {
            ExcelUtil.importNguoiHocFromExcel(file, nhdao);
            MsgBox.alert(this, "Import danh sách người học thành công!");
            this.loadData();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboGioiTinh;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNH;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
