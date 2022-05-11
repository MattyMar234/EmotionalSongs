package Java.Graphic_Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Java.Account.RegisteredAccount;
import Java.PlayList_Songs.PlayList;
import Java.PlayList_Songs.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class MainPageController_playList extends Controller implements Initializable {

    // ========================= TableView ========================= //
    @FXML private TableView<PlayList> PlaylistsTable;
    @FXML private TableColumn<PlayList, String> PlayListElements;
    @FXML private TableColumn<PlayList, String> PlayListName;
    @FXML private TableColumn<PlayList, String> DataCreazione;
    @FXML private TableColumn<PlayList, Button> DeletePlayList;
    @FXML private TableColumn<PlayList, Button> EditPlayList;

    private ObservableList<PlayList> userPlayLists = FXCollections.observableArrayList();

    // ========================= Label ========================= //
    @FXML private Label LabelPlaylist;
    

    // ========================= Buttons ========================= //
    @FXML private Button NewPlaylistButton;


    // ========================= variabili =========================//
    MainPageController mainPageReference;
 
    

    public MainPageController_playList() throws IOException {
        super();
        this.mainPageReference = MainPageController.MainPageControllerReference;
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        if(application.ConnectedAccount == null) return;
        System.out.println("loding playlists data...");

        if(application.ConnectedAccount instanceof RegisteredAccount) {
           // System.out.println(((RegisteredAccount)application.ConnectedAccount).getPlayLists().get(0));
  

            for(PlayList playlist : ((RegisteredAccount) application.ConnectedAccount).getPlayLists()) {
                userPlayLists.add(playlist);
            }

            PlayListName.setCellValueFactory(new PropertyValueFactory<PlayList, String>("nome"));
            PlayListElements.setCellValueFactory(new PropertyValueFactory<PlayList, String>("Elements"));
            DataCreazione.setCellValueFactory(new PropertyValueFactory<PlayList, String>("CreationDate"));
            PlaylistsTable.setItems(userPlayLists);
        }
   
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

        if(doubleClick() && (selected2 == selected1) && selected1 instanceof Song) {
            System.out.println("song selected");
            //SongWindow windowSong = new SongWindow(application, (Song) selected1);
        }
        else if(doubleClick() && (selected2 == selected1) && selected1 instanceof PlayList) {
            System.out.println("song selected");
            //SongWindow windowSong = new SongWindow(application, selected1);
        }

    }

    @FXML
    public void Selezione(ActionEvent event) {
        System.out.println("pressed");

    }

    @FXML
    void AddNewPlayList(ActionEvent event) throws IOException {
        AnchorPane view = getScenePage("NewPlaylistCreationPage").load();
        mainPageReference.borderPane.getChildren().removeAll();
        mainPageReference.borderPane.setCenter(view);  
    }
}
