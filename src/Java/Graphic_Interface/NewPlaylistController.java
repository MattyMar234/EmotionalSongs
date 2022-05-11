package Java.Graphic_Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class NewPlaylistController extends Controller implements Initializable {


    // ========================= Buttons ========================= //

    @FXML private Button AnnullaPlaylistButton;
    @FXML private Button newPlayListButton;

    // ========================= variabili =========================//
    MainPageController mainPageReference;

   

    public NewPlaylistController() throws IOException {
        super();
        this.mainPageReference = MainPageController.MainPageControllerReference;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
 
        
    }


    @FXML
    void ConfermeNewPlayList(ActionEvent event) throws IOException {
        //...
        mainPageReference.SetPlayListPage();
    }

    @FXML
    void turnBack(ActionEvent event) throws IOException {
        mainPageReference.SetPlayListPage();
    }
    
}
