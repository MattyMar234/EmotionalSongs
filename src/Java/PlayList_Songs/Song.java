package Java.PlayList_Songs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Random;

import Java.DataClasses.Comment;
import Java.Json.JsonParser;
import Java.emotionalsongs.Emotion;

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

    protected ArrayList<Emotion> emotions = new ArrayList<Emotion>();
    protected ArrayList<Comment> comments = new ArrayList<Comment>();

    public Song(String n1) {
        this.title = n1;
    }

    public Song(LinkedHashMap<String, Object> Account)
    {
        this.title     = (String) JsonParser.GetElement(Account, Arrays.asList("Title"));
        this.autor     = (String) JsonParser.GetElement(Account, Arrays.asList("Autor"));
        this.year      = (String) JsonParser.GetElement(Account, Arrays.asList("Year"));
        this.album     = (String) JsonParser.GetElement(Account, Arrays.asList("Album"));
        this.duration  = (String) JsonParser.GetElement(Account, Arrays.asList("Duration"));
        this.genre     = (String) JsonParser.GetElement(Account, Arrays.asList("Type"));
    
    }

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

    public Song getClassReference() {
        return this;
    }


    @Override
    public String toString() {
        String information = "";

        information += "Title: "    + this.title    + "\n\r";
        information += "Autor: "    + this.autor    + "\n\r";
        information += "Year: "     + this.year     + "\n\r";
        information += "Album: "    + this.album    + "\n\r";
        information += "duration: " + this.duration + "\n\r";
        information += "type: "     + this.genre     + "\n\r";
        
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
        return songID;
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
