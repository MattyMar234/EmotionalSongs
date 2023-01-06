package Java.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Java.PlayListSongs.PlayList;
import Java.PlayListSongs.Song;


/**
 * Questa classe rappresenta un Account registrato nell'Applicazione
 */
public class RegisteredAccount extends Account {

    //informazioni

    /**
    * Codice fiscale dell'utente
    */
    protected String fiscalCode; 
    
    /**
    * comune di residenza dell'utente
    */
    protected String comune;

    /**
    * provincia di residenza dell'utente
    */
    protected String provincia;

    /**
    * via/Piazza di residenza dell'utente
    */
    protected String viaPiazza;

    /**
    * ID dell'utente
    */
    protected String UserID;

    /**
    * Codice civico dell'utente
    */
    protected String civicNumber;

    /**
    * CAP dell'utente
    */
    protected String cap;

    //dati canzoni
    /**
    * Elenco PlayLists dell'utente
    */
    protected ArrayList<PlayList> PlayLists = new ArrayList<PlayList>();
    
    public HashMap<String, String> accountKeys = new HashMap<String, String>();
    

    public RegisteredAccount() {

    }

    /**
     * Costruttore che inizializza l'instanza con un oggetto JSONObject, in quale contiene tutte le informazioni che tale instanza deve possedere
     */
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

        updateHashMap();

    }

    /**
     * Aggiorna la struttura dati accountKeys
     */
    public void updateHashMap() {

        accountKeys.clear();
        
        accountKeys.put("name"          , this.name);
        accountKeys.put("surname"       , this.surname);
        accountKeys.put("email"         , this.email );
        accountKeys.put("password"      , this.password );
        accountKeys.put("userid"        , this.userID);
        accountKeys.put("common"        , this.comune);
        accountKeys.put("province"      , this.provincia);
        accountKeys.put("fiscalcode"    , this.fiscalCode);
        accountKeys.put("cap"           , this.cap);
        accountKeys.put("vie"           , this.viaPiazza);
        accountKeys.put("civicNumber"   , this.civicNumber);
    }
    
    
    /**
    * Carica in memoria le playlist dell'utente
    *  @param data Oggetto JSONObject che contiene le informazioni delle playlist create
    */
    public void loadPlaylits(JSONObject data) {
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
                    //System.out.println(songID);
                    NewPlaylist.addSong(this.main.songManager.getSong_by_ID((String)songID));
                    //System.out.println("ID: " + songID);
                }

                PlayLists.add(NewPlaylist);
                
            }
        }
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

    /**
    * Questa funzione converte i dati dell'utente in un oggetto JSONObject.
    * @return restituisce un oggetto JSONObject che rappresenta le informazioni dell'Account.
    */
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


    /** 
     * Elimina tutte le playlist possedute dall'utente
     */
    public void elimina_playlist(){
        PlayLists.clear();
    }


    
    /** 
     * Rimuove la playlist presente nella raccolta
     * @param toRemove La playlist da rimuovere dalla raccolta. Tale valore, deve essere un riferimento a una delle playlist presenti nella raccolta.
     */
    public void remove_playlist(PlayList toRemove) {
        PlayLists.remove(toRemove);
    }


    
    /** 
     * Aggiunge una nuova playlist alla raccolta dell'utente
     * @param NewPlaylist La nuova playlist da aggiungere alla raccolta delle playlist
     */
    public void addPlaylist(PlayList NewPlaylist) {
        this.PlayLists.add(NewPlaylist);
    }

    
    /** 
     * Restituisce l'userID auttoale dell'utente
     * @return String
     */
    public String getUserID() {
        return this.userID;
    }

    
    /** 
     * Imposta l'user ID 
     * @param userID il nuovo user ID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }


    
    /** 
     * Restituisce il codice fiscale posseduto dall'Utente
     * @return String
     */
    public String getFiscalCode() {
        return fiscalCode;
    }

    
    /** 
     * Imposta il codice fiscle dell'utente
     * @param taxIDcode il nuovo valore da assegnare.
     */
    public void SetFiscalCode(String taxIDcode) {
        this.fiscalCode = taxIDcode;
    }

    
    /** 
     * Restituisce le Playlist realizzate dall'utente
     * @return ArrayList<PlayList>
     */
    public ArrayList<PlayList> getPlayLists() {
        return this.PlayLists;
    }

    
    /** 
     * Imposta un nuovo ArrayList di playlist sovrascrivendo quello precedente
     * @param playLists La nuova playlist da assegnare
     */
    public void setPlayLists(ArrayList<PlayList> playLists) {
        PlayLists = playLists;
    }

    
    /** 
     * Restituisce il valore del Comune di residenza dell'utente
     * @return String
     */
    public String getComune() {
        return comune;
    }

    
    /** 
     * Imposta il nuovo valore del comune
     * @param comune
     */
    public void setComune(String comune) {
        this.comune = comune;
    }

    
    /** 
     * @return String
     */
    public String getProvincia() {
        return provincia;
    }

    
    /** 
     * Imposta il nuovo valore dalla provincia
     * @param provincia
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    
    /** 
     * Restituisce il valore dalla via/Piazza
     * @return String
     */
    public String getViaPiazza() {
        return viaPiazza;
    }

    
    /** 
     * Imposta il nuovo valore dalla via/Piazza
     * @param viaPiazza
     */
    public void setViaPiazza(String viaPiazza) {
        this.viaPiazza = viaPiazza;
    }

    
    /** 
     * Restituisce il CAP dell'utente
     * @return String
     */
    public String getCap() {
        return cap;
    }

    
    /** 
     * Imposta il nuovo valore dal CAP
     * @param cap il nuovo CAP da Assegnare
     */
    public void setCap(String cap) {
        this.cap = cap;
    }

    

    
    
}
