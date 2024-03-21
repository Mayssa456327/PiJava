package com.example.pidevjava.models;

import java.time.LocalDateTime;

public class Evenement {


    private int id;
    private String image_evenement;
    private String type_evenement;
    private String nom_evenement;
    private String lieu_evenement;
    private LocalDateTime date_debut;
    private LocalDateTime date_fin;
    private Double budget;

    public Evenement() {
    }

    public Evenement(int id, String image_evenement, String type_evenement, String nom_evenement,String lieu_evenement,
                     LocalDateTime date_debut,LocalDateTime date_fin,Double budget ) {
        this.id = id;
        this.image_evenement = image_evenement;
        this.type_evenement = type_evenement;
        this.nom_evenement = nom_evenement;
        this.lieu_evenement = lieu_evenement;
        this.date_debut = date_debut;
        this.date_fin= date_fin;
        this.budget = budget;
    }

    public Evenement(String image_evenement, String type_evenement, String nom_evenement,String lieu_evenement,
                     LocalDateTime date_debut,LocalDateTime date_fin,Double budget ) {
        this.image_evenement = image_evenement;
        this.type_evenement = type_evenement;
        this.nom_evenement = nom_evenement;
        this.lieu_evenement = lieu_evenement;
        this.date_debut = date_debut;
        this.date_fin= date_fin;
        this.budget = budget;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_evenement() {
        return image_evenement;
    }

    public void setImage_evenement(String image_evenement) {
        this.image_evenement = image_evenement;
    }

    public String getType_evenement() {
        return type_evenement;
    }
    public void setType_evenement(String type_evenement) {
        this.type_evenement = type_evenement;
    }

    public String getNom_evenement() {
        return nom_evenement;
    }

    public void setNom_evenement(String nom_evenement) {
        this.nom_evenement = nom_evenement;
    }

    public String getLieu_evenement() {
        return lieu_evenement;
    }

    public void setLieu_evenement(String lieu_evenement) {
        this.lieu_evenement = lieu_evenement;
    }

    public LocalDateTime getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDateTime date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDateTime getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDateTime date_fin) {
        this.date_fin = date_fin;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", image_evenement='" + image_evenement + '\'' +
                ", type_evenement='" + type_evenement + '\'' +
                ", nom_evenement='" + nom_evenement + '\'' +
                ", lieu_evenement='" + lieu_evenement + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", budget=" + budget +
                '}';
    }
}