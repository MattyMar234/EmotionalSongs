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
    
    public ArrayList<Song> ArchivioGolobaleCanzoni = new ArrayList<Song>();
    public ArrayList<Account> Users = new ArrayList<>();
    private Json jsonFileReader;

    

    public FXMLLoader[] loaders = new FXMLLoader[3];
    public Stage actualStage;


    public static void main(String[] args) throws Exception {
        //System.out.println("Runnning...");
        //new EmotionalSongs();
        launch(args);
    }

    @Override
    public void start(Stage stage) 
    {
        try {

            this.actualStage = stage;
            LoadAccounts();
            LoadSongs();
            System.out.print("caricamento file:\t");
            //qui viene anche eseguita la creazione della classe
            //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml_Page/UserRegistration.fxml"));

            loaders[0] = new FXMLLoader(getClass().getClassLoader().getResource("fxml_Page/UserRegistration.fxml"));
            loaders[1] = new FXMLLoader(getClass().getClassLoader().getResource("fxml_Page/MainPage.fxml"));
            loaders[2] = new FXMLLoader(getClass().getClassLoader().getResource("fxml_Page/LoadAccaunt.fxml"));

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
            System.out.println("Completato");
            
            System.out.print("impostazione root:\t");
            Scene scene = new Scene(root);
            System.out.println("Completato");
            
            stage.setTitle("EmotionaSong");
            stage.setScene(scene);

            stage.setOnCloseRequest(event -> {
                event.consume();
                logout(stage);
            });

            stage.show();


        } catch(NullPointerException e) {
            System.out.println("file non trovato, errore nel percorso del file fxml");

        } catch (IOException e) {
            //e.printStackTrace(); 
            //System.out.println(e);  
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
    /*    
        //Load accaunt
        if(!LoadAccounts()) {
            System.out.println("reading account data error");
        }

        if(!SaveAccounts()) {
            System.out.println("error");
        }
        */
    }

    @SuppressWarnings("unchecked")
    private boolean LoadSongs()
    {
        jsonFileReader = new Json(Directory + songsDataFilePath);
        LinkedHashMap<String, Object> Accounts = jsonFileReader.ReadJsonFile();

        for(String UserKey : Json.getKeys(Accounts)) {
            ArchivioGolobaleCanzoni.add(new Song((LinkedHashMap<String, Object>) Json.GetElement(Accounts, Arrays.asList(UserKey))));
            
            System.out.println(Users.get(Users.size() - 1));
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
            
            //System.out.println(Users.get(Users.size() - 1));
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

    public boolean exitingAccount(Account temp) {

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
