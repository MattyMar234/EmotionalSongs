package Java.emotionalsongs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import Java.Account.Account;
import Java.DataClasses.Emotion;
import Java.Graphic_Interface.WindowContainerController;
import Java.Json.JsonParser;
import Java.Managers.AccountsManager;
import Java.Managers.LocationsManager;
import Java.Managers.SongManager;
import Java.PlayListSongs.Song;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.imageio.*;
import org.json.simple.JSONObject;
import java.awt.image.BufferedImage;


public class EmotionalSongs extends Application{

    // =================== percorsi =================== //

    public static final String Directory    = System.getProperty("user.dir");
    public static final String iconsFolder  = Directory + "\\data\\icon\\";
    public static final String imageFolder  = Directory + "\\data\\image\\";
    public static final String emojiFolder  = Directory + "\\data\\EmojiIcon\\";
    public static final String flagFolder   = Directory + "\\data\\Flag\\";

    public static final String SongFile     = Directory + "\\data\\song.json";
    public static final String UsersFile    = Directory + "\\data\\UtentiRegistrati.json";
    public static final String LocationsData      = Directory + "\\data\\comuni.json";
    public static final String songsDataFilePath2 = Directory + "\\data\\Song.csv";

    public static final String FileFXML_path = "FXML/";
    public static final String [] XML_Paths = {

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
        FileFXML_path + "Playlist_AddSongPage.fxml",
        FileFXML_path + "CommentElement.fxml",
        FileFXML_path + "CommentsPage.fxml",
        FileFXML_path + "EmotionPage.fxml",
        FileFXML_path + "EditPlaylist.fxml",
        FileFXML_path + "AddEmotionPage3.fxml",
        FileFXML_path + "AddEmotionPageTableElement.fxml"
    };


    // =================== variabili globali =================== //

    public static EmotionalSongs classReference;                    //riferimento globale di questa classe
    public static WindowContainerController windowPageReference;
    public static int language = 1; //italiano 0, inglese 1
    public static HashMap<String, ImageView> globaApplicationImages = new HashMap<>();

    public HashMap<String, String> pageLoaders = new HashMap<String, String>();         
    public Account ConnectedAccount;                                             //Account in utilizzo
    public Stage mainStage;  
                                                        //finestra principale
    public SongManager songManager;
    public AccountsManager AccountsManager;
    public LocationsManager locationsManager;

    // =================== variabili locali =================== //

    public boolean skipLogin = false;
    

       
    public static void main(String[] args) throws Exception {
        System.out.println("Application Runnning...\n");
        launch(args);

        //Language 
        if(language == 0){
            System.out.println("Italian");
        }
        else{
            System.out.println("English");
        }
    }


