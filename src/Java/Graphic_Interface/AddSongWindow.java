package Java.Graphic_Interface;

import java.util.ArrayList;
import java.util.HashMap;

import Java.PlayListSongs.PlayList;
import Java.emotionalsongs.EmotionalSongs;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddSongWindow {

    public EmotionalSongs main;
    private Object controller;
    protected Stage stage;
    public int mode;
    public PlayList data;

    public AddSongWindow(Object Controller, EmotionalSongs main, int mode, PlayList data) throws Exception {

        this.main = main;
        this.mode = mode;
        this.data = data;
        this.controller = Controller;

        this.stage = new Stage();
        start();
    }

    public void close() throws Exception {

        if(controller instanceof NewPlaylistController) {
            ((NewPlaylistController)controller).addSelectedSong(data.getSongs());
        }
        else if(controller instanceof EditPlaylistController) {
            ((EditPlaylistController)controller).updateTable();
        }

        stage.close();
    }

    public void start() throws Exception 
    {
        FXMLLoader XMLloader = new FXMLLoader(getClass().getClassLoader().getResource(main.pageLoaders.get("Playlist_AddSongPage")));
        //per utilizzare il costruttore della classe
        XMLloader.setControllerFactory(c -> {    
            return new AddSongConstroller(this); // <-- parametri costruttore classe
        });

        Scene scene = new Scene(XMLloader.load());
        stage.setScene(scene);
        stage.show();
      
        
    }
    
}
