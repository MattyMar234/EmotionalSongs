package emotionalsongs;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import JsonFile.Json;

public class AccountsManager {

    public ArrayList<RegisteredAccount> Users = new ArrayList<>();  
    private String FilePath;
    private Json jsonFileReader;

    public AccountsManager() {

    }

    public AccountsManager(String FilePath) {
        this.FilePath = FilePath;
    }


    // ============================ Opzioni di ricerca ============================//

    public RegisteredAccount SerachByEmail(String email) {
        
        for (RegisteredAccount account : Users) {
            if(account.email.equals(email)) {
                return account;
            }
        }
        return null;
    }

    public int checkAccaunt(Account temp) {

        //return true;
        for(Account existingAcaunt : Users) {
            if(existingAcaunt.getEmail().equals(temp.getEmail())) {
                return 1;
            }  
        }

        //test cap

        //tutto aposto
        return 0;
    }

    @SuppressWarnings("unchecked")
    public boolean LoadAccounts()
    {
        jsonFileReader = new Json(FilePath);
        LinkedHashMap<String, Object> Accounts = jsonFileReader.ReadJsonFile();

        for(String UserKey : Json.getKeys(Accounts)) {
            Users.add(new RegisteredAccount((LinkedHashMap<String, Object>) Json.GetElement(Accounts, Arrays.asList(UserKey))));
        }
        return true;
    }
    
    public boolean SaveAccounts(String path2)
    {
        jsonFileReader = new Json(FilePath);
        LinkedHashMap<String, Object> UsersData = new LinkedHashMap<String, Object>();

        for(int i = 0; i < Users.size(); i++) {
            String name = "user" + (i + 1);
            UsersData.put(name, Users.get(i).getDataStructure());
        }
        
        //System.out.println("Opening " + Directory + UsersDataFilePath2);

        try {
            FileWriter Writer = new FileWriter(path2);
            
            Writer.write(jsonFileReader.BuildJsonFile(UsersData, 0));
            System.out.println("Data saved!");
            Writer.close();

        } catch (Exception e) {
            System.out.println("Writing File error: " + e);
        }

        return true;
    }
}
