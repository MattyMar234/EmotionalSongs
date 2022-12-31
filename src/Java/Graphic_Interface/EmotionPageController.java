package Java.Graphic_Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Java.DataClasses.Emotion;
import Java.PlayListSongs.Song;
import Java.emotionalsongs.EmotionalSongs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class EmotionPageController extends Controller implements Initializable{

    @FXML private Button back;

    @FXML
    private ImageView IMG1;
    
    // Tabella punteggi
    @FXML private TableView<Container1> emotionTable;
    @FXML private TableColumn<Container1, String> PunteggioUtente;
    @FXML private TableColumn<Container1, ImageView> emoji;
    @FXML private TableColumn<Container1, String> category;
    @FXML private TableColumn<Container1, String> explanation;
    @FXML private TableColumn<Container1, String> score;

    // Tabella valori
    @FXML private TableView<Container2> emotionTableScores;
    @FXML private TableColumn<Container2, Integer> users;
    @FXML private TableColumn<Container2, ImageView> emoji1;
    @FXML private TableColumn<Container2, String> category1;
    @FXML private TableColumn<Container2, Float> average;
    @FXML private TableColumn<Container2, Integer> massimo;
    @FXML private TableColumn<Container2, Integer> minimo;
    
    
    
    private Song canzoneAssociata;
    private MainPageController mainController;
    public ObservableList<Container1> list = FXCollections.observableArrayList();
    public ObservableList<Container2> media = FXCollections.observableArrayList();



    public class ContainerBase 
    {
        public ImageView emotionIMG;
        public String category;
        public String description;
        public String score;

        private Emotion e;

        public ContainerBase(Emotion e) {

            this.e = e;
            this.category = e.getCategory();
            this.description = e.getExplanation();
            this.score = Integer.toString(e.getScore());

            this.emotionIMG = new ImageView(Emotion.emotionImage[Emotion.getEmotionID(e)]);
            emotionIMG.setFitHeight(48);
            emotionIMG.setFitWidth(48);
        }

        public ImageView getEmotionIMG() {
            return emotionIMG;
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

    public class Container1 extends ContainerBase 
    {
        public String UserID;

        public Container1(Emotion e) {
            super(e);
            this.UserID = e.getAccountID();
        }

        public String getUserID() {
            return UserID;
        }

    }

    public class Container2 extends ContainerBase 
    {
        
        public float media;
        public float max;
        public float min;
        public int users;
        
        public Container2(Emotion e, float media, int users, float max, float min) {
            super(e); 

            this.media = media;
            this.users = users;
            this.max = max;
            this.min = min;
        }
        
        public float getMedia() {
            return media;
        }

        public float getUsers() {
            return users;
        }
        public int getMax() {
            return (int) max;
        }
        public int getMin() {
            return (int) min;
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
    public void initialize(URL location, ResourceBundle resources) 
    {

        super.setImage(IMG1);

        int [] scores = new int[Emotion.Emotions.length];
        int [] Maxs = new int[Emotion.Emotions.length];
        int [] Mins = new int[Emotion.Emotions.length];
        int [] EmotionNumbers = new int[Emotion.Emotions.length];
    

        back.setText(EmotionalSongs.language == 0 ? "indietro" : "back");

        // Tabella punteggi
        PunteggioUtente.setText(EmotionalSongs.language == 0 ? "Utenti" : "Users");
        category.setText(EmotionalSongs.language == 0 ? "Categoria" : "Category");
        explanation.setText(EmotionalSongs.language == 0 ? "Spiegazione" : "Explanaton");
        score.setText(EmotionalSongs.language == 0 ? "Punteggio" : "Score");

        // Tabella valori
        users.setText(EmotionalSongs.language == 0 ? "Numero Utenti" : "Users Count");
        category1.setText(EmotionalSongs.language == 0 ? "Categoria" : "Category");
        average.setText(EmotionalSongs.language == 0 ? "Media" : "Average");
        massimo.setText(EmotionalSongs.language == 0 ? "Massimo" : "Maxium");
        minimo.setText(EmotionalSongs.language == 0 ? "Minimo" : "Minium");

        //resetto i contatori
        for(int i = 0; i < scores.length; i++) {
            EmotionNumbers[i] = scores[i] = 0; 
            Maxs[i] = Mins[i] = 1;
        }

        for(Emotion e : canzoneAssociata.getEmotions()) {
            int EmotionIndex = Emotion.getEmotionID(e);

            //parte Tab Info
            EmotionNumbers[EmotionIndex]++; 
            scores[EmotionIndex] += e.getScore(); 

            if(Maxs[EmotionIndex] < e.getScore())
                Maxs[EmotionIndex] = e.getScore();

            if(Mins[EmotionIndex] > e.getScore())
                Mins[EmotionIndex] = e.getScore();

            //parte Tab Utenti
            list.add(new Container1(e));
        }

        //creazione elementi tab Info
        for(Emotion e : Emotion.Emotions) {
            int ID = Emotion.getEmotionID(e);

            if(EmotionNumbers[ID] > 0) 
                media.add(new Container2(e, (float)(scores[ID]/EmotionNumbers[ID]), EmotionNumbers[ID], Maxs[ID], Mins[ID]));
            else
                media.add(new Container2(e, 0,0, 0, 0));
        }

        


        PunteggioUtente.setCellValueFactory(new PropertyValueFactory<Container1, String>("UserID"));
        emoji.setCellValueFactory(new PropertyValueFactory<Container1, ImageView>("emotionIMG"));        
        category.setCellValueFactory(new PropertyValueFactory<Container1, String>("category"));
        score.setCellValueFactory(new PropertyValueFactory<Container1, String>("score"));
        explanation.setCellValueFactory(new PropertyValueFactory<Container1, String>("description"));     
        emotionTable.setItems(list);
        emotionTable.setSelectionModel(null);

        users.setCellValueFactory(new PropertyValueFactory<Container2, Integer>("users"));
        emoji1.setCellValueFactory(new PropertyValueFactory<Container2, ImageView>("emotionIMG"));
        category1.setCellValueFactory(new PropertyValueFactory<Container2, String>("category"));
        average.setCellValueFactory(new PropertyValueFactory<Container2, Float>("media")); 
        massimo.setCellValueFactory(new PropertyValueFactory<Container2, Integer>("max")); 
        minimo.setCellValueFactory(new PropertyValueFactory<Container2, Integer>("min")); 
        emotionTableScores.setItems(media);
        emotionTableScores.setSelectionModel(null);
    }
    

    @FXML
    void turnBack(ActionEvent event) throws IOException {
        
        this.mainController.Emotion_To_repository();
    }

}
