package Graphic_Interface;

import java.io.IOException;
import java.net.URL;
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

public class MainPageController_Reposity extends Controller implements Initializable {

    // ========================= tabella =========================//
    
    @FXML private TextField KeywordTextField;
    @FXML private TableView<Song> SongsTable;
    @FXML private TableColumn<Song, String> Album;
    @FXML private TableColumn<Song, String> Autor;
    @FXML private TableColumn<Song, String> Comment;
    @FXML private TableColumn<Song, String> songNumber;
    @FXML private TableColumn<Song, String> Score;
    @FXML private TableColumn<Song, String> Time;
    @FXML private TableColumn<Song, String> Title;
    @FXML private TableColumn<Song, String> Year;

    @FXML private TableView<PlayList> PlaylistsTable;
    @FXML private TableColumn<PlayList, String> PlayListElements;
    @FXML private TableColumn<PlayList, String> PlayListName;
    @FXML private TableColumn<PlayList, String> DataCreazione;

    private ObservableList<Song> list = FXCollections.observableArrayList();

    // ========================= Label ========================= //
    


    // ========================= Buttons ========================= //
    @FXML private Button CambioButton;
    @FXML private Button playlistButton;
    @FXML private Button profileButton;
    @FXML private Button optionsButton;
    @FXML private Button ExitButton;


    // ========================= variabili =========================//
    Song selected1;
    Song selected2;

    public MainPageController_Reposity() {
        super();
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        System.out.println("loding songs data...");

        for(Song song : application.ArchivioGolobaleCanzoni) {
            list.add(song);
        }

        Title.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
        Autor.setCellValueFactory(new PropertyValueFactory<Song, String>("autor"));
        Year.setCellValueFactory(new PropertyValueFactory<Song, String>("year"));
        songNumber.setCellValueFactory(new PropertyValueFactory<Song, String>("number"));
        Album.setCellValueFactory(new PropertyValueFactory<Song, String>("album"));
        Time.setCellValueFactory(new PropertyValueFactory<Song, String>("duration"));

        
        FilteredList<Song> filteredData = new FilteredList<Song>(list, b -> true);
        
        KeywordTextField.textProperty().addListener((Observable, oldValue, newValue) -> {
            filteredData.setPredicate(song -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                try {
                    String lowerCaseFilter = newValue.toLowerCase();
                    //varie chiavi (campi) su cui ricercare
                    if(song.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } 
                    else if(song.getAlbum().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    else if(song.getAutor().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    else if(song.getDuration().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    else if(song.getYear().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                } catch (NullPointerException e) {
                    return false;
                }
                
                
                return false;
            });  
        });
        
        SortedList<Song> sortedData = new SortedList<Song>(filteredData);
        sortedData.comparatorProperty().bind(SongsTable.comparatorProperty());
        SongsTable.setItems(sortedData);

      
        
    }

    @FXML
    void setReposity(ActionEvent event) throws IOException {
        
    }

    @FXML
    void SetPlayList(ActionEvent event) throws IOException {
        Stage window = (Stage) playlistButton.getScene().getWindow();
        super.SwitchScene(window, "MainPage_PLaylist");
    }

    @FXML
    void SetOptions(ActionEvent event) throws IOException {
        Stage window = (Stage) playlistButton.getScene().getWindow();
        super.SwitchScene(window, "MainPage_impostazioni");
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
            selected1 = SongsTable.getSelectionModel().getSelectedItem();
        }
        else if (state == 1) {
            selected2 = SongsTable.getSelectionModel().getSelectedItem();
        }

        if(doubleClick() && (selected2 == selected1)) {
            System.out.println("song selected");
            SongWindow windowSong = new SongWindow(application, selected1);
        }

    }

    @FXML
    void esci(ActionEvent event) {
        application.logout(application.mainStage);
    }
}
