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
	private final static ConnectDB connectDB = new ConnectDB();
	
	public List<Phim> getAllPhim() {
	    List<Phim> listPhim = new ArrayList<>();
	    String sql = "SELECT maPhim, tenPhim, thoiLuong, theLoai, trangThaiPhim, hinhAnh FROM Phim";

	    try (Connection conn = connectDB.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet resultSet = stmt.executeQuery(sql)) {

	        while (resultSet.next()) {
	            Phim phim = new Phim();
	            phim.setMaPhim(resultSet.getString("maPhim"));
	            phim.setTenPhim(resultSet.getString("tenPhim"));
	            phim.setThoiLuong(resultSet.getInt("thoiLuong"));
	            phim.setTheLoai(resultSet.getString("theLoai"));

	            String trangThaiStr = resultSet.getString("trangThaiPhim");
	            TrangThaiPhim trangThai = TrangThaiPhim.valueOf(trangThaiStr);
	            phim.setTrangThaiPhim(trangThai);

	            phim.setHinhAnh(resultSet.getString("hinhAnh"));

	            listPhim.add(phim);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return listPhim;
	}
	
	public boolean addPhim(Phim phim) {
	    String sql = "INSERT INTO Phim (maPhim, tenPhim, thoiLuong, theLoai, trangThaiPhim, hinhAnh) VALUES (?, ?, ?, ?, ?, ?)";

	    try (Connection conn = connectDB.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, phim.getMaPhim());
	        stmt.setString(2, phim.getTenPhim());
	        stmt.setInt(3, phim.getThoiLuong());
	        stmt.setString(4, phim.getTheLoai());
	        stmt.setString(5, phim.getTrangThaiPhim().name());
	        stmt.setString(6, phim.getHinhAnh());

	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public static Phim getPhimById(String maPhim) {
	    String sql = "SELECT maPhim, tenPhim, thoiLuong, theLoai, trangThaiPhim, hinhAnh FROM Phim WHERE maPhim = ?";
	    try (Connection conn = connectDB.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setString(1, maPhim);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                Phim phim = new Phim();
	                phim.setMaPhim(rs.getString("maPhim"));
	                phim.setTenPhim(rs.getString("tenPhim"));
	                phim.setThoiLuong(rs.getInt("thoiLuong"));
	                phim.setTheLoai(rs.getString("theLoai"));
	                TrangThaiPhim tt = TrangThaiPhim.valueOf(rs.getString("trangThaiPhim"));
	                phim.setTrangThaiPhim(tt);
	                phim.setHinhAnh(rs.getString("hinhAnh"));
	                return phim;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public boolean deletePhim(String maPhim) {
	    String sql = "DELETE FROM Phim WHERE maPhim = ?";

	    try (Connection conn = connectDB.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, maPhim);
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public boolean updatePhim(Phim phim) {
        String sql = "UPDATE Phim SET tenPhim = ?, thoiLuong = ?, theLoai = ?, trangThaiPhim = ?, hinhAnh = ? WHERE maPhim = ?";

        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phim.getTenPhim());
            stmt.setInt(2, phim.getThoiLuong());
            stmt.setString(3, phim.getTheLoai());
            stmt.setString(4, phim.getTrangThaiPhim().name());
            stmt.setString(5, phim.getHinhAnh());
            stmt.setString(6, phim.getMaPhim());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
