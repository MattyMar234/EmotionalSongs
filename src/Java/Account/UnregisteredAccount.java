package Java.Account;

import java.util.LinkedHashMap;

public class UnregisteredAccount extends Account{

    public UnregisteredAccount() {
        this.name     = "none";
        this.surname  = "none";
        this.email    = "none";
        this.password = "";
        this.userID   = "";
    }
    
}
