package Java.Graphic_Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Java.PlayList_Songs.Song;
import Java.emotionalsongs.Emotion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmotionPageController extends Controller implements Initializable{

    @FXML private Button back;
    
    @FXML private TableView<Container> emotionTable;
    @FXML private TableColumn<Container, String> user;
    @FXML private TableColumn<Container, String> category;
    @FXML private TableColumn<Container, String> explanation;
    @FXML private TableColumn<Container, String> score;
    
    private Song canzoneAssociata;
    private MainPageController mainController;
    public ObservableList<Container> list = FXCollections.observableArrayList();


    public class Container {
        
        private Emotion e;
        private Song s;

        public String name;
        public String category;
        public String description;
        public String score;

        public Container(Emotion e, Song song) {
            this.e = e;
            this.s = song;

            this.name = e.getAccountID();
            this.category = e.getCategory();
            this.description = e.getExplanation();
            this.score = Integer.toString(e.getScore());
        }

        public String getName() {
            return name;
        }
        public String getDescription() {
            return description;
        }
        public String getScore() {
            return score;
        }
        public String getCategory() {
            return category;
        }
        
    }

    

    public EmotionPageController() {
        super();
    }

    public EmotionPageController(Song song, MainPageController mainController) {
        this.canzoneAssociata = song;
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for(Emotion e : canzoneAssociata.getEmotions()) {
            list.add(new Container(e, canzoneAssociata));
        }

        user.setCellValueFactory(new PropertyValueFactory<Container, String>("name"));
        category.setCellValueFactory(new PropertyValueFactory<Container, String>("category"));
        explanation.setCellValueFactory(new PropertyValueFactory<Container, String>("description"));     
        score.setCellValueFactory(new PropertyValueFactory<Container, String>("score"));     
    
        emotionTable.setItems(list);
        emotionTable.setSelectionModel(null);
    }
    

    @FXML
    void turnBack(ActionEvent event) throws IOException {
        this.mainController.Emotion_To_repository();
    }

}
