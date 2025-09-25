
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import view.ClientView;
import view.ContratView;
import view.SinistreView;
import view.ConseillerView;

public class Main {

    private ClientView clientView;
    private ContratView contratView;
    private SinistreView sinistreView;
    private ConseillerView conseillerView;
    private Scanner scanner;

    public Main() throws SQLException {
        this.clientView = new ClientView();
        this.contratView = new ContratView();
        this.sinistreView = new SinistreView();
        this.conseillerView = new ConseillerView();
        this.scanner = new Scanner(System.in);
    }

    public void menuPrincipal() throws SQLException {
        int choix;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Gestion des Conseillers");
            System.out.println("2. Gestion des Clients");
            System.out.println("3. Gestion des Contrats");
            System.out.println("4. Gestion des Sinistres");
            System.out.println("0. Quitter");
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
                    conseillerView.menu();
                    break;
                case 2:
                    clientView.menu();
                    break;
                case 3:
                    contratView.menu();
                    break;
                case 4 :
                    sinistreView.menu();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide, réessayez !");
                    break;
            }

        } while (choix != 0);
    }

    public static void main(String[] args) {
        try {
            Main mainView = new Main();
            mainView.menuPrincipal();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'initialisation du système !");
            e.printStackTrace();
        }
    }
}
