package Java.Account;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.simple.JSONObject;

import Java.Json.JsonParser;
import Java.PlayList_Songs.Album;
import Java.PlayList_Songs.PlayList;
import Java.PlayList_Songs.Song;
import Java.emotionalsongs.EmotionalSongs;
import java.util.ArrayList;

public class Account {

    protected EmotionalSongs main;
    protected String name;
    protected String surname;
    protected String email;
    protected String password;
    protected String userID;
    
    
    //costruttore1 
    public Account()  {
        this.main = EmotionalSongs.classReference;
    }

    @Override
    public String toString() {
        String informations = "";

        informations += "Name     : " + this.name      + "\n\r";
        informations += "surname  : " + this.surname   + "\n\r";
        informations += "email    : " + this.email     + "\n\r";
        informations += "password : " + this.password  + "\n\r";
        informations += "ID       : " + this.userID    + "\n\r";
        
        return informations;
    }

    
    public boolean IsIndentical(Account toTest)  {
        return false;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getDataStructure() {
        JSONObject data = new JSONObject();

        data.put("name", this.name);
        data.put("surname",    this.surname);
        data.put("email",    this.email);
        data.put("password",    this.password);
        data.put("userID",   this.userID);

        return data;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getID() {
        return this.userID;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    

    
    

    

    
    
}
