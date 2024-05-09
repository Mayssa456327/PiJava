package org.example.models;

public class Hopital {
    private int id;
    private String nom;
    private String adresse;
    private String telephone ;
    private String email;
    private int nombre_chambre;

    public Hopital(){}

    public Hopital(int id,String nom){
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombre_chambre(int nombre_chambre) {
        this.nombre_chambre = nombre_chambre;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public int getNombre_chambre() {
        return nombre_chambre;
    }

    public Hopital(String nom, String adresse, String telephone, String email, int nombre_chambre){
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.nombre_chambre =nombre_chambre;
    }
    @Override
    public String toString() {
        return "Hopital{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", nombreChambre=" + nombre_chambre +
                '}';
    }

}
