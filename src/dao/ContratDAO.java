package dao;

import model.Client;
import model.Contrat;
import model.enums.TypeContrat;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class ContratDAO {


    private Connection conn;
    private final ClientDAO clientDAO = new ClientDAO();

    public ContratDAO() throws SQLException {
        this.conn = DBConnectionSingleton.getInstance().getConnection();
    }

    public void create(Contrat contrat) throws SQLException {
        String sql = "INSERT INTO contrat ( type_contrat, date_debut, date_fin, client_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contrat.getTypeContrat().name());
            stmt.setDate(2, Date.valueOf(contrat.getDateDebut()));
            stmt.setDate(3, Date.valueOf(contrat.getDateFin()));
            stmt.setInt(4, contrat.getClient() != null ? contrat.getClient().getId() : 0);
            stmt.executeUpdate();
        }
    }

    public Optional<Contrat> read(int id) throws SQLException {
        String sql = "SELECT * FROM contrat WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int contratId = rs.getInt("id");
                TypeContrat typeContrat = TypeContrat.valueOf(rs.getString("type_contrat"));
                LocalDate dateDebut = rs.getDate("date_debut").toLocalDate();
                LocalDate dateFin = rs.getDate("date_fin").toLocalDate();

                int clientId = rs.getInt("client_id");
                Client client = clientDAO.read(clientId).orElse(null);

                Contrat contrat = new Contrat(typeContrat, dateDebut, dateFin, client);
                contrat.setId(contratId);

                return Optional.of(contrat);
            }
        }
        return Optional.empty();
    }


    public void update(Contrat contrat) throws SQLException {
        String sql = "UPDATE contrat SET type_contrat = ?, date_debut = ?, date_fin = ?, client_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contrat.getTypeContrat().name());
            stmt.setDate(2, Date.valueOf(contrat.getDateDebut()));
            stmt.setDate(3, Date.valueOf(contrat.getDateFin()));
            stmt.setInt(4, contrat.getClient() != null ? contrat.getClient().getId() : 0);
            stmt.setInt(5, contrat.getId());
            stmt.executeUpdate();
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM contrat WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public List<Contrat> getAll() throws SQLException {
        List<Contrat> contrats = new ArrayList<>();
        String sql = "SELECT * FROM contrat";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int clientId = rs.getInt("client_id");
                Client client = null;
                if (clientId != 0) {
                    client = clientDAO.read(clientId).orElse(null);
                }

                Contrat contrat = new Contrat(
                        TypeContrat.valueOf(rs.getString("type_contrat")),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate(),
                        client
                );
                contrat.setId(rs.getInt("id"));
                contrats.add(contrat);
            }
        }
        return contrats;
    }

}
