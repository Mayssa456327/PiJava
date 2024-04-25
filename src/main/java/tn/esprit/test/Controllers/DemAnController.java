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
import tn.esprit.test.models.DemAn;
import tn.esprit.test.services.DemAnService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class DemAnController implements Initializable {
    private final DemAnService demAnService = new DemAnService();

    @FXML
    private TableView<DemAn> tableDemAn;

    @FXML
    private TableColumn<DemAn, Integer> colId;

    @FXML
    private TableColumn<DemAn, String> colnomp11;

    @FXML
    private TableColumn<DemAn, String> colidp11;

    @FXML
    private TableColumn<DemAn, String> colnomm11;

    @FXML
    private TableColumn<DemAn, String> coldes11;
    @FXML
    private TableColumn<DemAn, String> colttype;



    @FXML
    private Button dempage;

    @FXML
    private Button certpage;

    @FXML
    private Button ordpage;

    @FXML
    private TextField tnm;

    @FXML
    private TextField tnp;

    @FXML
    private TextField tidp;

    @FXML
    private TextArea tdes;
    @FXML
    private TextField ttype;


    @FXML
    void page(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/Fxml/Ords.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        this.ordpage.getScene().getWindow().hide();
    }

    @FXML
    void page1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/Fxml/Certs.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        this.certpage.getScene().getWindow().hide();
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
    void createDemAn(ActionEvent event) {
        String nomM = tnm.getText();
        String nomP = tnp.getText();
        String IDP = tidp.getText();
       // String type = ttype.getText();
        String description = tdes.getText();


        if (nomP.isEmpty() || IDP.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        } else {
            DemAn demAn = new DemAn(nomP, IDP, nomM, description, "");
            try {
                demAnService.add(demAn);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Demande d'analyse ajoutée avec succès!");
                alert.showAndWait();
                refresh();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Erreur lors de l'ajout de la demande d'analyse : " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    void updateDemAn(ActionEvent event) {
        DemAn selectedDemAn = tableDemAn.getSelectionModel().getSelectedItem();
        if (selectedDemAn == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une demande d'analyse à mettre à jour.");
            alert.showAndWait();
            return;
        }

        Dialog<DemAn> dialog = new Dialog<>();
        dialog.setTitle("Mise à jour de la demande d'analyse");
        dialog.setHeaderText("Mise à jour des informations de la demande d'analyse");

        ButtonType updateButtonType = new ButtonType("Mettre à jour", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField tnm = new TextField();
        tnm.setText(selectedDemAn.getNomM());
        TextField tnp = new TextField();
        tnp.setText(selectedDemAn.getNomP());
        TextField tidp = new TextField();
        tidp.setText(selectedDemAn.getIDP());
        TextArea tdes = new TextArea();
        tdes.setText(selectedDemAn.getDescription());


        grid.add(new Label("Nom du médecin:"), 0, 0);
        grid.add(tnm, 1, 0);
        grid.add(new Label("Nom du patient:"), 0, 1);
        grid.add(tnp, 1, 1);
        grid.add(new Label("ID du patient:"), 0, 2);
        grid.add(tidp, 1, 2);
        grid.add(new Label("Description:"), 0, 3);
        grid.add(tdes, 1, 3);

        dialog.getDialogPane().setContent(grid);

        Node updateButton = dialog.getDialogPane().lookupButton(updateButtonType);
        updateButton.setDisable(true);

        tnm.textProperty().addListener((observable, oldValue, newValue) -> updateButton.setDisable(newValue.trim().isEmpty()));
        tnp.textProperty().addListener((observable, oldValue, newValue) -> updateButton.setDisable(newValue.trim().isEmpty()));
        tidp.textProperty().addListener((observable, oldValue, newValue) -> updateButton.setDisable(newValue.trim().isEmpty()));
        tdes.textProperty().addListener((observable, oldValue, newValue) -> updateButton.setDisable(newValue.trim().isEmpty()));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                String idp = tidp.getText().trim();
                if (idp.length() > 8) {
                    idp = idp.substring(0, 8);
                }
                return new DemAn(tnp.getText(), idp, tnm.getText(), tdes.getText(), "");
            }
            return null;
        });

        Optional<DemAn> result = dialog.showAndWait();

        result.ifPresent(updatedDemAn -> {
            try {
                updatedDemAn.setId(selectedDemAn.getId());
                demAnService.update(updatedDemAn);
                refresh();
            } catch (SQLException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Erreur lors de la mise à jour de la demande d'analyse : " + e.getMessage());
                errorAlert.showAndWait();
            }
        });
    }

    @FXML
    public void deleteDemAn() {
        DemAn demAnToDelete = tableDemAn.getSelectionModel().getSelectedItem();

        if (demAnToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une demande d'analyse à supprimer.");
            alert.showAndWait();
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette demande d'analyse ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                demAnService.delete(demAnToDelete.getId());
                tableDemAn.getItems().remove(demAnToDelete);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Succès");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Demande d'analyse supprimée avec succès !");
                successAlert.showAndWait();
            } catch (SQLException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Erreur lors de la suppression de la demande d'analyse : " + e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colnomp11.setCellValueFactory(new PropertyValueFactory<>("NomP"));
        colidp11.setCellValueFactory(new PropertyValueFactory<>("IDP"));
        colnomm11.setCellValueFactory(new PropertyValueFactory<>("NomM"));
        coldes11.setCellValueFactory(new PropertyValueFactory<>("Description"));
        //colttype.setCellValueFactory(new PropertyValueFactory<>("Type"));

        refresh();
    }

    @FXML
    private void refresh() {
        try {
            tableDemAn.getItems().setAll(demAnService.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Erreur lors du rafraîchissement des demandes d'analyse : " + e.getMessage());
            errorAlert.showAndWait();
        }
    }
}
