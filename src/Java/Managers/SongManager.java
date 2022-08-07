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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import Java.Json.JsonParser;
import Java.PlayList_Songs.Song;
import Java.emotionalsongs.EmotionalSongs;

public class SongManager extends Manager <Song> {

    protected HashMap<String, Song> Songs_by_ID = new HashMap<String, Song>();
    protected HashMap<String, ArrayList<Song>> Abum = new HashMap<String, ArrayList<Song>>();
    protected HashMap<String, ArrayList<Song>> Autor = new HashMap<String, ArrayList<Song>>();
    
    private int loadingFile_type = 0;
   
    public SongManager(String path, EmotionalSongs main) {
        super(path, main);
    }


    public boolean LoadSongs () throws ParseException 
    {
        if(loadingFile_type == 2)this.FilePath = EmotionalSongs.Directory + "\\data\\newSong.txt";
        System.out.println("reading file " + this.FilePath);
        String FILE_DATA = "";

        try {
            FileReader reader = new FileReader(new File(this.FilePath));
            BufferedReader bufferedreader = new BufferedReader(reader);

            //JSON
            if(loadingFile_type == 0) {
                for(JSONObject songData : readJsonData()) 
                {
                    Song song = new Song(songData);
                    setElements(song);   
                }
            }
            //CSV
            else if(loadingFile_type == 1) 
            {
                if((bufferedreader.readLine()) == null) return false;

                while ((FILE_DATA = bufferedreader.readLine()) != null) {
                    String [] element = FILE_DATA.replace("\"","").replace("b\'","").replace("\'","").split(",");
                    Song song = new Song(element);
                    
                    setElements(song);
    
    
                    /* ==== autor ==== */
                    //if(Autor.get(key) == null) {
                    //    Autor.put(key, new ArrayList<Song>()); //creo la raccolta
                    //}
                    //Autor.get(key).add(song);
                }
            }
            //TXT
            else if(loadingFile_type == 2) 
            {
                int counter = 1;
                HashMap<String, Integer> map = new HashMap<String, Integer>();

                while ((FILE_DATA = bufferedreader.readLine()) != null) 
                {
                    FILE_DATA = FILE_DATA.replace("\"", ""); 
                    String [] element = FILE_DATA.split("<SEP>");

                    Song song = new Song();
                    String title = element[3];

                    //se ottengo null è perchè non lho ancora incontrato
                    if(map.get(title) == null) {
                        map.put(title, 0);  

                        song.setYear  (element[0]);
                        song.setSongID(element[1]);
                        song.setAlbum (element[2]);
                        song.setTitle (element[3]);
                        song.setNumber(Integer.toString(counter++));
                        
                        setElements(song); 
                    }
                }
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

    private void setElements(Song song) 
    {
        this.Data.add(song);

        /* ==== id ==== */
        this.Songs_by_ID.put(song.getSongID(), song);
        //System.out.println("keys: " + Songs_by_ID.size());
        


        //System.out.println(Songs_by_ID.get(song.getSongID()));

        String key = song.getAutor();

        /* ==== autor ==== */
        if(Autor.get(key) == null) {
            Autor.put(key, new ArrayList<Song>()); //creo la raccolta
        }
        Autor.get(key).add(song);
        
        
        /* ==== labum ==== */
        /*String key = song.getAlbum();
        
        //se non trovo la raccolta
        if(Abum.get(key) == null) {
            Abum.put(key, new ArrayList<Song>()); //creo la raccolta
        }
        Abum.get(key).add(song);*/
    }

    @SuppressWarnings("unchecked")
    public boolean saveData(String path) throws IOException
    {
        //EmotionalSongs.SongFile
        jsonFileReader = new JsonParser(path);
        JSONArray s = new JSONArray();

        for(int i = 0; i < Data.size(); i++) {
            s.add(Data.get(i).toJSON());
        }

        jsonFileReader.WriteJsonFile(s);
        System.out.println("Data Saved");
        
        //System.out.println("Opening " + Directory + UsersDataFilePath2);
        return true;
    }

    public Song getSong_by_ID(String ID) {
        //System.out.println("keys: " + Songs_by_ID.size());
        return this.Songs_by_ID.get(ID);
    }

    public HashMap<String, ArrayList<Song>> getAlbms() {
        return this.Abum;
    }

    public HashMap<String, ArrayList<Song>> getAutors() {
        return this.Autor;
    }
}
