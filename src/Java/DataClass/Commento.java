package Java.DataClass;

import java.util.Scanner;

public class Commento {
    
String comm;
int[] valutazione=new int[9];
Scanner in=new Scanner(System.in);

public Commento(String comm,String[] emozioni,int[]valutazione){
        this.comm= comm;
        this.valutazione=valutazione;
    }

    void ins_value(int value){
        value=in.nextInt();
        in.nextLine();
    }

    void controllo_valutation(int value){
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

    Boolean check_comm(String comm, boolean b){      
        if(comm.length()<256){
                    b=false;
                }
                else{
                    b=true;
                }
                return b;
    }

    void add_comm(String comm){
            comm=comm+in.nextLine();
    }

    void comm_finale(String comm){
        boolean b=true;
        while(b==true){
            add_comm(comm);
            check_comm(comm,b);
            if(b==true){
                comm_finale(comm);
            }
                
            }
        }

        void remove_comm(String comm){
            String vuota="";
            comm=vuota;
        }
        
    }


