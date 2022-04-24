package PlayListSongs;
import java.util.ArrayList;
public class PlayList {
    
    String nome;
    ArrayList<Song>canzoni=new ArrayList<Song>();

        PlayList(String nome, ArrayList<Song> canzoni){
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
    
    
}
