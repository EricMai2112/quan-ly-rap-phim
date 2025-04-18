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

	    return null; // Không tìm thấy hoặc xảy ra lỗi
	}
	public boolean themNhanVien(NhanVien nhanVien) {
	    String sql = "INSERT INTO NhanVien (maNhanVien, hoTen, ngaySinh, soDienThoai, cccd, vaiTro) VALUES (?, ?, ?, ?, ?, ?)";
	    try (Connection conn = connectDB.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        // Thiết lập các tham số cho PreparedStatement
	        ps.setString(1, nhanVien.getMaNHanVien());
	        ps.setString(2, nhanVien.getHoTen());
	        ps.setDate(3, new java.sql.Date(nhanVien.getNgaySinh().getTime())); // Chuyển từ java.util.Date sang java.sql.Date
	        ps.setString(4, nhanVien.getSoDienThoai());
	        ps.setString(5, nhanVien.getCccd());
	        //ps.setString(6, nhanVien.getVaiTro().toString()); // Vai trò là Enum, chuyển thành String
	        ps.setString(6, nhanVien.getVaiTro().getDbValue());
	        // Thực thi câu lệnh
	        int rowsAffected = ps.executeUpdate();
	        return rowsAffected > 0; // Trả về true nếu đã thêm thành công
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // Nếu có lỗi, trả về false
	    }
	}
	public boolean capNhatNhanVien(NhanVien nhanVien) {
	    String sql = "UPDATE NhanVien SET hoTen = ?, ngaySinh = ?, soDienThoai = ?, cccd = ?, vaiTro = ? WHERE maNhanVien = ?";
	    try (Connection conn = connectDB.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        // Thiết lập các tham số cho PreparedStatement
	        ps.setString(1, nhanVien.getHoTen());
	        ps.setDate(2, new java.sql.Date(nhanVien.getNgaySinh().getTime())); // Chuyển từ java.util.Date sang java.sql.Date
	        ps.setString(3, nhanVien.getSoDienThoai());
	        ps.setString(4, nhanVien.getCccd());
	        ps.setString(5, nhanVien.getVaiTro().name()); // Vai trò là Enum, chuyển thành String
	        ps.setString(6, nhanVien.getMaNHanVien()); // Mã nhân viên là điều kiện để cập nhật

	        // Thực thi câu lệnh
	        int rowsAffected = ps.executeUpdate();
	        return rowsAffected > 0; // Trả về true nếu đã cập nhật thành công
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // Nếu có lỗi, trả về false
	    }
	}
	public boolean xoaNhanVien(String maNhanVien) {
	    String sql = "DELETE FROM NhanVien WHERE maNhanVien = ?";
	    try (Connection conn = connectDB.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        // Thiết lập tham số cho PreparedStatement
	        ps.setString(1, maNhanVien);

	        // Thực thi câu lệnh
	        int rowsAffected = ps.executeUpdate();
	        return rowsAffected > 0; // Trả về true nếu đã xóa thành công
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // Nếu có lỗi, trả về false
	    }
	}




}
