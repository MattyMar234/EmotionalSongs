package emotionalsongs;

import java.util.ArrayList;


public class GestioneAccount {

    public static ArrayList<Account> Users = new ArrayList<>();


    public void print() 
    {
        System.out.println("Account presenti: " + Users.size() + "\n");

        for(Account account : Users) {
            System.out.println(account);
        }

        System.out.println();
    }


    public static void LoadAccount(String path) 
    {
        
    }


    public static boolean Exits(Account account)
    {
        if(account instanceof UnregisteredAccount || account instanceof Account) {
            return false;
        }

        RegisteredAccount AccountToTest = (RegisteredAccount)account;

        for(int i = 0; i < Users.size(); i++) {
            if(Users.get(i).equals(AccountToTest)) {
                return true;
            }
        }
        return false;
    }

    public static RegisteredAccount searchAccount_by_Email(String Email) 
    {
        for(Account account : Users) {
            if(account.getEmail().equals(Email)) {
                return (RegisteredAccount) account;
            }
        }
        return null;
    }

    public static RegisteredAccount searchAccount_by_userID(String ID) 
    {
        for(Account account : Users) {
            if(((RegisteredAccount)account).getUserID().equals(ID)) {
                return (RegisteredAccount) account;
            }
        }
        return null;
    }

    private static void OrdinateList() {
        //ordinare la liusa di utenti secondo un criterio alfanumerico
    }
    
}
