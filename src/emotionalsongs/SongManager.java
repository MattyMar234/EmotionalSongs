package emotionalsongs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import PlayListSongs.Song;

public class SongManager {
    
    public ArrayList<Song> SongList = new ArrayList<Song>();
    public EmotionalSongs main;


    public SongManager(EmotionalSongs main) {
        this.main = main;
    }

    public boolean LoadSongs (String path) {

        System.out.println("read file " + path);
        String FILE_DATA = "";

        try {
            FileReader reader = new FileReader(new File(path));
            BufferedReader bufferedreader = new BufferedReader(reader);

            //salto la prima line e verifico 
            if((bufferedreader.readLine()) == null) return false;

            while ((FILE_DATA = bufferedreader.readLine()) != null) {
                String [] element = FILE_DATA.replace("\"","").replace("b\'","").replace("\'","").split(",");
                this.SongList.add(new Song(element));
            }
        } 
        catch (FileNotFoundException e) {
            System.out.println("file " + path + " not found");
            return false;
        }
        catch (IOException e) {
            System.out.println("reading " + path + " failed");
            return false;
        }
        return true;
    }
}
