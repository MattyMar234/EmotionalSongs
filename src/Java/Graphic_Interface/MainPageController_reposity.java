package Java.Graphic_Interface;

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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

public class MainPageController_reposity extends Controller implements Initializable 
{

    // ========================= tabelle =========================//
    @FXML private TextField KeywordTextField;
    @FXML private TableView<Song> SongsTable;
    @FXML private TableColumn<Song, Song> Elements;

    // ========================= Label ========================= //
    @FXML private Label SerachLabel;

   
    // ========================= Buttons ========================= //
    @FXML private Button CambioButton;
    @FXML private Button playlistButton;
    @FXML private Button profileButton;
    @FXML private Button optionsButton;
    @FXML private Button ExitButton;


    // ========================= variabili =========================//
    private ObservableList<Song> list = FXCollections.observableArrayList();


    // ============================================================ //

    public MainPageController_reposity() throws IOException {
        super();
    }




    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        for(Song song : application.songManager.getList()) {
            list.add(song);
        }


        Callback<TableColumn<Song, Song>, TableCell<Song, Song>> cellFoctory = (TableColumn<Song, Song> param) -> {
            final TableCell<Song, Song> cell = new TableCell<Song, Song>() {
                
                @Override
                public void updateItem(Song item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } 
                    else 
                    {
                        try 
                        {
                            //carico la pagina
                            FXMLLoader XMLloader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/test.fxml"));
                            
                            /*XMLloader.setControllerFactory(c -> {    
                                return new RepositorySongElementController(item); // <-- parametri costruttore classe
                            });*/
                            
                            AnchorPane view = XMLloader.load();

                            //carico i parametri
                            RepositorySongElementController controller = XMLloader.getController();
                            controller.injectData(item);

                            setGraphic(view);
                        
                        } catch (IOException e) {
                            System.out.println(e);
                   
                        }
                      
                        
                        setText(null);

                    }
                }

            };

            return cell;
        };

        Elements.setCellValueFactory(new PropertyValueFactory<Song, Song>("classReference"));
        Elements.setCellFactory(cellFoctory);

        
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

        if(doubleClick() && selected1 == selected2) {
            System.out.println("song selected");
            SongWindow windowSong = new SongWindow(application, (Song) selected1);
        }
        

    }

    @FXML
    public void Selezione(ActionEvent event) {
        System.out.println("pressed");

    }

}
