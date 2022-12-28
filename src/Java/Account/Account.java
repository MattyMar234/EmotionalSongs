package Java.Account;



import org.json.simple.JSONObject;
import Java.emotionalsongs.EmotionalSongs;



/**
 * Questa classe rappresenta le informazioni base possedute da un Account qualsiasi.
 */
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

    
    
    /** 
     * Verifica se due account sono uguali
     * @param toTest Account da verificare con questa classe
     * @return L'esido dell'operazione di confronto
     */
    public boolean IsIndentical(Account toTest)  {
        return false;
    }

    
    /** 
     * Funzione che converte le informazioni delle classe in un oggetto JSONObject
     * @return Restituisce un oggetto JSONObject che rappresenta le informazioni di un oggetto Account.
     */
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


    
    /** 
     * @return String
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


    
    /** 
     * @return String
     */
    public String getSurname() {
        return surname;
    }

    
    /** 
     * @param surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }


    
    /** 
     * @return String
     */
    public String getEmail() {
        return email;
    }

    
    /** 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    
    /** 
     * @return String
     */
    public String getID() {
        return this.userID;
    }


    
    /** 
     * @return String
     */
    public String getPassword() {
        return password;
    }

    
    /** 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    

    
    

    

    
    
}
