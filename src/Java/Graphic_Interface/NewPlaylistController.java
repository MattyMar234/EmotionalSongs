package Java.Graphic_Interface;

import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import Java.PlayList_Songs.AddSongWindow;
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
    @FXML private Button AlbumButton;
    @FXML private Button AutorButton;
    @FXML private Button songButton;


    // ========================= tabelle =========================//

    @FXML private TableView<Song> SongsTable;
    @FXML private TableColumn<Song, String> Album;
    @FXML private TableColumn<Song, String> Autor;
    @FXML private TableColumn<Song, String> Title;
    @FXML private TableColumn<Song, String> Actions;

    // ========================= textField ========================= //
    @FXML private TextField KeywordTextField;

     // ========================= variabili =========================//
     MainPageController mainPageReference;
     AddSongWindow addSongWindow;
     HashMap<String,ArrayList<String>> SelectedData = new HashMap<String,ArrayList<String>>();
  


    private ObservableList<Song> list = FXCollections.observableArrayList();
    

    public NewPlaylistController() throws IOException {
        super();
        this.mainPageReference = MainPageController.MainPageControllerReference;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
       

        Title.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
        Autor.setCellValueFactory(new PropertyValueFactory<Song, String>("autor"));
        Album.setCellValueFactory(new PropertyValueFactory<Song, String>("album"));
        SongsTable.setItems(list);

        SelectedData.put("song" , new ArrayList<String>());
        SelectedData.put("autor", new ArrayList<String>());
        SelectedData.put("album", new ArrayList<String>());  
    }

    public void addSelectedSong(ArrayList<Song> song) {
        for(Song s : song) {
            list.add(s);
        }

        SongsTable.refresh();
    }

    // ====================== Button Action ================= //

    @FXML
    void ConfermeNewPlayList(ActionEvent event) throws IOException {
        
        if(KeywordTextField != null && KeywordTextField.getText().length() > 0) {
             
            ArrayList<Song> songlist = new ArrayList<Song>();

            for(Song s : list) {
                songlist.add(s);
            }

            PlayList newPlayList = new PlayList(KeywordTextField.getText(), songlist);
            mainPageReference.SetPlayListPage();
        }
        
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

    @FXML
    void AddAlbum(ActionEvent event) throws Exception {
        addSongWindow = new AddSongWindow(this, this.application, 2, SelectedData);
    }

    @FXML
    void AddAutor(ActionEvent event) throws Exception {
        addSongWindow = new AddSongWindow(this, this.application, 3, SelectedData);
    }

    @FXML
    void addSong(ActionEvent event) throws Exception {
        addSongWindow = new AddSongWindow(this, this.application, 1, SelectedData);
    }
}
