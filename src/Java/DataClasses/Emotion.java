package Java.DataClasses;

import java.util.HashMap;

import Java.Account.RegisteredAccount;
import Java.emotionalsongs.EmotionalSongs;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Questa classe è utilizzata per rappresentare tutte le informazioni associate a un'emozione.
 */
public class Emotion 
{

    /**
    * Raccolta di tutti i testi presente nelle spiegazioni delle emozioni.
    */
    public static final String[][] matrice = 
    {
        {"Stupore", "Amazement"}, 
        {"Solennità", "Solemnity"}, 
        {"Tenerezza", "Tenderness"}, 
        {"Nostalgia", "Nostalgia"},
        {"Calma", "Calmness"},
        {"Forza", "Power"},
        {"Gioia", "Joy"},
        {"Tensione", "Tension"},
        {"Tristezza", "Sadness"},

        {"Sensazione di meraviglia o felicità.","Feeling of wonder or happiness."},
        {"Sensazione di trascendenza, ispirazione. Brividi.", "Feeling of transcendence, inspiration. Thrills."},
        {"Sensualità, affetto, sentimento d'amore.", "Sensuality, affect, feeling of love."},
        {"Sensazioni sognanti, malinconiche, sentimentali.", "Dreamy, melancholic, sentimental feelings."},
        {"Rilassamento, serenità, meditazione.", "Relaxation, serenity, meditativeness."},
        {"Sentirsi forte, eroico, trionfante, energico.", "Feeling strong, heroic, triumphant, energetic."},
        {"Sensazione di ballare, sensazione di rimbalzo, animato, divertito.", "Feels like dancing, bouncy feeling, animated, amused."},
        {"Sensazione di nervoso, impaziente, irritato.", "Feeling nervous, impatient, irritated."},
        {"Sensazione di depressione, addolorato.", "Feeling depressed, sorrowful."},
    };

    private static int init = 0;
    public static Emotion Emotion_Amazement  = new Emotion(matrice[0][EmotionalSongs.language], matrice[9][EmotionalSongs.language]);
    public static Emotion Emotion_Solemnity  = new Emotion(matrice[1][EmotionalSongs.language], matrice[10][EmotionalSongs.language]);
    public static Emotion Emotion_Tenderness = new Emotion(matrice[2][EmotionalSongs.language], matrice[11][EmotionalSongs.language]);
    public static Emotion Emotion_Nostalgia  = new Emotion(matrice[3][EmotionalSongs.language], matrice[12][EmotionalSongs.language]);
    public static Emotion Emotion_Calmness   = new Emotion(matrice[4][EmotionalSongs.language], matrice[13][EmotionalSongs.language]);
    public static Emotion Emotion_Power      = new Emotion(matrice[5][EmotionalSongs.language], matrice[14][EmotionalSongs.language]);
    public static Emotion Emotion_Joy        = new Emotion(matrice[6][EmotionalSongs.language], matrice[15][EmotionalSongs.language]);
    public static Emotion Emotion_Tension    = new Emotion(matrice[7][EmotionalSongs.language], matrice[16][EmotionalSongs.language]);
    public static Emotion Emotion_Sadness    = new Emotion(matrice[8][EmotionalSongs.language], matrice[17][EmotionalSongs.language]);
    public static Image [] emotionImage      = new Image[10];
    
    /**
    * HashMap utilizzata accedere alle immagini associate ai nomi delle emozioni
    */
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

    
    /**
    * Categoria dell'emozione.
    */
    private String Category;

    /**
    * Spiegazione dell'emozione.
    */
    private String Explanation;

    /**
    * Immagine dell'emozione
    */
    private Image image;

    /**
    * Punteggio dell'emozione dato dall'utente
    */
    private int Score;

    private RegisteredAccount account;
    private int number;

    // ====================== Costruttori ====================== //

    //costruttore 1
    /**
    * costruttore dell'emozione finale
    *@param emozione riferimento di base del tipo di emozione
    *@param Score punteggio dell'emozione
    *@param account riferimento al proprietario che ha assegnato tale emozione
    */
    public Emotion(Emotion emozione, int Score, RegisteredAccount account) 
    {
        //number = Emotion.getEmotionID(emozione);
        
        this.Explanation = emozione.getExplanation();
        this.Category = emozione.getCategory();
        number = emozione.number;

        this.Score = Score;
        this.image = EmotionHashMap.get(emozione.getCategory());
        this.account = account;
    }

    //costruttore 2
    /**
    * Costruttore volutamente privato, utilizzato per la creazione base di un emozione
    */
    private Emotion(String category, String Explanation) 
    {
        this.Explanation = Explanation;
        this.Category = category;
        number = Emotion.init++;
 
    }

    
    
    /** 
     * Restituiasce l'ID dell'emozione dato il suo tipo
     * @param e rappresenta l'emozione di cui si vole ottenere l'ID
     * @return int il valore dell'ID corriposndente all'emozione.
     */
    // ====================== funzioni ====================== //

    public static int getEmotionID(Emotion e) 
    {
        for(int i = 0; i < 9; i++) 
            if(e.getCategory().equals(Emotion.matrice[i][0]) || e.getCategory().equals(Emotion.matrice[i][1]))
                return i;
        
        return -1;
    }

    @Override
    public String toString() {
        String str = "";

        str += "Category: " + this.Category + "\n";
        str += "score: " + this.Score + "\n";
        str += "AccountID: " + this.account.getID() + "\n";
        return new String(str);
    }



    
    /** 
     * Restiuisce la gategoria a cui appartiene l'emozione
     * @return String
     */
    public String getCategory() {
        return  Emotion.matrice[number][EmotionalSongs.language];
    }

    
    /** 
     * Imposta la categoria da assegnare all'emozione.
     * @param category
     */
    public void setCategory(String category) {
        Category = category;
    }

    
    /** 
     * Restituisce la spiegazione dell'emozione
     * @return String
     */
    public String getExplanation() {
        return Emotion.matrice[number + 9][EmotionalSongs.language];
    }
        

    
    /** 
     * Imposta la spiegazione dell'emozione
     * @param explanation
     */
    public void setExplanation(String explanation) {
        Explanation = explanation;
    }

    
    /** 
     * Restituisce il punteggio assegnato a tale emozione
     * @return int
     */
    public int getScore() {
        return Score;
    }

    
    /** 
     * Imposta il valore del punteggio
     * @param score
     */
    public void setScore(int score) {
        Score = score;
    }

    
    /** 
     * Restituisce L'ID del proprietario dell'emozione
     * @return String
     */
    public String getAccountID() {
        return this.account.getID();
    }

    
    /** 
     * Imposta l'user ID del proprietario dell'emozione
     * @param account
     */
    public void SetAccount(RegisteredAccount account) {
        this.account = account;
        System.out.println("account: " + getAccountID());
    }

}
