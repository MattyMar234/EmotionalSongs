package Graphic_Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainPageController extends Controller implements Initializable {

    // ========================= FXML object =========================//
    @FXML private TextField KeywordTextField;
    @FXML private TableView<Song> SongsTable;
    @FXML private TableColumn<Song, String> Album;
    @FXML private TableColumn<Song, String> Autor;
    @FXML private TableColumn<Song, String> Comment;
    @FXML private TableColumn<Song, String> Genre;
    @FXML private TableColumn<Song, String> Score;
    @FXML private TableColumn<Song, String> Time;
    @FXML private TableColumn<Song, String> Title;
    @FXML private TableColumn<Song, String> Year;

    @FXML private ImageView icon;

    private ObservableList<Song> list = FXCollections.observableArrayList();

    // ========================= variabili =========================//
    //doppio click variabili 
    float fistclick;
    float secondClick;
    float maxDt = 0.320f;   //320ms
    int state = 0;

    public MainPageController() {
        super();
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        System.out.println("loding songs data...");

        for(Song song : application.ArchivioGolobaleCanzoni) {
            list.add(song);
        }

        Title.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
        Autor.setCellValueFactory(new PropertyValueFactory<Song, String>("autor"));
        Year.setCellValueFactory(new PropertyValueFactory<Song, String>("year"));
        Genre.setCellValueFactory(new PropertyValueFactory<Song, String>("genre"));
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
                    else if(song.getGenre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
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
    public void Selezione(ActionEvent event) {
        System.out.println("pressed");

    }

    @FXML
    public void access(MouseEvent event) throws IOException 
    {
        Stage Window = (Stage) icon.getScene().getWindow();
        super.SwitchScene(Window, "LoadAccaunt");
           
    }

    @FXML
    public void elementSelected(MouseEvent event) throws Exception {
        
        if(doubleClick()) {
            System.out.println("song selected");
            Song selected = SongsTable.getSelectionModel().getSelectedItem();
            SongWindow windowSong = new SongWindow(application, selected);
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
}
