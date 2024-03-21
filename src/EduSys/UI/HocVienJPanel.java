/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package EduSys.UI;

import EduSys.DAO.ChuyenDeDAO;
import EduSys.DAO.HocVienDAO;
import EduSys.DAO.KhoaHocDAO;
import EduSys.DAO.NguoiHocDAO;
import EduSys.Entity.ChuyenDe;
import EduSys.Entity.HocVien;
import EduSys.Entity.KhoaHoc;
import EduSys.Entity.NguoiHoc;
import EduSys.Utils.Auth;
import EduSys.Utils.MsgBox;
import EduSys.Utils.XDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author anhba
 */
public class HocVienJPanel extends javax.swing.JPanel {

    //  public static int MaKH = -99;
    DefaultComboBoxModel modelcbo = new DefaultComboBoxModel();

    /**
     * Creates new form HocVienJPanel
     */
    public HocVienJPanel() {
        initComponents();
        //    this.MaKH = MAKH;
        cboNguoiHoc.setModel(modelcbo);
        rdoTatCa.setSelected(true);
        this.fillComboBox();
        this.fillComboBox2();
        this.fillComboBox3();
        this.fillGridView();
        chinhDoDaiCot();
    }

    void chinhDoDaiCot() {

        TableColumnModel columnModel3 = tblHocVien.getColumnModel();
        TableColumn column3 = columnModel3.getColumn(2);
        column3.setPreferredWidth(100);

        TableColumnModel columnModel = tblHocVien.getColumnModel();
        TableColumn column = columnModel.getColumn(5);
        column.setPreferredWidth(150);

    }

    HocVienDAO hvdao = new HocVienDAO();
    NguoiHocDAO nhdao = new NguoiHocDAO();
    KhoaHocDAO khdao = new KhoaHocDAO();
    ChuyenDeDAO cddao = new ChuyenDeDAO();

    void fillComboBox2() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboChuyenDe.getModel();
        model.removeAllElements();

