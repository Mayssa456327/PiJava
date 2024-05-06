package org.example.models;



public class Reservation {
    private int id ;
    private String nom_patient;
    private String date_debut;
    private String date_fin;
    private Hopital hopital;
    private String email;
    private String telephone;
    private int hopital_id;

    public int getHopital_id() {
        return hopital_id;
    }

    public void setHopital_id(int hopital_id) {
        this.hopital_id = hopital_id;
    }

    public Reservation() {
    }

    public Reservation(int hopital_id,String nom_patient, String date_debut, String date_fin,Hopital hopital,  String email, String telephone) {
        this.hopital_id= hopital_id;
        this.nom_patient = nom_patient;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.email = email;
        this.telephone = telephone;
        this.hopital = hopital;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_patient() {
        return nom_patient;
    }

    public void setNomPatient(String nom_patient) {
        this.nom_patient = nom_patient;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut){
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public Hopital getHopital() {
        return hopital;
    }

    public void setHopital(Hopital hopital) {
        this.hopital = hopital;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", nomPatient='" + nom_patient + '\'' +
                ", dateDebut=" + date_debut +
                ", dateFin=" + date_fin +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
