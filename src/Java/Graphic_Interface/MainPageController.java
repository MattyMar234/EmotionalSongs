package Java.Graphic_Interface;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import Java.Account.RegisteredAccount;
import Java.Account.UnregisteredAccount;
import Java.PlayList_Songs.PlayList;
import Java.PlayList_Songs.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;


public class MainPageController extends Controller implements Initializable 
{
    // ========================= Label ========================= //
   

    // ========================= pane ========================= //
    @FXML private AnchorPane SceneContainer;
    @FXML protected BorderPane borderPane;
    

    // ========================= Buttons ========================= //
    @FXML public Button profileButton;
    @FXML public Button reposityButton;
    @FXML public Button playlistButton;
    @FXML public Button optionsButton;
    @FXML public Button CambioButton;
    @FXML public Button ExitButton;


    // ========================= variabili - globali =========================//
    public static MainPageController MainPageControllerReference;


    
    // ========================= variabili - locali =========================//
    
    ArrayList<Button> buttons = new ArrayList<Button>();
    private int SelectedButton = 1;
    private final String ButtonColor = "-fx-background-color: #0bb813;" + "-fx-text-fill:#ffffff;";
    protected int state = 1;
    


    public MainPageController() throws IOException {
        super();
        MainPageControllerReference = this;
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1)  
    {
        
        if(this.windwoPosWidth < 1200.0) {
            this.windwoPosWidth = 1200.0;
            this.application.mainStage.setWidth(this.windwoPosWidth);
        }

        /*
        if(this.windwoPosWidth < (screenSize.getWidth()*100)/80) {
            this.windwoPosWidth = (screenSize.getWidth()*100)/80;
            this.application.mainStage.setWidth(this.windwoPosWidth);
        }*/

        if(this.windwoPosHeight < 800.0) {
            this.windwoPosHeight = 800.0;
            this.application.mainStage.setHeight(this.windwoPosHeight);
        }
        
        
        for(Field f : this.getClass().getFields())
        {
            try {
                Object obj = this.getClass().getField(f.getName()).get(this);

                if(obj instanceof Button) {
                    buttons.add((Button) obj);
                }

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        

        ClearActiveButtons();
        this.buttons.get(1).setStyle(ButtonColor);

        int index =  0;
        for(Button b : this.buttons) 
        {
            final int n = index++;

            b.setOnMouseEntered(e -> {
                if(state == n) {
                    b.setStyle(ButtonColor);//b.setStyle("-fx-background-color: #f18100f6");
                }
                else { 
                    
                    //9c9c9c66
                    b.setStyle("-fx-background-color: #9c9c9c66;" + "-fx-text-fill:#ffffff;");
                } 
                
                
            });

            b.setOnMouseExited(e -> {
                if(state == n) {
                    b.setStyle(ButtonColor);
                }
                else {
                    b.setStyle("-fx-background-color: transparent;" + "-fx-text-fill:#798AA6");
                }
            });
        }  
        
        if(this.application.ConnectedAccount instanceof UnregisteredAccount) {
            this.playlistButton.setDisable(true);
            this.profileButton.setText("Sign In");
        }
       
        try {
            SetReposityPage();  
        } 
        catch (IOException e) { 
            e.printStackTrace();
        }
    }

    private void ClearActiveButtons() {
        
        //reimposto a tutti lo sfondo
        for(Button b : this.buttons) {
            b.setStyle("-fx-background-color: transparent;");
        }

        
    }


    // -------------------------------- eventi -------------------------------- //
    
    @FXML
    void AccountButtonSelected(ActionEvent event) throws IOException 
    { 
        if(state != 4 ) {
            if(this.application.ConnectedAccount instanceof RegisteredAccount) { 
                state = 4;
                SetAccountPage();
            } 
            else if(this.application.ConnectedAccount instanceof UnregisteredAccount) {

            }
        }
    }

    @FXML
    void setReposity(ActionEvent event) throws IOException 
    {
        if(state != 1 ) {
            state = 1;
            ClearActiveButtons();
            this.buttons.get(1).setStyle(ButtonColor);
            SetReposityPage();
        }
    }

    @FXML
    void SetPlayList(ActionEvent event) throws IOException 
    {
        state = 2;
        ClearActiveButtons();
        this.buttons.get(2).setStyle(ButtonColor);
        SetPlayListPage();
    }

    @FXML
    void SetOptions(ActionEvent event) throws IOException {
        if(state != 3 ) {
            state = 3;
            ClearActiveButtons();
            this.buttons.get(3).setStyle(ButtonColor);
            SetOptionsPage();
        } 
    }


    @FXML
    public void access(MouseEvent event) throws IOException {
        //super.SwitchScene((Stage) icon.getScene().getWindow(), "LoadAccaunt");
     
    }

    @FXML
    void esci(ActionEvent event) {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initOwner(this.application.mainStage);
        alert.setTitle("Conferma");
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setContentText("Vuoi uscire dal programma ?");
        
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            application.logout(application.mainStage);
        }  
    }

    @FXML
    void ChangeAccount(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initOwner(this.application.mainStage);
        alert.setTitle("Conferma");
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setContentText("vuoi cambiare Account ?");
        
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            SwitchScene("AccessPage"); 
        }  
    }


