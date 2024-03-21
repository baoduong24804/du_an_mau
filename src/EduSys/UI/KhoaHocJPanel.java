/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package EduSys.UI;

import EduSys.DAO.ChuyenDeDAO;
import EduSys.DAO.KhoaHocDAO;
import EduSys.Entity.ChuyenDe;
import EduSys.Entity.KhoaHoc;
import EduSys.Utils.Auth;
import EduSys.Utils.KTValidate;
import EduSys.Utils.MsgBox;
import EduSys.Utils.XDate;
import java.awt.HeadlessException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author anhba
 */
public class KhoaHocJPanel extends javax.swing.JPanel {

    DefaultTableModel modeltbl = new DefaultTableModel(new Object[]{"STT", "Mã KH", "Chuyên đề", "Thời lượng", "Học phí", "Khai giảng", "Tạo bởi", "Ngày tạo"}, 0);

    DefaultComboBoxModel model2 = new DefaultComboBoxModel();

    /**
     * Creates new form KhoaHocJPanel
     */
    public KhoaHocJPanel() {
        initComponents();
        tblKhoaHoc.setModel(modeltbl);
        cboChuyenDe.setModel(model2);
        this.loadData();
        this.fillComboBox();
        this.clear();
        this.setStatus(true);
        chinhDoDaiCot();

    }
    int index = 0;
    KhoaHocDAO dao = new KhoaHocDAO();
    ChuyenDeDAO cddao = new ChuyenDeDAO();

    void chinhDoDaiCot() {

        TableColumnModel columnModel = tblKhoaHoc.getColumnModel();
        TableColumn column = columnModel.getColumn(0);
        column.setPreferredWidth(50);

    }

