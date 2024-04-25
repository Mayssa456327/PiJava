package tn.esprit.test.models;

import java.time.LocalDateTime;

public class DemAn {
    private int id;
    private String NomP;
    private String IDP;
    private String NomM;
    private LocalDateTime Date= LocalDateTime.now();
    private String Description;
    private String Type;

    public DemAn() {
    }

    public DemAn(String nomP, String IDP, String nomM, String description, String type) {
        NomP = nomP;
        this.IDP = IDP;
        NomM = nomM;
        Description = description;
        Type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  String getNomP() {
        return NomP;
    }

    public void setNomP(String nomP) {
        NomP = nomP;
    }

    public  String getIDP() {
        return IDP;
    }

    public void setIDP(String IDP) {
        this.IDP = IDP;
    }

    public  String getNomM() {
        return NomM;
    }

    public void setNomM(String nomM) {
        NomM = nomM;
    }

    public LocalDateTime getDate() {
        return Date;
    }

    public void setDate(LocalDateTime date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;

    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;

    }
}

