package PlayListSongs;

import java.util.Arrays;
import java.util.LinkedHashMap;

import JsonFile.Json;

public class Song {

    protected String title;
    protected String autor;
    protected String year;
    protected String album;
    protected String duration;
    protected String genre;

    public Song(String n1) {
        this.title = n1;
    }

    public Song(LinkedHashMap<String, Object> Account)
    {
        this.title     = (String) Json.GetElement(Account, Arrays.asList("Title"));
        this.autor     = (String) Json.GetElement(Account, Arrays.asList("Autor"));
        this.year      = (String) Json.GetElement(Account, Arrays.asList("Year"));
        this.album     = (String) Json.GetElement(Account, Arrays.asList("Album"));
        this.duration  = (String) Json.GetElement(Account, Arrays.asList("Duration"));
        this.genre     = (String) Json.GetElement(Account, Arrays.asList("Type"));
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String type) {
        this.genre = type;
    }

    
    
    
}
