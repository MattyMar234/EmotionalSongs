package emotionalsongs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import PlayListSongs.Album;
import PlayListSongs.PlayList;
import PlayListSongs.Song;

public class RegisteredAccount extends Account {

    //informazioni
    protected String TaxIDcode; 
    protected String comune;
    protected String provincia;
    protected String viaPiazza;
    protected int cap;

    //dati canzoni
    protected ArrayList<PlayList> PlayLists = new ArrayList<PlayList>();
    protected Album album; 

    public RegisteredAccount(HashMap<String, String> userCostructor) {
        super(userCostructor);
        
    }

    @Override
    public String toString() {
        String data = super.toString();

        data +=  "TaxIDcode: " + this.TaxIDcode;
        data +=  "cap: "       + this.cap;
        data +=  "comune: "    + this.comune;
        data +=  "provincia: " + this.provincia;
        data +=  "viaPiazza: " + this.viaPiazza;

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
            k.addcanzone(canzoni_scelte_prima.get(i));
        }
        album.add_playlist(k);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        userID = userID;
    }


    public String getTaxIDcode() {
        return TaxIDcode;
    }

    public void setTaxIDcode(String taxIDcode) {
        TaxIDcode = taxIDcode;
    }

    public ArrayList<PlayList> getPlayLists() {
        return PlayLists;
    }

    public void setPlayLists(ArrayList<PlayList> playLists) {
        PlayLists = playLists;
    }

    
    
}
