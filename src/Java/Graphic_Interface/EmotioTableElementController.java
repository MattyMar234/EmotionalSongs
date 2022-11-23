package Java.Graphic_Interface;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Java.Account.RegisteredAccount;
import Java.Graphic_Interface.EmotionCreationPageController.Container;
import Java.PlayList_Songs.PlayList;
import Java.PlayList_Songs.Song;
import Java.emotionalsongs.Emotion;
import Java.emotionalsongs.EmotionalSongs;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class EmotioTableElementController extends Controller implements Initializable
{

    private Image filledStart = null;
    private Image EmptyStart = null;
    
    private Song song;
    private PlayList playlist;
    private Emotion emotion;

    
    @FXML private ImageView SetScore1;
    @FXML private ImageView SetScore2;
    @FXML private ImageView SetScore3;
    @FXML private ImageView SetScore4;
    @FXML private ImageView SetScore5;
    @FXML private ImageView EmojiIcon;
    @FXML private CheckBox checkBox;

    private ImageView ScoreArrey[] = new ImageView[5];



    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        if(filledStart == null) {
            filledStart = SetScore1.getImage();
            EmptyStart  = SetScore5.getImage();
        }

        ScoreArrey[0] = SetScore1;
        ScoreArrey[1] = SetScore2;
        ScoreArrey[2] = SetScore3;
        ScoreArrey[3] = SetScore4;
        ScoreArrey[4] = SetScore5;
        
        int val = 1;

        for (ImageView imageView : ScoreArrey) 
        {
            final int v = val++;
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                setScore(v);
                event.consume();
            });

            
        }
    }


    public void injectData(Container item) 
    {
        this.emotion = item.emotion;
        this.playlist = item.playlist;
        this.song = item.song;

        //Imposto la stringa del checkbox e l'immagine
        checkBox.setText((EmotionalSongs.language == 0 ? "Includi " : "Include ") + emotion.getCategory());
        EmojiIcon.setImage(Emotion.EmotionHashMap.get(emotion.getCategory()));

        //cerco se è presenta quella che ho:
        ArrayList<Emotion> userEmotions = song.getUserEmotions(super.application.ConnectedAccount.getID());
        boolean found = false;

        if(userEmotions.size() > 0) {
            for (Emotion e : userEmotions) {

                //se la emozione che rappresento è la stessa che possiede l'user
                //allora cambio i parametri
                if(e.getCategory() == emotion.getCategory()) 
                {
                    found = true;
                    emotion = e;
                    setScore(e.getScore());
                    checkBox.setSelected(true);
                    break;
                }
            }
        }

        //se non ho trovato un Emotio già esistente, allora la creo una nuova
        if(!found) {
            clearScore(); 
            emotion = new Emotion(emotion, 0, (RegisteredAccount) super.application.ConnectedAccount);
        }
        
    }


    private void clearScore() {
        for(int i = 0; i < 5; i++) {
            ScoreArrey[i].setImage(EmptyStart);
        }
    }

    private void setScore(int value) {
        if(value >= 6 || !checkBox.isSelected())
            return;

        //carico il valore
        this.emotion = new Emotion(emotion, value, (RegisteredAccount) super.application.ConnectedAccount);
        emotion.SetAccount((RegisteredAccount) super.application.ConnectedAccount);

        //passo i riferimenti dell' oggetto che ho creato
        this.song.AddUserEmotions(this.emotion, (RegisteredAccount) super.application.ConnectedAccount);

        //imposte le stelline 
        for(int i = 0; i < value; i++)
            ScoreArrey[i].setImage(filledStart);
        for(int i = value; i < 5; i++)
            ScoreArrey[i].setImage(EmptyStart);
        
    }


    @FXML
    void checkBoxClicked(MouseEvent event) 
    {
        if(checkBox.isSelected()) 
        {
            for(int i = 0; i < 5; i++) {
                ScoreArrey[i].setDisable(false);
            }
            setScore(1);
        }
        else {
            clearScore(); 

            //displable
            for(int i = 0; i < 5; i++) {
                ScoreArrey[i].setDisable(false);
            }

            //rimuovo la la emotion dalla raccolta della canzone
            this.song.removeEmotion(this.emotion, (RegisteredAccount) super.application.ConnectedAccount);
        }
    }


    
}



