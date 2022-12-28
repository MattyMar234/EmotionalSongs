package Java.Managers;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import Java.Account.RegisteredAccount;
import Java.DataClasses.Common;
import Java.DataClasses.Province;
import Java.Json.JsonParser;
import Java.emotionalsongs.EmotionalSongs;

/**
 * Questa classe estende la classe Manager e gestisce i dati inerenti agli utenti registrati.
 */
public class AccountsManager extends Manager <RegisteredAccount> 
{

    /**
    * Crea un oggeto per la gestione dei dati degli utenti.
    * @param Path Percorso del file da cui si prelevano e scrivono i dati.
    * @param main Riferimento alla classe Main.
    */
    public AccountsManager(String FilePath, EmotionalSongs main) {
        super(FilePath, main);
    }


    
    /** 
     * Cerca a quale Account corrisponde l'email passato come argomento
     * @param email L'email dell'Account da cercare
     * @return Restituisce un oggetto RegisteredAccount che rappresenta l'Account che possiede tale Email. Altrimenti NULL se non vi è associato nessun Account a tale Email.
     */
    // ============================ Opzioni di ricerca ============================//

    public RegisteredAccount SearchByEmail(String email) {
        
        for (RegisteredAccount account : Data) {
            if(account.getEmail().equals(email)) {
                return account;
            }
        }
        return null;
    }

    
    /** 
     * Cerca a quale Account corrisponde l'ID passato come argomento
     * @param ID L'ID dell'Account da cercare
     * @return Restituisce un oggetto RegisteredAccount che rappresenta l'Account che possiede tale ID. Altrimenti NULL se non vi è associato nessun Account a tale ID.
     */
    public RegisteredAccount SearchByID(String ID) 
    {
        for (RegisteredAccount account : Data) {
            String id = account.getUserID();
            
            if(id != null && id.equals(ID)) {
                return account;
            }
        }
        return null;
    }

    
    /** 
     * Questa funzione verifica se il nuovo Account creato soddisfa i requisiti per potermo memorizzare.
     * @param temp Il nuovo Account da verificare
     * @return Restituisce un numero che corrisponde al requisito che non ha superato. Il risultato '0' significa che ha superato tutti i requisiti.
     */
    public int checkAccaunt(RegisteredAccount temp) 
    {
        //return true;
        for(RegisteredAccount existingAcaunt : Data) 
        {
            //se mancano alcuni campi di altri utenti (utenti di test)
            try {
                if(existingAcaunt.getEmail().equals(temp.getEmail()))           return 1;
                if(existingAcaunt.getUserID().equals(temp.getUserID()))         return 2;

            
            } catch (Exception e) {
                
            }
        }

        return 0;
    }

    /** 
     * Questa funzione carica tutte le playlist create da un Account
     */
    public void LoadAccountsPlaylists() {
        System.out.println("reading file " + this.FilePath);

        try {  

            //ottengo l'array dei dati
            JSONObject[] dataArray = readJsonData();

            //per ogni utente inserisco i suoi dati
            for(int i = 0; i < dataArray.length; i++) {
                this.Data.get(i).loadPlaylits(dataArray[i]);
            }
            

        } catch (FileNotFoundException e) {
            System.out.println("Json File not found");
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("Reading Error");
            e.printStackTrace();

        } catch (ParseException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
            
        } finally {
            System.out.println("reading completed");    
        }
    }


    
    /** 
     * Questa funzione carica i dati di tutti gli utenti salvati nel file
     * @return Restituisce TRUE se l'operazione di caricamento va a buon fine altrimenti FALSE.
     */
    public boolean LoadAccounts()
    {
        System.out.println("reading file " + this.FilePath);

        try {  
            for(JSONObject newUserData : readJsonData()) 
            {
                Data.add(new RegisteredAccount(newUserData));
            }

            System.out.println("Reading completed"); 
            return true;

        } catch (FileNotFoundException e) {
            System.out.println("Json File not found");
            e.printStackTrace();
            return false;

        } catch (IOException e) {
            System.out.println("Reading Error");
            e.printStackTrace();
            return false;

        } catch (ParseException e) {
            e.printStackTrace();
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
            
        }
    }
    
    
    /** 
     * Salva i dati degli utenti
     * @param path_ percorso del file dove salvare i dati
     * @return Restituisce TRUE se l'operazione di caricamento va a buon fine altrimenti FALSE.
     * @throws IOException Errore nella scrittura del file
     */
    @SuppressWarnings("unchecked")
    public boolean SaveAccounts(String path_) throws IOException
    {
        jsonFileReader = new JsonParser(System.getProperty("user.dir") +"\\data\\UtentiRegistrati.json");
        JSONArray data = new JSONArray();

        for(int i = 0; i < Data.size(); i++) {
            data.add(Data.get(i).getAccountDataStructure());
        }

        jsonFileReader.WriteJsonFile(data);
        System.out.println("Data Saved");
        
        //System.out.println("Opening " + Directory + UsersDataFilePath2);
        return true;
    }
}
