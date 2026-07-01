package campaignmonitoring;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class koneksi {
    private static Connection mysqlkonek;
    
    public static Connection koneksiDB() {
        try {
            // DIJELASKAN: Cek apakah koneksi kosong ATAU sudah telanjur ditutup (closed)
            if (mysqlkonek == null || mysqlkonek.isClosed()) {
                String url = "jdbc:mysql://localhost:3306/db_monitoring_rkp";
                String user = "root";
                String pass = "";
                
                Class.forName("com.mysql.cj.jdbc.Driver");
                mysqlkonek = DriverManager.getConnection(url, user, pass);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi ke Database Gagal: " + e.getMessage());
        }
        return mysqlkonek;
    }
}