package emotionalsongs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import Graphic_Interface.EnterController;
import Graphic_Interface.MainPageController;
import Graphic_Interface.SceneController;
import Graphic_Interface.Controller;
import JsonFile.Json;
import PlayListSongs.Song;
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
    private final String SongDataFilePath  = "data.txt";    //?
    private final String [] XML_Paths = {

        "fxml_Page/UserRegistration.fxml",
        "fxml_Page/MainPage.fxml",
        "fxml_Page/LoadAccaunt.fxml",
        "fxml_Page/AccessPage.fxml"
    };


    // =================== variabili globali =================== //
    public static EmotionalSongs classReference;
    public ArrayList<Song> ArchivioGolobaleCanzoni = new ArrayList<Song>();
    public ArrayList<Account> Users = new ArrayList<>();
    public Stage stage;
    public FXMLLoader[] loaders = new FXMLLoader[XML_Paths.length];
    public HashMap<String, Parent> pageLoaders = new HashMap<String, Parent>();
    public String rand = "patate";
   
    // =================== variabili locali =================== //
   
    private Json jsonFileReader;

       
    public static void main(String[] args) throws Exception {
        System.out.println("Application Runnning...\n");
        //new EmotionalSongs();
        launch(args);
    }


    @Override
    public void start(Stage stage) 
    {
        EmotionalSongs.classReference = this;

        try {
            System.out.println("Accounts Credentials Recovery:");
            LoadAccounts();
            System.out.println();
            System.out.println("\nLoading Songs:");
            LoadSongs();
            System.out.println("\n\nStart loading XML file...");

            for(String path : XML_Paths) {
                System.out.println("loading " + path);
                
                String key = path.split("/")[path.split("/").length - 1].replace(".fxml", "");
                FXMLLoader file = new FXMLLoader(getClass().getClassLoader().getResource(path));
                Parent root = file.load();
                
                //per utilizzare il costruttore della classe
                /*file.setControllerFactory(c -> {    
                    return new Controller(this); // <-- parametri costruttore classe
                });*/
                pageLoaders.put(key, root);
            }
            System.out.println("\nLoading Completed\n");
            

            stage.setOnCloseRequest(event -> {
                event.consume();
                logout(stage);
            });

            //pagina iniziale
            stage.setTitle("EmotionaSong");
            stage.setScene(new Scene(pageLoaders.get("AccessPage")));
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
        SaveAccounts();
        stage.close();
    }

    public EmotionalSongs() throws IOException
    {

    }

    @SuppressWarnings("unchecked")
    private boolean LoadSongs()
    {
        jsonFileReader = new Json(Directory + songsDataFilePath);
        LinkedHashMap<String, Object> Accounts = jsonFileReader.ReadJsonFile();

        for(String UserKey : Json.getKeys(Accounts)) {
            ArchivioGolobaleCanzoni.add(new Song((LinkedHashMap<String, Object>) Json.GetElement(Accounts, Arrays.asList(UserKey))));
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private boolean LoadAccounts()
    {
        jsonFileReader = new Json(Directory + UsersDataFilePath);
        LinkedHashMap<String, Object> Accounts = jsonFileReader.ReadJsonFile();

        for(String UserKey : Json.getKeys(Accounts)) {
            Users.add(new Account((LinkedHashMap<String, Object>) Json.GetElement(Accounts, Arrays.asList(UserKey))));
        }
        return true;
    }

    private boolean SaveAccounts()
    {
        jsonFileReader = new Json(Directory + UsersDataFilePath);
        LinkedHashMap<String, Object> UsersData = new LinkedHashMap<String, Object>();

        for(int i = 0; i < Users.size(); i++) {
            String name = "user" + (i + 1);
            UsersData.put(name, Users.get(i).getDataStructure());
        }
        
        System.out.println("Opening " + Directory + UsersDataFilePath2);

        try {
            FileWriter Writer = new FileWriter(Directory + UsersDataFilePath2);
            
            Writer.write(jsonFileReader.BuildJsonFile(UsersData, 0));
            System.out.println("Data saved!");
            Writer.close();

        } catch (Exception e) {
            System.out.println("Writing File error: " + e);
        }

        return true;
    }

    public boolean checkAccaunt(Account temp) {

        return true;

        /*
        for(Account existingAcaunt : Users) {
            System.out.println("test: " + existingAcaunt.getName() + " con " + temp.getName());
            if(existingAcaunt.getName().equals(temp.getName())) {
                System.out.println("l'account esiste");

                break;
            }
        }
        
        return true;*/

    }
}
