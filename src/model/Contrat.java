package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import model.enums.TypeContrat;

public class Contrat {

    private int id;
    private TypeContrat typeContrat;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Map<Integer, Sinistre> sinistres;   // key = id du sinistre

    private Client client;

    public Contrat(TypeContrat typeContrat, LocalDate dateDebut, LocalDate dateFin, Client client) {
        this.typeContrat = typeContrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.client = client;
        this.sinistres = new HashMap<>();
    }

    // Getter & Setter
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TypeContrat getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Map<Integer, Sinistre> getSinistres() {
        return sinistres;
    }

    public void setSinistres(Map<Integer, Sinistre> sinistres) {
        this.sinistres = sinistres;
    }

    public void ajouterSinistre(Sinistre sinistre) {
        sinistres.put(sinistre.getId(), sinistre);
    }

    public void supprimerSinistre(int sinistreId) {
        sinistres.remove(sinistreId);
    }

    public Sinistre rechercherSinistre(int sinistreId) {
        return sinistres.get(sinistreId);
    }

    @Override
    public String toString() {
        return " | Type: " + typeContrat +
                " | Début: " + dateDebut + " | Fin: " + dateFin +
                " | Nb sinistres: " + sinistres.size();
    }
}
