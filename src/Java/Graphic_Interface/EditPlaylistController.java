package Java.Graphic_Interface;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import Java.PlayList_Songs.PlayList;
import Java.PlayList_Songs.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class EditPlaylistController extends Controller implements Initializable {

    
    @FXML private TableView<Song> SongsTable;
    @FXML private TableColumn<Song, String> Album;
    @FXML private TableColumn<Song, String> Autor;
    @FXML private TableColumn<Song, String> Title;
    @FXML private TableColumn<Song, Song> Actions;   
    
    @FXML private Button AnnullaPlaylistButton;
    @FXML private Button NewPlaylistButton;

    private ObservableList<Song> list = FXCollections.observableArrayList();
    protected PlayList playlist;
    

    public EditPlaylistController(PlayList list) {
        super();
        this.playlist = list;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for(Song song : playlist.getSongs()) {
            list.add(song);
        }

        Title.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
        Autor.setCellValueFactory(new PropertyValueFactory<Song, String>("autor"));
        Album.setCellValueFactory(new PropertyValueFactory<Song, String>("album"));

        SongsTable.setItems(list);
    
    }
    
    @FXML
    void AddNewPlayList(ActionEvent event) {

    }

    @FXML
    void ConfermeNewPlayList(ActionEvent event) {

    }

    @FXML
    void Selezione(ActionEvent event) {

    }

    @FXML
    void elementSelected(MouseEvent event) {

    }

    @FXML
    void turnBack(ActionEvent event) {

    }
    
}
