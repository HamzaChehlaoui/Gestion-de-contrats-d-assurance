package dao;

import model.Conseiller;
import java.sql.*;
import java.util.*;

public class ConseillerDAO {
    private Connection conn;

    public ConseillerDAO() throws SQLException {
        this.conn = DBConnectionSingleton.getInstance().getConnection();
    }

    // Ajouter
    public void create(Conseiller conseiller) throws SQLException {
        String sql = "INSERT INTO conseiller (nom, prenom, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, conseiller.getNom());
            stmt.setString(2, conseiller.getPrenom());
            stmt.setString(3, conseiller.getEmail());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                conseiller.setId(rs.getInt(1));
            }
        }
    }

    public Optional<Conseiller> read(int id) throws SQLException {
        String sql = "SELECT * FROM conseiller WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Conseiller c = new Conseiller(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email")
                );
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }



    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM conseiller WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public List<Conseiller> getAll() throws SQLException {
        List<Conseiller> conseillers = new ArrayList<>();
        String sql = "SELECT * FROM conseiller";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Conseiller c = new Conseiller(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email")
                );
                conseillers.add(c);
            }
        }
        return conseillers;
    }
}
