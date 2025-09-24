package model;

import java.util.HashMap;
import java.util.Map;

public class Conseiller extends Person {

    private final Map<Integer, Client> clients = new HashMap<>();
    private int id;
    public Conseiller(int id , String nom, String prenom, String email) {
        super(nom, prenom, email);
        this.id = id;
    }

    public void addClient(Client client) {
        clients.put(client.getId(), client);
    }

    public void removeClient(int clientId) {
        clients.remove(clientId);
    }

    public int getId(){
        return id;
    }

    public void setId(int newId){
        this.id = id;
    }

    public Client getClient(int clientId) {
        return clients.get(clientId);
    }

    public void afficherClients() {
        if (clients.isEmpty()) {
            System.out.println("Aucun client assigné.");
        } else {
            clients.values().forEach(System.out::println);
        }
    }

    public Map<Integer, Client> getClients() {
        return clients;
    }

    @Override
    public String toString() {
        return "Conseiller : " + getNom() + " " + getPrenom() +
                " | Email: " + getEmail() +
                " | Nb Clients: " + clients.size();
    }
}
