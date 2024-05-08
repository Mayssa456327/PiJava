package com.example.pidevjava.controllers;
import com.example.pidevjava.models.PDF;
import com.example.pidevjava.models.Evenement;
import com.example.pidevjava.services.ServiceEvenement;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import java.text.ParseException;
import java.util.Date;


public class BackEvenementController implements Initializable {

    @FXML
    private Button pdfBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button AjouterBtn;
    @FXML
    private TextField Budget;
    @FXML
    private DatePicker DateDebut;
    @FXML
    private DatePicker DateFin;
    @FXML
    private VBox EventVBox;
    @FXML
    private Button ImageBtn;
    @FXML
    private TextField LieuEvenement;
    @FXML
    private TextField NomEvenement;
    @FXML
    private ScrollPane Scrollpane;
    @FXML
    private Button modifierBtn;
    @FXML
    private Button suppBtn;
    @FXML
    private TextField Image;
    @FXML
    private TextField searchBar;


    @FXML
    private Button triBtn;

    @FXML
    private TextField typeEvenement;
    private final ServiceEvenement SE = new ServiceEvenement();
    private String imagePath;

    private Evenement selectedEvenement;

    private String tri="ASC";
    private int i = 0;


    @FXML
    private TableView<Evenement> rdvTableView;
    // Déclarez la référence à l'étiquette dans votre classe de contrôleur
    @FXML
    private Label moisAnneeLabel;
    @FXML
    private Label label;
    private ServiceEvenement rdvService = new ServiceEvenement();

    @FXML
    private GridPane calendrierGridPane; // Ajout du champ pour le calendrier

    @FXML
    private Label moisLabel;

    @FXML
    private Label anneeLabel;

    private int currentYear;
    private int currentMonth;

    @FXML
    private Button moisPrecedentButton;
    @FXML
    private Button moisSuivantButton;

    @FXML
    private Button anneePrecedenteButton;
    @FXML
    private Button anneeSuivanteButton;


   /* private void updateMonthLabel(int month) {
        String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
        moisLabel.setText(months[month - 1]);
    }
    // Mettez à jour l'étiquette de l'année avec l'année sélectionnée
    private void updateYearLabel(int year) {
        anneeLabel.setText(Integer.toString(year));
    }*/
    private final String imageDirectory = "C:/Users/mayss/Desktop/web/PIDEV/public/uploads/";

    private TableView<Evenement> tableView = new TableView<>();

    // Define columns for the TableView
    private TableColumn<Evenement, String> nomColumn = new TableColumn<>("Nom");
    private TableColumn<Evenement, String> typeColumn = new TableColumn<>("Type");
    // Define other columns as needed



