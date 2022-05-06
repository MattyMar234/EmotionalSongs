package Java.PlayListSongs;

import java.time.LocalDate;
import java.util.ArrayList;


public class PlayList {
    
    protected String nome;
    protected String CreationDate;
    protected ArrayList<Song> PlayList = new ArrayList<Song>();
    protected String Elements;


    //constructor 1 
    public PlayList(String nome) {
        this.nome = nome;
        
        LocalDate time = LocalDate.now();
        this.CreationDate = time.toString();
    }
    
    //constructor 2
    public PlayList(String nome, ArrayList<Song> PlayList) {
        this(nome);                 //chiamo il costruttore 1
        this.PlayList = PlayList;     //copio l'arrayList
        Elements = String.valueOf(PlayList.size());
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
    


    public void cambiaNome(String nuovo_nome){
        this.nome = nuovo_nome;
    }

    public void addSong(Song a) {
        PlayList.add(a);
        Elements = String.valueOf(PlayList.size());
    }

    public void removeSong(Song a) {
        PlayList.remove(a);
        Elements = String.valueOf(PlayList.size());
    }

    public void clearPlayList() {
        PlayList.clear();
        Elements = String.valueOf(PlayList.size());
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public ArrayList<Song> getPlayList() {
        return PlayList;
    }

    public void setPlayList(ArrayList<Song> playList) {
        PlayList = playList;
    }

    public String getElements() {
        return Elements;
    }

    public static void clear() {
    }

    


    
    
    
}
