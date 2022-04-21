package Graphic_Interface;

import java.io.IOException;
import java.net.URL;
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



    public SceneController() {
        super();
    }

    public void validateNewUser() throws IOException {
        System.out.println("validate data");
        //event.consume();

        if(name.getText() != null || name.getText() != "") {
            Account temporaneo = new Account();
            
            temporaneo.setName(name.getText());
            if(!application.checkAccaunt(temporaneo)) {
                System.out.println("accaunt non valido");
                //name.setText("nome non valido");
                label1.setVisible(true);
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
