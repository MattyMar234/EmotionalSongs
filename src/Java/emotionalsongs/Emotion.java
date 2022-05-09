package Java.emotionalsongs;

public class Emotion {

    public static Emotion Emotion_Amazement  = new Emotion("Amazement", "Feeling of wonder or happiness");
    public static Emotion Emotion_Solemnity  = new Emotion("Solemnity", "Feeling of transcendence, inspiration. Thrills");
    public static Emotion Emotion_Tenderness  = new Emotion("Tenderness", "Sensuality, affect, feeling of love");
    public static Emotion Emotion_Nostalgia  = new Emotion("Nostalgia", "Dreamy, melancholic, sentimental feelings");
    public static Emotion Emotion_Calmness   = new Emotion("Calmness", "Relaxation, serenity, meditativeness");
    public static Emotion Emotion_Power      = new Emotion("Power", "Feeling strong, heroic, triumphant, energetic");
    public static Emotion Emotion_Joy        = new Emotion("Joy", "Feels like dancing, bouncy feeling, animated, amused ");
    public static Emotion Emotion_Tension    = new Emotion("Tension", "Feeling Nervous, impatient, irritated ");
    public static Emotion Emotion_Sadness    = new Emotion("Sadness", "Feeling Depressed, sorrowful");
    

    protected String Category;
    protected String Explanation;
    protected int Score = 0;
    protected String Comment;

    //costruttore 1
    public Emotion() 
    {
        
    }

    //costruttore 2
    public Emotion(String category, String Explanation, String Comment, int Score) 
    {
        this.Category = category;
        this.Explanation = Explanation;
        this.Score = Score;
        this.Comment = Comment;
    }

    //costruttore 3
    public Emotion(String category, String Explanation, int Score) 
    {
        this.Category = category;
        this.Explanation = Explanation;
        this.Score = Score;
    }

    //costruttore 4
    public Emotion(String category, String Explanation) 
    {
        this.Category = category;
        this.Explanation = Explanation;
    }

    //costruttore 5
    public Emotion(Emotion emozione) {

    }
    

    @Override
    public String toString() {
        String information = new String();

        information += "Category: " + Category + "\n";
        information += " Explanation: " + Explanation + "\n";
        //information += " Comment: " + Comment + "\n";
        information += " Score: " + Score + "\n"; 

        return information;
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

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    

}
