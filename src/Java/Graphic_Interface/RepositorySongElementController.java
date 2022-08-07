package Java.Graphic_Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Java.PlayList_Songs.Song;
import Java.emotionalsongs.Emotion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

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

        //this.LabelTitle.setText("Title: " + song.getTitle());
        //this.labelAutor.setText("Autor: " + song.getAutor());
        this.LabelTitle.setText(song.getTitle());
        this.labelAutor.setText(song.getAutor());
        this.LabelYear.setText(song.getYear());

        commentNumber = song.getComments().size();

        
        if(this.canzoneAssociata == null) {
            //labelCommenti.setText(labelCommenti.getText().replace("[n]", "0"));
            labelCommenti.setText("Nessun commento");
        }
        else 
        {
            if(commentNumber == 0) {
                labelCommenti.setText("Nessun commento");
            }
            else {
                labelCommenti.setText(labelCommenti.getText().replace("[n]", (commentNumber > 99) ? "99+" : String.valueOf(commentNumber)));
            }
        }

        int r = (int)(Math.random()*9);

        if(r == 0) {
            Label l = new Label();
            l.setText("Nessuna emozione");
            l.setId("labelAutor");
            EmojiContainer.getChildren().add(l);
        } 

        for(int i = 0; i < r; i++ ) 
        {
            ImageView img = new ImageView(Emotion.emotionImage[i]);
            img.setFitWidth(emojiSize);
            img.setFitHeight(emojiSize);
            img.setStyle("-fx-padding: 0 8 0 8;");

            EmojiContainer.getChildren().add(img);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        /*double x = songMenubackground.getWidth();
        LabelTitle.setMaxWidth(x);
        labelAutor.setMaxWidth(x);*/ 
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
