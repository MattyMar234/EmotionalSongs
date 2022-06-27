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

    //costruttore2
    public Account(String[] data)
    {
        name     = data[0];
        surname  = data[1];
        email    = data[2];
        password = data[3];
    }

    //costruttore3
    public Account(LinkedHashMap<String, Object> Account)
    {
        this.name       = (String) JsonParser.GetElement(Account, Arrays.asList("name"));
        this.surname    = (String) JsonParser.GetElement(Account, Arrays.asList("surname"));
        this.email      = (String) JsonParser.GetElement(Account, Arrays.asList("email"));
        this.password   = (String) JsonParser.GetElement(Account, Arrays.asList("password"));
        
    }

    //costruttore4
    public Account(HashMap<String, String> AccountData)
    {
        this.name       = AccountData.get("name");
        this.surname    = AccountData.get("surname");
        this.email      = AccountData.get("email");
        this.password   = AccountData.get("password1");
        this.userID     = AccountData.get("userID");
    }


    @Override
    public String toString() {
        String informations = "";

        informations += "Name: "     + this.name      + "\n\r";
        informations += "surname: "  + this.surname   + "\n\r";
        informations += "email: "    + this.email     + "\n\r";
        informations += "password: " + this.password  + "\n\r";
        
        return informations;
    }

    

    public boolean IsIndentical(Account totest)  {
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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    

    
    

    

    
    
}
