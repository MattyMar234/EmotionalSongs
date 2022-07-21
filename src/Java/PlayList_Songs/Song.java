package Java.PlayList_Songs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import Java.Account.Account;
import Java.DataClasses.Comment;
import Java.Json.JsonParser;
import Java.emotionalsongs.Emotion;
import Java.emotionalsongs.EmotionalSongs;

public class Song {

    protected String number;
    protected String title;
    protected String autor;
    protected String year;
    protected String album;
    protected String duration;
    protected String genre;
    protected String songID;
    protected int AlbumID;

    private static int counter = 1;

    protected ArrayList<Emotion> emotions = new ArrayList<Emotion>();
    protected ArrayList<Comment> comments = new ArrayList<Comment>();

    //costruttore 1
    public Song() {

    }

    //costruttore 2 per CSV
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
    public Song(JSONObject jsonData) {

        this.title    = (String) jsonData.get("title");
        this.year     = (String) jsonData.get("year");
        this.album    = (String) jsonData.get("album");
        this.genre    = (String) jsonData.get("genre");
        this.duration = (String) jsonData.get("duration");
        this.autor    = (String) jsonData.get("autor");
        this.songID   = (String) jsonData.get("SongID");
        
        JSONArray comments =  (JSONArray) jsonData.get("comments");
        if(comments.size() > 0) System.out.println("Comments: " + comments);
        if(comments == null) return;


        for(Object comm : comments) {
            if(comm instanceof JSONObject) 
            {
                JSONObject d = (JSONObject) comm;
                
                Comment comment = new Comment();
                comment.setComment((String) d.get("comment"));

                String ID = (String) d.get("userID");
               // System.out.println("Account ID: " + ID);
                comment.setAutor((Account) EmotionalSongs.classReference.AccountsManager.SearchByID(ID));
                

                //System.out.println(comment);

                this.comments.add(comment);
            }
        }
    }

    public Song getClassReference() {
        return this;
    }

    @SuppressWarnings("unchecked")
    public JSONObject toJSON() 
    {
        JSONObject data = new JSONObject();
        JSONArray comments_array = new JSONArray();

        for(Comment c : comments) {
            
            JSONObject d = new JSONObject(); //oggetto playlist
            
            d.put("userID", c.getAutor().getID());    //c,get ID
            d.put("comment", c.getComment());
            
            comments_array.add(d);  //aggiungo all'array la playlist
        }

        data.put("comments", comments_array);
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

    public ArrayList<Comment> getComments() {
        return new ArrayList<Comment>(comments);
    }

    public void addComment(Comment e ) {
        this.comments.add(e);
    }

    public boolean removeComment(int index) {
        return removeComment(comments.get(index));
    }

    public boolean removeComment(Comment e) {
        if(e != null) {
            comments.remove(comments.indexOf(e));
            return true;
        }
        return false;
    }


    

    public ArrayList<Emotion> getEmotions() {
        return this.emotions;
    }

    public void setEmotions(ArrayList<Emotion> emotions) {
        this.emotions = emotions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getNumber() {
        return number;
    }

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
