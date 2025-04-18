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
	public boolean addKhachHang(KhachHang khachHang) {
	       String sql = "INSERT INTO KhachHang (maKhachHang, tenKhachHang, soDienThoai, email) VALUES (?, ?, ?, ?)";

	       try (Connection conn = connectDB.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setString(1, khachHang.getMaKhachHang());
	            stmt.setString(2, khachHang.getTenKhachHang());
	            stmt.setString(3, khachHang.getSoDienThoai());
	            stmt.setString(4, khachHang.getEmail());

	            return stmt.executeUpdate() > 0;

	       } catch (SQLException e) {
	            e.printStackTrace();
	       }
	        return false;
	}
    // Cập nhật thông tin khách hàng
    public boolean updateKhachHang(KhachHang kh) {
        String sql = "UPDATE KhachHang SET tenKhachHang = ?, soDienThoai = ?, email = ? WHERE maKhachHang = ?";

        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kh.getTenKhachHang());
            stmt.setString(2, kh.getSoDienThoai());
            stmt.setString(3, kh.getEmail());
            stmt.setString(4, kh.getMaKhachHang());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa khách hàng theo mã
    public boolean deleteKhachHang(String maKhachHang) {
        String sql = "DELETE FROM KhachHang WHERE maKhachHang = ?";

        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maKhachHang);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
