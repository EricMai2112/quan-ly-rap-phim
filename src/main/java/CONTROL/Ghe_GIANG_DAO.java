package CONTROL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import MODEL.Ghe;
import MODEL.LoaiGhe;
import MODEL.NhanVien;
import MODEL.PhongChieu;
import MODEL.TrangThaiGhe;
import MODEL.VaiTro;

public class Ghe_GIANG_DAO {
	private final ConnectDB connectDB = new ConnectDB();
    public List<Ghe> getDanhSachTatCaGhe() {
        List<Ghe> danhSach = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();
            String sql = "SELECT g.*, pc.tenPhong FROM Ghe g JOIN PhongChieu pc ON g.phongChieu = pc.maPhong";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String maGhe = rs.getString("maGhe");
                String soGhe = rs.getString("soGhe");
                String loaiGhe = rs.getString("loaiGhe");
                String maPhong = rs.getString("phongChieu");
                String tenPhong = rs.getString("tenPhong");

                Ghe ghe = new Ghe(maGhe, new PhongChieu(maPhong, tenPhong), soGhe,
                                  LoaiGhe.valueOf(loaiGhe));
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

    public List<Ghe> getDanhSachGheTheoPhong(String maPhong) {
        List<Ghe> danhSach = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();
            String sql = "SELECT g.*, pc.tenPhong FROM Ghe g JOIN PhongChieu pc ON g.phongChieu = pc.maPhong WHERE g.phongChieu = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, maPhong);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String maGhe = rs.getString("maGhe");
                String soGhe = rs.getString("soGhe");
                String loaiGhe = rs.getString("loaiGhe");
                String tenPhong = rs.getString("tenPhong");

                Ghe ghe = new Ghe(maGhe, new PhongChieu(maPhong, tenPhong), soGhe,
                                  LoaiGhe.valueOf(loaiGhe));
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
    
    public Ghe getGheByMa(String maGhe) {
        String sql = "SELECT g.*, pc.tenPhong FROM Ghe g JOIN PhongChieu pc ON g.phongChieu = pc.maPhong WHERE g.maGhe = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maGhe);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Khởi tạo đối tượng Ghe từ dữ liệu trong ResultSet
                    String soGhe = rs.getString("soGhe");
                    String loaiGheStr = rs.getString("loaiGhe");
                    String trangThaiStr = rs.getString("trangThaiGhe");
                    String maPhong = rs.getString("phongChieu");
                    String tenPhong = rs.getString("tenPhong");

                    // Lấy thông tin phòng chiếu từ tên phòng
                    PhongChieu phongChieu = new PhongChieu(maPhong, tenPhong);

                    LoaiGhe loaiGhe = LoaiGhe.valueOf(loaiGheStr);
                    TrangThaiGhe trangThai = TrangThaiGhe.valueOf(trangThaiStr);

                    // Tạo đối tượng Ghe và gán các giá trị từ ResultSet
                    Ghe ghe = new Ghe(maGhe, phongChieu, soGhe, loaiGhe);

                    return ghe;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Không tìm thấy ghế hoặc xảy ra lỗi
    }



    public boolean themGhe(Ghe ghe) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectDB.getConnection();
            String sql = "INSERT INTO Ghe(maGhe, phongChieu, soGhe, loaiGhe) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, ghe.getMaGhe());
            stmt.setString(2, ghe.getPhongChieu().getMaPhong());
            stmt.setString(3, ghe.getSoGhe());
            stmt.setString(4, ghe.getLoaiGhe().name());
           

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    public boolean capNhatGhe(Ghe ghe) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectDB.getConnection();
            String sql = "UPDATE Ghe SET phongChieu = ?, soGhe = ?, loaiGhe = ?, trangThaiGhe = ? WHERE maGhe = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, ghe.getPhongChieu().getMaPhong());
            stmt.setString(2, ghe.getSoGhe());
            stmt.setString(3, ghe.getLoaiGhe().name());
            stmt.setString(4, ghe.getMaGhe());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    public boolean xoaGhe(String maGhe) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectDB.getConnection();
            String sql = "DELETE FROM Ghe WHERE maGhe = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, maGhe);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}
