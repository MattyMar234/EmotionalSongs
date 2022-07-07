package Java.DataClasses;

import java.util.Scanner;

import Java.Account.Account;

public class Comment 
{
   
    // ========================= costanti di classe ========================= //
    private static final int CommentLenght = 256;


    // ========================= variabili ========================= //

    protected String Comment;
    protected Account usersCommnet;
    protected int[] valutazione = new int[9];
    protected Scanner in = new Scanner(System.in);
    public Comment classReference;


    // ========================= costruttori ========================= //
    //1° costruttore
    public Comment() {
        this.Comment = new String();
        setRef();
    }

    //2° costruttore
    public Comment(String comment) {
        this.Comment = comment;
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
        s = "Comment: \n" + Comment;
        return s;
    }

    void setRef() {
        classReference = this;
    }

    public Comment getClassReference() {
        return this;
    }

    public void ins_value(int value){
        value=in.nextInt();
        in.nextLine();
    }

    public void controllo_valutation(int value){
            boolean b=true;
        while(b==true){
            ins_value(value);
            if(value<=5 && value>0){
                b=false;
            }
            else{
                controllo_valutation(value);
            }
        }
    }

    public Boolean check_comm(String comm, boolean b){      
        if(comm.length()<256){
                    b=false;
                }
                else{
                    b=true;
                }
                return b;
    }

    public void add_comm(String comm){
            comm=comm+in.nextLine();
    }

    public void comm_finale(String comm){
        boolean b=true;
        while(b==true){
            add_comm(comm);
            check_comm(comm,b);
            if(b==true){
                comm_finale(comm);
            }
                
            }
        }

    //?? necessaria ??
    public void clearComment() {
       this.Comment = "";
    }

    //ritorna il commento
    public String getComment() {
        return this.Comment;
    }

    //salva il commento se è <= di 256
    public boolean setComment(String comment) 
    {
        if(comment.length() <= this.CommentLenght) {
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


