package org.example.models;

public class Chambre {
    private int id;
    private Boolean disponibiliter;
    private Hopital hopital;
    private Reservation reservation;
    private int hopital_id;
    private int reservation_id;

    public int getHoptal_id() {
        return hopital_id;
    }

    public void setHoptal_id(int hoptal_id) {
        this.hopital_id = hoptal_id;
    }

    public Chambre() {
    }

    public Chambre(int id,boolean disponibiliter,int hopital_id,int reservation_id ){
        this.id = id;
        this.disponibiliter= disponibiliter;
        this.hopital_id = hopital_id;
        this.reservation_id = reservation_id;
    }

    public Chambre(int id,int hoptal_id ,int reservation_id,Boolean disponibiliter, Hopital hopital, Reservation reservation) {
        this.id = id;
        this.disponibiliter = disponibiliter;
        this.hopital = hopital;
        this.reservation = reservation;
        this.hopital_id = hoptal_id;
        this.reservation_id = reservation_id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Boolean getDisponibiliter() {
        return disponibiliter;
    }

    public void setDisponibiliter(Boolean diponibiliter) {
        this.disponibiliter = diponibiliter;
    }



    public Hopital getHopital() {
        return hopital;
    }

    public void setHopital(Hopital hopital) {
        this.hopital = hopital;
    }
    public int getHopital_id() {
        return hopital_id;
    }

    public void setHopital_id(int hopital_id) {
        this.hopital_id = hopital_id;
    }


    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }
    public int getReservation_id() {
        return reservation_id;
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
                ", disponibiliter=" + disponibiliter +
                ", hopital=" + hopital +
                ", reservation=" + reservation +
                '}';
    }
}
