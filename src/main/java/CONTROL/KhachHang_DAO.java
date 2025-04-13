package CONTROL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import MODEL.KhachHang;
import MODEL.NhanVien;

public class KhachHang_DAO {
	private final ConnectDB connectDB = new ConnectDB();
	public List<KhachHang> getAllKhachHang(){
		
		
		List<KhachHang> listKhachHang = new ArrayList<KhachHang>();
		String sql = "SELECT maKhachHang, tenKhachHang, soDienTHoai,email FROM KhachHang";
		try(Connection conn = connectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)){
			while(resultSet.next()) {
				KhachHang khachHang = new KhachHang();
				khachHang.setMaKhachHang(resultSet.getString("maKhachHang"));
				khachHang.setTenKhachHang(resultSet.getString("tenKhachHang"));
				khachHang.setSoDienThoai(resultSet.getString("soDienThoai"));
				khachHang.setEmail(resultSet.getString("email"));
				
				listKhachHang.add(khachHang);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return listKhachHang;
	}
	public KhachHang getKhachHangByMa(String maKH) {
		Connection con = connectDB.getConnection();
		String sql = "SELECT * FROM KhachHang WHERE maKhachHang = ?";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, maKH);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				String ten = rs.getString("tenKhachHang");
				String sdt = rs.getString("soDienThoai");
				String email = rs.getString("email");
				return new KhachHang(maKH,ten,sdt,email);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
