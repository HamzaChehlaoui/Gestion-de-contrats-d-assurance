package view;

import model.Conseiller;
import model.Client;
import service.ConseillerService;

import java.sql.SQLException;
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
            choix = scanner.nextInt();
            scanner.nextLine();

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

    private void ajouter() throws SQLException {
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("Prénom: ");
        String prenom = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        Conseiller c = new Conseiller(0, nom, prenom , email); // id auto-incrément en DB
        conseillerService.ajouterConseiller(c);
        System.out.println("Conseiller ajouté avec succès !");
    }

    private void supprimer() throws SQLException {
        System.out.print("ID du conseiller à supprimer: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        conseillerService.supprimerConseiller(id);
        System.out.println("Conseiller supprimé !");
    }

    private void rechercherParId() throws SQLException {
        System.out.print("ID du conseiller: ");
        int id = scanner.nextInt();
        scanner.nextLine();

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
            conseillers.forEach(System.out::println);
        }
    }

    private void clientsParConseiller() throws SQLException {
        System.out.print("ID du conseiller: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        List<Client> clients = conseillerService.clientsParConseiller(id);
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé pour ce conseiller.");
        } else {
            clients.forEach(System.out::println);
        }
    }
}
