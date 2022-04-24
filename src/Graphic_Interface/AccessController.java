package Graphic_Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    @FXML private TextField password;
    @FXML private TextField userName;
    
    
    public AccessController() {
        super();
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        
    }

    @FXML
    void NoAccount(ActionEvent event) throws IOException {
        Stage Window = (Stage) NoAccountButton.getScene().getWindow();
        super.SwitchScene(Window, "MainPage");
    }

    @FXML
    void CreateNewAccount(MouseEvent event) throws IOException {
        Stage Window = (Stage) NoAccountButton.getScene().getWindow();
        super.SwitchScene(Window, "UserRegistration");
    }

}