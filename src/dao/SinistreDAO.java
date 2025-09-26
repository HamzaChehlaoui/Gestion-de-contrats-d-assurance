package dao;

import model.Sinistre;
import model.Contrat;
import model.enums.TypeSinistre;

import java.sql.*;
import java.util.*;
import java.sql.Date;

public class SinistreDAO {

    private Connection conn;

    public SinistreDAO() throws SQLException {
        this.conn = DBConnectionSingleton.getInstance().getConnection();
    }

    // إنشاء Sinistre
    public void create(Sinistre sinistre) throws SQLException {
        if (sinistre.getContrat() == null || sinistre.getContrat().getId() == 0) {
            throw new IllegalArgumentException("Un sinistre doit toujours être lié à un contrat valide.");
        }

        String sql = "INSERT INTO sinistre (type_sinistre, date_debut, date_fin, montant, description, contrat_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, sinistre.getTypeSinistre().name());
            stmt.setDate(2, Date.valueOf(sinistre.getDateDebut()));
            stmt.setDate(3, sinistre.getDateFin() != null ? Date.valueOf(sinistre.getDateFin()) : null);
            stmt.setDouble(4, sinistre.getMontant());
            stmt.setString(5, sinistre.getDescription());
            stmt.setInt(6, sinistre.getContrat().getId());

            stmt.executeUpdate();

            // جلب ID اللي تولد تلقائياً
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    sinistre.setId(rs.getInt(1));
                }
            }
        }
    }

    // قراءة Sinistre بواسطة ID
    public Optional<Sinistre> read(int id) throws SQLException {
        String sql = "SELECT * FROM sinistre WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int contratId = rs.getInt("contrat_id");
                Contrat contrat = null;
                if (contratId > 0) {
                    contrat = new ContratDAO().read(contratId).orElse(null);
                }

                Sinistre s = new Sinistre(
                        rs.getInt("id"),
                        TypeSinistre.valueOf(rs.getString("type_sinistre")),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin") != null ? rs.getDate("date_fin").toLocalDate() : null,
                        rs.getDouble("montant"),
                        rs.getString("description"),
                        contrat
                );
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    // تعديل Sinistre
    public void update(Sinistre sinistre) throws SQLException {
        if (sinistre.getContrat() == null || sinistre.getContrat().getId() == 0) {
            throw new IllegalArgumentException("Un sinistre doit toujours être lié à un contrat valide.");
        }

        String sql = "UPDATE sinistre SET type_sinistre = ?, date_debut = ?, date_fin = ?, montant = ?, description = ?, contrat_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sinistre.getTypeSinistre().name());
            stmt.setDate(2, Date.valueOf(sinistre.getDateDebut()));
            stmt.setDate(3, sinistre.getDateFin() != null ? Date.valueOf(sinistre.getDateFin()) : null);
            stmt.setDouble(4, sinistre.getMontant());
            stmt.setString(5, sinistre.getDescription());
            stmt.setInt(6, sinistre.getContrat().getId());
            stmt.setInt(7, sinistre.getId());
            stmt.executeUpdate();
        }
    }

    // حذف Sinistre
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM sinistre WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // جلب جميع Sinistres مع الربط بالعقود
    public List<Sinistre> getAll() throws SQLException {
        List<Sinistre> sinistres = new ArrayList<>();
        String sql = "SELECT * FROM sinistre";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ContratDAO contratDAO = new ContratDAO(); // reuse DAO once

            while (rs.next()) {
                int contratId = rs.getInt("contrat_id");
                Contrat contrat = null;
                if (contratId > 0) {
                    contrat = contratDAO.read(contratId).orElse(null);
                }

                Sinistre s = new Sinistre(
                        rs.getInt("id"),
                        TypeSinistre.valueOf(rs.getString("type_sinistre")),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin") != null ? rs.getDate("date_fin").toLocalDate() : null,
                        rs.getDouble("montant"),
                        rs.getString("description"),
                        contrat
                );
                sinistres.add(s);
            }
        }
        return sinistres;
    }
}
