package emotionalsongs;

import java.util.Arrays;
import java.util.LinkedHashMap;
import JsonFile.Json;

public class Account {

    protected String name;
    protected String surname;
    protected String email;
    protected String password;

    protected String TaxIDcode; 
    protected String UserID;

    //costruttore1 
    public Account()
    {

    }

    //costruttore2
    public Account(String[] data)
    {

    }

    //costruttore3
    public Account(LinkedHashMap<String, Object> Account)
    {
        this.name       = (String) Json.GetElement(Account, Arrays.asList("name"));
        this.surname    = (String) Json.GetElement(Account, Arrays.asList("surname"));
        this.email      = (String) Json.GetElement(Account, Arrays.asList("email"));
        this.password   = (String) Json.GetElement(Account, Arrays.asList("password"));
    }


    @Override
    public String toString() {
        String informations = "";

        informations += "Name: "     + this.name      + "\n\r";
        informations += "surname: "  + this.surname   + "\n\r";
        informations += "email: "    + this.email     + "\n\r";
        informations += "password: " + this.password  + "\n\r";
        
        return informations;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof Account) {
            Account accountToTest = (Account) obj;

            //verifico solo l'email ????
            if(accountToTest.getEmail() == this.email) {
                return true;
            }
        }
        return false;
    }

    
    public LinkedHashMap<String, Object> getDataStructure()
    {
        LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();

        data.put("name",     this.name);
        data.put("surname",  this.surname);
        data.put("email",    this.email);
        data.put("password", this.password);

        return data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getTaxIDcode() {
        return TaxIDcode;
    }

    public void setTaxIDcode(String taxIDcode) {
        TaxIDcode = taxIDcode;
    }

    
    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    
    
}
