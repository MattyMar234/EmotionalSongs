package Java.Graphic_Interface;

import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import Java.Account.RegisteredAccount;
import Java.PlayListSongs.PlayList;
import Java.PlayListSongs.Song;
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

/**
 * Questa classe grafica gestisce la finestra di creazione di una nuova playlist
 */
public class NewPlaylistController extends Controller implements Initializable {


    // ========================= Buttons ========================= //
    @FXML private Button AlbumButton;
    @FXML private Button AutorButton;
    @FXML private Button songButton;

    @FXML private Button addSong;
    @FXML private Button AddSongAutor;
    @FXML private Button Back;
    @FXML private Button salva;
    @FXML private Button BackButton;


    // ========================= tabella =========================//
    @FXML private TableView<Song> emotionTable;
    @FXML private TableColumn<Song, String> Album;
    @FXML private TableColumn<Song, String> Autor;
    @FXML private TableColumn<Song, String> Title;
    @FXML private TableColumn<Song, String> SongDate;
    //@FXML private TableColumn<Song, String> Actions;    

    // ========================= textField ========================= //
    @FXML private TextField playlistNameField;

    // ========================= Labels ========================= //
    @FXML private Label labelError;
    @FXML private Label titoloPagina;
    @FXML private Label playlistName;
    @FXML private Label selectedSong;
    
    // ========================= Altro ========================= //

    @FXML
    private ImageView IMG1;

    @FXML
    private ImageView IMG2;

    @FXML
    private ImageView IMG3;

    @FXML
    private ImageView IMG4;
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

        super.setImage(IMG1, IMG2, IMG3, IMG4);

        Back.setText(EmotionalSongs.language == 0 ? "indietro" : "back");
        salva.setText(EmotionalSongs.language == 0 ? "Salva" : "Save");
        addSong.setText(EmotionalSongs.language == 0 ? "Aggiungi Canzoni" : "Add Songs");
        AddSongAutor.setText(EmotionalSongs.language == 0 ? "Aggiungi canzoni dall'Autore" : "Add Author songs");

        Title.setText(EmotionalSongs.language == 0 ? "Titolo" : "Title");
        Autor.setText(EmotionalSongs.language == 0 ? "Autore" : "Autor");
        SongDate.setText(EmotionalSongs.language == 0 ? "Data" : "Date");
        //Actions.setText(EmotionalSongs.language == 0 ? "Azioni" : "Actions");

        titoloPagina.setText(EmotionalSongs.language == 0 ? "MENÙ CREAZIONE PLAYLIST" : "PLAYLIST CREATION MENÙ");
        playlistName.setText(EmotionalSongs.language == 0 ? "Nome playlist:" : "Playlist name:");
        selectedSong.setText(EmotionalSongs.language == 0 ? "Canzoni selezionate:" : "Selected songs:");

        labelError.setStyle("-fx-font-size:13px; -fx-text-fill: transparent;");

       
        Title.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
        Autor.setCellValueFactory(new PropertyValueFactory<Song, String>("autor"));
        SongDate.setCellValueFactory(new PropertyValueFactory<Song, String>("year"));
        //Album.setCellValueFactory(new PropertyValueFactory<Song, String>("album"));
        emotionTable.setItems(list);

    }

    public void addSelectedSong(ArrayList<Song> song) 
    {
        list.clear();

        for(Song s : song) {
            list.add(s);
        }

        emotionTable.refresh();
    }

    // ====================== Button Action ================= //

    void ConfermeNewPlayList(ActionEvent event) throws IOException {
        
        if(playlistNameField != null && playlistNameField.getText().length() > 0 && playlistNameField.getText() != "name") {
             
            PlayList p = new PlayList(this.playlistNameField.getText(), new ArrayList<>(list));
            RegisteredAccount account = (RegisteredAccount)this.application.ConnectedAccount;
            account.addPlaylist(p);
            
            mainPageReference.SetPlayListPage();
        }
        else {
            labelError.setStyle("-fx-font-size:13px; -fx-text-fill: red;");
            labelError.setText( EmotionalSongs.language == 0 ? "nome playlist mancante" : "missing playlist name");
        }  
    }

    
    
    @FXML
    public void elementSelected(MouseEvent event) throws Exception 
    {        
        

    }

    @FXML
    public void Selezione(ActionEvent event) {
        System.out.println("pressed");

    }

    private PlayList getData() {
        PlayList p = new PlayList();
        
        for(Song s : list) {
            p.addSong(s);
        }

        p.setNome("newPlaylist");

        System.out.println(p);
        return p;
    }

    @FXML
    void AddAlbum(ActionEvent event) throws Exception {
        //addSongWindow = new AddSongWindow(this, this.application, 2, getData());
    }

    @FXML
    void AddAutor(ActionEvent event) throws Exception {
        addSongWindow = new AddSongWindow(this, this.application, 3, getData());
    }

    @FXML
    void addSong(ActionEvent event) throws Exception {
        addSongWindow = new AddSongWindow(this, this.application, 1, getData());
    }


    @FXML
    void save(ActionEvent event) throws IOException {
        ConfermeNewPlayList(event);

    }

    @FXML
    void turnBack(ActionEvent event) throws IOException {
        mainPageReference.SetPlayListPage();
    }


}
