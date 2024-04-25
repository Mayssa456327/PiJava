package tn.esprit.test.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.esprit.test.models.OrdMed;
import tn.esprit.test.services.OrdService;


import java.io.IOException;
import java.net.URL;
import java.sql.*;

import java.time.LocalDate;
import java.util.*;
import java.util.Date;

import static tn.esprit.test.services.CertService.*;

public class OrdController implements Initializable {
    private Connection connection ;
    private PreparedStatement st  ;
    private Statement ste  ;
    private final OrdService cs = new OrdService();
    ResultSet rs  ;

    @FXML
    private Button bclear1;

    @FXML
    private Button bdelete1;

    @FXML
    private Button bsave1;

    @FXML
    private Button bupdate1;
    @FXML
    private Button certpage;
    @FXML
    private Button dempage;

    @FXML
    private Button ordpage;
    @FXML
    private TableColumn<OrdMed, Date> coldate1;

    @FXML
    private TableColumn<OrdMed, String> coldes1;

    @FXML
    private TableColumn<OrdMed, Integer> colid1;

    @FXML
    private TableColumn<OrdMed, String> colidp1;

    @FXML
    private TableColumn<OrdMed, String> colnomm1;

    @FXML
    private TableColumn<OrdMed, String> colnomp1;

    @FXML
    private TableView<OrdMed> tablecerts1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            List<OrdMed> ordmeds = cs.getAll();
            tablecerts1.getItems().setAll(ordmeds);
            System.out.println("bonjour");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


   /* public List<Cert> getCerts(){
        Connection connection = MyDatabase.getConnection();
        String query = "SELECT * FROM certs";
        System.out.println(query);
        List<Cert> Certs = new ArrayList<>();


        try {

         //  Statement st = connection.prepareStatement(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                Cert cert = new Cert();
                cert.setId(rs.getInt("id"));
                cert.setNomP (rs.getString( "NomP"));
                cert.setIDP (rs.getString(  "IDP"));
                cert.setNomM (rs.getString(  "NomM"));
                cert.setDate (rs.getDate(  "Date"));
                cert.setDescription(rs.getString(  "Description"));
                Certs.add(cert);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Certs;


    }
    /*public void showCerts(){
        ObservableList<Cert> list = getCerts();

        tablecerts.setItems(list);
        colid.setCellValueFactory (new PropertyValueFactory<Cert, Integer>("id"));
        colnomp.setCellValueFactory (new PropertyValueFactory<Cert, String>("nomP"));
        colidp.setCellValueFactory (new PropertyValueFactory<Cert, String>("IDP"));
        colnomm.setCellValueFactory (new PropertyValueFactory<Cert, String>("nomM"));
        coldate.setCellValueFactory (new PropertyValueFactory<Cert, Date>("date"));
        coldes.setCellValueFactory (new PropertyValueFactory<Cert, String>("description"));

    }*/



    @FXML
    private DatePicker tdate1;

    @FXML
    private TextArea tdes1;

    @FXML
    private TextField tidp1;

    @FXML
    private TextField tnm1;

    @FXML
    private TextField tnp1;



       /* void clearField(ActionEvent event) {

        }*/

    @FXML
    void creatOrd(ActionEvent event) throws SQLException {



        OrdService ServiceQue = new OrdService();

        // Récupérer les valeurs des champs
        String NomM = tnm1.getText();
        String NomP = tnp1.getText();
        String IDP = tidp1.getText();
        String Description = tdes1.getText();



        if (NomP.isEmpty() || IDP.isEmpty() ) {
            // Afficher un message d'erreur si un champ est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        } else {
            // Créer un nouvel Evenement avec les valeurs des champs
            OrdMed e = new OrdMed(NomM,NomP,IDP,Description);

            // Ajouter l'Evenement en utilisant le service
            ServiceQue.add(e);

            // Afficher un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Certificat ajouté avec succès!");
            alert.showAndWait();

        }
    }



