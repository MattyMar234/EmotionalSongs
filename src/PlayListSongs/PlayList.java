package PlayListSongs;
import java.util.ArrayList;


public class PlayList 
{
    ArrayList<Song> canzoni = new ArrayList<Song>();
    String nome;

    PlayList(String nome, ArrayList<Song> canzoni) {
        this.nome = nome;
        this.canzoni = canzoni;   //clone??
    }

    public void cambianome(String nuovo_nome) {
        this.nome = nuovo_nome;
    }

    public void addCanzone(Song a){ 
        canzoni.add(a);
    }

    public void removeCanzone(Song a) {
        canzoni.remove(a);
    }

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
