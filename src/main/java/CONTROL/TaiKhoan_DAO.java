package CONTROL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoan_DAO {
    private static final String URL = 
        "jdbc:sqlserver://localhost:1434;databaseName=QuanLyRapChieuPhim;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "123";

    /**
     * Kiểm tra đăng nhập
     */
    public boolean kiemTraDangNhap(String tenDangNhap, String matKhau) {
        String sql = "SELECT COUNT(*) FROM TaiKhoan WHERE tenDangNhap = ? AND matKhau = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, tenDangNhap);
            stmt.setString(2, matKhau);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Đăng ký tài khoản mới, có liên kết với mã nhân viên
     */
    public boolean dangKyTaiKhoan(String maNV, String tenDangNhap, String matKhau, String email) {
        String maTaiKhoan = generateMaTaiKhoan();
        String sql = "INSERT INTO TaiKhoan(maTaiKhoan, nhanVien, tenDangNhap, matKhau, email) " +
                     "VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maTaiKhoan);
            stmt.setString(2, maNV);           // gán đúng mã nhân viên
            stmt.setString(3, tenDangNhap);
            stmt.setString(4, matKhau);
            stmt.setString(5, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Kiểm tra xem username đã tồn tại chưa (dùng cho quên mật khẩu)
     */
    public boolean isUsernameExist(String tenDangNhap) {
        String sql = "SELECT COUNT(*) FROM TaiKhoan WHERE tenDangNhap = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, tenDangNhap);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Cập nhật mật khẩu mới dựa trên tenDangNhap (quên mật khẩu)
     */
    public boolean capNhatMatKhau(String tenDangNhap, String matKhauMoi) {
        String sql = "UPDATE TaiKhoan SET matKhau = ? WHERE tenDangNhap = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, matKhauMoi);
            stmt.setString(2, tenDangNhap);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sinh tự động mã tài khoản mới, theo định dạng "TK" + 3 chữ số (TK001, TK002,…)
     */
    public String generateMaTaiKhoan() {
        String prefix = "TK";
        String sql = "SELECT TOP 1 maTaiKhoan FROM TaiKhoan "
                   + "WHERE maTaiKhoan LIKE 'TK%' "
                   + "ORDER BY maTaiKhoan DESC";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                String lastId = rs.getString(1);                // ví dụ "TK012"
                int num = Integer.parseInt(lastId.substring(2)); // lấy 12
                return prefix + String.format("%03d", num + 1); // "TK013"
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Nếu chưa có bản ghi nào thì khởi tạo từ TK001
        return prefix + "001";
    }
    
    public boolean isNhanVienExist(String maNV) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE maNhanVien = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
