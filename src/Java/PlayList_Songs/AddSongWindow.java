package Java.PlayList_Songs;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.ldap.HasControls;

import Java.Graphic_Interface.AddSongConstroller;
import Java.Graphic_Interface.NewPlaylistController;
import Java.emotionalsongs.EmotionalSongs;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddSongWindow {

    protected NewPlaylistController controller;
    public EmotionalSongs main;
    protected Stage stage;
    public int mode;
    public HashMap<String, ArrayList<String>> dataFilter;

    public AddSongWindow(NewPlaylistController controller, EmotionalSongs main, int mode, HashMap<String, ArrayList<String>> dataFilter) throws Exception {
        this.controller = controller;
        this.main = main;
        this.mode = mode;
        this.dataFilter = dataFilter;

        this.stage = new Stage();
        start();
    }

    public void close() throws Exception {
        stage.close();
    }

    public void start() throws Exception 
    {
        System.out.println("starting window");
        
        FXMLLoader XMLloader = new FXMLLoader(getClass().getClassLoader().getResource(main.pageLoaders.get("Playlist_AddSongPage")));
        //per utilizzare il costruttore della classe
        XMLloader.setControllerFactory(c -> {    
            return new AddSongConstroller(this); // <-- parametri costruttore classe
        });

        Scene scene = new Scene(XMLloader.load());
        stage.setScene(scene);
        stage.show();
      
        
    }

    public void returnSelectedData(ArrayList<Song> song) {
        this.controller.addSelectedSong(song);
    }
    
}
