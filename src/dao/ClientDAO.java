package dao;

import model.Client;
import model.Conseiller;
import java.util.List;
import java.util.ArrayList;


import java.sql.*;
import java.util.Optional;

public class ClientDAO {

    private Connection conn;

    public ClientDAO() throws SQLException {
        this.conn = DBConnectionSingleton.getInstance().getConnection();
    }

    // Create
    public void create(Client client) throws SQLException {
        String sql = "INSERT INTO client ( nom, prenom, email, conseiller_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getEmail());
            stmt.setInt(4, client.getConseiller() != null ? client.getConseiller().getId() : 0);
            stmt.executeUpdate();
        }
    }

    // Read
    public Optional<Client> read(int id) throws SQLException {
        String sql = "SELECT * FROM client WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Client c = new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        null
                );
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    // Update
    public void update(Client client) throws SQLException {
        String sql = "UPDATE client SET nom = ?, prenom = ?, email = ?, conseiller_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getEmail());
            stmt.setInt(4, client.getConseiller() != null ? client.getConseiller().getId() : 0);
            stmt.setInt(5, client.getId());
            stmt.executeUpdate();
        }
    }

    // Delete
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM client WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Client> getAll() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Client client = new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        null
                );
                clients.add(client);
            }
        }
        return clients;
    }
}
