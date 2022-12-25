package Java.Managers;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import Java.Json.JsonParser;
import Java.emotionalsongs.EmotionalSongs;


/**
 * Classe base per la gestione dei vari manager dei dati
 */
public abstract class Manager <T> {

    /**
    *Eleneco dei dati
    */
    protected ArrayList<T> Data = new ArrayList<T>(); 

    /**
    *Percorso del file dove sono memroizzati i dati.
    */
    protected String FilePath;

    /**
    *Oggetto JsonParser utilizzato per la lettura dei dati .
    */
    protected JsonParser jsonFileReader;

    /**
    *Riferimento della classe principale.
    */
    protected EmotionalSongs main;


    /**
    * @param Path Percorso del file da cui si prelevano e scrivono i dati.
    * @param main Riferimento alla classe Main.
    */
    public Manager(String path, EmotionalSongs main) {
        this.FilePath = path;
        this.main = main;
    }

    
    /** 
     * Legge i dati dal file json e restituisce un array di JSONObject
     * @return Elenco in froma JSONObject[] degli oggetti presenti nel file json.  
     * @throws ParseException Se si verifica un errore nella serializzazione dei dati nel file.
     * @throws IOException Se si verifica un errore nella lettura del file.
     */
    public JSONObject[] readJsonData() throws ParseException, IOException 
    {
        jsonFileReader = new JsonParser(FilePath);
        return jsonFileReader.ReadJsonFile_as_ArrayOfJsonObject(); 
    }

    
    /** 
     * Viene utilizzato per ottenere il riferimento della lista utilizzata dal Manager.
     * @return Ritorna l'ArrayList degli oggetti memorizzati dal Manager.
     */
    public ArrayList<T> getList() {
        return this.Data;
    }


    
    /** 
     * Viene utilizzato per aggiungere un nuovo elemento alla raccolta dati del Manager
     * @param data Dato da aggiungere alla raccolta dati del Manager.
     */
    public void appendData(T data) {
        this.Data.add(data);
    }

    
    /** 
     * Utilizzato per rimuovere un elemento dalla raccolta dati del Manager.
     * @param data Rimuove il dato dalla raccolta del Manager.
     */
    public void removeData(T data) {
        this.Data.remove(data);
    }

    
    /** 
     * Per ottenere l'indice della lista in cui è situato l'elemento.
     * @param element L'elemento da cercare nella raccolta dati del Manager.
     * @return Ritorna l'indice della lista in cui si trova l'elemento cercato.
     */
    public int getElement(T element) {
        return Data.indexOf(element);
    }

    
    /** 
     * Utilizzato per ottenere l'elemento della lista corripondente a tale indice.
     * @param index L'indice dell'elemento presente nella lista che si vuole ottenere.
     * @return Restituisce Null se l'indice non è valido. Altrimenti si ottiene l'oggetto corrispondente a tale indice.
     */
    public T getElement(int index) {
        return Data.get(index);
    }

    
    /** 
     * Per cambiare il percorso del file.
     * @param path Il nuovo percorso da assegnare
     */
    public void setPath(String path) {
        this.FilePath = path;
    }
    
}
