package Java.emotionalsongs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import Java.Account.Account;
import Java.Graphic_Interface.Controller;
import Java.Graphic_Interface.EnterController;
import Java.Graphic_Interface.MainPageController;
import Java.Graphic_Interface.NewUserRegistrationController;
import Java.Json.JsonParser;
import Java.PlayListSongs.Song;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class EmotionalSongs extends Application{

    // =================== percorsi =================== //

    private final String Directory = System.getProperty("user.dir");
    private final String UsersDataFilePath = "\\data\\UtentiRegistrati.json";
    private final String UsersDataFilePath2 = "\\data\\UtentiRegistrati2.json";   //prova
    private final String songsDataFilePath = "\\data\\canzoni.json";
    private final String songsDataFilePath2 = Directory + "\\data\\Song.csv";
    private final String SongDataFilePath  = "data.txt";    //?
    private final String FileFXML_path = "FXML/";
    private final String [] XML_Paths = {

        FileFXML_path + "UserRegistration.fxml",
        FileFXML_path + "MainPage.fxml",
        FileFXML_path + "MainPage_reposity.fxml",
        FileFXML_path + "MainPage_PLaylist.fxml",
        FileFXML_path + "MainPage_impostazioni.fxml",
        FileFXML_path + "LoadAccaunt.fxml",
        FileFXML_path + "AccessPage.fxml",
        FileFXML_path + "ongPageInformation.fxml"
    };


    // =================== variabili globali =================== //

    public static EmotionalSongs classReference;                                  //riferimento globale di questa classe
    public ArrayList<Song> ArchivioGolobaleCanzoni = new ArrayList<Song>();
    public HashMap<String, String> pageLoaders = new HashMap<String, String>();         
    public Account ConnectedAccount;                                             //Account in utilizzo
    public Stage mainStage;                                                      //finestra principale
    public SongManager songManager;
    public AccountsManager AccountsManager;

    // =================== variabili locali =================== //
    

       
    public static void main(String[] args) throws Exception {
        System.out.println("Application Runnning...\n");
        
        //new EmotionalSongs();
        launch(args);
    }


    @Override
    public void start(Stage stage) 
    {
        EmotionalSongs.classReference = this;
        this.mainStage = stage;

        songManager = new SongManager(this);
        AccountsManager = new AccountsManager(Directory + UsersDataFilePath);
        
        songManager.LoadSongs(songsDataFilePath2);

        try {
            System.out.println("Accounts Credentials Recovery:");
            AccountsManager.LoadAccounts();
            System.out.println();
            System.out.println("\nLoading Songs:");
            songManager.LoadSongs(songsDataFilePath2);
            //LoadSongs();
            System.out.println("\n\nStart loading XML file...");

            for(String path : XML_Paths) 
            {
                String key = path.split("/")[path.split("/").length - 1].replace(".fxml", "");
                pageLoaders.put(key, path);
                System.out.println("loading " + path);
            }

            System.out.println("\nLoading Completed\n");
            
                //lambda function 
            stage.setOnCloseRequest(event -> {
                event.consume();
                logout(stage);
            });

            //pagina iniziale
            stage.setTitle("EmotionaSong");
            changeScreen(stage, "AccessPage");
            stage.show();
           
            System.out.println("starting..."); 
        

        } catch(NullPointerException e) {
            System.out.println("file non trovato, errore nel percorso del file fxml");

        /*} catch (IOException e) {
            System.out.println(e);  
            e.printStackTrace(); */

        } catch (Exception e) {
            System.out.println(e);  
            e.printStackTrace(); 
        }
    }

    public void logout(Stage stage) {
        AccountsManager.SaveAccounts(Directory + UsersDataFilePath2);
        stage.close();
    }

    public EmotionalSongs() throws IOException
    {

    }

    /*@SuppressWarnings("unchecked")
    private boolean LoadSongs()
    {
        jsonFileReader = new Json(Directory + songsDataFilePath);
        LinkedHashMap<String, Object> Accounts = jsonFileReader.ReadJsonFile();

        for(String UserKey : Json.getKeys(Accounts)) {
            ArchivioGolobaleCanzoni.add(new Song((LinkedHashMap<String, Object>) Json.GetElement(Accounts, Arrays.asList(UserKey))));
        }
        return true;
    }*/

    

    public void changeScreen(Stage stage, String name) throws IOException 
    {
        FXMLLoader XMLloader = new FXMLLoader(getClass().getClassLoader().getResource(pageLoaders.get(name)));
        Scene scene = new Scene(XMLloader.load());
        stage.setScene(scene);
        stage.show();
    }

    public FXMLLoader getNewStageWindow(String name) throws IOException  {
        return new FXMLLoader(getClass().getClassLoader().getResource(pageLoaders.get(name)));
    }
}
