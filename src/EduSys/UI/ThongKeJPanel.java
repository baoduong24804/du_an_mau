/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package EduSys.UI;

import EduSys.DAO.KhoaHocDAO;
import EduSys.DAO.NguoiHocDAO;
import EduSys.DAO.ThongKeDAO;
import EduSys.Entity.KhoaHoc;
import EduSys.Utils.Auth;
import EduSys.Utils.ExcelUtil;
import EduSys.Utils.XDate;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author anhba
 */
public class ThongKeJPanel extends javax.swing.JPanel {

    /**
     * Creates new form ThongKeJPanel
     */
    public ThongKeJPanel() {
        initComponents();

        this.fillComboBoxKhoaHoc();
        this.fillComboBoxNam();
        this.fillTableBangDiem();
        this.fillTableDoanhThu();
        this.fillTongHopDiem();
        this.fillTableNguoiHoc();
        selectTab(tab);
        chinhDoDaiCot();
        checkNhanVien();
    }

    ThongKeDAO tkdao = new ThongKeDAO();
    KhoaHocDAO khdao = new KhoaHocDAO();
    NguoiHocDAO nhdao = new NguoiHocDAO();
    public static int tab = 0;

    public void selectTab(int index) {
        jTabbedPane3.setSelectedIndex(index);
    }
    
    void checkNhanVien(){
    if(Auth.isLogin() && !Auth.isManager()){
    jTabbedPane3.remove(3);
    
    }
    }

    void chinhDoDaiCot() {
        TableColumnModel columnModel = tblDoanhThuTK.getColumnModel();
        TableColumn column = columnModel.getColumn(0);
        column.setPreferredWidth(150);

    }

