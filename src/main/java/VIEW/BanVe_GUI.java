/*
 * Click nbfs://nbhost/SystemFile/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package VIEW;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import CONTROL.Ghe_DAO;
import CONTROL.LichChieu2_DAO;
import CONTROL.Phim_DAO;
import MODEL.Ghe;
import MODEL.LichChieu;
import MODEL.LoaiGhe;
import MODEL.Phim;
import MODEL.TrangThaiGhe;

/**
 *
 * @author Admin
 */
public class BanVe_GUI extends javax.swing.JPanel {
	private JPanel panelMain;
	private static BanVe_GUI instance;

    JDateChooser txtNgayCheckIn = new com.toedter.calendar.JDateChooser();
    JDateChooser txtNgayCheckOut = new com.toedter.calendar.JDateChooser();
    
    /**
     * Creates new form Phong_GUI
     */
    public static BanVe_GUI getInstance() {
        if (instance == null) {
            instance = new BanVe_GUI();
        }
        return instance;
    }
    
    public BanVe_GUI() {
        
        initComponents();
        loadAndDisplayPhimCards();

    }
    

    private void initComponents() {
    	
    	JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout());
        JPanel headerPanel = new JPanel();

        
        headerPanel.setBackground(Color.white);        
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        
     // Thêm lọc ngày tháng năm
        Calendar calendar = Calendar.getInstance();
        JPanel panelLocNgay = new JPanel();
        JLabel lbLocTu = new JLabel("Từ");
        lbLocTu.setFont(new Font("Segoe UI", Font.BOLD, 16));
		txtNgayCheckIn.setDateFormatString("dd/MM/yyyy");
		txtNgayCheckOut.setDateFormatString("dd/MM/yyyy");

