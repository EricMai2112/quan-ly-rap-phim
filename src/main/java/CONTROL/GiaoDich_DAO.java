package CONTROL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import MODEL.GiaoDich;
import MODEL.KhachHang;
import MODEL.NhanVien;

public class GiaoDich_DAO {
    private final ConnectDB connectDB = new ConnectDB();
    private final NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
    private final KhachHang_DAO khachHangDAO = new KhachHang_DAO();

    public List<GiaoDich> getAllGiaoDich() {
        List<GiaoDich> list = new ArrayList<>();
        String sql = "SELECT * FROM GiaoDich";

        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String maGiaoDich = rs.getString("maGiaoDich");
                Double tongTien = rs.getDouble("tongTien");
                Timestamp thoiGianGiaoDich = rs.getTimestamp("thoiGianGiaoDich");

                String maNhanVien = rs.getString("nhanVien");
                String maKhachHang = rs.getString("khachHang");

                NhanVien nhanVien = nhanVienDAO.getNhanVienByMa(maNhanVien);
                KhachHang khachHang = khachHangDAO.getKhachHangByMa(maKhachHang);

                if (nhanVien != null && khachHang != null) {
                    GiaoDich giaoDich = new GiaoDich(maGiaoDich, tongTien, thoiGianGiaoDich, nhanVien, khachHang);
                    list.add(giaoDich);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách giao dịch: " + e.getMessage());
        }

        return list;
    }

    public boolean themGiaoDich(GiaoDich giaoDich) {
        String sql = "INSERT INTO GiaoDich (maGiaoDich, tongTien, thoiGianGiaoDich, nhanVien, khachHang) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, giaoDich.getMaGiaoDich());
            stmt.setDouble(2, giaoDich.getTongTien());
            stmt.setTimestamp(3, new java.sql.Timestamp(giaoDich.getThoiGianGiaoDich().getTime()));
            stmt.setString(4, giaoDich.getNhanVien().getMaNHanVien());
            stmt.setString(5, giaoDich.getKhachHang().getMaKhachHang());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public GiaoDich findGiaoDichByMa(String maGiaoDich) {
        String sql = "SELECT * FROM GiaoDich WHERE maGiaoDich = ?";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maGiaoDich);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                GiaoDich giaoDich = new GiaoDich();
                giaoDich.setMaGiaoDich(rs.getString("maGiaoDich"));
                giaoDich.setTongTien(rs.getDouble("tongTien"));
                Timestamp timestamp = rs.getTimestamp("thoiGianGiaoDich");
                if (timestamp != null) {
                    giaoDich.setThoiGianGiaoDich(new java.util.Date(timestamp.getTime()));
                } else {
                    giaoDich.setThoiGianGiaoDich(null);
                }

                String maNhanVien = rs.getString("nhanVien");
                String maKhachHang = rs.getString("khachHang");

                NhanVien nhanVien = nhanVienDAO.getNhanVienByMa(maNhanVien);
                KhachHang khachHang = khachHangDAO.getKhachHangByMa(maKhachHang);

                giaoDich.setNhanVien(nhanVien);
                giaoDich.setKhachHang(khachHang);

                return giaoDich;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
