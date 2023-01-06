package Java.PlayListSongs;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Questa classe viene utilizzata per modellare le informazioni di una playlist
 */

public class PlayList {
    
    /**
     * Nome della playlist
     */
    protected String nome;

    /**
     * Data di creazione
     */
    protected String CreationDate;

    /**
     * Elenco delle canzoni presenti nella playlist
     */
    protected ArrayList<Song> PlayList = new ArrayList<Song>();
    protected String Elements;
 

    //constructor 1
    /**
     * Costruttore senza parametri
     */
    public PlayList() {
        LocalDate time = LocalDate.now();
        this.CreationDate = time.toString();
        //this.reference = this;
    }

    //constructor 2
    /**
     * @param nome Nome della playlist
     */
    public PlayList(String nome) {
        this();
        this.nome = nome;
    }
    
    //constructor 3
    /**
     * @param nome Nome della playlist
     * @param PlayList Elenco delle canzoni
     */
    public PlayList(String nome, ArrayList<Song> PlayList) {
        this(nome);                 //chiamo il costruttore 1
        this.PlayList = PlayList;     //copio l'arrayList
        Elements = String.valueOf(PlayList.size());
    }

    public PlayList getReference() {
        return this;
    }

    
    /** 
     * Crea una copia della playlist con le stesse canzoni
     * @return PlayList
     */
    public PlayList copy() {
        PlayList s = new PlayList();

        if(nome != null) {
            s.nome = new String(nome);
        }
        
        s.CreationDate = new String(CreationDate);
        s.PlayList = new ArrayList<Song>(PlayList);
        s.Elements = String.valueOf(s.PlayList.size());

        return s;
    }


    @Override
    public String toString() {
        String contenuto;

        contenuto = "playlist " + nome + "\nelementi presenti (" + PlayList.size() + "):\n";  

        int index = 1;
        for(Song a : PlayList){
            contenuto += "canzone " + (index++) + ": " + a.getTitle() + "\n\r";
        }
        return contenuto;
    }
    


    
    /** 
     * Questa funzione permette di cambiare il nome della playlist
     * @param nuovo_nome
     */
    public void cambiaNome(String nuovo_nome){
        this.nome = nuovo_nome;
    }

    
    /** 
     * Aggiunge una nuova canzone nella playlist
     * @param a La nuova canzone da  aggiungere
     */
    public void addSong(Song a) {
        PlayList.add(a);
        Elements = String.valueOf(PlayList.size());
    }

    
    /** 
     * Rimuove un acanzone dalla playlist
     * @param a La canzone da rimuovere
     */
    public void removeSong(Song a) {
        PlayList.remove(a);
        Elements = String.valueOf(PlayList.size());
    }

    /** 
     * Elimina tutte l ecanzoni datta playlist
     */
    public void clearPlayList() {
        PlayList.clear();
        Elements = String.valueOf(PlayList.size());
    }



    
    /** 
     * @return String
     */
    public String getNome() {
        return nome;
    }

    
    /** 
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    
    /** 
     * @return String
     */
    public String getCreationDate() {
        return CreationDate;
    }

    
    /** 
     * @param creationDate
     */
    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    
    /** 
     * @return ArrayList<Song>
     */
    public ArrayList<Song> getSongs() {
        return PlayList;
    }

    
    /** 
     * @return String
     */
    public String getElements() {
        return Elements;
    }  
}
