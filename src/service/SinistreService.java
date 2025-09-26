package service;

import dao.SinistreDAO;
import model.Sinistre;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Comparator;
import java.util.stream.Collectors;

public class SinistreService {
    private SinistreDAO sinistreDAO;

    public SinistreService() throws SQLException {
        this.sinistreDAO = new SinistreDAO();
    }

    public void ajouterSinistre(Sinistre s) throws SQLException {
        sinistreDAO.create(s);
    }

    public void supprimerSinistre(int id) throws SQLException {
        sinistreDAO.delete(id);
    }

    public Optional<Sinistre> chercherParId(int id) throws SQLException {
        return sinistreDAO.read(id);
    }

    public double coutTotalParClient(int clientId) throws SQLException {
        return sinistreDAO.getAll()
                .stream()
                .filter(s -> s.getContrat().getClient().getId() == clientId)
                .mapToDouble(Sinistre::getMontant)
                .sum();
    }

    public List<Sinistre> sinistresParContrat(int contratId) throws SQLException {
        return sinistreDAO.getAll()
                .stream()
                .filter(s -> s.getContrat() != null && s.getContrat().getId() == contratId)
                .collect(Collectors.toList());
    }

    public List<Sinistre> sinistresTriesParMontant() throws SQLException {
        return sinistreDAO.getAll()
                .stream()
                .sorted(Comparator.comparing(Sinistre::getMontant).reversed())
                .collect(Collectors.toList());
    }

    public List<Sinistre> sinistresParClient(int clientId) throws SQLException {
        return sinistreDAO.getAll()
                .stream()
                .filter(s -> s.getContrat() !=null && s.getContrat().getClient().getId() == clientId)
                .collect(Collectors.toList());
    }

    public List<Sinistre> sinistresAvantDate(LocalDate date) throws SQLException {
        return sinistreDAO.getAll()
                .stream()
                .filter(s -> s.getDateDebut().isBefore(date))
                .collect(Collectors.toList());
    }

    public List<Sinistre> sinistresCoutSuperieur(double montant) throws SQLException {
        return sinistreDAO.getAll()
                .stream()
                .filter(s -> s.getMontant() > montant)
                .collect(Collectors.toList());
    }
}
