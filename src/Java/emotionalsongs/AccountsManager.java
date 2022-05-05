package Java.emotionalsongs;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import Java.Account.Account;
import Java.Account.RegisteredAccount;
import Java.Json.JsonParser;

public class AccountsManager {

    public ArrayList<RegisteredAccount> Users = new ArrayList<>();  
    private String FilePath;
    private JsonParser jsonFileReader;

    public AccountsManager() {

    }

    public AccountsManager(String FilePath) {
        this.FilePath = FilePath;
    }


    // ============================ Opzioni di ricerca ============================//

    public RegisteredAccount SerachByEmail(String email) {
        
        for (RegisteredAccount account : Users) {
            if(account.getEmail().equals(email)) {
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
        try {  
            jsonFileReader = new JsonParser(FilePath);
            JSONObject data [] = jsonFileReader.ReadJsonFile_as_ArrayOfJsonObject();

            for(JSONObject newUserData : data) {
                Users.add(new RegisteredAccount(newUserData));
                System.out.println(Users.get(Users.size() - 1)  + "\n\n");
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
    
    public boolean SaveAccounts(String path2)
    {
        jsonFileReader = new JsonParser(FilePath);
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
