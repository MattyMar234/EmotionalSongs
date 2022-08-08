package Java.emotionalsongs;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Emotion {

    public static Emotion Emotion_Amazement  = new Emotion("Amazement", "Feeling of wonder or happiness");
    public static Emotion Emotion_Solemnity  = new Emotion("Solemnity", "Feeling of transcendence, inspiration. Thrills");
    public static Emotion Emotion_Tenderness = new Emotion("Tenderness", "Sensuality, affect, feeling of love");
    public static Emotion Emotion_Nostalgia  = new Emotion("Nostalgia", "Dreamy, melancholic, sentimental feelings");
    public static Emotion Emotion_Calmness   = new Emotion("Calmness", "Relaxation, serenity, meditativeness");
    public static Emotion Emotion_Power      = new Emotion("Power", "Feeling strong, heroic, triumphant, energetic");
    public static Emotion Emotion_Joy        = new Emotion("Joy", "Feels like dancing, bouncy feeling, animated, amused ");
    public static Emotion Emotion_Tension    = new Emotion("Tension", "Feeling Nervous, impatient, irritated ");
    public static Emotion Emotion_Sadness    = new Emotion("Sadness", "Feeling Depressed, sorrowful");
    
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

    public static Image [] emotionImage = new Image[10];
    

    protected String Category;
    protected String Explanation;
    protected int Score = 0;

    // ====================== Costruttori ====================== //
    //costruttore 1
    public Emotion() 
    {
        
    }

    //costruttore 2
    public Emotion(String category, String Explanation, int Score) 
    {
        this.Explanation = Explanation;
        this.Category = category;
        this.Score = Score;
    }

    //costruttore 3
    public Emotion(String category, String Explanation) 
    {
        this.Explanation = Explanation;
        this.Category = category;
    }

    //costruttore 4
    public Emotion(Emotion emozione) {

    }
    
    // ====================== funzioni ====================== //

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

}
