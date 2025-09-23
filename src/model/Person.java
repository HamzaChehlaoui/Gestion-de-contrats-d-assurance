package model;

public abstract class Person{

    private String nom ;
    private String prenom ;
    private String email ;

    public void setNom(String newNom){
        this.nom = newNom;
    }
    public String getNom(){
        return nom;
    }
    public void setPrenom(String newPrenom){
        this.prenom = newPrenom;
    }
    public String getPrenom(){
        return prenom;
    }
    public void setEmail(String newEmail){
        this.email = newEmail;
    }
    public String getEmail(){
        return email;
    }


}