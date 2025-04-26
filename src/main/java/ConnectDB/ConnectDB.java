package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Admin
 */
public class ConnectDB {
	public static Connection getConnection() {
        Connection connection = null;
        try {
        	String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyRapChieuPhim;encrypt=true;trustServerCertificate=true;";
            String username = "sa";
            String password = "sapassword";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
	public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
