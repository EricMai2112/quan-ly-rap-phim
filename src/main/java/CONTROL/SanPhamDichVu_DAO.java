package CONTROL;

import ConnectDB.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import MODEL.SanPham;

public class SanPhamDichVu_DAO {
	private final ConnectDB connectDB = new ConnectDB();

	public List<SanPham> getAllSanPham() {
		List<SanPham> listSanPham = new ArrayList<>();

		String sql = "SELECT maSanPham, tenSanPham, danhMuc, gia, soLuong FROM SanPham";
		try (Connection conn = connectDB.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet resultSet = stmt.executeQuery(sql)) {

			while (resultSet.next()) {
				SanPham sanPham = new SanPham();
				sanPham.setMaSanPham(resultSet.getString("maSanPham"));
				sanPham.setTenSanPham(resultSet.getString("tenSanPham"));
				sanPham.setDanhMuc(resultSet.getString("danhMuc"));
				sanPham.setGiaTien(resultSet.getDouble("gia"));
				sanPham.setSoLuong(resultSet.getInt("soLuong"));

				listSanPham.add(sanPham);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listSanPham;
	}

	public boolean addSPham(SanPham sanPham) {
		String sql = "INSERT INTO SanPham (maSanPham, tenSanPham, danhMuc, gia, soLuong) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = connectDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, sanPham.getMaSanPham()); // Sửa đúng thứ tự
			stmt.setString(2, sanPham.getTenSanPham());
			stmt.setString(3, sanPham.getDanhMuc());
			stmt.setDouble(4, sanPham.getGiaTien());
			stmt.setInt(5, sanPham.getSoLuong());

			return stmt.executeUpdate() > 0;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public boolean updateSPham(SanPham sp) {
		Connection con = connectDB.getConnection();
		String sql = "UPDATE SanPham SET tenSanPham = ?, danhMuc = ?, gia = ?, soLuong = ? WHERE maSanPham = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, sp.getTenSanPham());
			stmt.setString(2, sp.getDanhMuc());
			stmt.setDouble(3, sp.getGiaTien());
			stmt.setInt(4, sp.getSoLuong());
			stmt.setString(5, sp.getMaSanPham());

			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public SanPham getSanPhamByMa(String maSP) {
		Connection con = connectDB.getConnection();
		String sql = "SELECT * FROM SanPham WHERE maSanPham = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maSP);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				String ten = rs.getString("tenSanPham");
				String dm = rs.getString("danhMuc");
				double donGia = rs.getDouble("gia");
				int sl = rs.getInt("soLuong");
				return new SanPham(maSP, ten, dm, donGia, sl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteSPham(String maSanPham) {
		Connection con = connectDB.getConnection();
		String sql = "DELETE FROM SanPham WHERE MaSanPham = ?";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, maSanPham);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
