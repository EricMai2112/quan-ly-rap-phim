package CONTROL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ConnectDB.ConnectDB;
import MODEL.Phim;
import MODEL.TrangThaiPhim;

public class Phim_DAO {
	private final ConnectDB connectDB = new ConnectDB();
	
	    // Lấy danh sách tất cả phim
	    public List<Phim> getAllPhim() {
	        List<Phim> listPhim = new ArrayList<>();
	        String sql = "SELECT maPhim, tenPhim, thoiLuong, theLoai, trangThaiPhim FROM Phim";
	        
	        try (Connection conn = connectDB.getConnection();
	                Statement stmt = conn.createStatement();
	                ResultSet resultSet = stmt.executeQuery(sql))  {
	            
	            while (resultSet.next()) {
	                Phim phim = new Phim();
	                phim.setMaPhim(resultSet.getString("maPhim"));
	                phim.setTenPhim(resultSet.getString("tenPhim"));
	                phim.setThoiLuong(resultSet.getInt("thoiLuong"));
	                phim.setTheLoai(resultSet.getString("theLoai"));
	                
	                // Xử lý trạng thái phim
	                String trangThaiStr = resultSet.getString("trangThaiPhim");
	                TrangThaiPhim trangThai = TrangThaiPhim.valueOf(trangThaiStr);
	                phim.setTrangThaiPhim(trangThai);
	                
	                listPhim.add(phim);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        return listPhim;
	    }
}
