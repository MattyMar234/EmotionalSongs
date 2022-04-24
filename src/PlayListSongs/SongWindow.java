package PlayListSongs;

import Graphic_Interface.SongPageController;
import emotionalsongs.EmotionalSongs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SongWindow {

    protected EmotionalSongs main;
    protected Song selectedSong;
    public int test;
    public int test2;

    //costructor
    public SongWindow(EmotionalSongs main, Song selectedSong) throws Exception 
    {
        this.main = main;
        this.selectedSong = selectedSong;
        
        Stage stage = new Stage();
        start(stage);
    }

    
    public void start(Stage stage) throws Exception 
    {
        System.out.println("starting song window");
        
        FXMLLoader XMLloader = new FXMLLoader(getClass().getClassLoader().getResource(main.pageLoaders.get("SongPageInformation")));
        //per utilizzare il costruttore della classe
        XMLloader.setControllerFactory(c -> {    
            return new SongPageController(this.main, this.selectedSong); // <-- parametri costruttore classe
        });

        Scene scene = new Scene(XMLloader.load());
        stage.setTitle("Song Information");
        stage.setScene(scene);
        stage.show();
        
        System.out.println("starting..."); 
        
    }
    
}
