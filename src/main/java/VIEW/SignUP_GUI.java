package VIEW;

import CONTROL.NhanVien_DAO;
import CONTROL.TaiKhoan_DAO;
import MODEL.NhanVien;
import MODEL.VaiTro;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.util.Date;

/**
 * Giao diện đăng ký tài khoản và đồng thời tạo nhân viên mới.
 */
public class SignUP_GUI extends JFrame {
    // Các field nhập thông tin nhân viên
    private JTextField txtHoTen;
    private JDateChooser dcNgaySinh;
    private JTextField txtSDT;
    private JTextField txtCCCD;
    private JComboBox<VaiTro> cboVaiTro;
    // Các field tài khoản
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirm;
    private JTextField txtEmail;
    // Nút
    private JButton btnSignUp, btnCancel;

    public SignUP_GUI() {
        initComponents();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnSignUp);
    }

    private void initComponents() {
        setTitle("Đăng ký tài khoản và tạo nhân viên");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(450, 600);
        setResizable(false);
        getContentPane().setLayout(null);

        // Tiêu đề
        JLabel lblTitle = new JLabel("ĐĂNG KÝ TÀI KHOẢN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setBounds(75, 10, 300, 30);
        getContentPane().add(lblTitle);

        int y = 60, h = 25, gap = 40;

        // Họ tên
        getContentPane().add(new JLabel("Họ tên:")).setBounds(30, y, 100, h);
        txtHoTen = new JTextField();
        txtHoTen.setBounds(150, y, 250, h);
        getContentPane().add(txtHoTen);

        // Ngày sinh
        y += gap;
        getContentPane().add(new JLabel("Ngày sinh:")).setBounds(30, y, 100, h);
        dcNgaySinh = new JDateChooser(new Date(), "dd/MM/yyyy");
        dcNgaySinh.setBounds(150, y, 250, h);
        getContentPane().add(dcNgaySinh);

        // Số điện thoại
        y += gap;
        getContentPane().add(new JLabel("Số điện thoại:")).setBounds(30, y, 100, h);
        txtSDT = new JTextField();
        txtSDT.setBounds(150, y, 250, h);
        getContentPane().add(txtSDT);

        // CCCD
        y += gap;
        getContentPane().add(new JLabel("CCCD:")).setBounds(30, y, 100, h);
        txtCCCD = new JTextField();
        txtCCCD.setBounds(150, y, 250, h);
        getContentPane().add(txtCCCD);

        // Vai trò
        y += gap;
        getContentPane().add(new JLabel("Vai trò:")).setBounds(30, y, 100, h);
        cboVaiTro = new JComboBox<>(VaiTro.values());
        cboVaiTro.setBounds(150, y, 250, h);
        getContentPane().add(cboVaiTro);

        // Tên đăng nhập
        y += gap;
        getContentPane().add(new JLabel("Tên đăng nhập:")).setBounds(30, y, 100, h);
        txtUsername = new JTextField();
        txtUsername.setBounds(150, y, 250, h);
        getContentPane().add(txtUsername);

        // Mật khẩu
        y += gap;
        getContentPane().add(new JLabel("Mật khẩu:")).setBounds(30, y, 100, h);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, y, 250, h);
        getContentPane().add(txtPassword);

        // Nhập lại mật khẩu
        y += gap;
        getContentPane().add(new JLabel("Nhập lại MK:")).setBounds(30, y, 100, h);
        txtConfirm = new JPasswordField();
        txtConfirm.setBounds(150, y, 250, h);
        getContentPane().add(txtConfirm);

        // Email
        y += gap;
        getContentPane().add(new JLabel("Email:")).setBounds(30, y, 100, h);
        txtEmail = new JTextField();
        txtEmail.setBounds(150, y, 250, h);
        getContentPane().add(txtEmail);

        // Nút Đăng ký
        y += gap;
        btnSignUp = new JButton("Đăng ký");
        btnSignUp.setBounds(100, y, 100, 30);
        btnSignUp.addActionListener(e -> onSignUp());
        getContentPane().add(btnSignUp);

        // Nút Hủy
        btnCancel = new JButton("Hủy");
        btnCancel.setBounds(240, y, 100, 30);
        btnCancel.addActionListener(e -> dispose());
        getContentPane().add(btnCancel);
    }

    private void onSignUp() {
        // ... lấy dữ liệu từ các field
        String hoTen   = txtHoTen.getText().trim();
        Date ngaySinh  = dcNgaySinh.getDate();
        String sdt     = txtSDT.getText().trim();
        String cccd    = txtCCCD.getText().trim();
        VaiTro vaiTro  = (VaiTro) cboVaiTro.getSelectedItem();

        // 1) Tạo nhân viên mới
        NhanVien_DAO nvDao = new NhanVien_DAO();
        String newMaNV = nvDao.generateMaNhanVien();
        NhanVien nv = new NhanVien(newMaNV, hoTen, ngaySinh, sdt, cccd, vaiTro);
        if (!nvDao.themNhanVien(nv)) {
            JOptionPane.showMessageDialog(this, "Tạo nhân viên thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2) Kiểm tra username chưa tồn tại
        String username = txtUsername.getText().trim();
        String pass1    = new String(txtPassword.getPassword()).trim();
        String pass2    = new String(txtConfirm.getPassword()).trim();
        String email    = txtEmail.getText().trim();
        TaiKhoan_DAO tkDao = new TaiKhoan_DAO();
        if (tkDao.isUsernameExist(username)) {
            JOptionPane.showMessageDialog(this,
                "Tên đăng nhập đã tồn tại",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3) Đăng ký tài khoản với mã nhân viên vừa tạo
        boolean ok = tkDao.dangKyTaiKhoan(newMaNV, username, pass1, email);
        if (ok) {
            JOptionPane.showMessageDialog(this,
                "Đăng ký & tạo nhân viên thành công!",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose(); 
        } else {
            JOptionPane.showMessageDialog(this,
                "Đăng ký thất bại. Kiểm tra lại dữ liệu.",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
