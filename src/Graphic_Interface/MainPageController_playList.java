package Graphic_Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import PlayListSongs.PlayList;
import PlayListSongs.Song;
import PlayListSongs.SongWindow;
import emotionalsongs.EmotionalSongs;
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
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainPageController_playList extends Controller implements Initializable {

    // ========================= FXML object =========================//
    @FXML private TableView<PlayList> PlaylistsTable;
    @FXML private TableColumn<PlayList, String> PlayListElements;
    @FXML private TableColumn<PlayList, String> PlayListName;
    @FXML private TableColumn<PlayList, String> DataCreazione;

    // ========================= Label ========================= //
    @FXML private Label LabelPlaylist;


    // ========================= Buttons ========================= //
    @FXML private Button CambioButton;
    @FXML private Button playlistButton;
    @FXML private Button profileButton;
    @FXML private Button optionsButton;
    @FXML private Button ExitButton;

    private ObservableList<PlayList> userPlayLists = FXCollections.observableArrayList();

    // ========================= variabili =========================/
    protected PlayList selected1;
    protected PlayList selected2;

    public MainPageController_playList() {
        super();
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        if(application.ConnectedAccount == null) {
            return;
        }
        System.out.println("loding playlists data...");

        ArrayList<PlayList> playlists = application.ConnectedAccount.getPlayLists();
        
        for(PlayList playlist : playlists) {
            userPlayLists.add(playlist);
        }

        PlaylistsTable.setItems(userPlayLists);

      
        
    }

    @FXML
    void setReposity(ActionEvent event) throws IOException {
        Stage window = (Stage) playlistButton.getScene().getWindow();
        super.SwitchScene(window, "MainPage_reposity");
    }

    @FXML
    void SetPlayList(ActionEvent event) throws IOException {
        
    }

    @FXML
    void SetOptions(ActionEvent event) throws IOException {
        Stage window = (Stage) playlistButton.getScene().getWindow();
        super.SwitchScene(window, "MainPage_options");
    }

    @FXML
    void closeMenu(MouseEvent event) {
        
    }

    @FXML
    public void Selezione(ActionEvent event) {
        System.out.println("pressed");

    }

    @FXML
    public void access(MouseEvent event) throws IOException {
        //super.SwitchScene((Stage) icon.getScene().getWindow(), "LoadAccaunt");
     
        
    }

    @FXML
    public void elementSelected(MouseEvent event) throws Exception 
    {        
        if(state == 0) {
            selected1 = PlaylistsTable.getSelectionModel().getSelectedItem();
        }
        else if (state == 1) {
            selected2 = PlaylistsTable.getSelectionModel().getSelectedItem();
        }

        if(doubleClick() && (selected2 == selected1)) {
            System.out.println("song selected");
            //SongWindow windowSong = new SongWindow(application, selected1);
        }

    }

    

    @FXML
    void esci(ActionEvent event) {
        application.logout(application.mainStage);
    }
}
