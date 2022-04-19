package emotionalsongs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import Graphic_Interface.EnterController;
import Graphic_Interface.MainPageController;
import Graphic_Interface.SceneController;
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

    private final String Directory = System.getProperty("user.dir");
    private final String UsersDataFilePath = "\\data\\UtentiRegistrati.json";
    private final String UsersDataFilePath2 = "\\data\\UtentiRegistrati2.json";   //prova
    private final String songsDataFilePath = "\\data\\canzoni.json";
    private final String SongDataFilePath  = "data.txt";    //?

    private final String [] XML_Paths = {
        
        "fxml_Page/UserRegistration.fxml",
        "fxml_Page/MainPage.fxml",
        "fxml_Page/LoadAccaunt.fxml"
    };
    
    public ArrayList<Song> ArchivioGolobaleCanzoni = new ArrayList<Song>();
    public ArrayList<Account> Users = new ArrayList<>();
    private Json jsonFileReader;

    

    public FXMLLoader[] loaders = new FXMLLoader[XML_Paths.length];
    public Stage actualStage;


    public static void main(String[] args) throws Exception {
        System.out.println("Application Runnning...\n");
        //new EmotionalSongs();
        launch(args);
    }

    @Override
    public void start(Stage stage) 
    {
        try {
            System.out.println("Start loading XML file:");

            for(int index = 0; index < XML_Paths.length; index++) 
            {
                System.out.println(XML_Paths[index]);
                loaders[index] = new FXMLLoader(getClass().getClassLoader().getResource(XML_Paths[index]));
            }

            //per utilizzare il costruttore della classe
            loaders[0].setControllerFactory(c -> {
                return new SceneController(this); // <-- parametri costruttore classe
            });

            loaders[1].setControllerFactory(c -> {
                return new MainPageController(this); // <-- parametri costruttore classe
            });

            loaders[2].setControllerFactory(c -> {
                return new EnterController(this); // <-- parametri costruttore classe
            });

            Parent root = loaders[0].load();
            Scene scene = new Scene(root);

            System.out.println("loading completed\n");
            System.out.println("accounts credentials recovery:");
            LoadAccounts();
            System.out.println();
            System.out.println("loading songs:");
            LoadSongs();
            System.out.println();
            System.out.println();
            
            System.out.println("root settings:");

            stage.setOnCloseRequest(event -> {
                event.consume();
                logout(stage);
            });

            stage.setTitle("EmotionaSong");
            stage.setScene(scene);
            stage.show();
            System.out.println("completed\n");
            System.out.println("starting...");
            

        } catch(NullPointerException e) {
            System.out.println("file non trovato, errore nel percorso del file fxml");

        } catch (IOException e) {
            e.printStackTrace(); 
            System.out.println(e);  

        } catch (Exception e) {
            e.printStackTrace(); 
            System.out.println(e);  
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


    public void ChangeStage(int number) throws IOException  {
        actualStage.setScene(new Scene(loaders[number].load()));
    }
}
