package CONTROL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import ConnectDB.ConnectDB;

public class ThongKe_DAO {
	 private final ConnectDB connectDB = new ConnectDB();
	// Lấy doanh thu theo ngày trong khoảng thời gian
    public Map<String, Double> getRevenueByDate(String startDate, String endDate) {
        Map<String, Double> revenueData = new HashMap<>();
        String sql = "SELECT CAST(thoiGianGiaoDich AS DATE) AS Ngay, SUM(tongTien) AS DoanhThu " +
                     "FROM GiaoDich " +
                     "WHERE CAST(thoiGianGiaoDich AS DATE) BETWEEN ? AND ? " +
                     "GROUP BY CAST(thoiGianGiaoDich AS DATE) " +
                     "ORDER BY CAST(thoiGianGiaoDich AS DATE)";

        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String date = rs.getString("Ngay");
                double revenue = rs.getDouble("DoanhThu");
                revenueData.put(date, revenue);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy dữ liệu doanh thu theo ngày: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return revenueData;
    }

    // Lấy doanh thu theo từng ngày trong một tháng cụ thể
    public Map<String, Double> getRevenueByMonth(int month, int year) {
        Map<String, Double> revenueData = new HashMap<>();
        String sql = "SELECT CAST(thoiGianGiaoDich AS DATE) AS Ngay, SUM(tongTien) AS DoanhThu " +
                     "FROM GiaoDich " +
                     "WHERE MONTH(thoiGianGiaoDich) = ? AND YEAR(thoiGianGiaoDich) = ? " +
                     "GROUP BY CAST(thoiGianGiaoDich AS DATE) " +
                     "ORDER BY CAST(thoiGianGiaoDich AS DATE)";

        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String date = rs.getString("Ngay");
                double revenue = rs.getDouble("DoanhThu");
                revenueData.put(date, revenue);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy dữ liệu doanh thu theo tháng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return revenueData;
    }

    // Lấy doanh thu theo từng tháng trong một năm cụ thể
    public Map<String, Double> getRevenueByYear(int year) {
        Map<String, Double> revenueData = new HashMap<>();
        String sql = "SELECT 'Tháng ' + RIGHT('0' + CAST(MONTH(thoiGianGiaoDich) AS VARCHAR(2)), 2) AS Thang, SUM(tongTien) AS DoanhThu " +
                     "FROM GiaoDich " +
                     "WHERE YEAR(thoiGianGiaoDich) = ? " +
                     "GROUP BY MONTH(thoiGianGiaoDich), 'Tháng ' + RIGHT('0' + CAST(MONTH(thoiGianGiaoDich) AS VARCHAR(2)), 2) " +
                     "ORDER BY MONTH(thoiGianGiaoDich)";

        try (Connection conn = connectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String month = rs.getString("Thang");
                double revenue = rs.getDouble("DoanhThu");
                revenueData.put(month, revenue);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy dữ liệu doanh thu theo năm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return revenueData;
    }
	
}
