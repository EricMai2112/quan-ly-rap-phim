package CONTROL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ConnectDB.ConnectDB;
import MODEL.KhachHang;

public class KhachHang_DAO1 {
	
	private final ConnectDB connectDB = new ConnectDB();

    // Kiểm tra xem khách hàng đã tồn tại chưa dựa trên số điện thoại
    public KhachHang getKhachHangBySDT(String soDienThoai) {
        String sql = "SELECT * FROM KhachHang WHERE soDienThoai = ?";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, soDienThoai);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new KhachHang(
                    rs.getString("maKhachHang"),
                    rs.getString("tenKhachHang"),
                    rs.getString("soDienThoai"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm khách hàng mới và trả về mã khách hàng vừa tạo
    public String themKhachHang(String tenKhachHang, String soDienThoai, String email) {
        // Tạo mã khách hàng mới (KHXXX)
        String maKhachHang = taoMaKhachHangMoi();
        String sql = "INSERT INTO KhachHang (maKhachHang, tenKhachHang, soDienThoai, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maKhachHang);
            stmt.setString(2, tenKhachHang);
            stmt.setString(3, soDienThoai);
            stmt.setString(4, email != null ? email : "");
            stmt.executeUpdate();
            return maKhachHang;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Tạo mã khách hàng mới (KHXXX)
    private String taoMaKhachHangMoi() {
        String sql = "SELECT MAX(maKhachHang) AS maxId FROM KhachHang";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String maxId = rs.getString("maxId");
                if (maxId != null) {
                    int number = Integer.parseInt(maxId.replace("KH", "")) + 1;
                    return String.format("KH%03d", number);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "KH001"; // Nếu không có khách hàng nào, bắt đầu từ KH001
    }

}
