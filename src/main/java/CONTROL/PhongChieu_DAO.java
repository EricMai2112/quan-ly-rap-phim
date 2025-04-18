package CONTROL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import MODEL.PhongChieu;

public class PhongChieu_DAO {
    private final static ConnectDB connectDB = new ConnectDB();

    /**
     * Lấy tất cả phòng chiếu
     */
    public static List<PhongChieu> getAllPhongChieu() {
        List<PhongChieu> list = new ArrayList<>();
        String sql = "SELECT maPhong, tenPhong, soLuongNguoi FROM PhongChieu";

        try (Connection conn = connectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PhongChieu pc = new PhongChieu();
                pc.setMaPhong(rs.getString("maPhong"));
                pc.setTenPhong(rs.getString("tenPhong"));
                pc.setSoLuongNguoi(rs.getInt("soLuongNguoi"));
                list.add(pc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public PhongChieu getPhongByMaPhong(String maPhong) {
        PhongChieu phongChieu = null;
        
        // Kết nối đến cơ sở dữ liệu và thực hiện truy vấn
        String sql = "SELECT * FROM PhongChieu WHERE maPhong = ?";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPhong);  // Gán giá trị cho tham số trong câu lệnh SQL
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Nếu có dữ liệu, tạo đối tượng PhongChieu từ kết quả truy vấn
                phongChieu = new PhongChieu();
                phongChieu.setMaPhong(rs.getString("maPhong"));
                // Thiết lập các thuộc tính khác của PhongChieu (nếu cần)
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return phongChieu;
    }

    /**
     * Thêm mới một phòng chiếu
     */
    public static boolean addPhongChieu(PhongChieu pc) {
        String sql = "INSERT INTO PhongChieu (maPhong, tenPhong, soLuongNguoi) VALUES (?, ?, ?)";

        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pc.getMaPhong());
            stmt.setString(2, pc.getTenPhong());
            stmt.setInt(3, pc.getSoLuongNguoi());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Cập nhật thông tin phòng chiếu (không đổi mã)
     */
    public static boolean updatePhongChieu(PhongChieu pc) {
        String sql = "UPDATE PhongChieu SET tenPhong = ?, soLuongNguoi = ? WHERE maPhong = ?";

        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pc.getTenPhong());
            stmt.setInt(2, pc.getSoLuongNguoi());
            stmt.setString(3, pc.getMaPhong());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Xóa phòng chiếu theo mã
     */
    public static boolean deletePhongChieu(String maPhong) {
        String sql = "DELETE FROM PhongChieu WHERE maPhong = ?";

        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhong);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
