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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainPageController_reposity extends Controller implements Initializable 
{

    // ========================= tabelle =========================//
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


    private ObservableList<Song> list = FXCollections.observableArrayList();
    

    // ========================= Label ========================= //
    @FXML private Label SerachLabel;

   
    // ========================= Buttons ========================= //
    @FXML private Button CambioButton;
    @FXML private Button playlistButton;
    @FXML private Button profileButton;
    @FXML private Button optionsButton;
    @FXML private Button ExitButton;


    // ========================= variabili =========================//
    


    public MainPageController_reposity() throws IOException {
        super();
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        for(Song song : application.songManager.SongList) {
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
    public void elementSelected(MouseEvent event) throws Exception 
    {        
        if(state == 0) {
            selected1 = SongsTable.getSelectionModel().getSelectedItem();
        }
        else if (state == 1) {
            selected2 = SongsTable.getSelectionModel().getSelectedItem();
        }

        if(doubleClick() && (selected2 == selected1) && selected1 instanceof Song) {
            System.out.println("song selected");
            SongWindow windowSong = new SongWindow(application, (Song) selected1);
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

}
