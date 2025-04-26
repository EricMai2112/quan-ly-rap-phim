package CONTROL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< Updated upstream
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import MODEL.LichChieu;
import MODEL.PhongChieu;
import MODEL.Phim;
import CONTROL.PhongChieu_DAO;
import CONTROL.Phim_DAO;

/**
 * DAO cho bảng LichChieu, tương ứng với MODEL.LichChieu
 */
public class LichChieu_DAO {
    private static final ConnectDB connectDB = new ConnectDB();

    /**
     * Lấy toàn bộ danh sách lịch chiếu
     */
    public static List<LichChieu> getAllLichChieu() {
        List<LichChieu> list = new ArrayList<>();
        String sql = "SELECT * FROM LichChieu";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Lấy các trường
                String maLC = rs.getString("maLichChieu");
                String maPhong = rs.getString("phongChieu");
                String maPhim = rs.getString("phim");
                Date sqlNgay = rs.getDate("ngayChieu");
                Time sqlBD = rs.getTime("gioBatDau");
                Time sqlKT = rs.getTime("gioKetThuc");

                // Chuyển sang java.util.Date
                java.util.Date ngayChieu = new java.util.Date(sqlNgay.getTime());
                java.util.Date gioBatDau = new java.util.Date(sqlBD.getTime());
                java.util.Date gioKetThuc = new java.util.Date(sqlKT.getTime());

                // Lấy đối tượng PhongChieu và Phim từ DAO
                PhongChieu phong = PhongChieu_DAO.getPhongChieuById(maPhong);
                Phim phim = Phim_DAO.getPhimById(maPhim);

                // Tạo và thêm vào danh sách
                LichChieu lc = new LichChieu(maLC, phong, phim, ngayChieu, gioBatDau, gioKetThuc);
                list.add(lc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Thêm lịch chiếu mới
     */
    public static boolean addLichChieu(LichChieu lc) {
        String maLC = generateMaLichChieu();
        String sql = "INSERT INTO LichChieu(maLichChieu, phongChieu, phim, ngayChieu, gioBatDau, gioKetThuc) " +
                     "VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maLC);
            stmt.setString(2, lc.getPhongChieu().getMaPhong());
            stmt.setString(3, lc.getPhim().getMaPhim());
            stmt.setDate(4, new Date(lc.getNgayChieu().getTime()));
            stmt.setTime(5, new Time(lc.getGioBatDau().getTime()));
            stmt.setTime(6, new Time(lc.getGioKetThuc().getTime()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật thông tin lịch chiếu
     */
    public static boolean updateLichChieu(LichChieu lc) {
        String sql = "UPDATE LichChieu SET phongChieu = ?, phim = ?, ngayChieu = ?, gioBatDau = ?, gioKetThuc = ? " +
                     "WHERE maLichChieu = ?";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lc.getPhongChieu().getMaPhong());
            stmt.setString(2, lc.getPhim().getMaPhim());
            stmt.setDate(3, new Date(lc.getNgayChieu().getTime()));
            stmt.setTime(4, new Time(lc.getGioBatDau().getTime()));
            stmt.setTime(5, new Time(lc.getGioKetThuc().getTime()));
            stmt.setString(6, lc.getMaLichChieu());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xóa lịch chiếu theo mã
     */
    public static boolean deleteLichChieu(String maLichChieu) {
        String sql = "DELETE FROM LichChieu WHERE maLichChieu = ?";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maLichChieu);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sinh tự động mã lịch chiếu mới: "LC" + 3 chữ số
     */
    private static String generateMaLichChieu() {
        String prefix = "LC";
        String sql = "SELECT TOP 1 maLichChieu FROM LichChieu " +
                     "WHERE maLichChieu LIKE 'LC%' ORDER BY maLichChieu DESC";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String last = rs.getString(1); // ví dụ "LC012"
                int num = Integer.parseInt(last.substring(2));
                return prefix + String.format("%03d", num + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prefix + "001";
    }
    
    /**
     * Lấy một LichChieu theo mã
     */
    public static LichChieu getLichChieuById(String maLC) {
        String sql = "SELECT * FROM LichChieu WHERE maLichChieu = ?";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maLC);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // đọc các trường
                    String maLichChieu = rs.getString("maLichChieu");
                    String maPhong     = rs.getString("phongChieu");
                    String maPhim      = rs.getString("phim");
                    Date sqlNgay       = rs.getDate("ngayChieu");
                    Time sqlBD         = rs.getTime("gioBatDau");
                    Time sqlKT         = rs.getTime("gioKetThuc");
                    
                    // chuyển sang java.util.Date
                    java.util.Date ngayChieu  = new java.util.Date(sqlNgay.getTime());
                    java.util.Date gioBatDau  = new java.util.Date(sqlBD.getTime());
                    java.util.Date gioKetThuc = new java.util.Date(sqlKT.getTime());
                    
                    // lấy đối tượng PhongChieu và Phim
                    PhongChieu phong = PhongChieu_DAO.getPhongChieuById(maPhong);
                    Phim     phim  = new Phim_DAO().getPhimById(maPhim);
                    
                    return new LichChieu(
                        maLichChieu,
                        phong,
                        phim,
                        ngayChieu,
                        gioBatDau,
                        gioKetThuc
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

public class LichChieu_DAO {
=======
import java.sql.Time;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
>>>>>>> Stashed changes

import ConnectDB.ConnectDB;
import MODEL.LichChieu;
import MODEL.Phim;
import MODEL.PhongChieu;

public class LichChieu_DAO {
    private final static ConnectDB connectDB = new ConnectDB();

    /**
     * Lấy danh sách lịch chiếu của 1 phim
     */
    public List<LichChieu> getLichChieuByPhim(String maPhim) {
        List<LichChieu> danhSach = new ArrayList<>();
        String sql = "SELECT lc.maLichChieu, lc.ngayChieu, lc.gioBatDau, lc.gioKetThuc, "
                   + "pc.maPhong, pc.tenPhong, p.tenPhim "
                   + "FROM LichChieu lc "
                   + "JOIN PhongChieu pc ON lc.phongChieu = pc.maPhong "
                   + "JOIN Phim p ON lc.phim = p.maPhim "
                   + "WHERE lc.phim = ?";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhim);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String maLich     = rs.getString("maLichChieu");
                    Date ngayChieu    = rs.getDate("ngayChieu");
                    Time gioBatDau    = rs.getTime("gioBatDau");
                    Time gioKetThuc   = rs.getTime("gioKetThuc");

                    String maPhong    = rs.getString("maPhong");
                    String tenPhong   = rs.getString("tenPhong");
                    String tenPhim    = rs.getString("tenPhim");

                    PhongChieu phong = new PhongChieu(maPhong, tenPhong);
                    Phim phim         = new Phim(maPhim, tenPhim);

                    LichChieu lich = new LichChieu(
                        maLich, phong, phim,
                        ngayChieu, gioBatDau, gioKetThuc
                    );
                    danhSach.add(lich);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    /**
     * Lấy toàn bộ danh sách lịch chiếu
     */
    public static List<LichChieu> getAllLichChieu() {
        List<LichChieu> list = new ArrayList<>();
        String sql = "SELECT * FROM LichChieu";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String maLC      = rs.getString("maLichChieu");
                Date sqlNgay     = rs.getDate("ngayChieu");
                Time sqlBD       = rs.getTime("gioBatDau");
                Time sqlKT       = rs.getTime("gioKetThuc");
                String maPhong   = rs.getString("phongChieu");
                String maPhim    = rs.getString("phim");

                // Lấy đối tượng qua DAO riêng
                PhongChieu phong = PhongChieu_DAO.getPhongChieuById(maPhong);
                Phim phim         = Phim_DAO.getPhimById(maPhim);

                LichChieu lc = new LichChieu(
                    maLC, phong, phim,
                    sqlNgay, sqlBD, sqlKT
                );
                list.add(lc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Thêm lịch chiếu mới
     */
    public static boolean addLichChieu(LichChieu lc) {
        String maLC = generateMaLichChieu();
        String sql = "INSERT INTO LichChieu(maLichChieu, phongChieu, phim, ngayChieu, gioBatDau, gioKetThuc) "
                   + "VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maLC);
            stmt.setString(2, lc.getPhongChieu().getMaPhong());
            stmt.setString(3, lc.getPhim().getMaPhim());
            stmt.setDate(4, new Date(lc.getNgayChieu().getTime()));
            stmt.setTime(5, new Time(lc.getGioBatDau().getTime()));
            stmt.setTime(6, new Time(lc.getGioKetThuc().getTime()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật lịch chiếu
     */
    public static boolean updateLichChieu(LichChieu lc) {
        String sql = "UPDATE LichChieu SET phongChieu = ?, phim = ?, ngayChieu = ?, "
                   + "gioBatDau = ?, gioKetThuc = ? WHERE maLichChieu = ?";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lc.getPhongChieu().getMaPhong());
            stmt.setString(2, lc.getPhim().getMaPhim());
            stmt.setDate(3, new Date(lc.getNgayChieu().getTime()));
            stmt.setTime(4, new Time(lc.getGioBatDau().getTime()));
            stmt.setTime(5, new Time(lc.getGioKetThuc().getTime()));
            stmt.setString(6, lc.getMaLichChieu());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xóa lịch chiếu theo mã
     */
    public static boolean deleteLichChieu(String maLichChieu) {
        String sql = "DELETE FROM LichChieu WHERE maLichChieu = ?";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maLichChieu);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lấy 1 lịch chiếu theo mã
     */
    public static LichChieu getLichChieuById(String maLC) {
        String sql = "SELECT * FROM LichChieu WHERE maLichChieu = ?";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maLC);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Date sqlNgay   = rs.getDate("ngayChieu");
                    Time sqlBD     = rs.getTime("gioBatDau");
                    Time sqlKT     = rs.getTime("gioKetThuc");
                    String maPhong = rs.getString("phongChieu");
                    String maPhim  = rs.getString("phim");

                    PhongChieu phong = PhongChieu_DAO.getPhongChieuById(maPhong);
                    Phim phim         = Phim_DAO.getPhimById(maPhim);

                    return new LichChieu(
                        maLC, phong, phim,
                        sqlNgay, sqlBD, sqlKT
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sinh tự động mã lịch chiếu mới: "LC" + 3 chữ số
     */
    private static String generateMaLichChieu() {
        String prefix = "LC";
        String sql = "SELECT TOP 1 maLichChieu FROM LichChieu "
                   + "WHERE maLichChieu LIKE 'LC%' ORDER BY maLichChieu DESC";
        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String last = rs.getString(1); // ví dụ "LC012"
                int num = Integer.parseInt(last.substring(2));
                return prefix + String.format("%03d", num + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prefix + "001";
    }
}
