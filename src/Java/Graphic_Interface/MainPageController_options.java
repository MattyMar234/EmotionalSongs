package Java.Graphic_Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Java.PlayListSongs.PlayList;
import Java.PlayListSongs.Song;
import Java.PlayListSongs.SongWindow;
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
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainPageController_options extends Controller implements Initializable {

    // ========================= FXML object =========================//
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

    // ========================= Label ========================= //
    @FXML private Label LabelPlaylist;


    // ========================= Buttons ========================= //
    @FXML private Button CambioButton;
    @FXML private Button playlistButton;
    @FXML private Button profileButton;
    @FXML private Button optionsButton;
    @FXML private Button ExitButton;


    private ObservableList<Song> list = FXCollections.observableArrayList();
    

    // ========================= variabili =========================//
    //doppio click variabili 
    Song selected1;
    Song selected2;

    public MainPageController_options() {
        super();
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
       
        //SongsTable.setItems(sortedData);

      
        
    }

    @FXML
    void setReposity(ActionEvent event) throws IOException {
        Stage window = (Stage) playlistButton.getScene().getWindow();
        super.SwitchScene(window, "MainPage_reposity");
    }

    @FXML
    void SetPlayList(ActionEvent event) throws IOException {
        Stage window = (Stage) playlistButton.getScene().getWindow();
        super.SwitchScene(window, "MainPage_PLaylist");

    }

    @FXML
    void SetOptions(ActionEvent event) throws IOException {
        
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

    protected boolean doubleClick()
    {
        if(state == 0) {
            this.fistclick = System.nanoTime();
            state++;

            return false;
        }
        else {
            this.secondClick = System.nanoTime();
            state = 0;

            float dt = secondClick - fistclick; //ns
            //System.out.println("dt = " + dt + " ns");
            
            //converto da nano a secondi
            dt = (float) (dt/Math.pow(10, 9));

            //System.out.println("dt = " + dt + " s"); //s
            //System.out.println("dtmx = " + maxDt + " s"); //s
            //System.out.println("" + (this.maxDt - dt));
           
            if(this.maxDt - dt >= 0.0) {
                return true;
            }

            return false;
        }
    }

    @FXML
    void esci(ActionEvent event) {
        application.logout(application.mainStage);
    }
}