    @Override
    public void start(Stage stage) 
    {
        EmotionalSongs.classReference = this;
        this.mainStage = stage;

        songManager      = new SongManager(SongFile, this);
        AccountsManager  = new AccountsManager(UsersFile, this);
        locationsManager = new LocationsManager(LocationsData, this);

        int loadingState = 0;
        
        try {

            // ***************** caricamento Dati ***************** //
            
            
            System.out.print("Loading Accounts:");
            AccountsManager.LoadAccounts();
            System.out.println();
            
            System.out.print("Loading Songs: ");
            songManager.LoadSongs();
            AccountsManager.LoadAccountsPlaylists();
            System.out.println();
            

            System.out.print("Loading data: ");
            locationsManager.LoadData();
            System.out.println();
            
            


            // ***************** caricamento impostazioni ***************** //
            File fileSetting = new File(Directory + "\\settings.json");
            System.out.print("get file" + Directory + "\\settings.json : ");

            
            try 
            {
                // se esiste e non è una cartella
                if(fileSetting.exists() && fileSetting.isFile()) {
                    System.out.println("File found");
    
                    JsonParser jsonReader = new JsonParser(Directory + "\\settings.json");
                    JSONObject obj = jsonReader.ReadJsonFile_as_JsonObject();

                    System.out.println(obj);
    
                    //test user
                    if(obj.get("testUser") != null && ((String) obj.get("testUser")).equals("true")) {
                        ConnectedAccount = AccountsManager.SearchByEmail("test@gmail.com");
                        
                        if(ConnectedAccount != null) {
                            skipLogin = true;
                        }
                    }

                    //language
                    if(obj.get("language") != null) {

                        switch(((String) obj.get("language"))) 
                        {
                            case "fr":  language = 2; break;
                            case "en":  language = 1; break;
                            case "it":  language = 0; break;
                            
                            default: 
                             language = 0;
                        }
                    }
                }
                else {
                    System.out.println("File not found");
                }
                
            } catch (Exception e) {
                System.out.println(e);
            }
            

            // ***************** Creazione percosi file XML ***************** //
            
            System.out.println("\nStart loading XML file Name: ");

            for(String path : XML_Paths) 
            {
                String key = path.split("/")[path.split("/").length - 1].replace(".fxml", "");
                pageLoaders.put(key, path);
                System.out.println(Directory + "\\src\\" + path);
            }

            System.out.println();



            // ***************** caricamento Icone ***************** //

            /*BufferedImage img = ImageIO.read(new File(iconsFolder + "emoji.png"));
            int size   = 66;
            int start  = 1;
            int offset = 17;
            
            for(int  i = 0; i < 9; i++ ) {
                BufferedImage output = img.getSubimage(i*(size + offset) + start + i, 0, size, size + 1);
                Emotion.emotionImage[i] = SwingFXUtils.toFXImage(output, null);
            
                //int r = i*(size + offset) + start*(i + 1);
                //System.out.println("sunImage " + i + "start:" + r + " end: " + (r + size) );
            }*/

            String [] Dirpaths = { emojiFolder, flagFolder, imageFolder, iconsFolder };

            int counter = 0;
            for(String Dirpath : Dirpaths)
            {
                //ottengo tutti i file nella directory
                File folder = new File(Dirpath);
                File[] listOfFiles = folder.listFiles();

                if(counter == 0) {
                    for(int  i = 0; i < 9; i++ ) {
                        for(int  k = 0; k < 9; k++ )
                        {
                            //System.out.println(listOfFiles[k].getName());
                            //System.out.println(listOfFiles[k].getName().indexOf(Integer.toString( i + 1)));

                            if(listOfFiles[k].getName().indexOf(Integer.toString( i + 1)) >= 0)
                            {
                                System.out.println("loading image: " + listOfFiles[k].getAbsolutePath());
                                Emotion.emotionImage[i] = SwingFXUtils.toFXImage(ImageIO.read(listOfFiles[k]), null);
                                Emotion.EmotionHashMap.put(Emotion.matrice[i][0], Emotion.emotionImage[i]);
                                Emotion.EmotionHashMap.put(Emotion.matrice[i][1], Emotion.emotionImage[i]);
                                break;
                            }
                        }
                    }
                }
                else {
                    for(File f : listOfFiles) {
                        if(!f.isDirectory()) {
                            System.out.println("loading image: " + f.getAbsolutePath() + " --> " + f.getName());
                            Image img = SwingFXUtils.toFXImage(ImageIO.read(f), null);
                            EmotionalSongs.globaApplicationImages.put(f.getName(), new ImageView(img));
                        }
                    }
                }
                counter++;
            }



            
            

            
            
            
            //************ Impostazioni pagina************//
            
            //pagina iniziale
            stage.setTitle("EmotionaSong");
            changeScreen(stage, "Container");
            stage.show();

            //lambda function 
            stage.setOnCloseRequest(event -> {
                event.consume();
                logout(stage);
            });

            System.out.println("starting stage"); 
        

        } catch(NullPointerException e) {
            System.out.println("file non trovato, errore nel percorso del file fxml o altro");
            System.out.println(e);
            e.printStackTrace(); 

        } catch (Exception e) {
            System.out.println(e);  
            e.printStackTrace(); 
        }
        finally {
            System.out.println("Loading Completed\n");
        }
    }

    public void logout(Stage stage) {
        try {
            AccountsManager.SaveAccounts(UsersFile);
            songManager.saveData(EmotionalSongs.SongFile);
            

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
