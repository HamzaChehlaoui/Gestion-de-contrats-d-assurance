package service;

import dao.ContratDAO;
import model.Contrat;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContratService {
    private ContratDAO contratDAO;

    public ContratService() throws SQLException {
        this.contratDAO = new ContratDAO();
    }

    public List<Contrat> getAllContrats() throws SQLException {
        return contratDAO.getAll();
    }

    public void ajouterContrat(Contrat c) throws SQLException {
        contratDAO.create(c);
    }

    public Optional<Contrat> chercherParId(int id) throws SQLException {
        return contratDAO.read(id);
    }

    public void supprimerContrat(int id) throws SQLException {
        contratDAO.delete(id);
    }

    public List<Contrat> contratsParClient(int clientId) throws SQLException {
        return contratDAO.getAll()
                .stream()
                .filter(ct -> ct.getClient().getId() == clientId)
                .collect(Collectors.toList());
    }
}
