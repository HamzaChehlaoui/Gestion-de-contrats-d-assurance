package view;

import model.Client;
import model.Contrat;
import model.enums.TypeContrat;
import service.ClientService;
import service.ContratService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ContratView {

    private ContratService contratService;
    private ClientService clientService;
    private Scanner scanner;

    public ContratView() throws SQLException {
        this.contratService = new ContratService();
        this.clientService = new ClientService();
        this.scanner = new Scanner(System.in);
    }

    public void menu() throws SQLException {
        int choix;
        do {
            System.out.println("\n=== Gestion des Contrats ===");
            System.out.println("1. Ajouter un contrat");
            System.out.println("2. Supprimer un contrat");
            System.out.println("3. Rechercher un contrat par ID");
            System.out.println("4. Afficher les contrats d'un client");
            System.out.println("0. Retour");
            System.out.print("Choix: ");
            choix = scanner.nextInt();
            scanner.nextLine(); // Consommer le retour à la ligne

            switch (choix) {
                case 1 :
                    ajouterContrat();
                    break;
                case 2 :
                    supprimerContrat();
                    break;
                case 3 :rechercherParId();
                    break;
                case 4 :
                    afficherContratsParClient();
                    break;
            }
        } while (choix != 0);
    }

    private void ajouterContrat() throws SQLException {
        List<Client> clients = clientService.getAllClients();
        System.out.println("Liste des clients :");
        for (Client c : clients) {
            System.out.println(c.getId() + " - " + c.getNom() + " " + c.getPrenom());
        }

        System.out.print("ID du client pour ce contrat: ");
        int clientId = scanner.nextInt();
        scanner.nextLine();

        Client client = clientService.chercherParId(clientId).orElse(null);
        if (client == null) {
            System.out.println("Client introuvable !");
            return;
        }

        System.out.println("Types de contrat disponibles : AUTOMOBILE, MAISON, MALADIE");
        System.out.print("Type: ");
        String typeStr = scanner.nextLine().toUpperCase();
        TypeContrat type;
        try {
            type = TypeContrat.valueOf(typeStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Type de contrat invalide !");
            return;
        }

        System.out.print("Date début (YYYY-MM-DD): ");
        LocalDate dateDebut = LocalDate.parse(scanner.nextLine());

        System.out.print("Date fin (YYYY-MM-DD): ");
        LocalDate dateFin = LocalDate.parse(scanner.nextLine());

        Contrat contrat = new Contrat(0, type, dateDebut, dateFin, client);
        contratService.ajouterContrat(contrat);
        System.out.println("Contrat ajouté ");
    }

    private void supprimerContrat() throws SQLException {
        System.out.print("ID du contrat: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        contratService.supprimerContrat(id);
        System.out.println("Contrat supprimé ");
    }

    private void rechercherParId() throws SQLException {
        System.out.print("ID du contrat: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Optional<Contrat> contrat = contratService.chercherParId(id);

        if (contrat.isPresent()) {
            System.out.println(contrat.get());
        } else {
            System.out.println("Contrat non trouvé.");
        }
    }

    private void afficherContratsParClient() throws SQLException {
        System.out.print("ID du client: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        List<Contrat> contrats = contratService.contratsParClient(id);
        if (contrats.isEmpty()) {
            System.out.println("Aucun contrat pour ce client.");
        } else {
            contrats.forEach(System.out::println);
        }
    }
}