        // Ngày Check-in là ngày hiện tại
        java.util.Date checkInDate = calendar.getTime();
        txtNgayCheckIn.setDate(checkInDate);
        JLabel lbLocDen = new JLabel("Đến");
        lbLocDen.setFont(new Font("Segoe UI", Font.BOLD, 16));
        // Ngày Check-out là ngày hôm sau
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        java.util.Date checkOutDate = calendar.getTime();
        txtNgayCheckOut.setDate(checkOutDate);
        panelLocNgay.add(lbLocTu);
        panelLocNgay.add(txtNgayCheckIn);
        panelLocNgay.add(lbLocDen);
        panelLocNgay.add(txtNgayCheckOut);
        panelLocNgay.setBackground(Color.white);



        
        JLabel lblTitle = new JLabel("Danh sách phòng");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(panelLocNgay, BorderLayout.WEST);
        //Panel bên phải
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.white);
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
   

        // Thêm rightPanel vào bên phải của headerPanel
        headerPanel.add(rightPanel, BorderLayout.EAST);
        // Tạo panel chính chứa các card phòng
        panelMain = new JPanel(new GridLayout(0, 4, 10, 10)); // 4 cột, khoảng cách 10px
        panelMain.setBackground(Color.white);
        
        JScrollPane scrollPane = new JScrollPane(panelMain);

        
        mainContainer.add(headerPanel, BorderLayout.NORTH);
        mainContainer.add(scrollPane, BorderLayout.CENTER);
        
        // Cấu hình giao diện chính
        setLayout(new BorderLayout());
        add(mainContainer, BorderLayout.CENTER);

        setSize(800, 600);

    }
    

    public void loadAndDisplayPhimCards() {
        Phim_DAO phimDAO = new Phim_DAO();
        List<Phim> listPhim = phimDAO.getAllPhim();

        panelMain.removeAll();

        JPanel cardsPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        cardsPanel.setBackground(Color.white);

        for (Phim phim : listPhim) {
            JPanel card = createPhimCard(phim);
            cardsPanel.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(panelMain.getWidth(), panelMain.getHeight()));

        panelMain.setLayout(new BorderLayout());
        panelMain.add(scrollPane, BorderLayout.CENTER);

        panelMain.revalidate();
        panelMain.repaint();
    }


    private JPanel createPhimCard(Phim phim) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        card.setPreferredSize(new Dimension(200, 300));

        String hinhAnh = phim.getHinhAnh();
        JLabel imageLabel;
        
        try {
            if (hinhAnh != null && !hinhAnh.trim().isEmpty()) {
                imageLabel = new JLabel(new ImageIcon(new java.net.URL(hinhAnh)));
            } else {
                imageLabel = new JLabel(new ImageIcon("path/to/default/movie/image.png"));
            }
        } catch (Exception e) {
            imageLabel = new JLabel(new ImageIcon("path/to/default/movie/image.png"));
        }

        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        card.add(imageLabel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(phim.getTenPhim());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel genreLabel = new JLabel("Thể loại: " + phim.getTheLoai());
        genreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel durationLabel = new JLabel("Thời lượng: " + phim.getThoiLuong() + " phút");
        durationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JPanel pStatus = new JPanel();

        JLabel statusLabel = new JLabel("Trạng thái: " + phim.getTrangThaiPhim());
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        infoPanel.add(titleLabel);
        infoPanel.add(genreLabel);
        infoPanel.add(durationLabel);
        infoPanel.add(statusLabel);
        card.add(infoPanel, BorderLayout.SOUTH);
        
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hienDialogLichChieu(phim); // Mở dialog chọn lịch chiếu tương ứng phim
            }
        });


        return card;
    }
    
    private void hienDialogLichChieu(Phim phim) {
        List<LichChieu> lichChieuList = new LichChieu2_DAO().getLichChieuByPhim(phim.getMaPhim());
        if (lichChieuList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có lịch chiếu cho phim này.");
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chọn lịch chiếu", true);
        dialog.setSize(450, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel listPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        for (LichChieu lc : lichChieuList) {
            String ngay = new SimpleDateFormat("dd/MM/yyyy").format(lc.getNgayChieu());
            String gio = new SimpleDateFormat("HH:mm").format(lc.getGioBatDau());
            String phong = lc.getPhongChieu().getTenPhong();
            JButton btn = new JButton(ngay + " - " + gio + " tại " + phong);
            btn.addActionListener(e -> {
                dialog.dispose();
                hienDialogChonGhe(lc);
            });
            listPanel.add(btn);
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void hienDialogChonGhe(LichChieu lichChieu) {
        List<Ghe> danhSachGhe = new Ghe_DAO().getDanhSachGheTheoPhong(lichChieu.getPhongChieu().getMaPhong());

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chọn ghế", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel panelGhe = new JPanel(new GridLayout(5, 10, 5, 5));
        Map<JButton, Ghe> mapButtonToGhe = new HashMap<>();

        for (Ghe ghe : danhSachGhe) {
            JButton btnGhe = new JButton(ghe.getSoGhe());
            if (ghe.getTrangThaiGhe() == TrangThaiGhe.GHE_DAT) {
                btnGhe.setBackground(Color.RED);
                btnGhe.setEnabled(false);
            } else {
            	if(ghe.getLoaiGhe()== LoaiGhe.THUONG) {
                    btnGhe.setBackground(Color.GREEN);
            	}else {
            		btnGhe.setBackground(Color.ORANGE);
            	}
            }

            mapButtonToGhe.put(btnGhe, ghe);
            btnGhe.addActionListener(e -> {
                btnGhe.setBackground(Color.YELLOW); 
            });

            panelGhe.add(btnGhe);
        }

        dialog.add(new JScrollPane(panelGhe), BorderLayout.CENTER);
        dialog.setVisible(true);
    }



    
    
		
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel224;
    private javax.swing.JLabel jLabel225;
    private javax.swing.JLabel jLabel226;
    private javax.swing.JLabel jLabel227;
    private javax.swing.JLabel jLabel228;
    private javax.swing.JLabel jLabel229;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel230;
    private javax.swing.JLabel jLabel231;
    private javax.swing.JLabel jLabel232;
    private javax.swing.JLabel jLabel233;
    private javax.swing.JLabel jLabel234;
    private javax.swing.JLabel jLabel235;
    private javax.swing.JLabel jLabel236;
    private javax.swing.JLabel jLabel237;
    private javax.swing.JLabel jLabel238;
    private javax.swing.JLabel jLabel239;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel240;
    private javax.swing.JLabel jLabel241;
    private javax.swing.JLabel jLabel242;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JPanel panel101;
    private javax.swing.JPanel panel102;
    private javax.swing.JPanel panel103;
    private javax.swing.JPanel panel104;
    private javax.swing.JPanel panel105;
    private javax.swing.JPanel panel106;
    private javax.swing.JPanel panel107;
    private javax.swing.JPanel panel108;
    private javax.swing.JPanel panel201;
    private javax.swing.JPanel panel202;
    private javax.swing.JPanel panel203;
    private javax.swing.JPanel panel204;
    // End of variables declaration//GEN-END:variables
}