    void loadData() {
        DefaultTableModel model = (DefaultTableModel) tblKhoaHoc.getModel();
        model.setRowCount(0);
        int stt = 0;
        try {
            List<KhoaHoc> list = dao.select();
            for (KhoaHoc kh : list) {
                Object[] row = {
                    ++stt,
                    kh.getMaKH(),
                    kh.getMaCD(),
                    kh.getThoiLuong(),
                    kh.getHocPhi(),
                    XDate.ChuyenNgaySangChuoi(kh.getNgayKG().toString()),
                    kh.getMaNV(),
                    XDate.ChuyenNgaySangChuoi(kh.getNgayTao().toString())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void insert() {
        ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
        if (chuyenDe == null) {
            return;
        }

        if (txtHocPhi.getText().trim().isEmpty() || txtNgayKhaiGiang.toString().trim().isEmpty() || txtNgayTao.getText().trim().isEmpty() || txtNguoiTao.getText().trim().isEmpty() || txtThoiLuong.getText().trim().isEmpty()) {
            MsgBox.alertRed(null, "Vui lòng nhập đầy đủ thông tin");
            return;
        }
        
        try {
            XDate.ChuyenNgay(txtNgayKhaiGiang.getDate());
        } catch (Exception e) {
            MsgBox.alertRed(this, "Nhập đúng định dạng và \nngày khai giảng phải lớn hơn ngày tạo");
            return;
        }

        if (KTValidate.kiemTraNgayTruocVaSau(txtNgayTao.getText().trim(), XDate.ChuyenNgay(txtNgayKhaiGiang.getDate()))) {
            MsgBox.alertRed(this, "Nhập đúng định dạng và \nngày khai giảng phải lớn hơn ngày tạo");
            return;
        }

        KhoaHoc model = getModel();
        model.setNgayTao(new Date());
        try {
            dao.insert(model);
            this.loadData();
            this.clear();
            //       MsgBox.alert(this, "Thêm mới thành công!");
        } catch (HeadlessException e) {
            MsgBox.alert(this, "Thêm mới thất bại!");
        }
    }

    void update() {
        ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
        if (chuyenDe == null) {
            return;
        }
        if (txtHocPhi.getText().trim().isEmpty() || txtNgayKhaiGiang.toString().trim().isEmpty() || txtNgayTao.getText().trim().isEmpty() || txtNguoiTao.getText().trim().isEmpty() || txtThoiLuong.getText().trim().isEmpty()) {
            MsgBox.alertRed(null, "Vui lòng nhập đầy đủ thông tin");
            return;
        }
        try {
            XDate.ChuyenNgay(txtNgayKhaiGiang.getDate());
        } catch (Exception e) {
            MsgBox.alertRed(this, "Nhập đúng định dạng và \nngày khai giảng phải lớn hơn ngày tạo");
            return;
        }

        if (KTValidate.kiemTraNgayTruocVaSau(txtNgayTao.getText().trim(), XDate.ChuyenNgay(txtNgayKhaiGiang.getDate()))) {
            MsgBox.alertRed(this, "Nhập đúng định dạng và \nngày khai giảng phải lớn hơn ngày tạo");
            return;
        }
        KhoaHoc model = getModel();
        try {
            dao.update(model);
            this.loadData();
            //    MsgBox.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật thất bại!");
        }
    }

    void delete() {
        if (MsgBox.confirm(this, "Bạn thực sự muốn xóa khóa học này?")) {
            Integer makh = Integer.valueOf(cboChuyenDe.getToolTipText());
            try {
                dao.delete(makh);
                this.loadData();
                this.clear();
                //    MsgBox.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa thất bại!");
            }
        }
    }

    void clear() {
        KhoaHoc model = new KhoaHoc();
        ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();

        if (chuyenDe == null) {
            return;
        }
        if (Auth.user == null) {
            return;
        }
        model.setThoiLuong(chuyenDe.getThoiLuong());
        model.setHocPhi(chuyenDe.getHocPhi());
        model.setMaCD(chuyenDe.getMaCD());
        model.setMaNV(Auth.user.getMaNV());
        model.setNgayKG(XDate.now());
        model.setNgayTao(XDate.now());
        this.setStatus(true);
        this.setModel(model);
    }

    void edit() {
        try {
            Integer makh = (Integer) tblKhoaHoc.getValueAt(this.index, 1);
            KhoaHoc model = dao.findById(makh);
            ChuyenDe chuyenDe = cddao.findById(model.getMaCD());
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setModel(KhoaHoc model) {
        cboChuyenDe.setToolTipText(String.valueOf(model.getMaKH()));
        model2.setSelectedItem(cddao.findById(model.getMaCD()));
        txtNgayKhaiGiang.setDate(model.getNgayKG());
        txtHocPhi.setText(String.valueOf(model.getHocPhi()));
        txtThoiLuong.setText(String.valueOf(model.getThoiLuong()));
        txtNguoiTao.setText(model.getMaNV());
        txtNgayTao.setText(XDate.ChuyenNgay(model.getNgayTao()));
        txtGhiChu.setText(model.getGhiChu());
    }

    KhoaHoc getModel() {
        KhoaHoc model = new KhoaHoc();
        ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
        model.setMaCD(chuyenDe.getMaCD());
        String date = XDate.ChuyenNgay(txtNgayKhaiGiang.getDate());//  ngay/thang/nam
        model.setNgayKG(XDate.toDate(date, "dd/MM/yyyy"));

        model.setHocPhi(Double.parseDouble(txtHocPhi.getText()));
        model.setThoiLuong(Integer.parseInt(txtThoiLuong.getText()));
        model.setGhiChu(txtGhiChu.getText());
        model.setMaNV(Auth.user.getMaNV());

        model.setNgayTao(XDate.toDate(txtNgayTao.getText(), "dd/MM/yyyy"));
        model.setMaKH(Integer.parseInt(cboChuyenDe.getToolTipText()));
        return model;
    }

    void setStatus(boolean insertable) {
        btnThem.setEnabled(insertable);
        btnSua.setEnabled(!insertable);
        btnXoa.setEnabled(!insertable);
        boolean first = this.index > 0;
        boolean last = this.index < tblKhoaHoc.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPrev.setEnabled(!insertable && first);
        btnLast.setEnabled(!insertable && last);
        btnNext.setEnabled(!insertable && last);
        txtHocPhi.setEditable(false);
        txtThoiLuong.setEditable(false);
        txtNgayTao.setEditable(false);
        txtNguoiTao.setEditable(false);
        if (!Auth.isManager()) {
            btnXoa.setEnabled(false);
        }
    }

    void selectComboBox() {
        ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
        if (chuyenDe == null) {
            return;
        }
        txtThoiLuong.setText(String.valueOf(chuyenDe.getThoiLuong()));
        txtHocPhi.setText(String.valueOf(chuyenDe.getHocPhi()));
    }

    void fillComboBox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboChuyenDe.getModel();
        model.removeAllElements();
        try {
            List<ChuyenDe> list = cddao.select();
            for (ChuyenDe cd : list) {
                model.addElement(cd);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    private void first() {
        this.index = 0;
        this.edit();
        // this.btnMoi.setEnabled(true);
    }

    private void prev() {
        this.index--;
        this.edit();
        //  this.btnMoi.setEnabled(true);
    }

    private void next() {
        this.index++;
        this.edit();
        //  this.btnMoi.setEnabled(true);
    }

    private void last() {
        this.index = tblKhoaHoc.getRowCount() - 1;
        this.edit();
        //   this.btnMoi.setEnabled(true);
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
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtHocPhi = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtNguoiTao = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtThoiLuong = new javax.swing.JTextField();
        txtNgayTao = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        cboChuyenDe = new javax.swing.JComboBox<>();
        txtNgayKhaiGiang = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblKhoaHoc = new javax.swing.JTable();

        jTabbedPane1.setFont(jTabbedPane1.getFont().deriveFont(jTabbedPane1.getFont().getSize()+2f));

        jLabel9.setFont(jLabel9.getFont().deriveFont(jLabel9.getFont().getSize()+2f));
        jLabel9.setText("Chuyên đề");

        jLabel10.setFont(jLabel10.getFont().deriveFont(jLabel10.getFont().getSize()+2f));
        jLabel10.setText("Học phí");

        jLabel11.setFont(jLabel11.getFont().deriveFont(jLabel11.getFont().getSize()+2f));
        jLabel11.setText("Người tạo");

        jLabel12.setFont(jLabel12.getFont().deriveFont(jLabel12.getFont().getSize()+2f));
        jLabel12.setText("Ghi chú");

        jLabel13.setFont(jLabel13.getFont().deriveFont(jLabel13.getFont().getSize()+2f));
        jLabel13.setText("Ngày khai giảng");

        jLabel14.setFont(jLabel14.getFont().deriveFont(jLabel14.getFont().getSize()+2f));
        jLabel14.setText("Thời lượng ( giờ )");

        jLabel15.setFont(jLabel15.getFont().deriveFont(jLabel15.getFont().getSize()+2f));
        jLabel15.setText("Ngày tạo");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane2.setViewportView(txtGhiChu);

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

        cboChuyenDe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChuyenDeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9)
                                    .addComponent(txtHocPhi)
                                    .addComponent(txtNguoiTao)
                                    .addComponent(cboChuyenDe, 0, 220, Short.MAX_VALUE))
                                .addGap(71, 71, 71)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15)
                                    .addComponent(txtThoiLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                    .addComponent(txtNgayTao)
                                    .addComponent(txtNgayKhaiGiang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayKhaiGiang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        txtNgayKhaiGiang.setDateFormatString("dd/MM/yyyy");
        txtNgayKhaiGiang.setLocale(new Locale("vi", "VN"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(213, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quản lý", jPanel1);

        tblKhoaHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã KH", "Chuyên đề", "Thời lượng", "Học phí", "Khai giảng", "Tạo bởi", "Ngày tạo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhoaHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhoaHocMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tblKhoaHoc);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(128, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 158, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh sách", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 811, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane1)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 568, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addComponent(jTabbedPane1)
                    .addGap(4, 4, 4)))
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

    private void cboChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChuyenDeActionPerformed
        // TODO add your handling code here:
        if (cboChuyenDe.getItemCount() <= 0) {

            return;
        }
        selectComboBox();
    }//GEN-LAST:event_cboChuyenDeActionPerformed

    private void tblKhoaHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhoaHocMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() >= 1) {
            this.index = tblKhoaHoc.rowAtPoint(evt.getPoint());
            if (this.index >= 0) {
                this.edit();
                // System.out.println("hi");
                jTabbedPane1.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tblKhoaHocMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboChuyenDe;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblKhoaHoc;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHocPhi;
    private com.toedter.calendar.JDateChooser txtNgayKhaiGiang;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtNguoiTao;
    private javax.swing.JTextField txtThoiLuong;
    // End of variables declaration//GEN-END:variables
}
