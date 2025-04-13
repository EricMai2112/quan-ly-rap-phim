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
}
