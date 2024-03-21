/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package EduSys.UI;

import EduSys.DAO.ChuyenDeDAO;
import EduSys.Entity.ChuyenDe;
import EduSys.Utils.Auth;
import EduSys.Utils.KTValidate;
import EduSys.Utils.MsgBox;
import EduSys.Utils.QRCodeUtil;
import EduSys.Utils.XImage;
import com.google.zxing.WriterException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author anhba
 */
public class ChuyenDeJPanel extends javax.swing.JPanel {

    DefaultTableModel model = new DefaultTableModel(new Object[]{"STT", "Mã CD", "Tên CD", "Học phí", "Thời lượng", "Hình"}, 0);
    public static String qrCodeImage;
    public static String Chuyendelog;

    /**
     * Creates new form ChuyenDeJPanel
     */
    public ChuyenDeJPanel() {
        initComponents();
        tblChuyenDe.setModel(model);
        loadData();
        setStatus(true);
        chinhDoDaiCot();
    }
    int index = 0;
    ChuyenDeDAO daoChuyenDe = new ChuyenDeDAO();

    void chinhDoDaiCot() {
        TableColumnModel columnModel = tblChuyenDe.getColumnModel();
        TableColumn column = columnModel.getColumn(2);
        column.setPreferredWidth(200);

    }

    void loadData() {
        model.setRowCount(0);
        int stt = 0;
        try {
            for (ChuyenDe cd : daoChuyenDe.select()) {
                model.addRow(new Object[]{++stt, cd.getMaCD(), cd.getTenCD(), cd.getHocPhi(), cd.getThoiLuong(), cd.getHinh()});
            }
            lblHinh.setIcon(null);
        } catch (Exception e) {
            MsgBox.alertRed(null, "Lấy dữ liệu thất bại");
        }

    }

    void insert() {

        if (txtHocPhi.getText().trim().isEmpty() || txtMaChuyenDe.getText().trim().isEmpty() || txtTenChuyenDe.getText().trim().isEmpty() || txtThoiLuong.getText().trim().isEmpty()) {
            MsgBox.alertRed(null, "Vui lòng nhập đầy đủ thông tin");
            return;
        }
        if (!KTValidate.kiemTraKiTuAlphabet(txtTenChuyenDe.getText().trim())) {
            MsgBox.alertRed(null, "Tên chuyên đề chỉ được nhập chữ cái và khoảng trắng");
            return;
        }
        try {
            if (Double.parseDouble(txtThoiLuong.getText().trim()) < 0 || Double.parseDouble(txtHocPhi.getText().trim()) < 0) {
                MsgBox.alertRed(null, "Thời lượng và học phí phải là số\nlớn hơn hoặc bằng 0");
                return;
            }
        } catch (Exception e) {
            MsgBox.alertRed(null, "Thời lượng và học phí phải là số\nlớn hơn hoặc bằng 0");
            return;
        }

//        if (!KTValidate.kiemTraKiTuAlphabet(txtMoTa.getText().trim())) {
//            MsgBox.alertRed(null, "Mô tả được nhập chữ cái và khoảng trắng");
//            return;
//        }
        ChuyenDe model = getModel();
        try {
            if (model.getHinh() == null) {
                MsgBox.alertRed(this, "Bạn chưa chọn ảnh");
                return;
            }
            daoChuyenDe.insert(model);

            this.loadData();
            this.clear();
            //      MsgBox.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Thêm mới thất bại!");
        }
    }

    void update() {
        if (txtHocPhi.getText().trim().isEmpty() || txtMaChuyenDe.getText().trim().isEmpty() || txtTenChuyenDe.getText().trim().isEmpty() || txtThoiLuong.getText().trim().isEmpty()) {
            MsgBox.alertRed(null, "Vui lòng nhập đầy đủ thông tin");
            return;
        }
        if (!KTValidate.kiemTraKiTuAlphabet(txtTenChuyenDe.getText().trim())) {
            MsgBox.alertRed(null, "Tên chuyên đề chỉ được nhập chữ cái và khoảng trắng");
            return;
        }
        try {
            if (Double.parseDouble(txtThoiLuong.getText().trim()) < 0 || Double.parseDouble(txtHocPhi.getText().trim()) < 0) {
                MsgBox.alertRed(null, "Thời lượng và học phí phải là số\nlớn hơn hoặc bằng 0");
                return;
            }
        } catch (Exception e) {
            MsgBox.alertRed(null, "Thời lượng và học phí phải là số\nlớn hơn hoặc bằng 0");
            return;
        }

//        if (!KTValidate.kiemTraKiTuAlphabet(txtMoTa.getText().trim())) {
//            MsgBox.alertRed(null, "Mô tả được nhập chữ cái và khoảng trắng");
//            return;
//        }
        ChuyenDe model = getModel();
        try {
            daoChuyenDe.update(model);

            this.loadData();
            lblHinh.setIcon(null);
            this.clear();
            //    MsgBox.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Cập nhật thất bại!");
        }
    }

