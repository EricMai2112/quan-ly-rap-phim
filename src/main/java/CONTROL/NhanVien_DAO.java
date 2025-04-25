package CONTROL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import MODEL.NhanVien;
import MODEL.SanPham;
import MODEL.VaiTro;

public class NhanVien_DAO {
	private final ConnectDB connectDB = new ConnectDB();

	public List<NhanVien> getAllNhanVien() {
		List<NhanVien> listNhanVien = new ArrayList<>();
		String sql = "SELECT * FROM NhanVien";
		try (Connection conn = connectDB.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet resultSet = stmt.executeQuery(sql)) {

			while (resultSet.next()) {
				NhanVien nhanVien = new NhanVien();
				nhanVien.setMaNHanVien(resultSet.getString("maNhanVien"));
				nhanVien.setHoTen(resultSet.getString("hoTen"));
				nhanVien.setNgaySinh(resultSet.getDate("ngaySinh"));
				nhanVien.setSoDienThoai(resultSet.getString("soDienThoai"));
				nhanVien.setCccd(resultSet.getString("cccd"));

				String vaiTroStr = resultSet.getString("vaiTro");
				VaiTro vaiTro = VaiTro.valueOf(vaiTroStr);
				nhanVien.setVaiTro(vaiTro);	

				listNhanVien.add(nhanVien);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listNhanVien;
	}

	public NhanVien getNhanVienByMa(String maNhanVien) {
	    String sql = "SELECT * FROM NhanVien WHERE maNhanVien = ?";
	    try (Connection conn = connectDB.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, maNhanVien);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                NhanVien nv = new NhanVien();
	                nv.setMaNHanVien(rs.getString("maNhanVien"));
	                nv.setHoTen(rs.getString("hoTen"));
	                nv.setNgaySinh(rs.getDate("ngaySinh"));
	                nv.setSoDienThoai(rs.getString("soDienThoai"));
	                nv.setCccd(rs.getString("cccd"));

	                String vaiTroStr = rs.getString("vaiTro");
	                VaiTro vaiTro = VaiTro.valueOf(vaiTroStr);
	                nv.setVaiTro(vaiTro);

	                return nv;
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
<<<<<<< Updated upstream
=======
	
	// Sinh mã NV mới
		public String generateMaNhanVien() {
			Connection con = connectDB.getConnection();
		    String prefix = "NV";
		    String sql = "SELECT TOP 1 maNhanVien FROM NhanVien WHERE maNhanVien LIKE 'NV%' ORDER BY maNhanVien DESC";
		    try (PreparedStatement stmt = con.prepareStatement(sql);
		         ResultSet rs = stmt.executeQuery()) {
		        if (rs.next()) {
		            String last = rs.getString(1); // e.g. "NV012"
		            int num = Integer.parseInt(last.substring(2));
		            return prefix + String.format("%03d", num+1);
		        }
		    } catch (SQLException e) { e.printStackTrace(); }
		    return prefix + "001";
		}

>>>>>>> Stashed changes

	public boolean themNhanVien(NhanVien nhanVien) {
	    String sql = "INSERT INTO NhanVien (maNhanVien, hoTen, ngaySinh, soDienThoai, cccd, vaiTro) VALUES (?, ?, ?, ?, ?, ?)";
	    try (Connection conn = connectDB.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, nhanVien.getMaNHanVien());
	        ps.setString(2, nhanVien.getHoTen());
	        ps.setDate(3, new java.sql.Date(nhanVien.getNgaySinh().getTime()));
	        ps.setString(4, nhanVien.getSoDienThoai());
	        ps.setString(5, nhanVien.getCccd());
	        ps.setString(6, nhanVien.getVaiTro().getDbValue());
	        
	        int rowsAffected = ps.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public boolean capNhatNhanVien(NhanVien nhanVien) {
	    String sql = "UPDATE NhanVien SET hoTen = ?, ngaySinh = ?, soDienThoai = ?, cccd = ?, vaiTro = ? WHERE maNhanVien = ?";
	    try (Connection conn = connectDB.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, nhanVien.getHoTen());
	        ps.setDate(2, new java.sql.Date(nhanVien.getNgaySinh().getTime()));
	        ps.setString(3, nhanVien.getSoDienThoai());
	        ps.setString(4, nhanVien.getCccd());
	        ps.setString(5, nhanVien.getVaiTro().name());
	        ps.setString(6, nhanVien.getMaNHanVien());

	        int rowsAffected = ps.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public boolean xoaNhanVien(String maNhanVien) {
	    String sql = "DELETE FROM NhanVien WHERE maNhanVien = ?";
	    try (Connection conn = connectDB.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, maNhanVien);
	        int rowsAffected = ps.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
