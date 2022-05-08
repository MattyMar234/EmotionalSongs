package Java.Managers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONObject;

import Java.PlayList_Songs.Song;
import Java.emotionalsongs.EmotionalSongs;

public class SongManager extends Manager <Song> {

    protected HashMap<String, Song> Songs_by_ID = new HashMap<String, Song>();
    
    public SongManager(String path, EmotionalSongs main) {
        super(path, main);
    }

    public boolean LoadSongs () 
    {

        System.out.println("reading file " + this.FilePath);
        String FILE_DATA = "";

        try {
            FileReader reader = new FileReader(new File(this.FilePath));
            BufferedReader bufferedreader = new BufferedReader(reader);

            //salto la prima line e verifico 
            if((bufferedreader.readLine()) == null) return false;

            while ((FILE_DATA = bufferedreader.readLine()) != null) {
                String [] element = FILE_DATA.replace("\"","").replace("b\'","").replace("\'","").split(",");
                Song song = new Song(element);
    
                this.Data.add(song);
                Songs_by_ID.put(song.getSongID(), song);
            }

            reader.close();


        } 
        catch (FileNotFoundException e) {
            System.out.println("file " + this.FilePath + " not found");
            return false;
        }
        catch (IOException e) {
            System.out.println("reading " + this.FilePath + " failed");
            return false;
        }
        finally {
            System.out.println("reading completed");   
        }
        return true;
    }

    public Song getSong_by_ID(String ID) {
        return this.Songs_by_ID.get(ID);
    }
}
