package Java.DataClasses;


import Java.Account.Account;

public class Comment 
{
   
    // ========================= costanti di classe ========================= //
    private static final int CommentLenght = 256;

    // ========================= variabili ========================= //

    protected int[] valutazione = new int[9];
    protected String Comment;
    protected Account userCommnet;
    public Comment classReference;


    // ========================= costruttori ========================= //
    //1° costruttore
    public Comment() {
        this.Comment = new String();
        setRef();
    }

    //2° costruttore
    public Comment(String comment, Account user) {
        this.Comment = comment;
        this.userCommnet = user;
        setRef();
    }

    //3° costruttore
    public Comment(String Comment,String[] emozioni,int[]valutazione){
        this.Comment = Comment;
        this.valutazione=valutazione;
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
    public String getComment() {
        return this.Comment;
    }

    public void setAutor(Account user) {
        this.userCommnet = user;
    }

    public Account getAutor() {
        return this.userCommnet;
    }

    //salva il commento se è <= di 256
    public boolean setComment(String comment) 
    {
        System.out.println("setComment: " + comment);

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


