package Graphic_Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import PlayListSongs.PlayList;
import emotionalsongs.Account;
import emotionalsongs.RegisteredAccount;
import emotionalsongs.UnregisteredAccount;
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
         
    }

    @FXML
    void NoAccount(ActionEvent event) throws IOException {

        this.application.ConnectedAccount = new UnregisteredAccount();
        Stage Window = (Stage) NoAccountButton.getScene().getWindow();
        super.SwitchScene(Window, "MainPage");
    }

    @FXML
    void CreateNewAccount(MouseEvent event) throws IOException {
        Stage Window = (Stage) NoAccountButton.getScene().getWindow();
        super.SwitchScene(Window, "UserRegistration");
    }

    @FXML
    void searchAccount(ActionEvent event) throws IOException 
    {
       

        if(userName!= null && password!=null && userName.getText().length() > 0 && password.getText().length() > 0 ) {
            
            RegisteredAccount TempAccount = new RegisteredAccount();
            
            if(userName.getText().contains("@")) {
                TempAccount = application.AccountsManager.SerachByEmail(userName.getText());
            }
            else {
                //TempAccount.setUs(userName.getText());
            }

            if(TempAccount != null && TempAccount.getPassword().equals(password.getText())) 
            {
                

                /*****test*****/
                PlayList p = new PlayList("prova");
                p.addSong(application.songManager.SongList.get(20));
                p.addSong(application.songManager.SongList.get(287));
                p.addSong(application.songManager.SongList.get(60));
                TempAccount.addPlaylist(p);

                System.out.println(p);
                application.ConnectedAccount = TempAccount;
        
                Stage Window = (Stage) NoAccountButton.getScene().getWindow();
                super.SwitchScene(Window, "MainPage");
            }

        }
            
    

        
    }


}