    // -------------------------------- Cambio pagine -------------------------------- //

    //REPOSITY
    public void SetReposityPage() throws IOException  {       
        FXMLLoader loader = getScenePage("MainPage_reposity");
        AnchorPane view = loader.load();

        ((MainPageController_reposity)loader.getController()).mainController = this;

        borderPane.getChildren().removeAll();
        borderPane.setCenter(view);  
    }

    //COMMENTS
    public void SetCommentsPage(Song song) throws IOException  {       
        FXMLLoader loader = getScenePage("CommentsPage");

        loader.setControllerFactory( c -> {
            return new CommentsPageController(this, song);
        });

        AnchorPane view = loader.load();
        
        //((CommentsPageController)loader.getController()).injectData(this, song);

        borderPane.getChildren().removeAll();
        borderPane.setCenter(view);  
    }

    //COMMENTS --> REPOSITY
    public void Comment_To_repository() throws IOException {
        SetReposityPage();
    }

    //EMOTION
    public void SetEmotionPage(Song song) throws IOException  {       
        FXMLLoader loader = getScenePage("EmotionPage");

        loader.setControllerFactory( c -> {
            return new EmotionPageController(song, this);
        });

        AnchorPane view = loader.load();
        

        borderPane.getChildren().removeAll();
        borderPane.setCenter(view);  
    }

    //COMMENTS --> REPOSITY
    public void Emotion_To_repository() throws IOException {
        SetReposityPage();
    }

    //PLAYLIST
    public void SetPlayListPage() throws IOException  {  
        FXMLLoader loader = getScenePage("MainPage_PLaylist");
        AnchorPane view = loader.load();

        ((MainPageController_playList)loader.getController()).SetMainControllerReference(this);
        
        borderPane.getChildren().removeAll();
        borderPane.setCenter(view);  
    }  

    //IMPOSTAZIONI
    public void SetOptionsPage() throws IOException 
    {  
        AnchorPane view = getScenePage("MainPage_impostazioni").load();
        borderPane.getChildren().removeAll();
        borderPane.setCenter(view);  
        
    }  

    public void SetProfilePage() throws IOException {

    }

    public void NewPlaylistPage() throws IOException {
        AnchorPane view = getScenePage("NewPlaylistCreationPage").load();
        borderPane.getChildren().removeAll();
        borderPane.setCenter(view);  
    }


    public void SetPlaylistEditPage(PlayList playlist) throws IOException 
    {
        FXMLLoader loader = getScenePage("EditPlaylistPage");
        loader.setControllerFactory(c -> {    
            return new EditPlaylistController(playlist); // <-- parametri costruttore classe
        });

        AnchorPane view = loader.load();
        borderPane.getChildren().removeAll();
        borderPane.setCenter(view);  

        //getScenePage("MainPage_PLaylist").getController();
    }

    
    public void SetAccountPage() throws IOException
    {       
        AnchorPane view = getScenePage("MainPage_AccountInfo").load();
        borderPane.getChildren().removeAll();
        borderPane.setCenter(view);  
    }
}
