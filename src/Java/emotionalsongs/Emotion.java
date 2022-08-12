package Java.emotionalsongs;

import java.util.HashMap;

import Java.Account.RegisteredAccount;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Emotion 
{
    public static Emotion Emotion_Amazement  = new Emotion("Amazement", "Feeling of wonder or happiness");
    public static Emotion Emotion_Solemnity  = new Emotion("Solemnity", "Feeling of transcendence, inspiration. Thrills");
    public static Emotion Emotion_Tenderness = new Emotion("Tenderness", "Sensuality, affect, feeling of love");
    public static Emotion Emotion_Nostalgia  = new Emotion("Nostalgia", "Dreamy, melancholic, sentimental feelings");
    public static Emotion Emotion_Calmness   = new Emotion("Calmness", "Relaxation, serenity, meditativeness");
    public static Emotion Emotion_Power      = new Emotion("Power", "Feeling strong, heroic, triumphant, energetic");
    public static Emotion Emotion_Joy        = new Emotion("Joy", "Feels like dancing, bouncy feeling, animated, amused ");
    public static Emotion Emotion_Tension    = new Emotion("Tension", "Feeling Nervous, impatient, irritated ");
    public static Emotion Emotion_Sadness    = new Emotion("Sadness", "Feeling Depressed, sorrowful");
    public static Image [] emotionImage      = new Image[10];
    
    public static HashMap<String,Image> EmotionHashMap = new HashMap<String,Image>();
    
    public static Emotion[] Emotions = {
        
        Emotion.Emotion_Amazement,
        Emotion.Emotion_Solemnity,
        Emotion.Emotion_Tenderness,
        Emotion.Emotion_Nostalgia,
        Emotion.Emotion_Calmness,
        Emotion.Emotion_Power,
        Emotion.Emotion_Joy,
        Emotion.Emotion_Tension,
        Emotion.Emotion_Sadness,
    };

    
    
    private String Category;
    private String Explanation;
    private Image image;
    private int Score;
    private RegisteredAccount account;

    // ====================== Costruttori ====================== //

    //costruttore 1
    public Emotion(Emotion emozione, int Score, RegisteredAccount account) 
    {
        this.Explanation = emozione.getExplanation();
        this.Category = emozione.getCategory();
        this.Score = Score;
        this.image = EmotionHashMap.get(emozione.getCategory());
        this.account = account;
    }

    //costruttore 2
    private Emotion(String category, String Explanation) 
    {
        this.Explanation = Explanation;
        this.Category = category;
    }

    
    // ====================== funzioni ====================== //

    public static int getEmotionID(Emotion e) 
    {
        int counter = 0 ;
        
        for(Emotion i : Emotion.Emotions) {
            if(i.getCategory().equals(e.getCategory())) {
                return counter;
            }
            counter++;
        }

        return -1;
    }

    @Override
    public String toString() {
        return new String("Category: " + Category + ", score: " + this.Score);
    }



    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getExplanation() {
        return Explanation;
    }

    public void setExplanation(String explanation) {
        Explanation = explanation;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public String getAccountID() {
        return this.account.getID();
    }

}
