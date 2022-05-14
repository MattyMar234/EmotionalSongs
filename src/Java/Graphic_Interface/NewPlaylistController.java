package Java.Graphic_Interface;

import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Java.PlayList_Songs.PlayList;
import Java.PlayList_Songs.Song;
import Java.PlayList_Songs.SongWindow;
import Java.emotionalsongs.EmotionalSongs;
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

public class NewPlaylistController extends Controller implements Initializable {


    // ========================= Buttons ========================= //

    @FXML private Button AnnullaPlaylistButton;
    @FXML private Button newPlayListButton;

    // ========================= variabili =========================//
    MainPageController mainPageReference;

    // ========================= tabelle =========================//
    @FXML private TextField KeywordTextField;
    @FXML private TableView<Song> SongsTable;
    @FXML private TableColumn<Song, String> Album;
    @FXML private TableColumn<Song, String> Autor;
    @FXML private TableColumn<Song, String> Title;
  


    private ObservableList<Song> list = FXCollections.observableArrayList();
    

    public NewPlaylistController() throws IOException {
        super();
        this.mainPageReference = MainPageController.MainPageControllerReference;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(Song song : application.songManager.getList()) {
            list.add(song);
        }

        Title.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
        Autor.setCellValueFactory(new PropertyValueFactory<Song, String>("autor"));
        Album.setCellValueFactory(new PropertyValueFactory<Song, String>("album"));

        
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
    void ConfermeNewPlayList(ActionEvent event) throws IOException {
        //...
        mainPageReference.SetPlayListPage();
    }

    @FXML
    void turnBack(ActionEvent event) throws IOException {
        mainPageReference.SetPlayListPage();
    }
    
    @FXML
    public void elementSelected(MouseEvent event) throws Exception 
    {        
        

    }

    @FXML
    public void Selezione(ActionEvent event) {
        System.out.println("pressed");

    }
}
