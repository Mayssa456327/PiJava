package entities;

public class Order {
    private int id;
    private int user_id;
    private String adresse;
    private boolean ispayed;

    public Order() {
    }

    public Order(int user_id, String adresse, boolean ispayed) {
        this.user_id = user_id;
        this.adresse = adresse;
        this.ispayed = ispayed;
    }

    public Order(int id, int user_id, String adresse, boolean ispayed) {
        this.id = id;
        this.user_id = user_id;
        this.adresse = adresse;
        this.ispayed = ispayed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public boolean isIspayed() {
        return ispayed;
    }

    public void setIspayed(boolean ispayed) {
        this.ispayed = ispayed;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", adresse='" + adresse + '\'' +
                ", ispayed=" + ispayed +
                '}';
    }
}
