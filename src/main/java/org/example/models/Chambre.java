package org.example.models;

public class Chambre {
    private int id=0;
    private int numero;
    private Boolean diponibiliter;
    private int nombreLit;
    private Hopital hopital;
    private Reservation reservation;

    public Chambre() {
    }

    public Chambre(int id,int numero, Boolean diponibiliter, int nombreLit, Hopital hopital, Reservation reservation) {
        this.id = id;
        this.numero = numero;
        this.diponibiliter = diponibiliter;
        this.nombreLit = nombreLit;
        this.hopital = hopital;
        this.reservation = reservation;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Boolean getDiponibiliter() {
        return diponibiliter;
    }

    public void setDiponibiliter(Boolean diponibiliter) {
        this.diponibiliter = diponibiliter;
    }

    public int getNombreLit() {
        return nombreLit;
    }

    public void setNombreLit(int nombreLit) {
        this.nombreLit = nombreLit;
    }

    public Hopital getHopital() {
        return hopital;
    }

    public void setHopital(Hopital hopital) {
        this.hopital = hopital;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
    @Override
    public String toString() {
        return "Chambre{" +
                "id=" + id +
                ", numero=" + numero +
                ", diponibiliter=" + diponibiliter +
                ", nombreLit=" + nombreLit +
                ", hopital=" + hopital +
                ", reservation=" + reservation +
                '}';
    }
}
