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

public class AccountsManager extends Manager <RegisteredAccount> 
{

    public AccountsManager(String FilePath, EmotionalSongs main) {
        super(FilePath, main);
    }


    // ============================ Opzioni di ricerca ============================//

    public RegisteredAccount SearchByEmail(String email) {
        
        for (RegisteredAccount account : Data) {
            if(account.getEmail().equals(email)) {
                return account;
            }
        }
        return null;
    }

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

        

        //verifica cap
       /* if(!common.testCap(temp.getCap())) {
            return 6;
        }*/

        //tutto aposto
        return 0;
    }


    public boolean LoadAccounts()
    {
        System.out.println("reading file " + this.FilePath);

        try {  
            for(JSONObject newUserData : readJsonData()) 
            {
                Data.add(new RegisteredAccount(newUserData));
                //System.out.println(Data.get(Data.size() - 1)  + "\n\n");
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

        /*LinkedHashMap<String, Object> Accounts = jsonFileReader.ReadJsonFile();

        for(String UserKey : JsonParser.getKeys(Accounts)) {
            Users.add(new RegisteredAccount((LinkedHashMap<String, Object>) JsonParser.GetElement(Accounts, Arrays.asList(UserKey))));
        }*/
        return true;
    }
    
    @SuppressWarnings("unchecked")
    public boolean SaveAccounts(String path2) throws IOException
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
