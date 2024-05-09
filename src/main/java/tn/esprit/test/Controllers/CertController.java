package tn.esprit.test.Controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import com.itextpdf.text.Image;


import com.itextpdf.text.pdf.PdfWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.esprit.test.models.Cert;
import tn.esprit.test.services.CertService;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.List;





import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;

import javax.imageio.ImageIO;

import com.itextpdf.text.DocumentException;

import static tn.esprit.test.Controllers.CertController.MatrixToImageWriter.toBufferedImage;



public class CertController implements Initializable {
    private Connection connection ;
    private PreparedStatement st  ;
    private Statement ste  ;
    private final CertService cs = new CertService();
    ResultSet rs  ;
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    @FXML
    private Button bclear;

    @FXML
    private Button bdelete;

    @FXML
    private Button bsave;

    @FXML
    private Button bupdate;
    @FXML
    private Button certpage;

    @FXML
    private Button dempage;

    @FXML
    private Button ordpage;
    @FXML
    private TableColumn<Cert, Date> coldate;

    @FXML
    private TableColumn<Cert, String> coldes;

    @FXML
    private TableColumn<Cert, Integer> colid;

    @FXML
    private TableColumn<Cert, String> colidp;

    @FXML
    private TableColumn<Cert, String> colnomm;

    @FXML
    private TableColumn<Cert, String> colnomp;

    @FXML
    private TableView<Cert> tablecerts;
    private String idp;

