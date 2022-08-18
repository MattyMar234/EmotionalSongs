package Java.Graphic_Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Java.Account.Account;
import Java.Account.RegisteredAccount;
import Java.Account.UnregisteredAccount;
import Java.PlayList_Songs.PlayList;
import Java.emotionalsongs.EmotionalSongs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AccessController extends Controller implements Initializable {

    @FXML private Button LoginButton;
    @FXML private Label NewAccount;
    @FXML private Button NoAccountButton;
    @FXML private AnchorPane labelButton;
    @FXML private AnchorPane pane1;
    @FXML private PasswordField password;
    @FXML private TextField userName;

    @FXML private Label LabelName;
    @FXML private Label labelPassword;


    private final static String [][] matrice = {
        {"Accedi all'Account", "Login"},     //LoginButton
        {"Continua senza Account", "Continue without account"}, //  //NoAccountButton
        {"Crea un Account", "Create Account"}           //NewAccount
    };
    
    
    public AccessController() {
        super();
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {

        LoginButton.setText(AccessController.matrice[0][EmotionalSongs.language]);
        NoAccountButton.setText(AccessController.matrice[1][EmotionalSongs.language]);
        NewAccount.setText(AccessController.matrice[2][EmotionalSongs.language]);
        


        clearError();
       
        //non funzionano ??? 
        if(this.windwoPosWidth < 1200.0) {
            this.windwoPosWidth = 1200.0;
            this.application.mainStage.setWidth(this.windwoPosWidth);
        }


        if(this.windwoPosHeight < 1000.0) {
            this.windwoPosHeight = 1000.0;
            this.application.mainStage.setHeight(this.windwoPosHeight);
        }
    }

    @FXML
    void NoAccount(ActionEvent event) throws IOException {

        this.application.ConnectedAccount = new UnregisteredAccount();
        Stage Window = (Stage) NoAccountButton.getScene().getWindow();
        super.SwitchScene("MainPage");
    }

    @FXML
    void CreateNewAccount(MouseEvent event) throws IOException {
        Stage Window = (Stage) NoAccountButton.getScene().getWindow();
        super.SwitchScene("UserRegistration");
    }

    @FXML
    void searchAccount(ActionEvent event) throws IOException 
    {
        RegisteredAccount TempAccount = new RegisteredAccount();
        boolean error = false;

        clearError();

        //verifico validità del campo
        if(userName == null || userName.getText().length() == 0) {
            this.LabelName.setText("missing data");
            this.userName.setStyle("-fx-border-color: #a50303;");
            this.LabelName.setVisible(true);
            error = true;
        }
        else {
            //verifica email
            if(userName.getText().contains("@")) {
                TempAccount = application.AccountsManager.SearchByEmail(userName.getText());
                this.LabelName.setText("invalid email");
            }
            //verifica userID
            else {
                TempAccount = application.AccountsManager.SearchByID(userName.getText());
                this.LabelName.setText("invalid user ID");
            }

            if(TempAccount == null) {
                this.userName.setStyle("-fx-border-color: #a50303;");
                this.LabelName.setVisible(true);
                error = true;
            }
        }

        //verifico validità del campo
        if(password == null || password.getText().length() == 0) {
            this.labelPassword.setText("missing data");
            this.password.setStyle("-fx-border-color: #a50303;");
            this.labelPassword.setVisible(true);
            error = true;
        }


        if(error) {
            return;
        }

        if(!TempAccount.getPassword().equals(password.getText())) {
            this.labelPassword.setText("incorrect password");
            this.password.setStyle("-fx-border-color: #a50303;");
            this.password.clear();
            this.labelPassword.setVisible(true);
            return;
        }
            

        /*****test*****/
        /* PlayList p = new PlayList("prova");
        p.addSong(application.songManager.getElement(20));
        p.addSong(application.songManager.getElement(287));
        p.addSong(application.songManager.getElement(60));
        TempAccount.addPlaylist(p);*/

        application.ConnectedAccount = TempAccount;

        Stage Window = (Stage) NoAccountButton.getScene().getWindow();
        super.SwitchScene("MainPage");
        

    }  



    private void clearError() {
        this.LabelName.setVisible(false);
        this.labelPassword.setVisible(false);
        this.userName.setStyle("-fx-border-color: transparent;");
        this.password.setStyle("-fx-border-color: transparent;");
    }




}