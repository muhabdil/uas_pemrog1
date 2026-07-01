package campaignmonitoring;

public class CampaignMonitoring {

    public static void main(String[] args) {
        // Memanggil dan menampilkan FormLogin secara otomatis saat aplikasi di-run
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormLogin login = new FormLogin();
                login.setVisible(true); // Membuat form Login terlihat di layar
                login.setLocationRelativeTo(null); // Membuat posisi form otomatis di tengah layar
            }
        });
    }
    
}