package Java.PlayListSongs;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Java.Account.Account;
import Java.Account.RegisteredAccount;
import Java.DataClasses.Comment;
import Java.DataClasses.Emotion;
import Java.emotionalsongs.EmotionalSongs;


/**
 * Questa classe viene utilizzata per rappresentare le informazioni su una canzone 
 */
public class Song {

    
    protected String number;

    /**
     * Titolo della canzone 
     */
    protected String title;

    /**
     * Autor della canzone 
     */
    protected String autor;

    /**
     * Anno publicazione della canzone 
     */
    protected String year;

    /**
     * Album in cui è presente la canzone
     */
    protected String album;
    protected String duration;

    /**
     * Tipo di genere della canzone 
     */
    protected String genre;

    /**
     * L'id della canzone
     */
    protected String songID;
    protected int AlbumID;

    /**
     * Elenco delle emozioni messe dagli utenti
     */
    protected ArrayList<Emotion> emotions = new ArrayList<Emotion>();

    /**
     * Elenco dei commenti fatti dagli utenti
     */
    protected ArrayList<Comment> comments = new ArrayList<Comment>();

    //costruttore 1
    public Song() {

    }

    //costruttore 2 per CSV
    /**
     * Inizializza un oggetto Song con le informazioni prese da un file CSV
     * @param data eleneco delle colonne della singola linea
     */
    public Song(String [] data) {

        int minutes = (int)Float.parseFloat(data[10])/60;
        int seconds = (int)(Float.parseFloat(data[10]))%60;

        if(data[17].length() < 4) {
            Random rand = new Random();
            data[17] = String.valueOf(rand.nextInt(2006 - 1978) + 1978);
        }
   
        this.number    = data[0];
        this.title     = data[16];
        this.autor     = data[8];
        this.year      = data[17];
        this.album     = data[3];
        this.duration  = String.valueOf(minutes) + ":" + (String.valueOf(seconds).length() == 1 ? "0" + String.valueOf(seconds) : String.valueOf(seconds)) + " min";
        this.genre     = "?";
        this.AlbumID = Integer.parseInt(data[2]);
        this.songID = data[1];
    }

    //costruttore 3 per JSON
    /**
     * Inizializza un oggetto Song con le informazioni prese da un file Json
     * @param jsonData Oggetto JSONObject con le informazioni deserializzate prese dal file
     */
    public Song(JSONObject jsonData) {

        this.title    = (String) jsonData.get("title");
        this.year     = (String) jsonData.get("year");
        this.album    = (String) jsonData.get("album");
        this.genre    = (String) jsonData.get("genre");
        this.duration = (String) jsonData.get("duration");
        this.autor    = (String) jsonData.get("autor");
        this.songID   = (String) jsonData.get("SongID");
        
        JSONArray comments =  (JSONArray) jsonData.get("comments");
        JSONArray emotions =  (JSONArray) jsonData.get("emotions");

        
        //if(comments.size() > 0) System.out.println("Comments: " + comments);
        if(comments != null) {
            for(Object comm : comments) {
                if(comm instanceof JSONObject) 
                {
                    JSONObject d = (JSONObject) comm;
                    
                    Comment comment = new Comment();
                    comment.setComment((String) d.get("comment"));
    
                    String ID = (String) d.get("userID");
                 
                    comment.setAutor((Account) EmotionalSongs.classReference.AccountsManager.SearchByID(ID));
                    this.comments.add(comment);
                }
            }
        }

        if(emotions != null && !emotions.isEmpty()) 
        {
            for(Object emot : emotions) {
                if(emot instanceof JSONObject) 
                {
                    JSONObject d = (JSONObject) emot;

                    int index     = Integer.parseInt((String)d.get("ID"));
                    int score     = Integer.parseInt((String)d.get("score"));
                    String userID = (String) d.get("userID");


                    RegisteredAccount account = EmotionalSongs.classReference.AccountsManager.SearchByID(userID);
                    Emotion emotion = new Emotion(Emotion.Emotions[index], score, account);
                    
                    this.emotions.add(emotion);
                }
            }
        }
    }

    

