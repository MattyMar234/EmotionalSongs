package Java.Graphic_Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Java.PlayList_Songs.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class EmotionPageController extends Controller implements Initializable{

    private Song canzoneAssociata;
    private MainPageController mainController;

    @FXML private Button back;

    public EmotionPageController() {
        super();
    }

    public EmotionPageController(Song song, MainPageController mainController) {
        this.canzoneAssociata = song;
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
                
    }
    

    @FXML
    void turnBack(ActionEvent event) throws IOException {
        this.mainController.Emotion_To_repository();
    }

}
