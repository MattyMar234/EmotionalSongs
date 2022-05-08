package Java.Managers;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import Java.Json.JsonParser;
import Java.emotionalsongs.EmotionalSongs;

public class Manager <T> {

    protected ArrayList<T> Data = new ArrayList<T>(); 
    protected String FilePath;
    protected JsonParser jsonFileReader;
    protected EmotionalSongs main;

    public JSONObject[] readJsonData() throws ParseException, IOException 
    {
        jsonFileReader = new JsonParser(FilePath);
        return jsonFileReader.ReadJsonFile_as_ArrayOfJsonObject(); 
    }

    public ArrayList<T> getList() {
        return this.Data;
    }


    public Manager(String path, EmotionalSongs main) {
        this.FilePath = path;
        this.main = main;
    }

    public void appendData(T data) {
        this.Data.add(data);
    }

    public void removeData(T data) {
        this.Data.remove(data);
    }

    public int getElement(T element) {
        return Data.indexOf(element);
    }

    public T getElement(int element) {
        return Data.get(element);
    }

    public void setPath(String path) {
        this.FilePath = path;
    }
    
}
