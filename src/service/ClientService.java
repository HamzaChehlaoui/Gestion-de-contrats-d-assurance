package service;

import dao.ClientDAO;
import model.Client;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ClientService {
    private ClientDAO clientDAO;

    public ClientService() throws SQLException {
        this.clientDAO = new ClientDAO();
    }

    public List<Client> getAllClients() throws SQLException {
        return clientDAO.getAll();
    }

    public void ajouterClient(Client c) throws SQLException {
        clientDAO.create(c);
    }

    public void supprimerClient(int id) throws SQLException {
        clientDAO.delete(id);
    }

    public Optional<Client> chercherParId(int id) throws SQLException {
        return clientDAO.read(id);
    }

    public List<Client> chercherParNom(String nom) throws SQLException {
        return clientDAO.getAll()
                .stream()
                .filter(c -> c.getNom().equalsIgnoreCase(nom))
                .sorted(Comparator.comparing(Client::getNom))
                .collect(Collectors.toList());
    }

    public List<Client> clientsParConseiller(int conseillerId) throws SQLException {
        return clientDAO.getAll()
                .stream()
                .filter(c -> c.getConseiller().getId() == conseillerId)
                .collect(Collectors.toList());
    }
}
