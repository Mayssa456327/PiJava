package entities;

public class User {
    private int id;
    private String nom;
    private String prenom;
    private String mail;
    private String role;
    private String numeroTelephone;
    private String password;
    private String ville;
    private String sexe;
    private String profileImage;
    private Boolean isVerified;
    private Boolean status;
    private String resetToken;
    public static User Current_User;

    public User(int id,String nom, String prenom, String mail, String role, String numeroTelephone, String password, String ville, String sexe, String profileImage, Boolean isVerified, Boolean status, String resetToken) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.role = role;
        this.numeroTelephone = numeroTelephone;
        this.password = password;
        this.ville = ville;
        this.sexe = sexe;
        this.profileImage = profileImage;
        this.isVerified = isVerified;
        this.status = status;
        this.resetToken = resetToken;
    }
    public User(String nom, String prenom, String mail, String role, String numeroTelephone, String password, String ville, String sexe, String profileImage, Boolean isVerified, Boolean status, String resetToken) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.role = role;
        this.numeroTelephone = numeroTelephone;
        this.password = password;
        this.ville = ville;
        this.sexe = sexe;
        this.profileImage = profileImage;
        this.isVerified = isVerified;
        this.status = status;
        this.resetToken = resetToken;
    }

    public User(String nom, String prenom, String mail, String role, String numeroTelephone, String password, String ville, String sexe, String profileImage) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.role = role;
        this.numeroTelephone = numeroTelephone;
        this.password = password;
        this.ville = ville;
        this.sexe = sexe;
        this.profileImage = profileImage;
    }

    public User() {

    }

    public User(Integer id, String nom, String prenom, String email, String role, String num, String password, String ville, String sexe) {
        this.id=id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = email;
        this.role = role;
        this.numeroTelephone = num;
        this.password = password;
        this.ville = ville;
        this.sexe = sexe;
    }



    // Getters and setters for all fields
    public static User getCurrent_User() {
        return Current_User;
    }

    public static void setCurrent_User(User Current_User) {
        User.Current_User = Current_User;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public  String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public  String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public  String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public  String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public boolean isVerified() {
        return isVerified;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", role='" + role + '\'' +
                ", numeroTelephone='" + numeroTelephone + '\'' +
                ", password='" + password + '\'' +
                ", ville='" + ville + '\'' +
                ", sexe='" + sexe + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", isVerified=" + isVerified +
                ", status=" + status +
                ", resetToken='" + resetToken + '\'' +
                '}';
    }
}