    public Song getClassReference() {
        return this;
    }

    
    /** 
     * Restituisce l'elenco delle emozioni inserite dall'utente 
     * @param ID l'Id dell'utente
     * @return ArrayList<Emotion>
     */
    public ArrayList<Emotion> getUserEmotions(String ID) {
        ArrayList<Emotion> m = new ArrayList<Emotion>();

        for(Emotion e : emotions) {
            if(e.getAccountID().equals(ID)) {
                m.add(e);
               
                if(m.size() == 9) 
                    break;
            }
        }

        return m;
    }

    
    /** 
     * Aggiunge un'emozione nell'elenco
     * @param emotion emozione da inserire
     * @param account l'utente che sta inserendo l'emozione
     */
    public void AddUserEmotions(Emotion emotion, RegisteredAccount account) 
    {
       
        //verifico se tale emotion è gia presente e la elimino
        removeEmotion(emotion, account);
        emotions.add(emotion);
        //System.out.println("class SONG r144: emotion aggiunta");
    }

    
    /** 
     * Rimuove un'emozione dall'elenco
     * @param emotion L'emozione da rimuovere
     * @param account L'utente che sta rimuovendo l'emozione
     */
    public void removeEmotion(Emotion emotion, RegisteredAccount account) {

        String ID = account.getID();

        //elenco delle emotion da eliminare, anche per eventuali duplicati
        Stack<Emotion> DelBuffer = new Stack<Emotion>();

        //cerco nella raccolta
        for(Emotion e : emotions) {
            //System.out.println("class SONG r159, IDs: " + e.getCategory() + " ==> " + emotion.getCategory());
            
            //confronto gli ID e la tipologia
            if(e.getAccountID().equals(ID) && e.getCategory().equals(emotion.getCategory())) {
                DelBuffer.push(e);   
                //System.out.println(e);
            }
        }

        while (DelBuffer.size() != 0) {
            emotions.remove(DelBuffer.pop());
            //System.out.println("class SONG r169: emotion eliminata");
        }
    }

    
    /** 
     * Trasforma le informazioni della canzone in un oggetto JSONObject per la serializzazione
     * @return JSONObject
     */
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() 
    {
        JSONObject data = new JSONObject();
        JSONArray comments_array = new JSONArray();
        JSONArray Emotions_array = new JSONArray();


        for(Comment c : comments) {
            JSONObject d = new JSONObject(); //oggetto playlist
            
            d.put("userID", c.getAutor().getID());    //c,get ID
            d.put("comment", c.getComment());
            
            //aggiungo all'array la playlist
            comments_array.add(d);  
        }


        for(Emotion e : emotions) {   
            JSONObject d = new JSONObject(); //oggetto playlist
            
            d.put("ID"     , Integer.toString (Emotion.getEmotionID(e)));    //c,get ID
            d.put("userID" , e.getAccountID());
            d.put("score"  , Integer.toString(e.getScore()));
            
            //aggiungo all'array delle emozioni
            Emotions_array.add(d);  
        }

        data.put("comments", comments_array);
        data.put("emotions", Emotions_array);
        data.put("title",    this.title);
        data.put("autor",    this.autor);
        data.put("year",     this.year);
        data.put("album",    this.album);
        data.put("duration", this.duration);
        data.put("genre",    this.genre);
        data.put("SongID",   this.songID);

        //Integer.toString(Song.counter++)

        return data;
    }

    


    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        String information = "";

        information += "Title: "    + this.title    + "\n\r";
        information += "Autor: "    + this.autor    + "\n\r";
        information += "Year: "     + this.year     + "\n\r";
        information += "Album: "    + this.album    + "\n\r";
        information += "duration: " + this.duration + "\n\r";
        information += "type: "     + this.genre    + "\n\r";
        information += "ID: "       + this.songID   + "\n\r";
        
        return information;
    }

    
    /** 
     * Restituisce l'elenco dei commenti
     * @return ArrayList<Comment>
     */
    public ArrayList<Comment> getComments() {
        return new ArrayList<Comment>(comments);
    }

    
    /** 
     * Aggiunge un commento alla raccolta
     * @param e Il commento da aggiungere
     */
    public void addComment(Comment e ) {
        this.comments.add(e);
    }

    
    /** 
     * Rimuove il commento presenta a un determinato indice della lista
     * @param index L'indice della lista
     * @return Risultato dell'operazione
     */
    public boolean removeComment(int index) {
        return removeComment(comments.get(index));
    }

    
    /** 
     * Rimuove il commento dalla lista
     * @param e Il commento da rimuovere
     * @return Risultato dell'operazione
     */
    public boolean removeComment(Comment e) {
        if(e != null) {
            comments.remove(comments.indexOf(e));
            return true;
        }
        return false;
    }


    

    
    /** 
     * Restituisce l'elenco di tutte le emozioni
     * @return ArrayList<Emotion>
     */
    public ArrayList<Emotion> getEmotions() {
        return this.emotions;
    }

    
   

    
    /** 
     * @return String
     */
    public String getTitle() {
        return title;
    }

    
    /** 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    
    /** 
     * @return String
     */
    public String getAutor() {
        return autor;
    }

    
    /** 
     * @param autor
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    
    /** 
     * @return String
     */
    public String getYear() {
        return year;
    }

    
    /** 
     * @param year
     */
    public void setYear(String year) {
        this.year = year;
    }

    
    /** 
     * @return String
     */
    public String getAlbum() {
        return album;
    }

    
    /** 
     * @param album
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    
    /** 
     * @return String
     */
    public String getDuration() {
        return duration;
    }

    
    /** 
     * @param duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    
    /** 
     * @return String
     */
    public String getNumber() {
        return number;
    }

    
    /** 
     * @param number
     */
    public void setNumber(String number) {
        this.number = number;
    }



    public String getSongID() {
        return this.songID;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }

    public int getAlbumID() {
        return AlbumID;
    }

    public void setAlbumID(int albumID) {
        AlbumID = albumID;
    }

}