    void fillComboBoxKhoaHoc() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboKhoaHoc.getModel();
        model.removeAllElements();
        List<KhoaHoc> list = khdao.select();
        for (KhoaHoc kh : list) {
            model.addElement(kh);
        }
        if (cboKhoaHoc.getItemCount() <= 0) {
            return;
        }
        cboKhoaHoc.setSelectedIndex(0);
    }

    void fillComboBoxNam() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNam.getModel();
        model.removeAllElements();
        List<KhoaHoc> list = khdao.select();
        for (KhoaHoc kh : list) {
            int nam = kh.getNgayKG().getYear() + 1900;
            if (model.getIndexOf(nam) < 0) {
                model.addElement(nam);
            }
        }
        if (cboNam.getItemCount() <= 0) {
            return;
        }
        cboNam.setSelectedIndex(0);
    }

    void fillTableBangDiem() {
        DefaultTableModel model = (DefaultTableModel) tblKhoaHocTK.getModel();
        model.setRowCount(0);
        KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
        if (kh == null) {
            return;
        }
        List<Object[]> list = tkdao.getBangDiem(kh.getMaKH());
        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    void fillTableNguoiHoc() {

        DefaultTableModel model = (DefaultTableModel) tblNguoiHocTK.getModel();
        model.setRowCount(0);
        List<Object[]> list = tkdao.getNguoiHoc();

        for (Object[] row : list) {

            model.addRow(row);

        }


    }

    void fillTongHopDiem() {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        DefaultTableModel model = (DefaultTableModel) tblBangDiemTK.getModel();
        model.setRowCount(0);
        List<Object[]> list = tkdao.getDiemTheoChuyenDe();
        if (list.size() <= 0) {
            return;
        }
        for (Object[] row : list) {

            model.addRow(row);

        }

        for (int i = 0; i < list.size(); i++) {
            Double diem = Double.valueOf(tblBangDiemTK.getValueAt(i, 4).toString());
            String format = decimalFormat.format(diem);
            tblBangDiemTK.setValueAt(Double.valueOf(format), i, 4);
            if (Double.parseDouble(tblBangDiemTK.getValueAt(i, 2).toString()) < 0) {
                tblBangDiemTK.setValueAt(0, i, 4);
            }

        }

    }

    void fillTableDoanhThu() {
        DefaultTableModel model = (DefaultTableModel) tblDoanhThuTK.getModel();
        model.setRowCount(0);
        if (cboNam.getItemCount() <= 0) {
            return;
        }
        int nam = Integer.parseInt(cboNam.getSelectedItem().toString());

        List<Object[]> list = tkdao.getDoanhThu(nam);
        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tblNguoiHocTK = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblBangDiemTK = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tblKhoaHocTK = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        cboKhoaHoc = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        tblDoanhThuTK = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        cboNam = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();

        jTabbedPane3.setFont(jTabbedPane3.getFont().deriveFont(jTabbedPane3.getFont().getSize()+2f));

        tblNguoiHocTK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Năm", "Số người học", "Đầu tiên", "Sau cùng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguoiHocTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguoiHocTKMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(tblNguoiHocTK);

        jButton2.setText("Xuất Excel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Người học", jPanel6);

        tblBangDiemTK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Chuyên đề", "Tổng số HV", "Thấp nhất", "Cao nhất", "Điểm TB"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBangDiemTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangDiemTKMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tblBangDiemTK);

        jButton3.setText("Xuất Excel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Tổng hợp điểm", jPanel3);

        tblKhoaHocTK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã KH", "Mã NH", "Họ và tên", "Điểm", "Xếp loại"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhoaHocTK.setToolTipText("");
        tblKhoaHocTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhoaHocTKMouseClicked(evt);
            }
        });
        jScrollPane16.setViewportView(tblKhoaHocTK);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Khóa học:");

        cboKhoaHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKhoaHocActionPerformed(evt);
            }
        });

        jButton4.setText("Xuất Excel");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton4))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Bảng điểm", jPanel7);

        tblDoanhThuTK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Chuyên đề", "Số khóa", "Số HV", "Doanh thu", "HP cao nhất", "HP thấp nhất", "HP trung bình"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDoanhThuTK.setToolTipText("");
        tblDoanhThuTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDoanhThuTKMouseClicked(evt);
            }
        });
        jScrollPane17.setViewportView(tblDoanhThuTK);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Khóa học:");

        cboNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNamActionPerformed(evt);
            }
        });

        jButton5.setText("Xuất Excel");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton5))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Doanh thu", jPanel8);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKhoaHocActionPerformed
        // TODO add your handling code here:
        this.fillTableBangDiem();
    }//GEN-LAST:event_cboKhoaHocActionPerformed

    private void tblKhoaHocTKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhoaHocTKMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblKhoaHocTKMouseClicked

    private void tblBangDiemTKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangDiemTKMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblBangDiemTKMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //   System.out.println(tblBangDiem.getValueAt(0, 1));
        try {
            Workbook workBook = ExcelUtil.printNguoiHocToExcel(tblNguoiHocTK, tkdao);
            ExcelUtil.chooseDirectoryToSave(workBook);
            Logger.getLogger(ThongKeJPanel.class.getName()).log(Level.INFO,
                    "Export NguoiHocTK to Excel file successful!");
        } catch (IOException ex) {
            Logger.getLogger(ThongKeJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tblNguoiHocTKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiHocTKMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblNguoiHocTKMouseClicked

    private void tblDoanhThuTKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDoanhThuTKMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDoanhThuTKMouseClicked

    private void cboNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNamActionPerformed
        // TODO add your handling code here:
        this.fillTableDoanhThu();
    }//GEN-LAST:event_cboNamActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {
            Workbook workBook = ExcelUtil.printTongHopDiemToExcel(tblBangDiemTK, tkdao);
            ExcelUtil.chooseDirectoryToSave(workBook);
            Logger.getLogger(ThongKeJPanel.class.getName()).log(Level.INFO,
                    "Export TongHopDiem to Excel file successful!");
        } catch (IOException ex) {
            Logger.getLogger(ThongKeJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        try {
            Workbook workBook = ExcelUtil.printBangDiemKhoaHocToExcel(tblKhoaHocTK, cboKhoaHoc, tkdao);
            ExcelUtil.chooseDirectoryToSave(workBook);
            Logger.getLogger(ThongKeJPanel.class.getName()).log(Level.INFO,
                    "Export BangDiem to Excel file successful!");
        } catch (IOException ex) {
            Logger.getLogger(ThongKeJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        try {
            Workbook workBook = ExcelUtil.printDoanhThuToExcel(tblDoanhThuTK, cboNam, tkdao);
            ExcelUtil.chooseDirectoryToSave(workBook);
            Logger.getLogger(ThongKeJPanel.class.getName()).log(Level.INFO,
                    "Export DoanhThu to Excel file successful!");
        } catch (IOException ex) {
            Logger.getLogger(ThongKeJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboKhoaHoc;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable tblBangDiemTK;
    private javax.swing.JTable tblDoanhThuTK;
    private javax.swing.JTable tblKhoaHocTK;
    private javax.swing.JTable tblNguoiHocTK;
    // End of variables declaration//GEN-END:variables
}
