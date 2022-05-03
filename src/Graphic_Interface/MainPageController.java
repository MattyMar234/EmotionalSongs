package Graphic_Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import PlayListSongs.PlayList;
import PlayListSongs.Song;
import PlayListSongs.SongWindow;
import emotionalsongs.EmotionalSongs;
import emotionalsongs.RegisteredAccount;
import emotionalsongs.UnregisteredAccount;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainPageController extends Controller implements Initializable {

    

    // ========================= Label ========================= //
   

    // ========================= pane ========================= //
    @FXML private AnchorPane SceneContainer;
    @FXML private BorderPane borderPane;
    


    // ========================= Buttons ========================= //
    @FXML private Button profileButton;
    @FXML private Button CambioButton;
    @FXML private Button reposityButton;
    @FXML private Button playlistButton;
    @FXML private Button optionsButton;
    @FXML private Button ExitButton;


    // ========================= variabili =========================//
    protected int state = 1;
    

    public MainPageController() throws IOException {
        super();
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        System.out.println("loding songs");
        
        this.reposityButton.requestFocus();
    
    
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


    // -------------------------------- eventi -------------------------------- //
    
    @FXML
    void AccountButtonSelected(ActionEvent event) throws IOException 
    { 
        if(state != 4 ) {
            if(this.application.ConnectedAccount instanceof RegisteredAccount) { 
                state = 4;
                SetProfilePage();
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
            SetReposityPage();
        }
    }

    @FXML
    void SetPlayList(ActionEvent event) throws IOException 
    {
        if(state != 2 ) {
            state = 2;
            SetPlayListPage();
        } 
    }

    @FXML
    void SetOptions(ActionEvent event) throws IOException {
        if(state != 3 ) {
            state = 3;
            SetOptionsPage();
        } 
    }


    @FXML
    public void access(MouseEvent event) throws IOException {
        //super.SwitchScene((Stage) icon.getScene().getWindow(), "LoadAccaunt");
     
    }

    @FXML
    void esci(ActionEvent event) {
        application.logout(application.mainStage);
    }


     
    // -------------------------------- Cambio pagine -------------------------------- //

    protected void SetReposityPage() throws IOException
    {       
        AnchorPane view = getScenePage("MainPage_reposity").load();
        borderPane.getChildren().removeAll();
        borderPane.setCenter(view);  
    }

    protected void SetPlayListPage() throws IOException 
    {  
        AnchorPane view = getScenePage("MainPage_PLaylist").load();
        borderPane.getChildren().removeAll();
        borderPane.setCenter(view);  
    }  

    protected void SetOptionsPage() throws IOException 
    {  
        AnchorPane view = getScenePage("MainPage_impostazioni").load();
        borderPane.getChildren().removeAll();
        borderPane.setCenter(view);  
    }  

    protected void SetProfilePage() throws IOException {

    }
    
}