    void delete() {
        if (MsgBox.confirm(this, "Bạn có muốn xóa hay không?")) {
            String macd = txtMaChuyenDe.getText();
            try {
                daoChuyenDe.delete(macd);

                this.loadData();
                this.clear();
                //      MsgBox.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Có khóa học của chuyên đề này");
            }
        }
    }

    void clear() {
        ChuyenDe model = new ChuyenDe();
        model.setHocPhi(1.0);
        model.setThoiLuong(1);
        this.setModel(model);
        lblHinh.setIcon(null);
        this.setStatus(true);
    }

    void edit() {
        try {
            String macd = (String) tblChuyenDe.getValueAt(this.index, 1);
            ChuyenDe model = daoChuyenDe.findById(macd);
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
            }
        } catch (Exception e) {
            MsgBox.alertRed(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setModel(ChuyenDe model) {
        try {
            Double a = model.getHocPhi();
            int b = model.getThoiLuong();
        } catch (Exception e) {
            return;
        }

        txtMaChuyenDe.setText(model.getMaCD());
        txtTenChuyenDe.setText(model.getTenCD());
        txtThoiLuong.setText(String.valueOf(model.getThoiLuong()));
        txtHocPhi.setText(String.valueOf(model.getHocPhi()));
        txtMoTa.setText(model.getMoTa());
        lblHinh.setToolTipText(model.getHinh());

        if (model.getHinh() != null) {
            try {
                String imagePath = "/EduSys/Image/" + model.getHinh().trim();
                InputStream inputStream2 = null;
                InputStream inputStream = null;
                BufferedImage originalImage = null;
             //   System.out.println(inputStream);
              
                
                if (getClass().getResource(imagePath) != null) {
                    inputStream = getClass().getResource(imagePath).openStream();
                    originalImage = ImageIO.read(inputStream);
                  //  txtHinh.setText(nv.getHinh());
                } else {
                    lblHinh.removeAll();
                 //   txtHinh.setText("Lỗi ảnh");
                    String imagePath2 = "/EduSys/Image/User.png";
                    inputStream2 = MainJFrame.class.getResource(imagePath2).openStream();
                    
                    originalImage = ImageIO.read(inputStream2);
                }

                ImageIcon icon = new ImageIcon(originalImage);

                Image image = icon.getImage();

                Image scaledImage = image.getScaledInstance(177, 232, Image.SCALE_DEFAULT);

                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                lblHinh.setIcon(scaledIcon);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    ChuyenDe getModel() {
        ChuyenDe model = new ChuyenDe();
        model.setMaCD(txtMaChuyenDe.getText().trim());
        model.setTenCD(txtTenChuyenDe.getText().trim());
        model.setThoiLuong(Integer.valueOf(txtThoiLuong.getText().trim()));
        model.setHocPhi(Double.parseDouble(txtHocPhi.getText().trim()));
        model.setHinh(lblHinh.getToolTipText());
        model.setMoTa(txtMoTa.getText().trim());
        return model;
    }

    void setStatus(boolean insertable) {
        txtMaChuyenDe.setEditable(insertable);
        btnThem.setEnabled(insertable);
        btnSua.setEnabled(!insertable);

        btnXoa.setEnabled(!insertable);

        boolean first = this.index > 0;
        boolean last = this.index < tblChuyenDe.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPrev.setEnabled(!insertable && first);
        btnLast.setEnabled(!insertable && last);
        btnNext.setEnabled(!insertable && last);
        if (!Auth.isManager()) {
            btnXoa.setEnabled(false);
        }
    }

    public void selectImage() {
        JFileChooser fileChooser = new JFileChooser("src\\EduSys\\Image\\");
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (XImage.save(file)) {

                try {
                    BufferedImage originalImage = ImageIO.read(new File(file.toString()));

                    ImageIcon icon = new ImageIcon(originalImage);

                    Image image = icon.getImage();

                    Image scaledImage = image.getScaledInstance(177, 232, Image.SCALE_DEFAULT);

                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    lblHinh.setToolTipText(file.getName());
                    lblHinh.setIcon(scaledIcon);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void first() {
        this.index = 0;
        this.edit();

    }

    private void prev() {
        this.index--;
        this.edit();

    }

    private void next() {
        this.index++;
        this.edit();

    }

    private void last() {
        this.index = tblChuyenDe.getRowCount() - 1;
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblHinh = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaChuyenDe = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTenChuyenDe = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtThoiLuong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtHocPhi = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblChuyenDe = new javax.swing.JTable();

        jTabbedPane1.setFont(jTabbedPane1.getFont().deriveFont(jTabbedPane1.getFont().getSize()+2f));

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+2f));
        jLabel2.setText("Hình Logo");

        lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getSize()+2f));
        jLabel4.setText("Mã chuyên đề");

        jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getSize()+2f));
        jLabel5.setText("Tên chuyên đề");

        jLabel6.setFont(jLabel6.getFont().deriveFont(jLabel6.getFont().getSize()+2f));
        jLabel6.setText("Thời lượng ( giờ )");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Học phí");

        jLabel8.setFont(jLabel8.getFont().deriveFont(jLabel8.getFont().getSize()+2f));
        jLabel8.setText("Mô tả chuyên đề");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane1.setViewportView(txtMoTa);

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

        jButton1.setText("Mã QR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(139, 139, 139))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(28, 28, 28)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel4)
                                .addComponent(txtMaChuyenDe)
                                .addComponent(jLabel5)
                                .addComponent(txtTenChuyenDe)
                                .addComponent(jLabel6)
                                .addComponent(txtThoiLuong)
                                .addComponent(jLabel7)
                                .addComponent(txtHocPhi, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)))
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(btnThem)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                            .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(159, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMaChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTenChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quản lý", jPanel2);

        tblChuyenDe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã CD", "Tên CD", "Học phí", "Thời lượng", "Hình"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChuyenDe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChuyenDeMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tblChuyenDe);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(118, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh sách", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        // TODO add your handling code here:
        selectImage();
    }//GEN-LAST:event_lblHinhMouseClicked

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

    private void tblChuyenDeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChuyenDeMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() >= 1) {
            this.index = tblChuyenDe.rowAtPoint(evt.getPoint());
            if (this.index >= 0) {
                this.edit();
                // System.out.println("hi");
                jTabbedPane1.setSelectedIndex(0);
            }
        }

    }//GEN-LAST:event_tblChuyenDeMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {

