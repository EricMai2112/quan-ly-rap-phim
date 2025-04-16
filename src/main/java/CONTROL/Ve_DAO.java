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
                    null, // LoaiGhe
                    null  // TrangThaiGhe
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
}
