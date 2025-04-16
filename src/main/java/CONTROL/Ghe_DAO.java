package CONTROL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ConnectDB.ConnectDB;
import MODEL.Ghe;
import MODEL.LoaiGhe;
import MODEL.PhongChieu;
import MODEL.TrangThaiGhe;

public class Ghe_DAO {
	private final ConnectDB connectDB = new ConnectDB();
	
	 public List<Ghe> getDanhSachGheTheoPhong(String maPhong) {
	        List<Ghe> danhSach = new ArrayList<>();
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;

	        try {
	            conn = ConnectDB.getConnection();
	            String sql = "SELECT g.*, pc.tenPhong " +
	                         "FROM Ghe g " +
	                         "JOIN PhongChieu pc ON g.phongChieu = pc.maPhong " +
	                         "WHERE g.phongChieu = ?";
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, maPhong);
	            rs = stmt.executeQuery();

	            while (rs.next()) {
	                String maGhe = rs.getString("maGhe");
	                String soGhe = rs.getString("soGhe");
	                String maLoaiGhe = rs.getString("loaiGhe");
	                String trangThai = rs.getString("trangThaiGhe");
	                String tenPhong = rs.getString("tenPhong");

	                // Map Enum
	                LoaiGhe loai = LoaiGhe.valueOf(maLoaiGhe); // enum LoaiGhe: VIP, THUONG
	                TrangThaiGhe tt = TrangThaiGhe.valueOf(trangThai); // enum: TRONG, DA_DAT

	                PhongChieu phong = new PhongChieu(maPhong, tenPhong);
	                Ghe ghe = new Ghe(maGhe, phong, soGhe, loai, tt);
	                danhSach.add(ghe);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try { if (rs != null) rs.close(); } catch (Exception e) {}
	            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
	            try { if (conn != null) conn.close(); } catch (Exception e) {}
	        }

	        return danhSach;
	    }
	 
	 public boolean capNhatTrangThaiGhe(String maGhe, TrangThaiGhe trangThai) {
		    String sql = "UPDATE Ghe SET trangThaiGhe = ? WHERE maGhe = ?";
		    try (Connection conn = connectDB.getConnection();
		         PreparedStatement stmt = conn.prepareStatement(sql)) {
		        System.out.println("Updating trangThaiGhe for maGhe = " + maGhe + " to: " + trangThai.getDbValue());
		        stmt.setString(1, trangThai.getDbValue()); // Sử dụng giá trị hợp lệ cho DB
		        stmt.setString(2, maGhe);
		        stmt.executeUpdate();
		        return true;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}
}
