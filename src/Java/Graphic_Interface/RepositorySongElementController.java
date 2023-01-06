package Java.Graphic_Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Java.DataClasses.Emotion;
import Java.PlayListSongs.Song;
import Java.emotionalsongs.EmotionalSongs;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * Questa classe grafica gestisce l'elemento grafico della tabella contenente tutte le canzoni della repository 
 */
public class RepositorySongElementController extends Controller implements Initializable {

    private Song canzoneAssociata;
    private MainPageController_reposity repositoryController;
    private int emojiSize = 20;

    @FXML private Label LabelTitle;
    @FXML private Label labelAutor;
    @FXML private Label labelCommenti;
    @FXML private Label LabelYear;
    @FXML private HBox EmojiContainer;
    @FXML private Label EmotionLabel;

    @FXML
    private ImageView IMG1;

    @FXML
    private ImageView IMG2;

    @FXML
    private ImageView IMG3;


    @FXML private AnchorPane songMenubackground;

    private int commentNumber;
    

    public RepositorySongElementController() {

    }
    
    public RepositorySongElementController(Song song) {


    }

    public void injectData(MainPageController_reposity mainController, Song song) 
    {
        
        this.canzoneAssociata = song;
        this.repositoryController = mainController;
        this.LabelTitle.setText(song.getTitle());
        this.labelAutor.setText(song.getAutor());
        this.LabelYear.setText(song.getYear());

        commentNumber = song.getComments().size();

        
        if(this.canzoneAssociata == null)
            labelCommenti.setText(EmotionalSongs.language == 0 ? "Nessun commento" : "No comment");
        else {
            if(commentNumber == 0) 
                labelCommenti.setText(EmotionalSongs.language == 0 ? "Nessun commento" : "No comment");
            else {
                String numero = (commentNumber > 99) ? "99+" : String.valueOf(commentNumber);
                labelCommenti.setText(EmotionalSongs.language == 0 ? "Visualizza tutti i " + numero + " commenti..." : "View all " + numero + " comment..."); 
                }
        }

        boolean [] missingImg = new boolean[9];
        int emojiCounters = 0;


        for(int i = 0; i < 9; i++) {
            missingImg[i] = true;
        }

        for(Emotion e : song.getEmotions()) 
        {
            int n = Emotion.getEmotionID(e);
            
            
            if(missingImg[n]) 
            {
                missingImg[n] = false;

                ImageView img = new ImageView(Emotion.emotionImage[n]);
                img.setFitWidth(emojiSize);
                img.setFitHeight(emojiSize);
                img.setStyle("-fx-padding: 0 8 0 8;");
                EmojiContainer.getChildren().add(img);

                /*
                img.setOnMouseEntered(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent t) {
                        int count = 0;
                        double dt = 0;
                        
                        Label l = new Label("3.5");
                        l.setTranslateX(img.getX());
                        l.setTranslateY(img.getY() + 20);

                        EmojiContainer.getChildren().add(l);
                        //(img.getX())

                        //AnchorPane p = new AnchorPane();
                        

                        
                    }
                });*/

                

                if(++emojiCounters >= 9) break;
            }
        }

        if(emojiCounters == 0) {
            Label l = new Label();
            l.setText("Nessuna emozione");
            l.setId("labelAutor");
            EmojiContainer.getChildren().add(l);
        } 
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        super.setImage(IMG1, IMG2, IMG3);

        EmotionLabel.setText(EmotionalSongs.language == 0 ? "Emozioni: " : "Emotions: ");
    }

    @FXML
    void viewComment(MouseEvent event) throws IOException {
        this.repositoryController.mainController.SetCommentsPage(this.canzoneAssociata);
    }
    

    @FXML
    void viewEmotions(MouseEvent event) throws IOException {
        this.repositoryController.mainController.SetEmotionPage(this.canzoneAssociata);
    }
}
