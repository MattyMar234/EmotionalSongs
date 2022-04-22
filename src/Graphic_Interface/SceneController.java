package Graphic_Interface;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import emotionalsongs.Account;
import emotionalsongs.EmotionalSongs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class SceneController extends Controller implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private Button confirmButton;
    @FXML private TextField name;
    @FXML private TextField surname;
    @FXML private TextField userID;
    @FXML private TextField email;
    @FXML private PasswordField password1;
    @FXML private PasswordField password2;

    @FXML private TextField civicNumber;
    @FXML private TextField cap;
    @FXML private TextField comune;
    @FXML private TextField provincia;
    @FXML private TextField codiceFiscale;
    @FXML private TextField viaPiazza;

    @FXML private Label label1;

    private ArrayList<TextField> fileds = new ArrayList<TextField>();



    public SceneController() {
        super();
        fileds.add(name);
    }

    public void validateNewUser() throws IOException {
        System.out.println("validate data");
        //event.consume();

        if(name.getText() != null || name.getText() != "") 
        {
            String [] data = new String[4];
            
            data[0] = name.getText();
            data[1] = surname.getText();
            data[2] = email.getText();
            data[3] = password1.getText();

            Account testAccount = new Account(data);
            System.out.println(testAccount);
        
            int result = application.checkAccaunt(testAccount);
            if(result == 1) {
                System.out.println("email o ID già utilizzata");
                label1.setVisible(true);
            }
            else if(result > 1)
            {
                System.out.println("credenziali non valide");
            }
            else {
                //event.consume();
                Stage Window = (Stage) confirmButton.getScene().getWindow();
                super.SwitchScene(Window, "MainPage");
                /*stage.setOnCloseRequest(event -> {
                    event.consume();
                    application.logout(stage);
                });*/

            
            }
        }
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label1.setVisible(false);
        label1.setText("non valido");
    }

    @FXML
    void typed(KeyEvent event) {
        label1.setVisible(false);
    }


}
