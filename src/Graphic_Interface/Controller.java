package Graphic_Interface;

import emotionalsongs.EmotionalSongs;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {
    
    protected EmotionalSongs application;

    public Controller() {
        this.application = EmotionalSongs.classReference;
    }

    public void SwitchScene(Stage Window, String SceneName)
    {
        Scene scene = new Scene(this.application.pageLoaders.get(SceneName));
        Window.setScene(scene);
        Window.show();
    }
}
