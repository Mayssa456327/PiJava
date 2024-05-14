package entities;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Evenement {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty image_evenement = new SimpleStringProperty();
    private final StringProperty type_evenement = new SimpleStringProperty();
    private final StringProperty nom_evenement = new SimpleStringProperty();
    private final StringProperty lieu_evenement = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> date_debut = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> date_fin = new SimpleObjectProperty<>();
    private final DoubleProperty budget = new SimpleDoubleProperty();



    // Ajoutez des constructeurs et d'autres méthodes si nécessaire
    public Evenement() {

    }
    public Evenement(int id, String image_evenement, String type_evenement, String nom_evenement, String lieu_evenement,
                     LocalDateTime date_debut, LocalDateTime date_fin, double budget) {
        setId(id);
        setImage_evenement(image_evenement);
        setType_evenement(type_evenement);
        setNom_evenement(nom_evenement);
        setLieu_evenement(lieu_evenement);
        setDate_debut(date_debut);
        setDate_fin(date_fin);
        setBudget(budget);
    }

    public Evenement(String image_evenement, String type_evenement, String nom_evenement, String lieu_evenement,
                     LocalDateTime date_debut, LocalDateTime date_fin, double budget) {
        setImage_evenement(image_evenement);
        setType_evenement(type_evenement);
        setNom_evenement(nom_evenement);
        setLieu_evenement(lieu_evenement);
        setDate_debut(date_debut);
        setDate_fin(date_fin);
        setBudget(budget);
    }
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getImage_evenement() {
        return image_evenement.get();
    }


    public StringProperty image_evenementProperty() {
        return image_evenement;
    }

    public void setImage_evenement(String image_evenement) {
        this.image_evenement.set(image_evenement);
    }

    public String getType_evenement() {
        return type_evenement.get();
    }

    public StringProperty type_evenementProperty() {
        return type_evenement;
    }

    public void setType_evenement(String type_evenement) {
        this.type_evenement.set(type_evenement);
    }

    public String getNom_evenement() {
        return nom_evenement.get();
    }

    public StringProperty nom_evenementProperty() {
        return nom_evenement;
    }

    public void setNom_evenement(String nom_evenement) {
        this.nom_evenement.set(nom_evenement);
    }

    public String getLieu_evenement() {
        return lieu_evenement.get();
    }

    public StringProperty lieu_evenementProperty() {
        return lieu_evenement;
    }

    public void setLieu_evenement(String lieu_evenement) {
        this.lieu_evenement.set(lieu_evenement);
    }

    public LocalDateTime getDate_debut() {
        return date_debut.get();
    }

    public ObjectProperty<LocalDateTime> date_debutProperty() {
        return date_debut;
    }

    public void setDate_debut(LocalDateTime date_debut) {
        this.date_debut.set(date_debut);
    }

    public LocalDateTime getDate_fin() {
        return date_fin.get();
    }

    public ObjectProperty<LocalDateTime> date_finProperty() {
        return date_fin;
    }

    public void setDate_fin(LocalDateTime date_fin) {
        this.date_fin.set(date_fin);
    }

    public float getBudget() {
        return (float) budget.get();
    }

    public DoubleProperty budgetProperty() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget.set(budget);
    }

    // Ajoutez d'autres accesseurs et mutateurs si nécessaire

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

/*public class Evenement {


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
*/
