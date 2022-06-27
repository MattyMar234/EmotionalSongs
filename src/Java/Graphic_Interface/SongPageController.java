package Java.Graphic_Interface;

import java.net.URL;
import java.util.ResourceBundle;

import Java.PlayList_Songs.Song;
import Java.emotionalsongs.Emotion;
import Java.emotionalsongs.EmotionalSongs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class SongPageController extends Controller implements Initializable 
{
    protected EmotionalSongs main;
    protected Song selectedSong;   
    
    @FXML private TableView<Emotion> SongInformationTable;
    @FXML private TableColumn<Emotion, String> emotional;
    @FXML private TableColumn<Emotion, String> explanation;
    @FXML private TableColumn<Emotion, Integer> score;
    @FXML private TextArea songCommnet;
    @FXML private Label songName;

    private ObservableList<Emotion> EmotionsList = FXCollections.observableArrayList();

    //costruttore
    public SongPageController(EmotionalSongs main, Song SelectedSong) 
    {
        SelectedSong.getEmotions().add(new Emotion("happy", "feel happy",5));
        System.out.println(SelectedSong.getEmotions().get(0));

        this.main = main;
        this.selectedSong = SelectedSong;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        //aggiungo l'elenco delle emozioni
        for(Emotion emt : this.selectedSong.getEmotions()) {
            EmotionsList.add(emt);
        }

        emotional.setCellValueFactory(new PropertyValueFactory<Emotion, String>("Category"));
        explanation.setCellValueFactory(new PropertyValueFactory<Emotion, String>("Explanation"));
        score.setCellValueFactory(new PropertyValueFactory<Emotion, Integer>("Score"));
        
        SongInformationTable.setItems(EmotionsList);

        this.songName.setText(this.selectedSong.getTitle());
    }
}
