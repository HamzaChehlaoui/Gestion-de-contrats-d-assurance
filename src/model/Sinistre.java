package model;

import java.time.LocalDate;
import model.enums.TypeSinistre;

public class Sinistre {

    private int id;
    private TypeSinistre typeSinistre;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double montant;
    private String description;
    private Contrat contrat;

    public Sinistre(int id, TypeSinistre typeSinistre,
                    LocalDate dateDebut, LocalDate dateFin,
                    double montant, String description,
                    Contrat contrat) {
        this.id = id;
        this.typeSinistre = typeSinistre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.montant = montant;
        this.description = description;
        this.contrat = contrat;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public TypeSinistre getTypeSinistre() { return typeSinistre; }
    public void setTypeSinistre(TypeSinistre typeSinistre) { this.typeSinistre = typeSinistre; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Contrat getContrat() { return contrat; }
    public void setContrat(Contrat contrat) { this.contrat = contrat; }

    @Override
    public String toString() {
        return "Sinistre #" + id +
                " | Type: " + typeSinistre +
                " | Début: " + dateDebut +
                " | Fin: " + dateFin +
                " | Montant: " + montant +
                " | Desc: " + description +
                " | ContratID: " + (contrat != null ? contrat.getId() : "null");
    }
}
