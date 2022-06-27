package Java.Graphic_Interface;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Java.emotionalsongs.EmotionalSongs;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class WindowContainerController extends Controller implements Initializable {

    public BorderPane anchor;

    public WindowContainerController() {
       
       
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {  
        EmotionalSongs.windowPageReference = this;
        
        if(application.skipLogin) {
            anchor.setMinWidth(1000);
            anchor.setMinHeight(800);
            try {
                SwitchScene("MainPage"); 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            anchor.setMinWidth(800);
            anchor.setMinHeight(800);
            try {
                SwitchScene("AccessPage"); 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }


}

