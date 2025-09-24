package model;

import java.util.HashMap;
import java.util.Map;

public class Client extends Person {

    private Map<Integer, Contrat> contrats;
    private Conseiller conseiller;
    private int id;

    public Client(int id, String nom, String prenom, String email, Conseiller conseiller) {
        super(nom, prenom, email);
        this.conseiller = conseiller;
        this.contrats = new HashMap<>();
        this.id = id;
    }

    public Map<Integer, Contrat> getContrats() {
        return contrats;
    }

    public int getId(){
        return id;
    }

    public void setId(int newId){
        this.id = newId;
    }

    public void setContrats(Map<Integer, Contrat> contrats) {
        this.contrats = contrats;
    }

    public Conseiller getConseiller() {
        return conseiller;
    }

    public void setConseiller(Conseiller conseiller) {
        this.conseiller = conseiller;
    }

    public void ajouterContrat(Contrat contrat) {
        contrats.put(contrat.getId(), contrat);
    }

    public void supprimerContrat(int contratId) {
        contrats.remove(contratId);
    }

    public Contrat rechercherContrat(int contratId) {
        return contrats.get(contratId);
    }

    @Override
    public String toString() {
        return super.toString() + " | Nb contrats: " + contrats.size() +
                " | Conseiller: " + (conseiller != null ? conseiller.getNom() : "Aucun");
    }
}
