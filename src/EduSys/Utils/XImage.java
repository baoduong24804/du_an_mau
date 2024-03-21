/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSys.Utils;

import EduSys.Entity.NhanVien;
import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class XImage {

//    public static Image getAppIcon() {
//        String file = "/src/EduSys/Image/fpt.png";
//        return new ImageIcon(XImage.class.getResource(file)).getImage();
//    }
    public static ImageIcon imageIcon = new ImageIcon(XImage.class.getResource("/EduSys/Image/fpt.png"));

    public static Image getAppIcon() {
        return imageIcon.getImage();
    }

    public static File saveExel(File src) {
        File dst = new File("storeFiles", src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            return dst;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Sao chép file logo chuyên đề vào thư mục logo
     *
     * @param src là đối tượng file ảnh
     */
    public static boolean save(File src) {
        File dst = new File("logos", src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception ex) {
            return false;

        }

    }

    /**
     * Sao chép file excel danh sach nguoi học vào folder storeFiles
     *
     * @param src là đối tượng file excel
     */
    
    

    /**
     * Đọc hình ảnh logo chuyên đề
     *
     * @param fileName là tên file logo
     * @return ảnh đọc được
     */
    public static ImageIcon read(String fileName) {
        File path = new File("logos", fileName);
        return new ImageIcon(path.getAbsolutePath());
    }

    public static void setAnh(String file, JLabel lbl) {
        try {
            //   BufferedImage originalImage = ImageIO.read(new File(file.toString()));

            ImageIcon icon = new ImageIcon(file);

            Image image = icon.getImage();

            Image scaledImage = image.getScaledInstance(lbl.getWidth(), lbl.getHeight(), Image.SCALE_DEFAULT);

            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            lbl.setIcon(scaledIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
        public static void setAnh2(String file,JLabel lbl, int width, int height) {
        try {
            //   BufferedImage originalImage = ImageIO.read(new File(file.toString()));

            ImageIcon icon = new ImageIcon(file);

            Image image = icon.getImage();

            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);

            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            lbl.setIcon(scaledIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
