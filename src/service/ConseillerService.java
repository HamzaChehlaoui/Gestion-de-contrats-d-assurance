package service;

import dao.ConseillerDAO;
import dao.ClientDAO;
import model.Conseiller;
import model.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConseillerService {
    private final ConseillerDAO conseillerDAO;
    private final ClientDAO clientDAO;

    public ConseillerService() throws SQLException {
        this.conseillerDAO = new ConseillerDAO();
        this.clientDAO = new ClientDAO();
    }

    public void ajouterConseiller(Conseiller c) throws SQLException {
        conseillerDAO.create(c);
    }

    public List<Conseiller> getAllConseillers() throws SQLException {
        return conseillerDAO.getAll();
    }


    public boolean supprimerConseiller(int id) throws SQLException {
        return conseillerDAO.delete(id);
    }

    public Optional<Conseiller> chercherParId(int id) throws SQLException {
        return conseillerDAO.read(id);
    }

    public List<Client> clientsParConseiller(int conseillerId) throws SQLException {
        return clientDAO.getAll()
                .stream()
                .filter(c -> c.getConseiller() != null && c.getConseiller().getId() == conseillerId)
                .collect(Collectors.toList());
    }
}
