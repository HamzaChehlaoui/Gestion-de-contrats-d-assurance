package view;

import model.Client;
import model.Conseiller;
import service.ClientService;
import service.ConseillerService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientView {

    private ClientService clientService;
    private ConseillerService conseillerService;
    private Scanner scanner;

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
            choix = scanner.nextInt();
            scanner.nextLine(); // Consommer le retour à la ligne

            switch (choix) {
                case 1 :
                    ajouterClient();
                    break ;
                case 2 :
                    supprimerClient();
                    break;
                case 3 :
                    rechercherParId();
                    break;
                case 4 :
                    rechercherParNom();
                    break;
                case 5 :
                    afficherClientsParConseiller();
                    break;
            }
        } while (choix != 0);
    }

    private void ajouterClient() throws SQLException {
        // Afficher tous les conseillers
        List<Conseiller> conseillers = conseillerService.getAllConseillers();
        System.out.println("Liste des conseillers :");
        for (Conseiller c : conseillers) {
            System.out.println(c.getId() + " - " + c.getNom() + " " + c.getPrenom());
        }

        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("Prenom: ");
        String prenom = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("ID du conseiller (0 si aucun): ");
        int conseillerId = scanner.nextInt();
        scanner.nextLine();

        Conseiller conseiller = null;
        if (conseillerId != 0) {
            conseiller = conseillerService.chercherParId(conseillerId).orElse(null);
        }

        // ID = 0 pour auto-increment
        Client c = new Client(0, nom, prenom, email, conseiller);
        clientService.ajouterClient(c);
        System.out.println("Client ajouté avec succès ");
    }

    private void supprimerClient() throws SQLException {
        System.out.print("ID du client à supprimer: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        clientService.supprimerClient(id);
        System.out.println("Client supprimé ");
    }

    private void rechercherParId() throws SQLException {
        System.out.print("ID du client: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Optional<Client> client = clientService.chercherParId(id);

        if (client.isPresent()) {
            System.out.println(client.get());
        } else {
            System.out.println("Client non trouvé.");
        }
    }


    private void rechercherParNom() throws SQLException {
        System.out.print("Nom du client: ");
        String nom = scanner.nextLine();
        List<Client> clients = clientService.chercherParNom(nom);
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé avec ce nom.");
        } else {
            clients.forEach(System.out::println);
        }
    }

    private void afficherClientsParConseiller() throws SQLException {
        System.out.print("ID du conseiller: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        List<Client> clients = clientService.clientsParConseiller(id);
        if (clients.isEmpty()) {
            System.out.println("Aucun client pour ce conseiller.");
        } else {
            clients.forEach(System.out::println);
        }
    }
}