        List<ChuyenDe> list = cddao.select();
        if (list == null) {
            return;
        }
        for (ChuyenDe cd : list) {

            model.addElement(cd);
        }

    }

    void fillComboBox3() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNam.getModel();
        model.removeAllElements();
        ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
        if (chuyenDe == null) {
            return;
        }
        List<KhoaHoc> kh = khdao.selectbyMacd(chuyenDe.getMaCD());
        if (kh == null) {
            return;
        }
        for (KhoaHoc khoaHoc : kh) {
            //   ChuyenDe cd = cddao.findById(khoaHoc.getMaCD());

            model.addElement("[" + khoaHoc.getMaKH() + "]" + chuyenDe.getMaCD() + " (" + XDate.ChuyenNgay(khoaHoc.getNgayKG()) + ")");

        }

    }

    void fillComboBox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNguoiHoc.getModel();
        ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
        model.removeAllElements();
        if (cboNam.getSelectedIndex() == -1) {
            return;
        }
        if (chuyenDe == null) {
            return;
        }
        String nam = cboNam.getSelectedItem().toString().trim();
        int begin = cboNam.getSelectedItem().toString().indexOf("[");
        int end = cboNam.getSelectedItem().toString().lastIndexOf("]");
        String nam2 = nam.substring(begin + 1, end).trim();

        try {
            List<NguoiHoc> nhlist = nhdao.selectByCourse(Integer.parseInt(nam2.trim()));
            for (NguoiHoc nguoiHoc : nhlist) {

                model.addElement(nguoiHoc);

            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Lỗi truy vấn học viên!");
        }
    }

    void fillGridView() {
        DefaultTableModel model = (DefaultTableModel) tblHocVien.getModel();
        ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
        model.setRowCount(0);
        if (cboNam.getSelectedIndex() == -1) {
            return;
        }

        if (chuyenDe == null) {
            return;
        }
        String nam = cboNam.getSelectedItem().toString().trim();
        int begin = cboNam.getSelectedItem().toString().indexOf("[");
        int end = cboNam.getSelectedItem().toString().lastIndexOf("]");

        String nam2 = nam.substring(begin + 1, end).trim();

        List<HocVien> listHocVien = hvdao.selectByMaKH(Integer.parseInt(nam2.trim()));
        if (listHocVien.size() <= 0) {
            return;
        }
        int stt = 0;
        for (HocVien hocVien : listHocVien) {

            NguoiHoc nguoiHoc = nhdao.findById(hocVien.getMaNH());

            if (rdoTatCa.isSelected()) {
                model.addRow(new Object[]{++stt, hocVien.getMaKH(), chuyenDe.getTenCD(), hocVien.getMaHV(), hocVien.getMaNH(), nguoiHoc.getHoTen(), hocVien.getDiem()});
            }
            if (rdoDaCoDiem.isSelected()) {
                if (hocVien.getDiem() >= 0) {
                    model.addRow(new Object[]{++stt, hocVien.getMaKH(), chuyenDe.getTenCD(), hocVien.getMaHV(), hocVien.getMaNH(), nguoiHoc.getHoTen(), hocVien.getDiem()});
                }
            }

            if (rdoChuaCoDiem.isSelected()) {
                if (hocVien.getDiem() <= -1.0) {
                    model.addRow(new Object[]{++stt, hocVien.getMaKH(), chuyenDe.getTenCD(), hocVien.getMaHV(), hocVien.getMaNH(), nguoiHoc.getHoTen(), hocVien.getDiem()});
                }
            }

        }

    }

    boolean kiemTraDiem(String input) {
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

    void insert() {
        HocVien model = new HocVien();
        if (cboNam.getSelectedIndex() == -1) {
            return;
        }
        String nam = cboNam.getSelectedItem().toString().trim();
        int begin = cboNam.getSelectedItem().toString().indexOf("[");
        int end = cboNam.getSelectedItem().toString().lastIndexOf("]");

        String nam2 = nam.substring(begin + 1, end).trim();

        if (txtDiem.getText().trim().isEmpty()) {
            MsgBox.alertRed(this, "Điểm phải từ 0 đến 10 hoặc -1(chưa nhập)");
            return;
        }

        if (!kiemTraDiem(txtDiem.getText().trim().toString())) {
            MsgBox.alertRed(this, "Điểm phải từ 0 đến 10 hoặc -1(chưa nhập)");
            fillGridView();
            return;
        }

        NguoiHoc nguoiHoc = (NguoiHoc) cboNguoiHoc.getSelectedItem();
        ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
        if (nguoiHoc == null || chuyenDe == null) {
            return;
        }

        model.setMaKH(Integer.parseInt(nam2.trim()));
        model.setMaNH(nguoiHoc.getMaNH());
        if (txtDiem.getText().trim().isEmpty()) {
            model.setDiem(-1.0);
        } else {
            model.setDiem(Double.parseDouble(txtDiem.getText().trim()));
        }

        try {

            hvdao.insert(model);
            this.fillComboBox();
            this.fillGridView();
            MsgBox.alert(this, "Thêm thành công");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Lỗi thêm học viên vào khóa học!");
        }
    }

    void update() {

        for (int i = 0; i < tblHocVien.getRowCount(); i++) {
            Boolean isDelete = (Boolean) tblHocVien.getValueAt(i, 7);
            //   System.out.println(isDelete);
            if (isDelete == null || isDelete == false) {
                continue;
            }
            if (isDelete == true) {
                if (!Auth.isManager()) {
                    MsgBox.alert(null, "Chức năng xóa chỉ dành cho quản lí");
                    return;
                }
            }

        }

        for (int i = 0; i < tblHocVien.getRowCount(); i++) {
            Integer mahv = (Integer) tblHocVien.getValueAt(i, 3);
            String manh = (String) tblHocVien.getValueAt(i, 4);
            Double diem = (Double) tblHocVien.getValueAt(i, 6);
            if (!kiemTraDiem(tblHocVien.getValueAt(i, 6).toString())) {
                MsgBox.alertRed(this, "Điểm phải từ 0 đến 10 hoặc -1(chưa nhập)");
                fillGridView();
                return;
            }
            Boolean isDelete = (Boolean) tblHocVien.getValueAt(i, 7);
            //     System.out.println(isDelete);
            if (isDelete == null || isDelete == false) {
                HocVien model = new HocVien();
                model.setMaHV(mahv);
                model.setDiem(diem);
                hvdao.update(model);
                continue;
            }

            hvdao.delete(mahv);

        }
        this.fillComboBox();
        this.fillGridView();
        MsgBox.alert(this, "Cập nhật thành công!");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        cboNguoiHoc = new javax.swing.JComboBox<>();
        txtDiem = new javax.swing.JTextField();
        btnThemHocVien = new javax.swing.JButton();
        cboChuyenDe = new javax.swing.JComboBox<>();
        cboNam = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblHocVien = new javax.swing.JTable();
        rdoTatCa = new javax.swing.JRadioButton();
        rdoDaCoDiem = new javax.swing.JRadioButton();
        rdoChuaCoDiem = new javax.swing.JRadioButton();
        btnCapNhatHocVien = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Học viên khác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        cboNguoiHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNguoiHocActionPerformed(evt);
            }
        });

        txtDiem.setText("0");
        txtDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiemActionPerformed(evt);
            }
        });

        btnThemHocVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThemHocVien.setText("Thêm");
        btnThemHocVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHocVienActionPerformed(evt);
            }
        });

        cboChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChuyenDeActionPerformed(evt);
            }
        });

        cboNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboChuyenDe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboNguoiHoc, 0, 431, Short.MAX_VALUE)
                    .addComponent(cboNam, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addComponent(txtDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnThemHocVien)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboNguoiHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemHocVien, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(cboChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Học viên của khóa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        tblHocVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã KH", "Tên CD", "Mã HV", "Mã NH", "Họ và tên", "Điểm", "Xóa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHocVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHocVienMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tblHocVien);

        buttonGroup1.add(rdoTatCa);
        rdoTatCa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoTatCa.setText("Tất cả");
        rdoTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTatCaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoDaCoDiem);
        rdoDaCoDiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoDaCoDiem.setText("Đã có điểm");
        rdoDaCoDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDaCoDiemActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoChuaCoDiem);
        rdoChuaCoDiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoChuaCoDiem.setText("Chưa có điểm");
        rdoChuaCoDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoChuaCoDiemActionPerformed(evt);
            }
        });

        btnCapNhatHocVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCapNhatHocVien.setText("Cập nhật");
        btnCapNhatHocVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatHocVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(rdoTatCa)
                        .addGap(18, 18, 18)
                        .addComponent(rdoDaCoDiem)
                        .addGap(18, 18, 18)
                        .addComponent(rdoChuaCoDiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCapNhatHocVien)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoDaCoDiem)
                    .addComponent(rdoChuaCoDiem)
                    .addComponent(btnCapNhatHocVien)
                    .addComponent(rdoTatCa))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemHocVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHocVienActionPerformed
        // TODO add your handling code here:
        insert();

    }//GEN-LAST:event_btnThemHocVienActionPerformed

    private void tblHocVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHocVienMouseClicked

    }//GEN-LAST:event_tblHocVienMouseClicked

    private void rdoTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTatCaActionPerformed
        // TODO add your handling code here:
        fillGridView();
    }//GEN-LAST:event_rdoTatCaActionPerformed

    private void rdoDaCoDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDaCoDiemActionPerformed
        // TODO add your handling code here:
        fillGridView();
    }//GEN-LAST:event_rdoDaCoDiemActionPerformed

    private void rdoChuaCoDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoChuaCoDiemActionPerformed
        // TODO add your handling code here:
        fillGridView();
    }//GEN-LAST:event_rdoChuaCoDiemActionPerformed

    private void btnCapNhatHocVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatHocVienActionPerformed
        // TODO add your handling code here:
        update();
        //   iss();
    }//GEN-LAST:event_btnCapNhatHocVienActionPerformed

    private void cboNguoiHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNguoiHocActionPerformed
        // TODO add your handling code here:
