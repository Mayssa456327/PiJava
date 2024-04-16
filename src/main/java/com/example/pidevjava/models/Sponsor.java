package com.example.pidevjava.models;

import java.time.LocalDateTime;

public class Sponsor {
    private int id;
    private Double budget;
    private int evenement_id;
    private String nom_sponsor;
    private String email_sponsor;
    private String adresse;

    public Sponsor() {
    }

    public Sponsor(int id, Double budget, int evenement_id, String nom_sponsor,String email_sponsor,
                   String adresse ) {
        this.id = id;
        this.budget = budget;
        this.evenement_id = evenement_id;
        this.nom_sponsor = nom_sponsor;
        this.email_sponsor = email_sponsor;
        this.adresse = adresse;

    }
    public Sponsor(Double budget, int evenement_id, String nom_sponsor,String email_sponsor,
                   String adresse ) {
        this.budget = budget;
        this.evenement_id = evenement_id;
        this.nom_sponsor = nom_sponsor;
        this.email_sponsor = email_sponsor;
        this.adresse = adresse;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public int getEvenement_id() {
        return evenement_id;
    }

    public void setEvenement_id(int evenement_id) {
        this.evenement_id = evenement_id;
    }

    public String getNom_sponsor() {
        return nom_sponsor;
    }

    public void setNom_sponsor(String nom_sponsor) {
        this.nom_sponsor = nom_sponsor;
    }

    public String getEmail_sponsor() {
        return email_sponsor;
    }

    public void setEmail_sponsor(String email_sponsor) {
        this.email_sponsor = email_sponsor;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Sponsor{" +
                "id=" + id +
                ", budget=" + budget +
                ", evenement_id=" + evenement_id +
                ", nom_sponsor='" + nom_sponsor + '\'' +
                ", email_sponsor='" + email_sponsor + '\'' +
                ", adresse=" + adresse +
                '}';
    }
}
