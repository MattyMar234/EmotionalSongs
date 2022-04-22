package emotionalsongs;

import java.util.Arrays;
import java.util.LinkedHashMap;
import JsonFile.Json;

public class Account {

    protected String name = new String();
    protected String surname = new String();
    protected String email = new String();
    protected String password = new String();

    protected String TaxIDcode; 
    protected String UserID;
    protected int cap;
    protected String comune;


    //costruttore1 
    public Account()
    {

    }

    //costruttore2
    public Account(String[] data)
    {
        name     = data[0];
        surname = data[1];
        email = data[2];
        password = data[3];
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

            if(accountToTest.getEmail() == this.email || accountToTest.getUserID() == this.UserID) {
                return true;
            }
        }
        return false;
    }

    public boolean IsIndentical(Account totest)
    {
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
