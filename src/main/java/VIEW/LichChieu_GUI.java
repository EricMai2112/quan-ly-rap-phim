/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package VIEW;

import CONTROL.LichChieu_DAO;
import CONTROL.PhongChieu_DAO;
import CONTROL.Phim_DAO;
import MODEL.LichChieu;
import MODEL.PhongChieu;
import MODEL.Phim;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.DefaultListCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LichChieu_GUI extends JPanel {
    private DefaultTableModel originalModel;
    private final LichChieu_DAO lcDAO = new LichChieu_DAO();
    private Timer timer;

    public LichChieu_GUI() {
        initComponents();
        updateHeader();
        setDefaultDate();
        setWidthColumns();
        loadData();
        addEventHandlers();
    }

    /** Load dữ liệu từ DAO vào bảng */
    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) tbDanhSachDatPhong.getModel();
        model.setRowCount(0);
        List<LichChieu> list = LichChieu_DAO.getAllLichChieu();
        SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm");
        for (LichChieu lc : list) {
            model.addRow(new Object[]{
                lc.getMaLichChieu(),
                lc.getPhongChieu().getTenPhong(),
                lc.getPhim().getTenPhim(),
                dfDate.format(lc.getNgayChieu()),
                dfTime.format(lc.getGioBatDau()),
                dfTime.format(lc.getGioKetThuc())
            });
        }
        originalModel = model;
    }

    /** Gắn sự kiện cho các nút Thêm, Cập nhật, Xóa và Tìm kiếm */
    private void addEventHandlers() {
        // --- Thêm lịch chiếu mới ---
        btnThem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

                panel.add(new JLabel("Chọn phòng:"));
                JComboBox<PhongChieu> cbPhong = new JComboBox<>();
                for (PhongChieu pc : PhongChieu_DAO.getAllPhongChieu()) {
                    cbPhong.addItem(pc);
                }
                cbPhong.setRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList<?> list,
                                                                  Object value, int index, boolean isSelected, boolean cellHasFocus) {
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        if (value instanceof PhongChieu) {
                            setText(((PhongChieu) value).getTenPhong());
                        }
                        return this;
                    }
                });
                panel.add(cbPhong);

                panel.add(new JLabel("Chọn phim:"));
                JComboBox<Phim> cbPhim = new JComboBox<>();
                for (Phim p : new Phim_DAO().getAllPhim()) {
                    cbPhim.addItem(p);
                }
                cbPhim.setRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList<?> list,
                                                                  Object value, int index, boolean isSelected, boolean cellHasFocus) {
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        if (value instanceof Phim) {
                            setText(((Phim) value).getTenPhim());
                        }
                        return this;
                    }
                });
                panel.add(cbPhim);

                panel.add(new JLabel("Ngày chiếu:"));
                JDateChooser dcNgay = new JDateChooser(new Date(), "dd/MM/yyyy");
                panel.add(dcNgay);

                panel.add(new JLabel("Giờ BD:"));
                JSpinner spnGioBD = new JSpinner(new SpinnerDateModel());
                spnGioBD.setEditor(new JSpinner.DateEditor(spnGioBD, "HH:mm"));
                panel.add(spnGioBD);

                panel.add(new JLabel("Giờ KT:"));
                JSpinner spnGioKT = new JSpinner(new SpinnerDateModel());
                spnGioKT.setEditor(new JSpinner.DateEditor(spnGioKT, "HH:mm"));
                panel.add(spnGioKT);

                int result = JOptionPane.showConfirmDialog(
                    LichChieu_GUI.this, panel,
                    "Nhập lịch chiếu mới",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
                );
                if (result == JOptionPane.OK_OPTION) {
                    PhongChieu selPhong = (PhongChieu) cbPhong.getSelectedItem();
                    Phim selPhim       = (Phim) cbPhim.getSelectedItem();
                    java.sql.Date ngay  = new java.sql.Date(dcNgay.getDate().getTime());
                    Date bd             = (Date) spnGioBD.getValue();
                    java.sql.Time gioBD = new java.sql.Time(bd.getTime());
                    Date kt             = (Date) spnGioKT.getValue();
                    java.sql.Time gioKT = new java.sql.Time(kt.getTime());

                    LichChieu lc = new LichChieu(
                        null,
                        selPhong,
                        selPhim,
                        new Date(ngay.getTime()),
                        new Date(gioBD.getTime()),
                        new Date(gioKT.getTime())
                    );
                    boolean ok = LichChieu_DAO.addLichChieu(lc);
                    if (ok) {
                        JOptionPane.showMessageDialog(
                            LichChieu_GUI.this,
                            "Thêm thành công",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(
                            LichChieu_GUI.this,
                            "Thêm thất bại",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });

        // --- Cập nhật lịch chiếu ---
        btnCapNhat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tbDanhSachDatPhong.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(
                        LichChieu_GUI.this,
                        "Vui lòng chọn dòng để cập nhật",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                String maLC = tbDanhSachDatPhong.getValueAt(row, 0).toString();
                LichChieu existing = LichChieu_DAO.getLichChieuById(maLC);

                JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
                panel.add(new JLabel("Chọn phòng:"));
                JComboBox<PhongChieu> cbPhong = new JComboBox<>();
                for (PhongChieu pc : PhongChieu_DAO.getAllPhongChieu()) {
                    cbPhong.addItem(pc);
                }
                cbPhong.setRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList<?> list,
                                                                  Object value, int index, boolean isSelected, boolean cellHasFocus) {
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        if (value instanceof PhongChieu) {
                            setText(((PhongChieu) value).getTenPhong());
                        }
                        return this;
                    }
                });
                cbPhong.setSelectedItem(existing.getPhongChieu());
                panel.add(cbPhong);

                panel.add(new JLabel("Chọn phim:"));
                JComboBox<Phim> cbPhim = new JComboBox<>();
                for (Phim p : new Phim_DAO().getAllPhim()) {
                    cbPhim.addItem(p);
                }
                cbPhim.setRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList<?> list,
                                                                  Object value, int index, boolean isSelected, boolean cellHasFocus) {
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        if (value instanceof Phim) {
                            setText(((Phim) value).getTenPhim());
                        }
                        return this;
                    }
                });
                cbPhim.setSelectedItem(existing.getPhim());
                panel.add(cbPhim);

                panel.add(new JLabel("Ngày chiếu:"));
                JDateChooser dcNgay = new JDateChooser(existing.getNgayChieu(), "dd/MM/yyyy");
                panel.add(dcNgay);

                panel.add(new JLabel("Giờ BD:"));
                SpinnerDateModel mBD = new SpinnerDateModel(existing.getGioBatDau(), null, null, Calendar.MINUTE);
                JSpinner spnBD = new JSpinner(mBD);
                spnBD.setEditor(new JSpinner.DateEditor(spnBD, "HH:mm"));
                panel.add(spnBD);

                panel.add(new JLabel("Giờ KT:"));
                SpinnerDateModel mKT = new SpinnerDateModel(existing.getGioKetThuc(), null, null, Calendar.MINUTE);
                JSpinner spnKT = new JSpinner(mKT);
                spnKT.setEditor(new JSpinner.DateEditor(spnKT, "HH:mm"));
                panel.add(spnKT);

                int result = JOptionPane.showConfirmDialog(
                    LichChieu_GUI.this, panel,
                    "Cập nhật lịch chiếu",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
                );
                if (result == JOptionPane.OK_OPTION) {
                    PhongChieu selPhong = (PhongChieu) cbPhong.getSelectedItem();
                    Phim selPhim       = (Phim) cbPhim.getSelectedItem();
                    java.sql.Date ngay  = new java.sql.Date(dcNgay.getDate().getTime());
                    Date bd             = (Date) spnBD.getValue();
                    java.sql.Time gioBD = new java.sql.Time(bd.getTime());
                    Date kt             = (Date) spnKT.getValue();
                    java.sql.Time gioKT = new java.sql.Time(kt.getTime());

                    existing.setPhongChieu(selPhong);
                    existing.setPhim(selPhim);
                    existing.setNgayChieu(new Date(ngay.getTime()));
                    existing.setGioBatDau(new Date(gioBD.getTime()));
                    existing.setGioKetThuc(new Date(gioKT.getTime()));

                    boolean ok = LichChieu_DAO.updateLichChieu(existing);
                    if (ok) {
                        JOptionPane.showMessageDialog(
                            LichChieu_GUI.this,
                            "Cập nhật thành công",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(
                            LichChieu_GUI.this,
                            "Cập nhật thất bại",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });

        // --- Xóa lịch chiếu ---
        btnHuy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tbDanhSachDatPhong.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(
                        LichChieu_GUI.this,
                        "Vui lòng chọn dòng để xóa",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                String maLC = tbDanhSachDatPhong.getValueAt(row, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(
                    LichChieu_GUI.this,
                    "Bạn có chắc muốn xóa " + maLC + "?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean ok = LichChieu_DAO.deleteLichChieu(maLC);
                    if (ok) {
                        JOptionPane.showMessageDialog(
                            LichChieu_GUI.this,
                            "Xóa thành công",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(
                            LichChieu_GUI.this,
                            "Xóa thất bại",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });

     // --- Tìm kiếm theo từ khóa, khoảng ngày và trạng thái ---
        btnTimKiem.addActionListener(e -> {
            String raw = txtTimKiem.getText().trim();
            String keyword = raw.equals("Nhập tên phim") ? "" : raw.toLowerCase();
            String status  = (String) cboTrangThai.getSelectedItem();
            Date from      = txtTuNgayChieu.getDate();
            Date to        = txtDenNgayChieu.getDate();
            Date now       = new Date();

            if (keyword.isEmpty() && "Tất cả".equals(status)) {
                loadData();
                return;
            }

            DefaultTableModel filtered = new DefaultTableModel(
                new String[]{ "Mã LC","Phòng","Phim","Ngày chiếu","Giờ BD","Giờ KT" }, 0
            );
            SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm");

            for (LichChieu lc : lcDAO.getAllLichChieu()) {
                String phim  = lc.getPhim().getTenPhim().toLowerCase();
                String phong = lc.getPhongChieu().getTenPhong().toLowerCase();
                boolean matchText = phim.contains(keyword) || phong.contains(keyword);
                Date showDate = lc.getNgayChieu();
                boolean inRange = !showDate.before(from) && !showDate.after(to);
                boolean matchStatus;
                if ("Chưa nhận".equals(status)) {
                    matchStatus = showDate.after(now);
                } else if ("Đã nhận".equals(status)) {
                    matchStatus = !showDate.after(now);
                } else {
                    matchStatus = true;
                }
                if (matchText && inRange && matchStatus) {
                    filtered.addRow(new Object[]{
                        lc.getMaLichChieu(),
                        lc.getPhongChieu().getTenPhong(),
                        lc.getPhim().getTenPhim(),
                        dfDate.format(showDate),
                        dfTime.format(lc.getGioBatDau()),
                        dfTime.format(lc.getGioKetThuc())
                    });
                }
            }

            tbDanhSachDatPhong.setModel(filtered);
            setWidthColumns();
            if (filtered.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy lịch chiếu phù hợp!");
            }
        });
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        titleLichChieu = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDanhSachDatPhong = new javax.swing.JTable();
        btnHuy = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnCapNhat = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnThem = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTuNgayChieu = new com.toedter.calendar.JDateChooser();
        txtDenNgayChieu = new com.toedter.calendar.JDateChooser();
        cboTrangThai = new javax.swing.JComboBox<>();

        setPreferredSize(new java.awt.Dimension(855, 634));
        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 694));

        titleLichChieu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        titleLichChieu.setText("Lịch chiếu phim");

        tbDanhSachDatPhong.setModel(new javax.swing.table.DefaultTableModel(
        	    new Object[][] {},
        	    new String[] { "Mã LC", "Phòng", "Phim", "Ngày chiếu", "Giờ BD", "Giờ KT" }
        	) {
        	    Class<?>[] types = new Class<?>[] {
        	        String.class, String.class, String.class,
        	        String.class, String.class, String.class
        	    };
        	    public Class<?> getColumnClass(int columnIndex) {
        	        return types[columnIndex];
        	    }
        	    @Override
        	    public boolean isCellEditable(int row, int column) {
        	        return false;
        	    }
        	});

        tbDanhSachDatPhong.setRowHeight(40);
        jScrollPane1.setViewportView(tbDanhSachDatPhong);

        btnHuy.setBackground(new java.awt.Color(255, 0, 0));
        btnHuy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
 
        jLabel1.setText("Hủy");

        javax.swing.GroupLayout btnHuyLayout = new javax.swing.GroupLayout(btnHuy);
        btnHuy.setLayout(btnHuyLayout);
        btnHuyLayout.setHorizontalGroup(
            btnHuyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnHuyLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnHuyLayout.setVerticalGroup(
            btnHuyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnHuyLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnCapNhat.setBackground(new java.awt.Color(245, 109, 40));
        btnCapNhat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel2.setText("Cập nhật");

        javax.swing.GroupLayout btnCapNhatLayout = new javax.swing.GroupLayout(btnCapNhat);
        btnCapNhat.setLayout(btnCapNhatLayout);
        btnCapNhatLayout.setHorizontalGroup(
            btnCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
        );
        btnCapNhatLayout.setVerticalGroup(
            btnCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnCapNhatLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        btnThem.setBackground(new java.awt.Color(25, 159, 254));
        btnThem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));


        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel3.setText("Nhận Phòng");

        javax.swing.GroupLayout btnThemLayout = new javax.swing.GroupLayout(btnThem);
        btnThem.setLayout(btnThemLayout);
        btnThemLayout.setHorizontalGroup(
            btnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnThemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnThemLayout.setVerticalGroup(
            btnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnThemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtTimKiem.setForeground(new java.awt.Color(144, 144, 144));
        txtTimKiem.setText("Nhập tên phim");
        txtTimKiem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTimKiemFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTimKiemFocusLost(evt);
            }
        });
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/Search.png"))); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Đến");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Từ");




        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Chưa nhận", "Đã nhận" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLichChieu, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTuNgayChieu, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDenNgayChieu, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(78, 78, 78)
                .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(titleLichChieu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTimKiem)
                                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                                .addComponent(txtTuNgayChieu, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDenNgayChieu, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnCapNhat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnHuy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemActionPerformed
                                    

    private void txtTimKiemFocusGained(FocusEvent evt) {
        if ("Nhập tên phim".equals(txtTimKiem.getText())) {
            txtTimKiem.setText("");
            txtTimKiem.setForeground(Color.BLACK);
        }
    }

    private void txtTimKiemFocusLost(FocusEvent evt) {
        if (txtTimKiem.getText().trim().isEmpty()) {
            txtTimKiem.setForeground(Color.decode("#909090"));
            txtTimKiem.setText("Nhập tên phim");
        }
    }

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {
    }

    
    private void updateHeader() {
		JTableHeader header = tbDanhSachDatPhong.getTableHeader();
		header.setFont(new Font("Times new Romans", Font.BOLD, 16));
	}
    
    private void setWidthColumns() {
        tbDanhSachDatPhong.getColumnModel().getColumn(0).setMaxWidth(50); // Đặt chiều rộng tối thiểu cho cột STT
        tbDanhSachDatPhong.getColumnModel().getColumn(4).setMaxWidth(120);
	}
    
   
    
    // Phương thức lọc dữ liệu
    private void filterTableData() {
        String keyword = txtTimKiem.getText().trim().toLowerCase(); // Lấy từ khóa tìm kiếm

        // Kiểm tra nếu từ khóa rỗng, khôi phục dữ liệu ban đầu
        if (keyword.isEmpty()) {
            tbDanhSachDatPhong.setModel(originalModel); // Khôi phục model ban đầu
            return;
        }

        // Tạo model mới để chứa dữ liệu lọc
        DefaultTableModel filteredModel = new DefaultTableModel(
                new String[] { "Stt", "Mã Phiếu", "Tên khách hàng", "Số điện thoại", "Phòng", "Ngày nhận", "Ngày trả", "Tiền cọc", "Trạng thái" }, 
                0
        );

        boolean found = false; // Đánh dấu nếu tìm thấy dữ liệu

        // Duyệt qua từng hàng trong originalModel và lọc dữ liệu
        for (int i = 0; i < originalModel.getRowCount(); i++) {// Lấy tên khách hàng và mã phòng, kiểm tra null và loại bỏ khoảng trắng
            String tenKhachHang = originalModel.getValueAt(i, 2) != null 
                    ? originalModel.getValueAt(i, 2).toString().trim().toLowerCase() 
                    : "";
			String maPhong = originalModel.getValueAt(i, 3) != null 
			               ? originalModel.getValueAt(i, 3).toString().trim().toLowerCase() 
			               : "";
			

            // Kiểm tra nếu từ khóa xuất hiện trong tên khách hàng hoặc mã phòng
            if (tenKhachHang.contains(keyword) || maPhong.contains(keyword)) {
                filteredModel.addRow(new Object[]{
                    originalModel.getValueAt(i, 0),
                    originalModel.getValueAt(i, 1), 
                    originalModel.getValueAt(i, 2), 
                    originalModel.getValueAt(i, 3),
                    originalModel.getValueAt(i, 4),
                    originalModel.getValueAt(i, 5), 
                    originalModel.getValueAt(i, 6),
                    originalModel.getValueAt(i, 7), 
                    originalModel.getValueAt(i, 8),
                });
                found = true; // Đánh dấu là đã tìm thấy dữ liệu
            }
        }

        // Cập nhật JTable với model đã lọc hoặc hiển thị thông báo nếu không tìm thấy
        if (found) {
            tbDanhSachDatPhong.setModel(filteredModel);
            setWidthColumns();// Cập nhật model đã lọc
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu phù hợp!");
        }
    }
    
    private void setDefaultDate() {
    	Calendar calendar = Calendar.getInstance();

        // Đặt giờ về 0:00 để tránh sai lệch thời gian
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        txtTuNgayChieu.setDateFormatString("dd/MM/yyyy");
        txtDenNgayChieu.setDateFormatString("dd/MM/yyyy");

        // Ngày Check-in là ngày hiện tại
        Date checkInDate = calendar.getTime();
        txtTuNgayChieu.setDate(checkInDate);

        // Ngày Check-out là ngày hôm sau
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date checkOutDate = calendar.getTime();
        txtDenNgayChieu.setDate(checkOutDate);
    }
    

    
    public void startTimer() {
        if (!timer.isRunning()) {
            timer.start(); // Bắt đầu timer khi nó chưa chạy
            setWidthColumns();
        }
    }
    
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnCapNhat;
    private javax.swing.JPanel btnHuy;
    private javax.swing.JPanel btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbDanhSachDatPhong;
    private javax.swing.JLabel titleLichChieu;
    private com.toedter.calendar.JDateChooser txtDenNgayChieu;
    private javax.swing.JTextField txtTimKiem;
    private com.toedter.calendar.JDateChooser txtTuNgayChieu;
    // End of variables declaration//GEN-END:variables
}
