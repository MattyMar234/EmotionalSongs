package Java.emotionalsongs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import Java.Account.Account;
import Java.Graphic_Interface.WindowContainerController;
import Java.Managers.AccountsManager;
import Java.Managers.LocationsManager;
import Java.Managers.SongManager;
import Java.PlayList_Songs.Song;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EmotionalSongs extends Application{

    // =================== percorsi =================== //

    private final String Directory = System.getProperty("user.dir");
    private final String UsersDataFilePath  = "\\data\\UtentiRegistrati.json";
    private final String UsersDataFilePath2 = "\\data\\UtentiRegistrati2.json";   //prova
    private final String songsDataFilePath  = "\\data\\canzoni.json";
    private final String songsDataFilePath2 = Directory + "\\data\\Song.csv";
    private final String LocationsData      = Directory + "\\data\\comuni.json";
    private final String SongDataFilePath   = "data.txt";    //?
    private final String FileFXML_path      = "FXML/";
    private final String [] XML_Paths = {


        FileFXML_path + "Container.fxml",
        FileFXML_path + "UserRegistration.fxml",
        FileFXML_path + "MainPage.fxml",
        FileFXML_path + "MainPage_reposity.fxml",
        FileFXML_path + "MainPage_PLaylist.fxml",
        FileFXML_path + "MainPage_impostazioni.fxml",
        FileFXML_path + "LoadAccaunt.fxml",
        FileFXML_path + "AccessPage.fxml",
        FileFXML_path + "ongPageInformation.fxml",
        FileFXML_path + "SongPageInformation.fxml",
        FileFXML_path + "NewPlaylistCreationPage.fxml",
        FileFXML_path + "MainPage_AccountInfo.fxml",
        FileFXML_path + "Playlist_AddSongPage.fxml"
    };


    // =================== variabili globali =================== //

    public static EmotionalSongs classReference;                    //riferimento globale di questa classe
    public static WindowContainerController windowPageReference;
    
    public ArrayList<Song> ArchivioGolobaleCanzoni = new ArrayList<Song>();
    public HashMap<String, String> pageLoaders = new HashMap<String, String>();         
    public Account ConnectedAccount;                                             //Account in utilizzo
    public Stage mainStage;  
                                                        //finestra principale
    public SongManager songManager;
    public AccountsManager AccountsManager;
    public LocationsManager locationsManager;

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

        songManager      = new SongManager(songsDataFilePath2, this);
        AccountsManager  = new AccountsManager(Directory + UsersDataFilePath, this);
        locationsManager = new LocationsManager(LocationsData, this);
        
        try {

            System.out.println("Loading state data");
            locationsManager.LoadData();
            System.out.println();

            System.out.println("Loading Songs:");
            songManager.LoadSongs();
            System.out.println();

            System.out.println("Loading Accounts:");
            AccountsManager.LoadAccounts();
            System.out.println();
            
            
            System.out.println("\n\nStart loading XML file Name: ");

            for(String path : XML_Paths) 
            {
                String key = path.split("/")[path.split("/").length - 1].replace(".fxml", "");
                pageLoaders.put(key, path);
                System.out.println(Directory + "\\src\\" + path);
            }

            System.out.println("Loading Completed\n");
            
                //lambda function 
            stage.setOnCloseRequest(event -> {
                event.consume();
                logout(stage);
            });

            //pagina iniziale
            stage.setTitle("EmotionaSong");
            changeScreen(stage, "Container");
            stage.show();
           
            System.out.println("starting stage"); 
        

        } catch(NullPointerException e) {
            System.out.println("file non trovato, errore nel percorso del file fxml");

        } catch (Exception e) {
            System.out.println(e);  
            e.printStackTrace(); 
        }
    }

    public void logout(Stage stage) {
        try {
            AccountsManager.SaveAccounts(Directory + UsersDataFilePath2);
        } catch (IOException e) {
            System.out.println("errore di salvataggio");
            e.printStackTrace();
        }
        stage.close();
    }

    public EmotionalSongs() throws IOException
    {

    }

    
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
