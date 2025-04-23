package CONTROL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ConnectDB.ConnectDB;
import MODEL.ChiTietGiaoDich;

public class ChiTietGiaoDich_DAO {
	private final ConnectDB connectDB = new ConnectDB();

	public boolean themChiTietGiaoDich(ChiTietGiaoDich chiTiet) {
        String sql = "INSERT INTO ChiTietGiaoDich (maChiTiet, giaoDich, ve, sanPham, soLuong, donGia, thanhTien) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, chiTiet.getMaChiTiet());
            stmt.setString(2, chiTiet.getGiaoDich().getMaGiaoDich());
            if (chiTiet.getVe() != null) {
                stmt.setString(3, chiTiet.getVe().getMaVe());
            } else {
                stmt.setNull(3, java.sql.Types.NVARCHAR);
            }
            if (chiTiet.getSanPham() != null) {
                stmt.setString(4, chiTiet.getSanPham().getMaSanPham());
            } else {
                stmt.setNull(4, java.sql.Types.NVARCHAR);
            }
            stmt.setInt(5, chiTiet.getSoLuong());
            stmt.setDouble(6, chiTiet.getDonGia());
            stmt.setDouble(7, chiTiet.getThanhTien());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
