package CONTROL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import MODEL.Ghe;
import MODEL.GiaoDich;
import MODEL.KhachHang;
import MODEL.LichChieu;
import MODEL.NhanVien;
import MODEL.Ve;

public class Ve_DAO {
	private final ConnectDB connectDB = new ConnectDB();
    
	public List<Ve> getAllGiaoDich() {
        List<Ve> list = new ArrayList<>();
        
        // Truy vấn không có điều kiện lc.package = ?
        String sql = "SELECT v.maVe, v.lichChieu, v.ghe, v.giaVe, v.trangThaiVe, v.thoiGianBan, " +
                     "v.nhanVien, v.khachHang, " +
                     "lc.ngayChieu, lc.gioBatDau, lc.gioKetThuc, " +
                     "g.soGhe, " +
                     "nv.hoTen AS tenNhanVien, nv.soDienThoai AS sdtNhanVien, " +
                     "kh.tenKhachHang, kh.soDienThoai AS sdtKhachHang, kh.email " +
                     "FROM Ve v " +
                     "LEFT JOIN LichChieu lc ON v.lichChieu = lc.maLichChieu " +
                     "LEFT JOIN Ghe g ON v.ghe = g.maGhe " +
                     "LEFT JOIN NhanVien nv ON v.nhanVien = nv.maNhanVien " +
                     "LEFT JOIN KhachHang kh ON v.khachHang = kh.maKhachHang";
        
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                // Tạo đối tượng LichChieu
                LichChieu lichChieu = new LichChieu(
                    rs.getString("lichChieu"),
                    null, // PhongChieu
                    null, // Phim
                    rs.getDate("ngayChieu"),
                    rs.getTimestamp("gioBatDau"),
                    rs.getTimestamp("gioKetThuc")
                );
                
                // Tạo đối tượng Ghe
                Ghe ghe = new Ghe(
                    rs.getString("ghe"),
                    null, // PhongChieu
                    rs.getString("soGhe"),
                    null
                );
                
                // Tạo đối tượng NhanVien
                NhanVien nhanVien = new NhanVien(
                    rs.getString("nhanVien"),
                    rs.getString("tenNhanVien"),
                    null, // ngaySinh
                    rs.getString("sdtNhanVien"),
                    null, // cccd
                    null  // VaiTro
                );
                
                // Tạo đối tượng KhachHang
                KhachHang khachHang = new KhachHang(
                    rs.getString("khachHang"),
                    rs.getString("tenKhachHang"),
                    rs.getString("sdtKhachHang"),
                    rs.getString("email")
                );
                
                // Tạo đối tượng Ve
                Ve ve = new Ve(
                    rs.getString("maVe"),
                    lichChieu,
                    ghe,
                    rs.getDouble("giaVe"),
                    rs.getString("trangThaiVe"),
                    rs.getTimestamp("thoiGianBan"),
                    nhanVien,
                    khachHang
                );
                
                list.add(ve);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
	
	// Thêm vé mới
	public String themVe(String maLichChieu, String maGhe, double giaVe, String maNhanVien, String maKhachHang) {
        // Kiểm tra xem ghế đã được đặt cho lịch chiếu này chưa
        String checkSql = "SELECT COUNT(*) FROM Ve WHERE ghe = ? AND lichChieu = ?";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, maGhe);
            checkStmt.setString(2, maLichChieu);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Ghế đã được đặt
                System.out.println("Ghế " + maGhe + " đã được đặt cho lịch chiếu " + maLichChieu);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        // Nếu ghế chưa được đặt, tiến hành chèn vé mới
        String maVe = generateMaVe();
        String sql = "INSERT INTO Ve (maVe, lichChieu, ghe, giaVe, trangThaiVe, thoiGianBan, nhanVien, khachHang) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maVe);
            stmt.setString(2, maLichChieu);
            stmt.setString(3, maGhe);
            stmt.setDouble(4, giaVe);
            stmt.setString(5, "DA_THANH_TOAN");
            stmt.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
            stmt.setString(7, maNhanVien);
            stmt.setString(8, maKhachHang);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Không có bản ghi nào được chèn vào bảng Ve.");
                return null;
            }
            return maVe;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Tạo mã vé mới (VXXX)
    private String generateMaVe() {
        String sql = "SELECT MAX(maVe) AS maxId FROM Ve";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String maxId = rs.getString("maxId");
                if (maxId != null) {
                    int number = Integer.parseInt(maxId.replace("V", "")) + 1;
                    return String.format("V%03d", number);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "V001"; // Nếu không có vé nào, bắt đầu từ V001
    }
	
}
