package model;

public abstract class Person {

    private String nom;
    private String prenom;
    private String email;

    public Person(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    protected Person() {
    }

    // Getters & Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String newNom) {
        this.nom = newNom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String newPrenom) {
        this.prenom = newPrenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    @Override
    public String toString() {
        return nom + " " + prenom + " (" + email + ")";
    }
}
