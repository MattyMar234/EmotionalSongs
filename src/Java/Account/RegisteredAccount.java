package Java.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Java.PlayListSongs.Album;
import Java.PlayListSongs.PlayList;
import Java.PlayListSongs.Song;

public class RegisteredAccount extends Account {

    //informazioni
    protected String taxIDcode; 
    protected String comune;
    protected String provincia;
    protected String viaPiazza;
    protected int cap;

    //dati canzoni
    protected ArrayList<PlayList> PlayLists = new ArrayList<PlayList>();
    protected Album album; 


    public RegisteredAccount(LinkedHashMap<String, Object> userCostructor) {
        super(userCostructor);  
    }
    
    public RegisteredAccount(HashMap<String, String> userCostructor) {
        super(userCostructor);  
    }

    public RegisteredAccount() {

    }

    public RegisteredAccount(JSONObject data) 
    {
        this.name       = (String) data.get("name");
        this.surname    = (String) data.get("surname");
        this.email      = (String) data.get("email");
        this.password   = (String) data.get("password");
        this.taxIDcode  = (String) data.get("taxIDcode");
        this.comune     = (String) data.get("comune");
        this.provincia  = (String) data.get("provincia");
        this.viaPiazza  = (String) data.get("viaPiazza");
        //this.cap        = ( int  ) data.get("cap");

        JSONArray PlayListsData =  (JSONArray) data.get("PlayLists");

        /*for(Object playlistData : PlayListsData) {
            if(playlistData instanceof JSONObject) {
                //this.playlists.add(new PlayList(nome))
            }
        }*/
    }

    

    @Override
    public String toString() {
        String data = super.toString();

        data +=  "TaxIDcode: " + this.taxIDcode + "\n\r";
        data +=  "cap: "       + this.cap       + "\n\r";
        data +=  "comune: "    + this.comune    + "\n\r";
        data +=  "provincia: " + this.provincia + "\n\r";
        data +=  "viaPiazza: " + this.viaPiazza + "\n\r";

        return data;
    }


    @Override
    public boolean equals(Object obj) {

        if(obj instanceof RegisteredAccount) {
            RegisteredAccount accountToTest = (RegisteredAccount) obj;

            if(accountToTest.getEmail() == this.email || accountToTest.getUserID() == this.userID) {
                return true;
            }
        }
        return false;
    }


    public void elimina_playlist(){
        PlayLists.clear();
    }


    public void crea_playlist_vuota(){
        String nome="";
        ArrayList<Song> daje=new ArrayList<Song>();
        PlayList k = new PlayList(nome,daje);
        album.add_playlist(k);
    }

    public void crea_playlist_con_qualche_canzone(ArrayList<Song> canzoni_scelte_prima) {
        String nome="";
        ArrayList<Song> daje=new ArrayList<Song>();
        PlayList k=new PlayList(nome,daje);
        for(int i=0;i<((CharSequence) canzoni_scelte_prima).length();i++){
            k.addSong(canzoni_scelte_prima.get(i));
        }
        album.add_playlist(k);
    }

    public void addPlaylist(PlayList p) {
        this.PlayLists.add(p);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        userID = userID;
    }


    public String getTaxIDcode() {
        return taxIDcode;
    }

    public void setTaxIDcode(String taxIDcode) {
        taxIDcode = taxIDcode;
    }

    public ArrayList<PlayList> getPlayLists() {
        return this.PlayLists;
    }

    public void setPlayLists(ArrayList<PlayList> playLists) {
        PlayLists = playLists;
    }

    public void clearPlalyst (PlayList a) {
        PlayLists.clear();
    }

    public void removePlayList(PlayList a) {
        PlayLists.remove(a);
    }
    
    public void addPlayList(PlayList a) {
        PlayLists.add(a);
    }

    /*public void cambiaNomePlayList(String nuovo_nome){
        this.nome = nuovo_nome;
    }*/
    
}
