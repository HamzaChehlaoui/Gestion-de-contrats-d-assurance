package view;

import model.Client;
import model.Conseiller;
import service.ClientService;
import service.ConseillerService;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientView {

    private final ClientService clientService;
    private final ConseillerService conseillerService;
    private final Scanner scanner;

    public ClientView() throws SQLException {
        this.clientService = new ClientService();
        this.conseillerService = new ConseillerService();
        this.scanner = new Scanner(System.in);
    }

    public void menu() throws SQLException {
        int choix;
        do {
            System.out.println("\n=== Gestion des Clients ===");
            System.out.println("1. Ajouter un client");
            System.out.println("2. Supprimer un client");
            System.out.println("3. Rechercher par ID");
            System.out.println("4. Rechercher par Nom");
            System.out.println("5. Afficher les clients d'un conseiller");
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
                    ajouterClient();
                    break;
                case 2:
                    supprimerClient();
                    break;
                case 3:
                    rechercherParId();
                    break;
                case 4:
                    rechercherParNom();
                    break;
                case 5:
                    afficherClientsParConseiller();
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
                scanner.nextLine(); // vider le buffer
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

    private void ajouterClient() throws SQLException {

        List<Conseiller> conseillers = conseillerService.getAllConseillers();
        System.out.println("Liste des conseillers :");
        for (Conseiller c : conseillers) {
            System.out.println(c.getId() + " - " + c.getNom() + " " + c.getPrenom());
        }

        String nom = lireTexte("Nom: ");
        String prenom = lireTexte("Prénom: ");
        String email = lireTexte("Email: ");
        int conseillerId = lireEntier("ID du conseiller (0 si aucun): ");

        Conseiller conseiller = null;
        if (conseillerId != 0) {
            conseiller = conseillerService.chercherParId(conseillerId).orElse(null);
        }

        Client c = new Client(0, nom, prenom, email, conseiller);
        clientService.ajouterClient(c);
        System.out.println("Client ajouté avec succès !");
    }

    private void supprimerClient() throws SQLException {
        int id = lireEntier("ID du client à supprimer: ");
        boolean supprime = clientService.supprimerClient(id);
        if (supprime) {
            System.out.println("Client supprimé !");
        } else {
            System.out.println("Aucun client trouvé avec l'ID " + id);
        }
    }


    private void rechercherParId() throws SQLException {
        int id = lireEntier("ID du client: ");
        Optional<Client> client = clientService.chercherParId(id);

        if (client.isPresent()) {
            System.out.println(client.get());
        } else {
            System.out.println("Client non trouvé.");
        }
    }

    private void rechercherParNom() throws SQLException {
        String nom = lireTexte("Nom du client: ");
        List<Client> clients = clientService.chercherParNom(nom);
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé avec ce nom.");
        } else {
            clients.forEach(System.out::println);
        }
    }

    private void afficherClientsParConseiller() throws SQLException {
        int id = lireEntier("ID du conseiller: ");
        List<Client> clients = clientService.clientsParConseiller(id);
        if (clients.isEmpty()) {
            System.out.println("Aucun client pour ce conseiller.");
        } else {
            clients.forEach(System.out::println);
        }
    }
}
