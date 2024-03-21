package controllers;
import entities.User;
import services.ServiceUser;
//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.xml.JRXmlLoader;
//import net.sf.jasperreports.view.JasperViewer;
import utils.MyDB;
public class Admin implements Initializable {
    @FXML
    private TableView<User> tableviewUser;
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
    private TextField Recherche_User;


    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    User user = null ;
    /**
     * Initializes the controller class.
     */
    @FXML
    public void exit(){
        System.exit(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showRec();
        searchRec();
    }
    public  ObservableList<User> getUserList() {
        cnx = MyDB.getInstance().getConnection();

        ObservableList<User> UserList = FXCollections.observableArrayList();
        try {
            String query2="SELECT * FROM  user ";
            PreparedStatement smt = cnx.prepareStatement(query2);
            User user;
            ResultSet rs= smt.executeQuery();
            while(rs.next()){
                user=new User(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("mail"),
                        rs.getString("role"),
                        rs.getString("numeroTelephone"),
                        rs.getString("password"),
                        rs.getString("ville"),
                        rs.getString("sexe"),
                        rs.getString("profile_image"),
                        rs.getBoolean("is_verified"),
                        rs.getBoolean("status"),
                        rs.getString("reset_token")
                );
                UserList.add(user);
            }
            System.out.println(UserList);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return UserList;

    }

    public void showRec(){

        ObservableList<User> list = getUserList();
        idUser.setCellValueFactory(new PropertyValueFactory<>("id"));
        NomUser.setCellValueFactory(new PropertyValueFactory<>("nom"));
        PrenomUser.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        EmailUser.setCellValueFactory(new PropertyValueFactory<>("mail"));
        RoleUser.setCellValueFactory(new PropertyValueFactory<>("role"));
        NumerotlfUser.setCellValueFactory(new PropertyValueFactory<>("numeroTelephone"));

        tableviewUser.setItems(list);

    }
    private void refresh(){
        ObservableList<User> list = getUserList();
        idUser.setCellValueFactory(new PropertyValueFactory<>("id"));
        NomUser.setCellValueFactory(new PropertyValueFactory<>("nom"));
        PrenomUser.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        EmailUser.setCellValueFactory(new PropertyValueFactory<>("mail"));
        RoleUser.setCellValueFactory(new PropertyValueFactory<>("role"));
        NumerotlfUser.setCellValueFactory(new PropertyValueFactory<>("numeroTelephone"));

        tableviewUser.setItems(list);

    }
    @FXML
    private void SupprimerUser(ActionEvent event) {
        ServiceUser u=new ServiceUser();
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
    private void ModifierUser(ActionEvent event) {
        user = tableviewUser.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("/Gui/ModifierUser.fxml"));
        try {
            loader.load();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        ModifierUserController muc = loader.getController();
        // mrc.setUpdate(true);
        muc.setTextFields(user);
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        showRec();

    }

    private void searchRec() {
        idUser.setCellValueFactory(new PropertyValueFactory<>("id"));
        CinUser.setCellValueFactory(new PropertyValueFactory<>("CIN"));
        Username.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        NumeroUser.setCellValueFactory(new PropertyValueFactory<>("numero"));
        EmailUser.setCellValueFactory(new PropertyValueFactory<>("email"));
        AdresseUser.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        ObservableList<User> list = getUserList();
        tableviewUser.setItems(list);
        FilteredList<User> filteredData = new FilteredList<>(list,b->true);
        Recherche_User.textProperty().addListener((observable,oldValue,newValue)-> {
            filteredData.setPredicate(rec-> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (rec.getEmail().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                    return true;
                }else if (rec.getUserName().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                    return true;
                }
                else
                    return false ;

            });
        });
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableviewUser.comparatorProperty());
        tableviewUser.setItems(sortedData);

    }

    @FXML
    private void pdf_user(ActionEvent event) {
        System.out.println("hello");
        try{

            JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\ASUS\\OneDrive\\Documents\\NetBeansProjects\\login\\src\\login\\report.jrxml");

            JasperReport jReport = JasperCompileManager.compileReport(jDesign);

            JasperPrint jPrint = JasperFillManager.fillReport(jReport, null, cnx);

            JasperViewer viewer = new JasperViewer(jPrint, false);

            viewer.setTitle("Liste des Utilistaeurs");
            viewer.show();
            System.out.println("hello");


        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
