package view;

import model.Conseiller;
import model.Client;
import service.ConseillerService;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConseillerView {

    private final ConseillerService conseillerService;
    private final Scanner scanner;

    public ConseillerView() throws SQLException {
        this.conseillerService = new ConseillerService();
        this.scanner = new Scanner(System.in);
    }

    public void menu() throws SQLException {
        int choix;
        do {
            System.out.println("\n--- Menu Conseillers ---");
            System.out.println("1. Ajouter un conseiller");
            System.out.println("2. Supprimer un conseiller");
            System.out.println("3. Rechercher par ID");
            System.out.println("4. Lister tous les conseillers");
            System.out.println("5. Lister les clients d’un conseiller");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");

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
                    ajouter();
                    break;
                case 2:
                    supprimer();
                    break;
                case 3:
                    rechercherParId();
                    break;
                case 4:
                    listerTous();
                    break;
                case 5:
                    clientsParConseiller();
                    break;
                case 0:
                    System.out.println("Retour au menu principal...");
                    break;
                default:
                    System.out.println("Choix invalide, réessayez !");
            }
        } while (choix != 0);
    }


    private int lireEntier(String message) {
        while (true) {
            try {
                System.out.print(message);
                int valeur = scanner.nextInt();
                scanner.nextLine();
                return valeur;
            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un nombre valide !");
                scanner.nextLine();
            }
        }
    }

    private String lireTexte(String message) {
        String input;
        do {
            System.out.print(message);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Ce champ ne peut pas être vide !");
            }
        } while (input.isEmpty());
        return input;
    }

    // ===================== MÉTHODES MÉTIER =====================

    private void ajouter() throws SQLException {
        String nom = lireTexte("Nom: ");
        String prenom = lireTexte("Prénom: ");
        String email = lireTexte("Email: ");

        Conseiller c = new Conseiller(0, nom, prenom, email); // id auto-incrément
        conseillerService.ajouterConseiller(c);
        System.out.println("Conseiller ajouté avec succès !");
    }

    private void supprimer() throws SQLException {
        int id = lireEntier("ID du consieller à supprimer: ");
        boolean supprime = conseillerService.supprimerConseiller(id);
        if (supprime) {
            System.out.println("Conseiller supprimé !");
        } else {
            System.out.println("Aucun conseiller trouvé avec l'ID " + id);
        }
    }

    private void rechercherParId() throws SQLException {
        int id = lireEntier("ID du conseiller: ");
        Optional<Conseiller> conseiller = conseillerService.chercherParId(id);
        if (conseiller.isPresent()) {
            System.out.println(conseiller.get());
        } else {
            System.out.println("Conseiller non trouvé.");
        }
    }

    private void listerTous() throws SQLException {
        List<Conseiller> conseillers = conseillerService.getAllConseillers();
        if (conseillers.isEmpty()) {
            System.out.println("Aucun conseiller trouvé.");
        } else {
            System.out.printf("%-5s %-15s %-15s %-25s%n", "ID", "Nom", "Prénom", "Email");
            conseillers.forEach(c ->
                    System.out.printf("%-5d %-15s %-15s %-25s%n",
                            c.getId(), c.getNom(), c.getPrenom(), c.getEmail())
            );
        }
    }

    private void clientsParConseiller() throws SQLException {
        int id = lireEntier("ID du conseiller: ");
        List<Client> clients = conseillerService.clientsParConseiller(id);
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé pour ce conseiller.");
        } else {
            clients.forEach(System.out::println);
        }
    }
}
