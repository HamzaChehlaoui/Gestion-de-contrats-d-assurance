package service;

import dao.ClientDAO;
import dao.ContratDAO;
import model.Client;
import model.Contrat;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContratService {
    private ContratDAO contratDAO;
    ClientDAO clientDAO = new ClientDAO();

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


    public boolean supprimerContrat(int id) throws SQLException {
        return contratDAO.delete(id);
    }

    public List<Contrat> contratsParClient(int clientId) throws SQLException {
        List<Contrat> allContrats = contratDAO.getAll();

        Client client = clientDAO.read(clientId).orElse(null);
        if (client == null) {
            return Collections.emptyList();
        }

        return allContrats.stream()
                .filter(ct -> ct.getClient() != null && ct.getClient().getId() == clientId)
                .collect(Collectors.toList());
    }



}
