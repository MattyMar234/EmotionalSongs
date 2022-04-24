package Graphic_Interface;

import java.io.IOException;

import emotionalsongs.EmotionalSongs;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {
    
    protected EmotionalSongs application;

    public Controller() {
        this.application = EmotionalSongs.classReference;
    }

    public void SwitchScene(Stage Window, String SceneName) throws IOException {
        this.application.changeScreen(Window, SceneName);
    }
}
