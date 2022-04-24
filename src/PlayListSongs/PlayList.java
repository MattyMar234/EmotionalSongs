package PlayListSongs;
import java.util.ArrayList;
public class PlayList {
    
    String nome;
    ArrayList<Song>canzoni=new ArrayList<Song>();

    public PlayList(String nome, ArrayList<Song> canzoni) {
        this.nome=nome;
        this.canzoni=canzoni;
    }

    public void cambianome(String nuovo_nome){
        this.nome=nuovo_nome;
    }

    public void addcanzone(Song a){
        canzoni.add(a);
    }

    public void removecanzone(Song a){
        canzoni.remove(a);
    }

    public void clear(){}


    @Override
    public String toString() {
        String contenuto;

        contenuto = "playlist " + nome + "\nelementi presenti (" + canzoni.size() + "):\n";  

        int index = 1;
        for(Song a : canzoni){
            contenuto += "canzone " + index++ + ": " + a.getTitle();
        }

        return contenuto;

    }
    
    
}