    @FXML


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            List<Cert> certs = cs.getAll();
            tablecerts.getItems().setAll(certs);
            System.out.println("bonjour");
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        this.certpage.getScene().getWindow().hide();
    }
    @FXML
    void page2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/Fxml/DemAN.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        this.dempage.getScene().getWindow().hide();
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
    private DatePicker tdate;

    @FXML
    private TextArea tdes;

    @FXML
    private TextField tidp;

    @FXML
    private TextField tnm;

    @FXML
    private TextField tnp;



       /* void clearField(ActionEvent event) {

        }*/

    @FXML
    void generatePDF(ActionEvent event) {
        Cert selectedCert = tablecerts.getSelectionModel().getSelectedItem();

        if (selectedCert == null) {
            // Aucun certificat sélectionné, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un certificat pour générer le PDF.");
            alert.showAndWait();
            return;
        }

        Document document = new Document(); // Déclaration de la variable document

        try {
            // Chemin d'accès au dossier de sauvegarde
            String savePath = "C:/Users/ahmed/OneDrive/certs/";

            // Assurez-vous que le dossier existe, sinon créez-le
            File directory = new File(savePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Nom du fichier PDF
            String fileName = savePath + "Certificat_" + selectedCert.getId() + ".pdf";

            // Création du document PDF
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Ajouter le titre
            Paragraph title = new Paragraph("Certificat Médical");
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            // Ajouter les données du certificat au document PDF
            addParagraph(document, "Je, soussigné Dr :", selectedCert.getNomM()) ;
            addParagraph(document, "Certifie que l'examen de M,Mlle, Mme", selectedCert.getNomP());
            addParagraph(document, "ID du patient :", selectedCert.getIDP());
            addParagraph(document, "Description :", selectedCert.getDescription());

            // Ajouter le code QR au document PDF
            addQRCode(document, fileName);

            document.close();

            // Afficher un message de succès
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Fichier PDF généré avec succès !");
            successAlert.showAndWait();

        } catch (Exception e) {
            // En cas d'erreur, afficher un message d'erreur
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Erreur lors de la génération du fichier PDF : " + e.getMessage());
            errorAlert.showAndWait();
        }
    }
    // Méthode pour ajouter le code QR au document PDF
    private void addQRCode(Document document, String qrData) throws DocumentException, WriterException, IOException {
        // Assurez-vous que qrData est un lien URI
        String uri = "https://onedrive.live.com/?cid=CD190E121C325F17&id=CD190E121C325F17%212102&parId=CD190E121C325F17%21259&o=OneUp";
        // Générer le code QR avec le lien URI
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(uri, BarcodeFormat.QR_CODE, 200, 200);

        // Ajouter le code QR au document PDF
        BufferedImage qrImage = toBufferedImage(bitMatrix);
        Image qrCodeImage = Image.getInstance(qrImage, null);
        document.add(qrCodeImage);
    }


    // Méthode pour ajouter un paragraphe au document PDF
    private void addParagraph(Document document, String label, String value) throws DocumentException {
        Paragraph paragraph = new Paragraph(label + " " + value);
        document.add(paragraph);
    }



    public static void writeToFile(BitMatrix matrix, String format, String file) throws Exception {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, new File(file))) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    // Méthode pour ajouter le code QR au document PDF
    // Méthode pour ajouter le code QR au document PDF



    @FXML
    void creatCert(ActionEvent event) throws SQLException {



        CertService ServiceQue = new CertService();

        // Récupérer les valeurs des champs
        String NomM = tnm.getText();
        String NomP = tnp.getText();
        String IDP = tidp.getText();
        String Description = tdes.getText();



        if (NomP.isEmpty() || IDP.isEmpty() ) {
            // Afficher un message d'erreur si un champ est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        } else {
            // Créer un nouvel Evenement avec les valeurs des champs
            Cert e = new Cert(NomM,NomP,IDP,Description);

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
    void updateCert(ActionEvent event) throws SQLException {
        // Récupérer l'élément sélectionné dans la TableView
        Cert selectedCert = tablecerts.getSelectionModel().getSelectedItem();

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
        Dialog<Cert> dialog = new Dialog<>();
        dialog.setTitle("Mise à jour du certificat");
        dialog.setHeaderText("Mise à jour des informations du certificat");

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
                return new Cert(tnp.getText() ,  idp ,tnm.getText(), tdes.getText());
            }
            return null;
        });

        Optional<Cert> result = dialog.showAndWait();

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

    public void deleteCert() {
        // Récupérer l'élément sélectionné dans la TableView
        Cert certToDelete = tablecerts.getSelectionModel().getSelectedItem();

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
                tablecerts.getItems().remove(certToDelete);

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
            List<Cert> certs = cs.getAll();
            tablecerts.getItems().clear(); // Effacer toutes les entrées existantes dans la TableView
            tablecerts.getItems().addAll(certs); // Ajouter les certificats actualisés à la TableView
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du rafraîchissement des certificats : " + e.getMessage(), e);

        }



    }
    @FXML
    private TextField searchField;


   /* public void searchByIDP(String IDP) throws SQLException {
        idp = IDP;
        if (IDP.isEmpty()) {
            // Si la barre de recherche est vide, rafraîchissez la TableView avec toutes les entrées
            refresh();
            return;
        }
        IDP = searchField.getText();
        List<Cert> searchResults = cs.getByIDP(IDP); // Méthode à implémenter dans votre service
        tablecerts.getItems().clear(); // Effacer toutes les entrées existantes dans la TableView
        tablecerts.getItems().addAll(searchResults); // Ajouter les résultats de la recherche à la TableView
    }*/

    @FXML
    public void searchByIDP(KeyEvent event) {
        String IDP = searchField.getText().trim();

        if (IDP.isEmpty()) {
            try {
                refresh(); // Si la barre de recherche est vide, rafraîchissez la TableView avec toutes les entrées
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            List<Cert> searchResults = cs.getByIDP(IDP); // Appel à la méthode du service pour obtenir les résultats de la recherche
            tablecerts.getItems().clear(); // Effacer toutes les entrées existantes dans la TableView
            tablecerts.getItems().addAll(searchResults); // Ajouter les résultats de la recherche à la TableView
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static class MatrixToImageWriter {
        public static BufferedImage toBufferedImage(BitMatrix matrix) {
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            return image;
        }
    }


}


