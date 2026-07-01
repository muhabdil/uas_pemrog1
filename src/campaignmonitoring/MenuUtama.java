package campaignmonitoring;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MenuUtama extends JFrame {
    private JTable tabelCampaign, tabelProgressKOL;
    private DefaultTableModel modelCampaign, modelProgress;
    
    // Form Input Campaign (Kiri)
    private JTextField txtNamaCampaign, txtBrand, txtTotalBudget, txtSisaBudget;
    private JComboBox<String> cbStatusCampaign;
    private JButton btnSimpanC, btnUpdateC, btnHapusC, btnClearC;
    private String idCampaignTerpilih = ""; 

    // Form Input Progress Creator (Kanan)
    private JComboBox<String> cbKOL, cbPackage;
    private JTextField txtLinkKonten, txtBiayaAktual;
    private JButton btnSimpanP, btnUpdateP, btnHapusP, btnClearP, btnKelolaKOL;
    private String idProgressTerpilih = ""; 

    public MenuUtama() {
        setTitle("Dashboard Monitoring Campaign & Creator - PT RKP");
        setSize(1350, 820);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 1. HEADER
        JLabel lblJudul = new JLabel("Dashboard Monitoring Campaign & Creator - PT RKP", JLabel.CENTER);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 20));
        lblJudul.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        add(lblJudul, BorderLayout.NORTH);

        // 2. CENTER PANEL (Dua Tabel Vertikal)
        JPanel panelTengah = new JPanel(new GridLayout(2, 1, 10, 10));
        panelTengah.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        // Tabel Master Campaign
        modelCampaign = new DefaultTableModel(new String[]{"ID Campaign", "Nama Campaign", "Brand", "Total Budget", "Sisa Budget", "Status"}, 0);
        tabelCampaign = new JTable(modelCampaign);
        JPanel pC = new JPanel(new BorderLayout());
        pC.add(new JLabel("■ Daftar Master Campaign (Klik untuk memfilter list creator)", JLabel.LEFT), BorderLayout.NORTH);
        pC.add(new JScrollPane(tabelCampaign), BorderLayout.CENTER);
        panelTengah.add(pC);

        // Tabel Progress Creator
        modelProgress = new DefaultTableModel(new String[]{"ID Progress", "Nama Creator", "Platform", "Package/Progress", "Link Konten/Video", "Biaya Aktual"}, 0);
        tabelProgressKOL = new JTable(modelProgress);
        JPanel pP = new JPanel(new BorderLayout());
        pP.add(new JLabel("■ Monitor Progress Kerja & Link Konten Creator pada Campaign Aktif", JLabel.LEFT), BorderLayout.NORTH);
        pP.add(new JScrollPane(tabelProgressKOL), BorderLayout.CENTER);
        panelTengah.add(pP);

        add(panelTengah, BorderLayout.CENTER);

        // 3. SOUTH PANEL (Dual Form CRUD Berdampingan)
        JPanel panelBawah = new JPanel(new GridLayout(1, 2, 20, 10));
        panelBawah.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        // --- FORM CAMPAIGN (KIRI) ---
        JPanel formCampaign = new JPanel(new BorderLayout(5, 5));
        formCampaign.setBorder(BorderFactory.createTitledBorder("Kelola Master Campaign"));
        JPanel inC = new JPanel(new GridLayout(5, 2, 5, 5));
        inC.add(new JLabel("Nama Campaign:")); txtNamaCampaign = new JTextField(); inC.add(txtNamaCampaign);
        inC.add(new JLabel("Brand:")); txtBrand = new JTextField(); inC.add(txtBrand);
        inC.add(new JLabel("Total Budget:")); txtTotalBudget = new JTextField(); inC.add(txtTotalBudget);
        inC.add(new JLabel("Sisa Budget:")); txtSisaBudget = new JTextField(); inC.add(txtSisaBudget);
        inC.add(new JLabel("Status:")); cbStatusCampaign = new JComboBox<>(new String[]{"Planning", "Running", "Closed"}); inC.add(cbStatusCampaign);
        
        JPanel btnPanelC = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        btnSimpanC = new JButton("Simpan Campaign"); btnUpdateC = new JButton("Update Campaign"); btnHapusC = new JButton("Hapus Campaign"); btnClearC = new JButton("Clear");
        btnPanelC.add(btnSimpanC); btnPanelC.add(btnUpdateC); btnPanelC.add(btnHapusC); btnPanelC.add(btnClearC);
        formCampaign.add(inC, BorderLayout.CENTER); formCampaign.add(btnPanelC, BorderLayout.SOUTH);
        panelBawah.add(formCampaign);

        // --- FORM PROGRESS CREATOR (KANAN) ---
        JPanel formProgress = new JPanel(new BorderLayout(5, 5));
        formProgress.setBorder(BorderFactory.createTitledBorder("Kelola Progress & Hasil Kerja Creator"));
        JPanel inP = new JPanel(new GridLayout(4, 2, 5, 5));
        inP.add(new JLabel("Pilih Creator/KOL:")); cbKOL = new JComboBox<>(); inP.add(cbKOL);
        inP.add(new JLabel("Package Konten:")); cbPackage = new JComboBox<>(new String[]{"Top Creator Live Stream 30 Hari", "2 Shopee Video", "1 Video TikTok", "Special Campaign (Flat 40rb)"}); inP.add(cbPackage);
        inP.add(new JLabel("Link Konten / Bukti:")); txtLinkKonten = new JTextField(); inP.add(txtLinkKonten);
        inP.add(new JLabel("Biaya Aktual:")); txtBiayaAktual = new JTextField(); inP.add(txtBiayaAktual);
        
        JPanel btnPanelP = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        btnSimpanP = new JButton("Simpan Progress"); btnUpdateP = new JButton("Update Progress"); btnHapusP = new JButton("Hapus Progress");
        btnKelolaKOL = new JButton("⚙ Kelola KOL"); btnClearP = new JButton("Clear");
        btnPanelP.add(btnSimpanP); btnPanelP.add(btnUpdateP); btnPanelP.add(btnHapusP); btnPanelP.add(btnKelolaKOL); btnPanelP.add(btnClearP);
        formProgress.add(inP, BorderLayout.CENTER); formProgress.add(btnPanelP, BorderLayout.SOUTH);
        panelBawah.add(formProgress);

        add(panelBawah, BorderLayout.SOUTH);

        // Inisialisasi Event Listener
        initActionEvents();
        
        // Load Data Awal
        loadDataCampaign();
        isiComboKOL();
    }

    private void initActionEvents() {
        // CRUD Campaign
        btnSimpanC.addActionListener(e -> aksiSimpanC());
        btnUpdateC.addActionListener(e -> aksiUpdateC());
        btnHapusC.addActionListener(e -> aksiHapusC());
        btnClearC.addActionListener(e -> clearFormC());

        // CRUD Progress
        btnSimpanP.addActionListener(e -> aksiSimpanP());
        btnUpdateP.addActionListener(e -> aksiUpdateP());
        btnHapusP.addActionListener(e -> aksiHapusP());
        btnClearP.addActionListener(e -> clearFormP());

        // Aksi Tombol Kelola KOL (Ikat Kuat)
        btnKelolaKOL.addActionListener(e -> {
            try {
                FormCreator formKOL = new FormCreator(); 
                formKOL.pack(); 
                formKOL.setLocationRelativeTo(this); 
                formKOL.setVisible(true);
                formKOL.toFront(); 
                
                formKOL.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        isiComboKOL(); 
                    }
                });
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal membuka Form Pendaftaran KOL: " + ex.getMessage());
            }
        });

        // Klik Tabel Campaign
        tabelCampaign.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tabelCampaign.getSelectedRow();
                if (baris != -1) {
                    idCampaignTerpilih = modelCampaign.getValueAt(baris, 0).toString();
                    txtNamaCampaign.setText(modelCampaign.getValueAt(baris, 1).toString());
                    txtBrand.setText(modelCampaign.getValueAt(baris, 2).toString());
                    txtTotalBudget.setText(modelCampaign.getValueAt(baris, 3).toString());
                    txtSisaBudget.setText(modelCampaign.getValueAt(baris, 4).toString());
                    cbStatusCampaign.setSelectedItem(modelCampaign.getValueAt(baris, 5).toString());
                    loadProgressCreator(idCampaignTerpilih);
                }
            }
        });

        // Klik Tabel Progress
        tabelProgressKOL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tabelProgressKOL.getSelectedRow();
                if (baris != -1) {
                    idProgressTerpilih = modelProgress.getValueAt(baris, 0).toString();
                    cbKOL.setSelectedItem(modelProgress.getValueAt(baris, 1).toString());
                    cbPackage.setSelectedItem(modelProgress.getValueAt(baris, 3).toString());
                    txtLinkKonten.setText(modelProgress.getValueAt(baris, 4).toString());
                    txtBiayaAktual.setText(modelProgress.getValueAt(baris, 5).toString());
                }
            }
        });
        
        cbPackage.addActionListener(e -> {
            if(cbPackage.getSelectedItem().toString().contains("Special Campaign")) {
                txtBiayaAktual.setText("40000");
            }
        });
    }

    private void loadDataCampaign() {
        modelCampaign.setRowCount(0);
        try (Connection conn = campaignmonitoring.koneksi.koneksiDB();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM tabel_campaign")) {
            while (rs.next()) {
                modelCampaign.addRow(new Object[]{rs.getString("id_campaign"), rs.getString("nama_campaign"), rs.getString("nama_brand"), rs.getString("total_budget"), rs.getString("sisa_budget"), rs.getString("status")});
            }
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    private void loadProgressCreator(String idCampaign) {
        modelProgress.setRowCount(0);
        try (Connection conn = campaignmonitoring.koneksi.koneksiDB();
             PreparedStatement ps = conn.prepareStatement("SELECT p.id_progress, k.nama_kol, k.platform, p.progress_konten, p.link_konten, p.biaya_aktual FROM tabel_progress p JOIN tabel_kol k ON p.id_kol = k.id_kol WHERE p.id_campaign = ?")) {
            ps.setString(1, idCampaign);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    modelProgress.addRow(new Object[]{rs.getString("id_progress"), rs.getString("nama_kol"), rs.getString("platform"), rs.getString("progress_konten"), rs.getString("link_konten"), rs.getString("biaya_aktual")});
                }
            }
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    public void isiComboKOL() {
        cbKOL.removeAllItems();
        try (Connection conn = campaignmonitoring.koneksi.koneksiDB();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT nama_kol FROM tabel_kol")) {
            while (rs.next()) { cbKOL.addItem(rs.getString("nama_kol")); }
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    private void aksiSimpanP() {
        if (idCampaignTerpilih.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Silakan pilih Master Campaign di tabel atas terlebih dahulu!");
            return;
        }
        if (cbKOL.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Belum ada Creator. Klik 'Kelola KOL' untuk menambahkan kreator!");
            return;
        }
        try (Connection conn = campaignmonitoring.koneksi.koneksiDB()) {
            String idKOL = "1";
            try (PreparedStatement psK = conn.prepareStatement("SELECT id_kol FROM tabel_kol WHERE nama_kol = ?")) {
                psK.setString(1, cbKOL.getSelectedItem().toString());
                ResultSet rsK = psK.executeQuery();
                if(rsK.next()) idKOL = rsK.getString("id_kol");
            }

            String sql = "INSERT INTO tabel_progress (id_campaign, id_kol, tanggal_update, progress_konten, link_konten, biaya_aktual) VALUES (?, ?, NOW(), ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, idCampaignTerpilih);
                ps.setString(2, idKOL);
                ps.setString(3, cbPackage.getSelectedItem().toString());
                ps.setString(4, txtLinkKonten.getText().trim());
                ps.setDouble(5, Double.parseDouble(txtBiayaAktual.getText().trim()));
                ps.executeUpdate();
            }
            JOptionPane.showMessageDialog(this, "Data Progress Kerja Creator Berhasil Ditambahkan!");
            loadProgressCreator(idCampaignTerpilih);
            clearFormP();
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Gagal Simpan Progress: " + e.getMessage()); }
    }

    private void aksiUpdateP() {
        if (idProgressTerpilih.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data progress di tabel bawah terlebih dahulu!");
            return;
        }
        try (Connection conn = campaignmonitoring.koneksi.koneksiDB()) {
            String idKOL = "1";
            try (PreparedStatement psK = conn.prepareStatement("SELECT id_kol FROM tabel_kol WHERE nama_kol = ?")) {
                psK.setString(1, cbKOL.getSelectedItem().toString());
                ResultSet rsK = psK.executeQuery();
                if(rsK.next()) idKOL = rsK.getString("id_kol");
            }
            
            String sql = "UPDATE tabel_progress SET id_kol=?, progress_konten=?, link_konten=?, biaya_aktual=? WHERE id_progress=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, idKOL);
                ps.setString(2, cbPackage.getSelectedItem().toString());
                ps.setString(3, txtLinkKonten.getText().trim());
                ps.setDouble(4, Double.parseDouble(txtBiayaAktual.getText().trim()));
                ps.setString(5, idProgressTerpilih);
                ps.executeUpdate();
            }
            JOptionPane.showMessageDialog(this, "Progress Kerja Creator Berhasil Diperbarui!");
            loadProgressCreator(idCampaignTerpilih);
            clearFormP();
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Gagal Update: " + e.getMessage()); }
    }

    private void aksiHapusP() {
        if (idProgressTerpilih.isEmpty()) return;
        int konf = JOptionPane.showConfirmDialog(this, "Hapus progress data ini?", "Hapus", JOptionPane.YES_NO_OPTION);
        if (konf == JOptionPane.YES_OPTION) {
            try (Connection conn = campaignmonitoring.koneksi.koneksiDB();
                 PreparedStatement ps = conn.prepareStatement("DELETE FROM tabel_progress WHERE id_progress = ?")) {
                ps.setString(1, idProgressTerpilih);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Progress Terhapus!");
                loadProgressCreator(idCampaignTerpilih);
                clearFormP();
            } catch (Exception e) { System.out.println(e.getMessage()); }
        }
    }

    private void aksiSimpanC() {
        try (Connection conn = campaignmonitoring.koneksi.koneksiDB();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO tabel_campaign (nama_campaign, nama_brand, total_budget, sisa_budget, tanggal_mulai, tanggal_selesai, status) VALUES (?, ?, ?, ?, NOW(), NOW(), ?)")) {
            ps.setString(1, txtNamaCampaign.getText().trim()); ps.setString(2, txtBrand.getText().trim());
            ps.setDouble(3, Double.parseDouble(txtTotalBudget.getText().trim())); ps.setDouble(4, Double.parseDouble(txtSisaBudget.getText().trim()));
            ps.setString(5, cbStatusCampaign.getSelectedItem().toString());
            ps.executeUpdate(); loadDataCampaign(); clearFormC();
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    private void aksiUpdateC() {
        if (idCampaignTerpilih.isEmpty()) return;
        try (Connection conn = campaignmonitoring.koneksi.koneksiDB();
             PreparedStatement ps = conn.prepareStatement("UPDATE tabel_campaign SET nama_campaign=?, nama_brand=?, total_budget=?, sisa_budget=?, status=? WHERE id_campaign=?")) {
            ps.setString(1, txtNamaCampaign.getText().trim()); ps.setString(2, txtBrand.getText().trim());
            ps.setDouble(3, Double.parseDouble(txtTotalBudget.getText().trim())); ps.setDouble(4, Double.parseDouble(txtSisaBudget.getText().trim()));
            ps.setString(5, cbStatusCampaign.getSelectedItem().toString()); ps.setString(6, idCampaignTerpilih);
            ps.executeUpdate(); loadDataCampaign(); clearFormC();
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    private void aksiHapusC() {
        if (idCampaignTerpilih.isEmpty()) return;
        try (Connection conn = campaignmonitoring.koneksi.koneksiDB();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM tabel_campaign WHERE id_campaign = ?")) {
            ps.setString(1, idCampaignTerpilih); ps.executeUpdate(); loadDataCampaign(); clearFormC();
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    private void clearFormC() {
        txtNamaCampaign.setText(""); txtBrand.setText(""); txtTotalBudget.setText(""); txtSisaBudget.setText("");
        cbStatusCampaign.setSelectedIndex(0); idCampaignTerpilih = ""; modelProgress.setRowCount(0);
    }

    private void clearFormP() {
        txtLinkKonten.setText(""); txtBiayaAktual.setText(""); cbPackage.setSelectedIndex(0); idProgressTerpilih = "";
    }
}