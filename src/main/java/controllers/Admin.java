package controllers;

import com.google.gson.Gson;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
 import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import net.glxn.qrgen.image.ImageType;
import net.glxn.qrgen.QRCode;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import services.ServiceUser;
import utils.MyDB;

import javax.swing.text.Element;

import java.awt.*;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
public class Admin implements Initializable {
    @FXML
    private TableView<User> tableviewUser;
    @FXML
    private ImageView qrCodeImage;
    @FXML
    private TableColumn<?, ?> idUser;
    @FXML
    private TableColumn<?, ?> NomUser;
    @FXML
    private TableColumn<?, ?> PrenomUser;
    @FXML
    private TableColumn<?, ?> EmailUser;
    @FXML
    private TableColumn<?, ?> RoleUser;
    @FXML
    private TableColumn<?, ?> NumerotlfUser;
    @FXML
    private TableColumn<?, ?> VilleUser;
    @FXML
    private TableColumn<?, ?> SexeUser;
    @FXML
    private TableColumn<?, ?> PasswordUser;

    @FXML
    private TextField Recherche_User;
    @FXML
    private Button pdfButton;
    @FXML
    private Button QrCodeButton;
    @FXML
    private Button logOut;

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    User user = null;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Sign_in.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

        // Close the sign-in window
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showRec();
        searchRec();
        pdfButton.setOnAction(event -> {
            pdf_user();
        });
    }

    public ObservableList<User> getUserList() {
        cnx = MyDB.getInstance().getConnection();

        ObservableList<User> UserList = FXCollections.observableArrayList();
        try {
            String query2 = "SELECT * FROM  user ";
            PreparedStatement smt = cnx.prepareStatement(query2);
            User user;
            ResultSet rs = smt.executeQuery();
            while (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("mail"),
                        rs.getString("role"),
                        rs.getString("numeroTelephone"),
                        rs.getString("password"),
                        rs.getString("ville"),
                        rs.getString("sexe")

                );
                UserList.add(user);
            }
            System.out.println(UserList);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return UserList;

    }

    public void showRec() {

        ObservableList<User> list = getUserList();
        idUser.setCellValueFactory(new PropertyValueFactory<>("id"));
        NomUser.setCellValueFactory(new PropertyValueFactory<>("nom"));
        PrenomUser.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        EmailUser.setCellValueFactory(new PropertyValueFactory<>("mail"));
        RoleUser.setCellValueFactory(new PropertyValueFactory<>("role"));
        NumerotlfUser.setCellValueFactory(new PropertyValueFactory<>("numeroTelephone"));
        PasswordUser.setCellValueFactory(new PropertyValueFactory<>("password"));
        VilleUser.setCellValueFactory(new PropertyValueFactory<>("ville"));
        SexeUser.setCellValueFactory(new PropertyValueFactory<>("sexe"));


        tableviewUser.setItems(list);

    }

    private void refresh() {
        ObservableList<User> list = getUserList();
        idUser.setCellValueFactory(new PropertyValueFactory<>("id"));
        NomUser.setCellValueFactory(new PropertyValueFactory<>("nom"));
        PrenomUser.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        EmailUser.setCellValueFactory(new PropertyValueFactory<>("mail"));
        RoleUser.setCellValueFactory(new PropertyValueFactory<>("role"));
        NumerotlfUser.setCellValueFactory(new PropertyValueFactory<>("numeroTelephone"));
        PasswordUser.setCellValueFactory(new PropertyValueFactory<>("password"));
        VilleUser.setCellValueFactory(new PropertyValueFactory<>("ville"));
        SexeUser.setCellValueFactory(new PropertyValueFactory<>("sexe"));

        tableviewUser.setItems(list);

    }

    @FXML
    private void SupprimerUser(ActionEvent event) {
        ServiceUser u = new ServiceUser();
        //       commandeplat t = tvcommande.getSelectionModel().getSelectedItem();
        User user = (User) tableviewUser.getSelectionModel().getSelectedItem();
        //  Plat p = new Plat(c.getreference());
        u.supprimer(user);
        refresh();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("DocDirect  :: Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Utilisateur supprimé");
        alert.showAndWait();

    }

    @FXML
    private void AjouterUser(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterUser.fxml"));
            Parent root = loader.load();

            // Hide the current window (admin window)
            ((Node)(event.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene (modifier window)
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajout User");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading AjoutUser: " + e.getMessage());
        }
    }
    @FXML
    void evenements( ActionEvent event)  {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackEvenement.fxml"));
            Parent root = loader.load();
            ((Node)(event.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("evenement");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading evenement: " + e.getMessage());
        }

    }
    @FXML
    void hopital( ActionEvent event)  {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hopitals.fxml"));
            Parent root = loader.load();
            ((Node)(event.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("hopitals");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading hopitals: " + e.getMessage());
        }

    }
    @FXML
    void blogs( ActionEvent event)  {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TopActiveBlogs.fxml"));
            Parent root = loader.load();
            ((Node)(event.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("blogs");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading blogs: " + e.getMessage());
        }

    }
    @FXML
    void certificat( ActionEvent event)  {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/certificat.fxml"));
            Parent root = loader.load();
            ((Node)(event.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("certificat");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading certificat: " + e.getMessage());
        }

    }
    @FXML
    void labos( ActionEvent event)  {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/labos.fxml"));
            Parent root = loader.load();
            ((Node)(event.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("labos");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error labos: " + e.getMessage());
        }

    }
    @FXML
    void sponsor( ActionEvent event)  {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackSponsor.fxml"));
            Parent root = loader.load();
            ((Node)(event.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("sponsors");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error sponsors: " + e.getMessage());
        }

    }

    @FXML
    private void ModifierUser(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierUser.fxml"));
            Parent root = loader.load();

            // Hide the current window (admin window)
            ((Node)(event.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene (modifier window)
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier User");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading ModifierUser: " + e.getMessage());
        }
    }

    @FXML
    void gotoproduits(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestion-produits-admin.fxml"));
            Parent root = loader.load();

            // Hide the current window (admin window)
            ((Node)(event.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene (modifier window)
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier User");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading ModifierUser: " + e.getMessage());
        }
    }

    @FXML
    private void trie_user(ActionEvent event) {
        ObservableList<User> list = getUserList();

        // Create a SortedList to sort the users
        SortedList<User> sortedList = new SortedList<>(list, (user1, user2) -> {
            // Compare users based on their IDs in descending order
            return Integer.compare(user2.getId(), user1.getId());
        });

        // Bind the sorted list to the tableview's comparator
        tableviewUser.setItems(sortedList);

    }

    private void searchRec() {
        idUser.setCellValueFactory(new PropertyValueFactory<>("id"));
        NomUser.setCellValueFactory(new PropertyValueFactory<>("nom"));
        PrenomUser.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        NumerotlfUser.setCellValueFactory(new PropertyValueFactory<>("numeroTelephone"));
        EmailUser.setCellValueFactory(new PropertyValueFactory<>("mail"));
        RoleUser.setCellValueFactory(new PropertyValueFactory<>("role"));
        ObservableList<User> list = getUserList();
        tableviewUser.setItems(list);
        FilteredList<User> filteredData = new FilteredList<>(list, b -> true);
        Recherche_User.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(rec -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (rec.getMail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (rec.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (rec.getPrenom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false;

            });
        });
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableviewUser.comparatorProperty());
        tableviewUser.setItems(sortedData);

    }
    @FXML
    private void stat_user(ActionEvent event)
    {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StatUser.fxml"));
            Parent root = loader.load();

            // Create a new stage for the loaded FXML scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Stats User");
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading Stats User: " + e.getMessage());
        }

    }
    @FXML
    private void qr_code() {
        // Get user list data (assuming it's in JSON format)
        ObservableList<User> userList = getUserList();
        String userListJson = convertUserListToJson(userList);

        // Generate QR code
        ByteArrayOutputStream outputStream = QRCode.from(userListJson).to(ImageType.PNG).stream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        // Display QR code image
        javafx.scene.image.Image qrCodeImage = new javafx.scene.image.Image(inputStream);
        ImageView imageView = new ImageView(qrCodeImage);

        // Add imageView to a dialog or a new stage to display it to the user
        // Example: create a new stage and show the QR code
        Stage qrCodeStage = new Stage();
        qrCodeStage.setScene(new Scene(new StackPane(imageView)));
        qrCodeStage.setTitle("QR Code - User List");
        qrCodeStage.show();
    }

    // Convert user list to JSON format (you may need to use a JSON library like Gson)
    private String convertUserListToJson(ObservableList<User> userList) {
        // Implement this method according to your user list structure
        // Example:
        Gson gson = new Gson();
        return gson.toJson(userList);
    }
    @FXML
    private void pdf_user() {
        try {
            // Create a new PDF document
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Set up content stream
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Load font
            PDType0Font font = PDType0Font.load(document, getClass().getResourceAsStream("/fonts/CairoPlay-VariableFont_slnt,wght.ttf"));

            // Define margin and starting y position
            float margin = 50;
            float yPosition = page.getMediaBox().getHeight() - margin;

            // Define column widths and row height
            float columnWidth = 100;
            float rowHeight = 20;
             //Add logo image
            InputStream logoStream = getClass().getResourceAsStream("/logo pidev.png");
            if (logoStream == null) {
                System.out.println("Logo image not found!");
                return;
            }
            PDImageXObject logoImage = PDImageXObject.createFromByteArray(document, IOUtils.toByteArray(logoStream), "logo.png");
            float logoWidth = 150;
            float logoHeight = logoWidth * logoImage.getHeight() / logoImage.getWidth();
            float centerX = (page.getMediaBox().getWidth() - logoWidth) / 2;
            float centerY = yPosition - logoHeight - 260; // Adjusted position to leave space below the logo
            contentStream.drawImage(logoImage, centerX, centerY, logoWidth, logoHeight);
            // Add header row
            drawTableRow(contentStream, font, margin, yPosition, columnWidth, rowHeight,
                    "ID", "Name", "Email", "Role", "Sexe", "Ville", "Numero Tel", "Password");

            // Add user data rows
            ObservableList<User> userList = tableviewUser.getItems();
            for (User user : userList) {
                yPosition -= rowHeight;
                drawTableRow(contentStream, font, margin, yPosition, columnWidth, rowHeight,
                        String.valueOf(user.getId()), user.getNom() + " " + user.getPrenom(), user.getMail(),
                        user.getRole(), user.getSexe(), user.getVille(), user.getNumeroTelephone(), user.getPassword());
            }

            // Close content stream
            contentStream.close();

            // Save and open PDF file
            File file = new File("ListOfUsers.pdf");
            document.save(file);
            document.close();

            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawTableRow(PDPageContentStream contentStream, PDType0Font font, float startX, float yPosition, float columnWidth, float rowHeight, String... columns) throws IOException {
        float nextX = startX;
        for (String column : columns) {
            contentStream.setFont(font, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(nextX, yPosition);
            contentStream.showText(column);
            contentStream.endText();
            nextX += columnWidth;
        }
    }

}
