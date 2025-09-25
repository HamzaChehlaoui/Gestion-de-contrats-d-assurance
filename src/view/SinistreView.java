package view;

import model.Contrat;
import model.Sinistre;
import model.enums.TypeSinistre;
import service.ContratService;
import service.SinistreService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SinistreView {

    private SinistreService sinistreService;
    private ContratService contratService;
    private Scanner scanner;

    public SinistreView() throws SQLException {
        this.sinistreService = new SinistreService();
        this.contratService = new ContratService();
        this.scanner = new Scanner(System.in);
    }

    public void menu() throws SQLException {
        int choix;
        do {
            System.out.println("\n=== Gestion des Sinistres ===");
            System.out.println("1. Ajouter un sinistre");
            System.out.println("2. Supprimer un sinistre");
            System.out.println("3. Rechercher un sinistre par ID");
            System.out.println("4. Afficher les sinistres par contrat");
            System.out.println("5. Afficher les sinistres par client");
            System.out.println("0. Retour");
            System.out.print("Choix: ");
            choix = scanner.nextInt();
            scanner.nextLine(); // Consommer le retour à la ligne

            switch (choix) {
                case 1 :
                    ajouterSinistre();
                    break;
                case 2 :
                    supprimerSinistre();
                    break;
                case 3 :
                    rechercherParId();
                    break;
                case 4 :
                    afficherParContrat();
                    break;
                case 5 :
                    afficherParClient();
                    break;
            }
        } while (choix != 0);
    }

    private void ajouterSinistre() throws SQLException {
        List<Contrat> contrats = contratService.getAllContrats();
        System.out.println("Liste des contrats :");
        for (Contrat c : contrats) {
            System.out.println(c.getId() + " - " + c.getTypeContrat() + " | Client: " + c.getClient().getNom());
        }

        System.out.print("ID du contrat pour ce sinistre: ");
        int contratId = scanner.nextInt();
        scanner.nextLine();

        Contrat contrat = contratService.chercherParId(contratId).orElse(null);
        if (contrat == null) {
            System.out.println("Contrat introuvable !");
            return;
        }

        System.out.println("Types de sinistre disponibles : ACCIDENT_VOITURE, ACCIDENT_MAISON, MALADIE");
        System.out.print("Type: ");
        String typeStr = scanner.nextLine().toUpperCase();
        TypeSinistre type;
        try {
            type = TypeSinistre.valueOf(typeStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Type de sinistre invalide !");
            return;
        }

        System.out.print("Date début (YYYY-MM-DD): ");
        LocalDate dateDebut = LocalDate.parse(scanner.nextLine());

        System.out.print("Date fin (YYYY-MM-DD): ");
        LocalDate dateFin = LocalDate.parse(scanner.nextLine());

        System.out.print("Montant: ");
        double montant = Double.parseDouble(scanner.nextLine());

        System.out.print("Description: ");
        String description = scanner.nextLine();

        Sinistre sinistre = new Sinistre(0, type, dateDebut, dateFin, montant, description, contrat);
        sinistreService.ajouterSinistre(sinistre);
        System.out.println("Sinistre ajouté ");
    }

    private void supprimerSinistre() throws SQLException {
        System.out.print("ID du sinistre: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        sinistreService.supprimerSinistre(id);
        System.out.println("Sinistre supprimé ");
    }

    private void rechercherParId() throws SQLException {
        System.out.print("ID du sinistre: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Optional<Sinistre> sinistre = sinistreService.chercherParId(id);
        if (sinistre.isPresent()) {
            System.out.println(sinistre.get());
        } else {
            System.out.println("Sinistre non trouvé.");
        }
    }

    private void afficherParContrat() throws SQLException {
        System.out.print("ID du contrat: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        List<Sinistre> sinistres = sinistreService.sinistresParContrat(id);
        if (sinistres.isEmpty()) {
            System.out.println("Aucun sinistre pour ce contrat.");
        } else {
            sinistres.forEach(System.out::println);
        }
    }

    private void afficherParClient() throws SQLException {
        System.out.print("ID du client: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        List<Sinistre> sinistres = sinistreService.sinistresParClient(id);
        if (sinistres.isEmpty()) {
            System.out.println("Aucun sinistre pour ce client.");
        } else {
            sinistres.forEach(System.out::println);
        }
    }
}