    @FXML
    void page(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/Fxml/Ords.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        this.ordpage.getScene().getWindow().hide();
    }
    @FXML
    void page1(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/Fxml/Certs.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        this.ordpage.getScene().getWindow().hide();


    }
    @FXML
    void page2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/Fxml/DemAn.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        this.dempage.getScene().getWindow().hide();
    }




    @FXML
    void updateOrd(ActionEvent event) throws SQLException {
        // Récupérer l'élément sélectionné dans la TableView
        OrdMed selectedCert = tablecerts1.getSelectionModel().getSelectedItem();

        if (selectedCert == null) {
            // Aucun élément sélectionné, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un certificat à mettre à jour.");
            alert.showAndWait();
            return;
        }

        // Créer un nouveau dialogue pour la mise à jour
        Dialog<OrdMed> dialog = new Dialog<>();
        dialog.setTitle("Mise à jour du Ordenace Medicale");
        dialog.setHeaderText("Mise à jour des informations du Ordenace Medicale");

        // Bouton de confirmation
        ButtonType updateButtonType = new ButtonType("Mettre à jour", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        // Création des champs de saisie dans le dialogue
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField tnm = new TextField();
        tnm.setText(selectedCert.getNomM());
        TextField tnp = new TextField();
        tnp.setText(selectedCert.getNomP());
        TextField tidp = new TextField();
        tidp.setText(selectedCert.getIDP());
        TextArea tdes = new TextArea();
        tdes.setText(selectedCert.getDescription());

        grid.add(new Label("Nom du médecin:"), 0, 0);
        grid.add(tnm, 1, 0);
        grid.add(new Label("Nom du patient:"), 0, 1);
        grid.add(tnp, 1, 1);
        grid.add(new Label("ID du patient:"), 0, 2);
        grid.add(tidp, 1, 2);
        grid.add(new Label("Description:"), 0, 3);
        grid.add(tdes, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Activation du bouton de mise à jour lorsque les champs sont remplis
        Node updateButton = dialog.getDialogPane().lookupButton(updateButtonType);
        updateButton.setDisable(true);

        // Vérification de la validité des champs
        tnm.textProperty().addListener((observable, oldValue, newValue) -> updateButton.setDisable(newValue.trim().isEmpty()));
        tnp.textProperty().addListener((observable, oldValue, newValue) -> updateButton.setDisable(newValue.trim().isEmpty()));
        tidp.textProperty().addListener((observable, oldValue, newValue) -> updateButton.setDisable(newValue.trim().isEmpty()));
        tdes.textProperty().addListener((observable, oldValue, newValue) -> updateButton.setDisable(newValue.trim().isEmpty()));

        // Affichage du dialogue et récupération des résultats
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                // Vérifier la longueur des données et les tronquer si nécessaire
                String idp = tidp.getText().trim();
                if (idp.length() > 8) { // Mettez la longueur maximale de la colonne IDP de votre base de données
                    idp = idp.substring(0, 8); // Tronquer les données à la longueur maximale
                }
                // Créer un nouvel objet Cert avec les valeurs modifiées
                return new OrdMed(tnp.getText() ,  idp ,tnm.getText(), tdes.getText());
            }
            return null;
        });

        Optional<OrdMed> result = dialog.showAndWait();

        result.ifPresent(updatedCert -> {
            // Mise à jour du certificat dans la base de données
            try {
                updatedCert.setId(selectedCert.getId()); // Assurer que l'ID reste le même
                cs.update(updatedCert); // Utiliser le service pour mettre à jour le certificat

                // Rafraîchir la TableView pour afficher les modifications
                refresh();
            } catch (SQLException e) {
                // Gérer les exceptions
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Erreur lors de la mise à jour du certificat : " + e.getMessage());
                errorAlert.showAndWait();
            }
        });
    }



    @FXML

    public void deleteOrd() {
        // Récupérer l'élément sélectionné dans la TableView
        OrdMed certToDelete = tablecerts1.getSelectionModel().getSelectedItem();

        if (certToDelete == null) {
            // Aucun élément sélectionné, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un certificat à supprimer.");
            alert.showAndWait();
            return;
        }

        // Confirmation de la suppression
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce certificat ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Utiliser le service pour supprimer le certificat de la base de données
                cs.delete(certToDelete.getId());

                // Supprimer l'élément de la TableView
                tablecerts1.getItems().remove(certToDelete);

                // Afficher un message de succès
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Succès");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Certificat supprimé avec succès !");
                successAlert.showAndWait();
            } catch (SQLException e) {
                // En cas d'erreur lors de la suppression, afficher un message d'erreur
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Erreur lors de la suppression du certificat : " + e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }

    public void refresh() throws SQLException {
        try {
            List<OrdMed> certs = cs.getAll();
            tablecerts1.getItems().clear(); // Effacer toutes les entrées existantes dans la TableView
            tablecerts1.getItems().addAll(certs); // Ajouter les certificats actualisés à la TableView
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du rafraîchissement des certificats : " + e.getMessage(), e);

        }



    }

}


