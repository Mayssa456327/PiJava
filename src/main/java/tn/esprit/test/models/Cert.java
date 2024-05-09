package tn.esprit.test.models;
import java.awt.image.BufferedImage;

import java.time.LocalDateTime;

public class Cert {
    private int id;
    private String NomP;
    private String IDP;
    private String NomM;
    private LocalDateTime Date= LocalDateTime.now();
    private String Description;
    private BufferedImage logo;

    // Cachet du médecin
    private BufferedImage cachetMedecin;

    public BufferedImage getLogo() {
        return logo;
    }

    public void setLogo(BufferedImage logo) {
        this.logo = logo;
    }

    public BufferedImage getCachetMedecin() {
        return cachetMedecin;
    }

    public void setCachetMedecin(BufferedImage cachetMedecin) {
        this.cachetMedecin = cachetMedecin;
    }

    public BufferedImage getQrCode() {
        return qrCode;
    }

    public void setQrCode(BufferedImage qrCode) {
        this.qrCode = qrCode;
    }

    // Code QR
    private BufferedImage qrCode;
    public Cert() {
    }

    public Cert(String nomP, String IDP, String nomM, String description) {
        NomP = nomP;
        this.IDP = IDP;
        NomM = nomM;
        Description = description;
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
}

