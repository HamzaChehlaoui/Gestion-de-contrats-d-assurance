package dao;

import model.Sinistre;
import model.Contrat;
import model.enums.TypeSinistre;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class SinistreDAO {

    private Connection conn;

    public SinistreDAO() throws SQLException {
        this.conn = DBConnectionSingleton.getInstance().getConnection();
    }

    public void create(Sinistre sinistre) throws SQLException {
        String sql = "INSERT INTO sinistre (type_sinistre, date_debut, date_fin, montant, description, contrat_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sinistre.getTypeSinistre().name());
            stmt.setDate(2, Date.valueOf(sinistre.getDateDebut()));
            stmt.setDate(3, Date.valueOf(sinistre.getDateFin()));
            stmt.setDouble(4, sinistre.getMontant());
            stmt.setString(5, sinistre.getDescription());
            stmt.setInt(6, sinistre.getContrat() != null ? sinistre.getContrat().getId() : 0);
            stmt.executeUpdate();
        }

    }

    public Optional<Sinistre> read(int id) throws SQLException {
        String sql = "SELECT * FROM sinistre WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Sinistre s = new Sinistre(
                        rs.getInt("id"),
                        TypeSinistre.valueOf(rs.getString("type_sinistre")),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate(),
                        rs.getDouble("montant"),
                        rs.getString("description"),
                        null
                );
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    public void update(Sinistre sinistre) throws SQLException {
        String sql = "UPDATE sinistre SET type_sinistre = ?, date_debut = ?, date_fin = ?, montant = ?, description = ?, contrat_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sinistre.getTypeSinistre().name());
            stmt.setDate(2, Date.valueOf(sinistre.getDateDebut()));
            stmt.setDate(3, Date.valueOf(sinistre.getDateFin()));
            stmt.setDouble(4, sinistre.getMontant());
            stmt.setString(5, sinistre.getDescription());
            stmt.setInt(6, sinistre.getContrat() != null ? sinistre.getContrat().getId() : 0);
            stmt.setInt(7, sinistre.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM sinistre WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Sinistre> getAll() throws SQLException {
        List<Sinistre> sinistres = new ArrayList<>();
        String sql = "SELECT * FROM sinistre";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Sinistre s = new Sinistre(
                        rs.getInt("id"),
                        TypeSinistre.valueOf(rs.getString("type_sinistre")),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate(),
                        rs.getDouble("montant"),
                        rs.getString("description"),
                        null
                );
                sinistres.add(s);
            }
        }
        return sinistres;
    }
}
