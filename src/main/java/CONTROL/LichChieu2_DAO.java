package CONTROL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ConnectDB.ConnectDB;
import MODEL.LichChieu;
import MODEL.Phim;
import MODEL.PhongChieu;

public class LichChieu2_DAO {
	private final ConnectDB connectDB = new ConnectDB();
	
	public List<LichChieu> getLichChieuByPhim(String maPhim) {
        List<LichChieu> danhSach = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();
            String sql = "SELECT lc.maLichChieu, lc.ngayChieu, lc.gioBatDau, lc.gioKetThuc, " +
                    "pc.maPhong, pc.tenPhong, p.tenPhim " +
                    "FROM LichChieu lc " +
                    "JOIN PhongChieu pc ON lc.phongChieu = pc.maPhong " +
                    "JOIN Phim p ON lc.phim = p.maPhim " +
                    "WHERE lc.phim = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, maPhim);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String maLich = rs.getString("maLichChieu");
                String maPhong = rs.getString("maPhong");
                String tenPhong = rs.getString("tenPhong");
                String tenPhim = rs.getString("tenPhim");

                Date ngayChieu = rs.getDate("ngayChieu");
                Time gioBatDau = rs.getTime("gioBatDau");
                Time gioKetThuc = rs.getTime("gioKetThuc");

                PhongChieu phong = new PhongChieu(maPhong, tenPhong);
                Phim phim = new Phim(maPhim, tenPhim);
                LichChieu lich = new LichChieu(maLich, phong, phim, ngayChieu, gioBatDau, gioKetThuc);
                danhSach.add(lich);
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
}
