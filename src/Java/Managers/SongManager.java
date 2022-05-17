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
    protected HashMap<String, ArrayList<Song>> Abum = new HashMap<String, ArrayList<Song>>();
    protected HashMap<String, ArrayList<Song>> Autor = new HashMap<String, ArrayList<Song>>();
    
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

                //inserisco la canzone nella varie strutture

                /* ==== id ==== */
                Songs_by_ID.put(song.getSongID(), song);

                /* ==== labum ==== */
                String key = song.getAlbum();
                

                //se non trovo la raccolta
                if(Abum.get(key) == null) {
                    Abum.put(key, new ArrayList<Song>()); //creo la raccolta
                }
                Abum.get(key).add(song);


                /* ==== autor ==== */
                if(Autor.get(key) == null) {
                    Autor.put(key, new ArrayList<Song>()); //creo la raccolta
                }
                Autor.get(key).add(song);

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

    public HashMap<String, ArrayList<Song>> getAlbms() {
        return this.Abum;
    }

    public HashMap<String, ArrayList<Song>> getAutors() {
        return this.Autor;
    }
}
