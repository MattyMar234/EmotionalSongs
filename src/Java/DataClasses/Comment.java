package Java.DataClasses;


import Java.Account.Account;

/**
 * Questa classe rappresenta le infromazioni di un commento scritto da un utente.
 */
public class Comment 
{
   
    // ========================= costanti di classe ========================= //
    /**
    * Numero di caratteri presenti in un commento.
    */
    private static final int CommentLenght = 256;

    // ========================= variabili ========================= //

    /**
    * Testo del commento
    */
    protected String Comment;

    /**
    * Riferimento all'Utente che ha scritto il commento.
    */
    protected Account userCommnet;
    public Comment classReference;


    // ========================= costruttori ========================= //
    //1° costruttore
    /**
    * Costruttore per un commento generico
    */
    public Comment() {
        this.Comment = new String();
        setRef();
    }

    //2° costruttore
    /**
    * Costruttore per un commento predefinito
    */
    public Comment(String comment, Account user) {
        this.Comment = comment;
        this.userCommnet = user;
        setRef();
    }

    

    // ========================= funzioni ========================= //
    @Override
    public String toString() {
        String s = new String();
        s = "Autor: " + userCommnet.getID() + "\nComment: \n" + Comment;
        return s;
    }

    void setRef() {
        classReference = this;
    }


    public Comment getClassReference() {
        return this;
    }

    //ritorna il commento
    /**
    * Questa funzione restituisce il testo del commento
    * @return String
    */
    public String getComment() {
        return this.Comment;
    }

    /**
    * Questa funzione imposta i riferimenti del proprietario del commento
    * @param user L'account proprietario del commento
    */
    public void setAutor(Account user) {
        this.userCommnet = user;
    }

    /**
    * Questa funzione restituisce il riferimento del proprietario del commento
    * @return Account
    */
    public Account getAutor() {
        return this.userCommnet;
    }

    //salva il commento se è <= di 256
    /**
    * Questa funzione salva il testo del commento appena creato e verifica se la lunghezza di tale commento rispecchia i requisiti.
    * @param comment Il testo del commento
    */
    public boolean setComment(String comment) 
    {
        //System.out.println("setComment: " + comment);

        if(comment != null && comment.length() <= this.CommentLenght) {
            this.Comment = comment;
            return true;
        }

        return false;
    }

    //salva il commento se è <= di 256. se supera il valore, tronca la stringa
    public boolean forceSetComment(String comment) 
    {
        if(!setComment(comment)) {
            this.Comment += comment.substring(0,256); //0 fino a 256
        } 
        return true;
    }

}


