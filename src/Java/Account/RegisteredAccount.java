package Java.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Java.PlayList_Songs.Album;
import Java.PlayList_Songs.PlayList;
import Java.PlayList_Songs.Song;

public class RegisteredAccount extends Account {

    //informazioni
    protected String fiscalCode; 
    protected String comune;
    protected String provincia;
    protected String viaPiazza;
    protected String UserID;
    protected String civicNumber;
    protected String cap;

    //dati canzoni
    protected ArrayList<PlayList> PlayLists = new ArrayList<PlayList>();
    protected Album album; 


    public RegisteredAccount(JSONObject data) 
    {
        this.name        = (String) data.get("name");
        this.surname     = (String) data.get("surname");
        this.email       = (String) data.get("email");
        this.password    = (String) data.get("password");
        this.fiscalCode  = (String) data.get("codiceFiscale");
        this.comune      = (String) data.get("comune");
        this.userID      = (String) data.get("userID");
        this.civicNumber = (String) data.get("civicNumber");
        this.provincia   = (String) data.get("provincia");
        this.viaPiazza   = (String) data.get("viaPiazza");
        this.cap         = (String) data.get("cap");
        //this.cap        = ( int  ) data.get("cap");

        JSONArray PlayLists_array =  (JSONArray) data.get("PlayLists");

        if(PlayLists_array == null) {
            return;
        }

        for(Object playlistData : PlayLists_array) {
            if(playlistData instanceof JSONObject) 
            {

                PlayList NewPlaylist = new PlayList();
                JSONObject d = (JSONObject) playlistData;
                JSONArray SongsList = (JSONArray) d.get("song");

                NewPlaylist.setNome((String)d.get("name"));
                NewPlaylist.setCreationDate((String)d.get("creationDate"));

                for (Object songID : SongsList) {
                    NewPlaylist.addSong(this.main.songManager.getSong_by_ID((String)songID));
                }

                PlayLists.add(NewPlaylist);
                
            }
        }
    }


    public RegisteredAccount() {

    }

    @Override
    public String toString() {
        String data = super.toString();

        data +=  "TaxIDcode: " + this.fiscalCode + "\n\r";
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


    @SuppressWarnings("unchecked")
    public JSONObject getAccountDataStructure() 
    {
        JSONObject data = super.getDataStructure();
        JSONArray PlayListsData_array = new JSONArray();
        Song s = new Song();

        data.put("codiceFiscale", this.fiscalCode);
        data.put("cap",           this.cap);
        data.put("comune",        this.comune);
        data.put("provincia",     this.provincia);
        data.put("viaPiazza",     this.viaPiazza);
        data.put("userID",        this.userID);
        data.put("civicNumber",   this.civicNumber);

        try {
            for(PlayList p : PlayLists) {
                
                JSONObject playlistData = new JSONObject(); //oggetto playlist
                JSONArray songData_array = new JSONArray(); //suoni presenti

                for(Song song : p.getSongs()) {
                    songData_array.add(song.getSongID());
                    s = song;
                }

                playlistData.put("name", p.getNome());
                playlistData.put("creationDate", p.getCreationDate());
                playlistData.put("elements", p.getElements());
                playlistData.put("song",songData_array);

                PlayListsData_array.add(playlistData);  //aggiungo all'array la playlist
            }

            data.put("PlayLists", PlayListsData_array);

            
        } catch (Exception e) {
            System.out.println("null song error");
            System.out.println(e);
        }

        return data;
        
    }



    public void elimina_playlist(){
        PlayLists.clear();
    }


    public void remove_playlist(PlayList a) {
        PlayLists.remove(a);
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
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public String getFiscalCode() {
        return fiscalCode;
    }

    public void SetFiscalCode(String taxIDcode) {
        this.fiscalCode = taxIDcode;
    }

    public ArrayList<PlayList> getPlayLists() {
        return this.PlayLists;
    }

    public void setPlayLists(ArrayList<PlayList> playLists) {
        PlayLists = playLists;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getViaPiazza() {
        return viaPiazza;
    }

    public void setViaPiazza(String viaPiazza) {
        this.viaPiazza = viaPiazza;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    

    
    
}
