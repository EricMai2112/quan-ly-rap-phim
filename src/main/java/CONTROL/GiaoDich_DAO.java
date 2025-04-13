package CONTROL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import MODEL.GiaoDich;
import MODEL.KhachHang;
import MODEL.NhanVien;
import MODEL.SanPham;

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
}