            ChuyenDe chuyenDe = daoChuyenDe.findById(txtMaChuyenDe.getText().trim());
            if (chuyenDe == null) {
                return;
            }

            String qrCodeText = "Mã chuyên đề: " + chuyenDe.getMaCD() + " Tên chuyên đề: " + chuyenDe.getTenCD() + " Thời lượng: " + chuyenDe.getThoiLuong() + " Học phí: " + chuyenDe.getHocPhi();
            String filePath = chuyenDe.getMaCD() + ".jpg";
            File destination = new File("storeFiles", filePath);

            filePath = Paths.get(destination.getAbsolutePath()).toString();
            int size = 200;
            String fileType = "jpg";
            File qrFile = new File(filePath);
            QRCodeUtil.createQRImage(qrFile, qrCodeText, size, fileType);
            this.qrCodeImage = filePath;
            QRCodeJDialog.name = this.qrCodeImage;
            new QRCodeJDialog(null, true).setVisible(true);
        } catch (WriterException ex) {
        } catch (IOException ex) {
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JTable tblChuyenDe;
    private javax.swing.JTextField txtHocPhi;
    private javax.swing.JTextField txtMaChuyenDe;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtTenChuyenDe;
    private javax.swing.JTextField txtThoiLuong;
    // End of variables declaration//GEN-END:variables
}
