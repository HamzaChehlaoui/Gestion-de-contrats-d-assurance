
import java.sql.SQLException;
import java.util.Scanner;
import view.ClientView;
import view.ContratView;
import view.SinistreView;

public class Main {

    private ClientView clientView;
    private ContratView contratView;
    private SinistreView sinistreView;
    private Scanner scanner;

    public Main() throws SQLException {
        this.clientView = new ClientView();
        this.contratView = new ContratView();
        this.sinistreView = new SinistreView();
        this.scanner = new Scanner(System.in);
    }

    public void menuPrincipal() throws SQLException {
        int choix;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Gestion des Clients");
            System.out.println("2. Gestion des Contrats");
            System.out.println("3. Gestion des Sinistres");
            System.out.println("0. Quitter");
            System.out.print("Choix: ");
            choix = scanner.nextInt();
            scanner.nextLine(); // Consommer le retour à la ligne

            switch (choix) {
                case 1:
                    clientView.menu();
                    break;
                case 2:
                    contratView.menu();
                    break;
                case 3:
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
