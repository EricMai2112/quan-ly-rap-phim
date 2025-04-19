package CONTROL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
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
				for (VaiTro vt : VaiTro.values()) {
					if (vt.toString().equalsIgnoreCase(vaiTroStr)) {
						nhanVien.setVaiTro(vt);
						break;
					}
				}

				listNhanVien.add(nhanVien);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listNhanVien;
	}
	
	public NhanVien getNhanVienByMa(String maNV) {
		Connection con = connectDB.getConnection();
		String sql = "SELECT * FROM NhanVien WHERE maNhanVien = ?";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, maNV);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				String ten = rs.getString("hoTen");
				Date ngaySinh = rs.getDate("ngaySinh");
				String sdt = rs.getString("soDienThoai");
				VaiTro vt = VaiTro.valueOf(rs.getString("vaiTro").toUpperCase());
				String cccd = rs.getString("cccd");
				return new NhanVien(maNV, ten, ngaySinh, sdt, cccd,vt);
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
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

	// Thêm nhân viên mới
	public boolean themNhanVien(NhanVien nv) {
		Connection conn = connectDB.getConnection();
	    String sql = "INSERT INTO NhanVien(maNhanVien, hoTen, ngaySinh, soDienThoai, cccd, vaiTro) VALUES(?,?,?,?,?,?)";
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, nv.getMaNHanVien());
	        stmt.setString(2, nv.getHoTen());
	        stmt.setDate(3, new java.sql.Date(nv.getNgaySinh().getTime()));
	        stmt.setString(4, nv.getSoDienThoai());
	        stmt.setString(5, nv.getCccd());
	        stmt.setString(6, nv.getVaiTro().toString());
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) { e.printStackTrace(); return false; }
	}


}