//        ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
//        if (chuyenDe == null) {
//            return;
//        }
//        List<KhoaHoc> khoaHoc = khdao.select();
//        for (KhoaHoc khoaHoc1 : khoaHoc) {
//            HocVien hv = hvdao.findByMAKH(khoaHoc1.getMaKH());
//            if (hv == null) {
//                continue;
//            }
//
//        }


    }//GEN-LAST:event_cboNguoiHocActionPerformed

    private void txtDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiemActionPerformed

    private void cboChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChuyenDeActionPerformed
        // TODO add your handling code here:
        fillComboBox3();
        fillComboBox();
        fillGridView();
        //   fillComboBoxKhoaHoc();
    }//GEN-LAST:event_cboChuyenDeActionPerformed

    private void cboNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNamActionPerformed
        // TODO add your handling code here:
        fillGridView();
        fillComboBox();
    }//GEN-LAST:event_cboNamActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatHocVien;
    private javax.swing.JButton btnThemHocVien;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboChuyenDe;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JComboBox<String> cboNguoiHoc;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JRadioButton rdoChuaCoDiem;
    private javax.swing.JRadioButton rdoDaCoDiem;
    private javax.swing.JRadioButton rdoTatCa;
    private javax.swing.JTable tblHocVien;
    private javax.swing.JTextField txtDiem;
    // End of variables declaration//GEN-END:variables
}