    private void setupSearchBarListener() {
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterEvenement(newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private boolean isNomEvenementUnique(String nomEvenement) throws SQLException {
        List<Evenement> evenements = SE.getAll(); // Assuming getEvenements() returns a List<Evenement>
        for (Evenement event : evenements) {
            if (event.getNom_evenement().equals(nomEvenement)) {
                return false; // Event name is not unique
            }
        }
        return true; // Event name is unique
    }


    @FXML
    void OnClickedAjouter(ActionEvent event) {
        try {
            if (champsSontValides()) {
                String nomEvenement = NomEvenement.getText(); // Get the name of the event
                // Check if the event name is unique
                if (isNomEvenementUnique(nomEvenement)) {
                    String imagePath = Image.getText(); // Ensure imagePath is correctly retrieved
                    Evenement newEvent = new Evenement(
                            imagePath,
                            typeEvenement.getText(),
                            nomEvenement,
                            LieuEvenement.getText(),
                            DateDebut.getValue().atStartOfDay(),
                            DateFin.getValue().atStartOfDay(),

                            Integer.parseInt(Budget.getText())
                    );
                    SE.add(newEvent);

                    // Show success message
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Événement ajouté");
                    alert.setHeaderText(null);
                    alert.setContentText("L'événement a été ajouté avec succès!");
                    alert.showAndWait();

                    // Clear fields after successful addition
                    clearFields();

                    // Refresh event list
                    refreshEvents();
                } else {
                    // Show error message if event name is not unique
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setHeaderText(null);
                    alert.setContentText("Le nom de l'événement est deja exist!");
                    alert.showAndWait();
                }
            } else {
                // Show error message if fields are not valid
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs!");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            // Show error message if budget field is not a valid number
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Le budget doit être un nombre valide!");
            alert.showAndWait();
        } catch (SQLException e) {
            // Handle database errors
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de l'ajout de l'événement. Veuillez réessayer plus tard.");
            alert.showAndWait();
            e.printStackTrace(); // Log the exception for debugging
        }
    }

    private boolean champsSontValides() {
        return !typeEvenement.getText().isEmpty() &&
                !NomEvenement.getText().isEmpty() &&
                !LieuEvenement.getText().isEmpty() &&
                DateDebut.getValue() != null &&
                DateFin.getValue() != null &&
                !Budget.getText().isEmpty(); // Utilisez !Budget.getText().isEmpty() pour vérifier si le champ Budget n'est pas vide
    }

    private void clearFields() {
        typeEvenement.clear();
        NomEvenement.clear();
        LieuEvenement.clear();
        DateDebut.setValue(null);
        DateFin.setValue(null);
        Budget.clear();

    }

    @FXML
    void OnClickedImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        // Filtrer uniquement les fichiers d'image
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );
        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Récupérer le chemin de l'image sélectionnée
            imagePath = selectedFile.getName(); // Obtenir seulement le nom du fichier
            // Afficher le chemin de l'image sélectionnée dans le champ texte Image
            Image.setText(imagePath);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventVBox.getChildren().clear();
        try {
            List<Evenement> events = SE.getAll();
            for (Evenement evenement : events) {
                HBox eventBox = new HBox(); // Create an HBox for each event
                Label eventLabel = new Label(
                        "Nom: " + evenement.getNom_evenement() +
                                ", Type: " + evenement.getType_evenement() +
                                ", Date Debut: " + evenement.getDate_debut().toString() +
                                ", Date fin: " + evenement.getDate_fin().toString() +
                                ", Lieu: " + evenement.getLieu_evenement() +
                                ", budget: " + evenement.getBudget()
                );
                ImageView imageView = new ImageView();
                try {
                    File imageFile = new File("C:/Users/mayss/Desktop/web/PIDEV/public/uploads/" + evenement.getImage_evenement());
                    Image image = new Image(imageFile.toURI().toString());
                    imageView.setImage(image);
                    imageView.setFitWidth(100); // Set the desired width of the image
                    imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image
                } catch (Exception e) {
                    // Handle image loading errors
                    System.err.println("Erreur de chargement de l’image : " + e.getMessage());
                    // Show error message in alert dialog
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Erreur de chargement de l’image :" + e.getMessage());
                    alert.showAndWait();
                }

                eventLabel.setOnMouseClicked(event -> {
                    selectedEvenement = evenement;
                    ImageBtn.setText(selectedEvenement.getImage_evenement());
                    typeEvenement.setText(selectedEvenement.getType_evenement());
                    NomEvenement.setText(selectedEvenement.getNom_evenement());
                    DateDebut.setValue(selectedEvenement.getDate_debut().toLocalDate());
                    DateFin.setValue(selectedEvenement.getDate_fin().toLocalDate());
                    LieuEvenement.setText(selectedEvenement.getLieu_evenement());
                    Budget.setText(String.valueOf(selectedEvenement.getBudget()));

                });

                eventBox.getChildren().addAll(imageView, eventLabel); // Add ImageView and Label to the HBox
                EventVBox.getChildren().add(eventBox); // Add the HBox to the VBox
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setupSearchBarListener();
        triBtn.setOnAction(event -> {
            try {
                OnClickedTri(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        if(i % 2 == 0){
            tri = "ASC";
        }else{
            tri = "DESC";
        }
        i++;
        refreshEvents();

        Budget.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                Budget.setText(oldValue);
            }
        });
    }

    private void filterEvenement(String searchText) throws SQLException {
        List<Evenement> evenements = SE.getAll();
        EventVBox.getChildren().clear();
        for (Evenement evenement : evenements) {
            if (evenement.getNom_evenement().toLowerCase().contains(searchText.toLowerCase())) {
                HBox eventBox = new HBox(); // Create an HBox for each event
                Label eventLabel = new Label("Nom: " + evenement.getNom_evenement() +
                        ", Type: " + evenement.getType_evenement() +
                        ", Date Debut: " + evenement.getDate_debut().toString() +
                        ", Date fin: " + evenement.getDate_fin().toString() +
                        ", Lieu: " + evenement.getLieu_evenement() +
                        ", budget: " + evenement.getBudget());
                ImageView imageView = new ImageView();
                try {
                    File imageFile = new File("C:/Users/mayss/Desktop/web/PIDEV/public/uploads/" + evenement.getImage_evenement());
                    Image image = new Image(imageFile.toURI().toString());
                    imageView.setImage(image);
                    imageView.setFitWidth(100); // Set the desired width of the image
                    imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image
                } catch (Exception e) {
                    // Handle image loading errors
                    System.err.println("Erreur de chargement de l’image : " + e.getMessage());
                    // Show error message in alert dialog
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Erreur de chargement de l’image : " + e.getMessage());
                    alert.showAndWait();
                }
                eventLabel.setOnMouseClicked(event -> {
                    selectedEvenement = evenement;
                    ImageBtn.setText(selectedEvenement.getImage_evenement());
                    typeEvenement.setText(selectedEvenement.getType_evenement());
                    NomEvenement.setText(selectedEvenement.getNom_evenement());
                    DateDebut.setValue(selectedEvenement.getDate_debut().toLocalDate());
                    DateFin.setValue(selectedEvenement.getDate_fin().toLocalDate());
                    LieuEvenement.setText(selectedEvenement.getLieu_evenement());
                    Budget.setText(String.valueOf(selectedEvenement.getBudget()));

                });

                eventBox.getChildren().addAll(imageView, eventLabel); // Add ImageView and Label to the HBox
                EventVBox.getChildren().add(eventBox); // Add the HBox to the VBox
            }
        }
    }

    @FXML
    void OnClickedModifier(ActionEvent event) throws SQLException {
        if (selectedEvenement != null) {String nouveauNomEvenement = NomEvenement.getText();
            if (isNomEvenementUnique(nouveauNomEvenement) && !nouveauNomEvenement.equals(selectedEvenement.getNom_evenement())) {
                selectedEvenement.setImage_evenement(imagePath); // Utilisez imagePath pour définir le chemin de l'image

                selectedEvenement.setType_evenement(typeEvenement.getText());
                selectedEvenement.setNom_evenement(NomEvenement.getText());
                selectedEvenement.setDate_debut(DateDebut.getValue().atStartOfDay());
                selectedEvenement.setDate_fin(DateFin.getValue().atStartOfDay());

                selectedEvenement.setLieu_evenement(LieuEvenement.getText());
                selectedEvenement.setBudget(Double.parseDouble(Budget.getText()));

                SE.update(selectedEvenement);
                refreshEvents();


            }else {
                // Show error message if the new event name is not unique or is the same as the current name
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                if (!isNomEvenementUnique(nouveauNomEvenement)) {
                    alert.setContentText("Le nom de l'événement est deja existe! Veuillez entrer un nouveau nom d'événement différent!");
                }
                alert.showAndWait();
            }
        }
    }

    @FXML
    void OnClickedSupprimer(ActionEvent event) {
        try {
            if (selectedEvenement != null) {
                SE.delete(selectedEvenement);
                refreshEvents();
                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Événement supprimé");
                alert.setHeaderText(null);
                alert.setContentText("L'événement a été supprimé avec succès!");
                alert.showAndWait();
            } else {
                // Show error message if no event is selected
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Aucun événement sélectionné pour la suppression.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // Handle database errors
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de la suppression de l'événement. Veuillez réessayer plus tard.");
            alert.showAndWait();
            e.printStackTrace(); // Log the exception for debugging
        }
    }


    private void refreshEvents() {
        EventVBox.getChildren().clear(); // Efface les événements précédents
        // Chargez à nouveau les événements et ajoutez-les à EventVBox
        try {
            List<Evenement> events = SE.getAll();
            // Boucle pour charger les événements dans EventVBox
            for (Evenement evenement : events) {
                HBox eventBox = new HBox(); // Créez une HBox pour chaque événement
                Label eventLabel = new Label(
                        "Nom: " + evenement.getNom_evenement() +
                                ", Type: " + evenement.getType_evenement() +
                                ", Date Debut: " + evenement.getDate_debut().toString() +
                                ", Date fin: " + evenement.getDate_fin().toString() +
                                ", Lieu: " + evenement.getLieu_evenement() +
                                ", budget: " + evenement.getBudget()
                );
                ImageView imageView = new ImageView();
                try {
                    File imageFile = new File(imageDirectory + evenement.getImage_evenement());
                    Image image = new Image(imageFile.toURI().toString());
                    // À l'intérieur du bloc try où vous chargez l'image
                    imageView.setImage(image);
                    imageView.setFitWidth(100); // Définir la largeur souhaitée de l'image
                    imageView.setPreserveRatio(true); // Préserver le rapport hauteur/largeur de l'image
                } catch (Exception e) {
                    // Gérer les erreurs de chargement d'image
                    System.err.println("Erreur de chargement de l'image : " + e.getMessage());
                    // Afficher un message d'erreur dans une boîte de dialogue d'alerte
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Erreur de chargement de l'image : " + e.getMessage());
                    alert.showAndWait();
                }

                eventLabel.setOnMouseClicked(event -> {
                    selectedEvenement = evenement;
                    ImageBtn.setText(selectedEvenement.getImage_evenement());
                    typeEvenement.setText(selectedEvenement.getType_evenement());
                    NomEvenement.setText(selectedEvenement.getNom_evenement());
                    DateDebut.setValue(selectedEvenement.getDate_debut().toLocalDate());
                    DateFin.setValue(selectedEvenement.getDate_fin().toLocalDate());
                    LieuEvenement.setText(selectedEvenement.getLieu_evenement());
                    Budget.setText(String.valueOf(selectedEvenement.getBudget()));

                });

                eventBox.getChildren().addAll(imageView, eventLabel); // Ajouter ImageView et Label à HBox
                EventVBox.getChildren().add(eventBox); // Ajouter HBox à VBox
            }



        } catch (SQLException e) {
            // Gérer les exceptions SQLException ici
            e.printStackTrace();
            // Afficher un message d'erreur dans une boîte de dialogue d'alerte
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors du chargement des événements : " + e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void OnClickedBack(ActionEvent event) {
        SE.changeScreen(event,"/com/example/pidevjava/Sign_in.fxml", " Accueille ");

    }

    @FXML
    void OnClickedTri(ActionEvent event) throws SQLException {
        if(i % 2 == 0){
            tri = "ASC";
        }else{
            tri = "DESC";

        }
        i++;
        refreshEvents();
    }

    @FXML
    public void onClickedCalendar(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/pidevjava/CalendarView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            CalendarViewController controller = fxmlLoader.getController();
            List<Evenement> events = SE.getAll();  // This method should fetch all events from your database
            controller.setEvents(events);
            stage.setTitle("Calendar View");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void OnClickPDF(ActionEvent event) {
        SE.changeScreen(event,"/com/example/pidevjava/Sign_in.fxml", " Accueille ");

    }

}
