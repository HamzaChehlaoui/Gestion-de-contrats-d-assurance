package view;

import model.Contrat;
import model.Sinistre;
import model.enums.TypeSinistre;
import service.ContratService;
import service.SinistreService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
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
            try {
                choix = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un nombre valide !");
                scanner.nextLine();
                choix = -1;
            }
            switch (choix) {
                case 1:
                    ajouterSinistre();
                    break;
                case 2:
                    supprimerSinistre();
                    break;
                case 3:
                    rechercherParId();
                    break;
                case 4:
                    afficherParContrat();
                    break;
                case 5:
                    afficherParClient();
                    break;
                case 0:
                    System.out.println("Retour au menu principal...");
                    break;
                default:
                    System.out.println("Choix invalide, réessayez !");
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
        String saisie = scanner.nextLine();

        int contratId;
        try {
            contratId = Integer.parseInt(saisie);
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer un nombre valide (ID du contrat) !");
            return;
        }


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

        LocalDate dateDebut = saisirDate("Date début (YYYY-MM-DD): ");
        LocalDate dateFin = saisirDate("Date fin (YYYY-MM-DD): ");

        if (dateFin.isBefore(dateDebut)) {
            System.out.println("La date de fin doit être après la date de début !");
            return;
        }

        double montant = saisirMontant("Montant: ");

        System.out.print("Description: ");
        String description = scanner.nextLine();

        Sinistre sinistre = new Sinistre(0, type, dateDebut, dateFin, montant, description, contrat);
        sinistreService.ajouterSinistre(sinistre);
        System.out.println("Sinistre ajouté ");
    }
    private int lireEntier(String message) {
        while (true) {
            try {
                System.out.print(message);
                int valeur = scanner.nextInt();
                scanner.nextLine(); // vider le buffer
                return valeur;
            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un nombre valide !");
                scanner.nextLine();
            }
        }
    }
    private void supprimerSinistre() throws SQLException {
        int id = lireEntier("ID du sinistre à supprimer: ");
        boolean supprime = sinistreService.supprimerSinistre(id);
        if (supprime) {
            System.out.println("Sinistre supprimé !");
        } else {
            System.out.println("Aucun sinistre trouvé avec l'ID " + id);
        }
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

    // === Helpers sécurisés ===
    private LocalDate saisirDate(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            try {
                return LocalDate.parse(input);
            } catch (Exception e) {
                System.out.println("Format invalide ! Utilisez le format YYYY-MM-DD.");
            }
        }
    }

    private double saisirMontant(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide !");
            }
        }
    }
}
