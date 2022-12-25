package Java.Graphic_Interface;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import Java.Managers.SongManager;
import Java.PlayListSongs.PlayList;
import Java.PlayListSongs.Song;
import Java.emotionalsongs.EmotionalSongs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class AddSongConstroller extends Controller implements Initializable 
{
    protected AddSongWindow window;
    protected int mode;

    @FXML private TableView<Data> Table;
    @FXML private TableColumn<Data, String> number;
    @FXML private TableColumn<Data, String> name;

    private ObservableList<Data> list = FXCollections.observableArrayList();


    public class Data 
    {
        public String title;
        public String elements = new String("-");
        public ArrayList<Song> songs = new ArrayList<Song>();

        public Data(String title) {
            this.title = title;
        }

        public Data(String title, int Elements, ArrayList<Song> songs) {
            this.elements = (Elements == 0 ? "1" : Integer.toString(Elements));
            this.songs = songs;
            this.title = title;

        }

        public String getTitle() {
            return this.title;
        }

        public String getElements() {
            return this.elements;
        }

    }


    public AddSongConstroller(AddSongWindow window) {
        super();
        this.mode = window.mode;
        this.window = window;

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        SongManager manager = this.window.main.songManager;
        int index;

        name.setCellValueFactory(new PropertyValueFactory<Data, String>("title"));
        number.setCellValueFactory(new PropertyValueFactory<Data, String>("elements"));

        name.setText(EmotionalSongs.language == 0 ? "Nome Canzone" : "Song Name");
        number.setText(EmotionalSongs.language == 0 ? "Numero Elementi" : "Elements");

        switch (mode) {
            case 1:
                
                //ottengo l'elenco e rimuovo quello che ho gia'
                for(Song song : manager.getList()) 
                {
                
                    //confronto la playlist con l'archivio.
                    //ottengo l'indice della canzone nella playlist
                    //se ho -1 vuoldire che la canzone non e' presente nella playlist
                   if(window.data.getSongs().indexOf(song) == -1) {
                        ArrayList<Song> s = new ArrayList<Song>();
                        s.add(song);     
                        list.add(new Data(song.getTitle(), 0 , s));
                   } 
                }
                break;

            case 2:
            /* 
                raccolta = manager.getAlbms();
                ArrayList<String> AlbumFilter = new ArrayList<String>();
                AlbumFilter.addAll(dataFilter.get("album"));

                for(String key : raccolta.keySet()) {

                    if(AlbumFilter.size() > 0 && (index = FindKey(key, AlbumFilter)) >= 0) {
                        AlbumFilter.remove(index);
                        continue; //saltando l'iterazione, non aggiungo l'elemento
                    }

                    list.add(new Data(key, raccolta.get(key).size(), raccolta.get(key)));
                }
                break;
                */

            case 3:
                HashMap<String, ArrayList<Song>> raccolta = manager.getAutors();
               

                //System.out.println("playlist: " + window.data);
                ArrayList<Song> playlistElements = window.data.copy().getSongs();
                

                for(String key : raccolta.keySet()) 
                {
                    if(playlistElements.size() > 0) {

                        //creo una copia della raccolta
                        ArrayList<Song> elencoBrani = new ArrayList<Song>(raccolta.get(key));
                        ArrayList<Song> elencoBrani_clone = new ArrayList<Song>(raccolta.get(key));

                        for(Song song : elencoBrani_clone) {

                            //se ottengo un numero >= 0 vuoldire che è presente l'elemento
                            if(playlistElements.indexOf(song) >= 0) {
                                elencoBrani.remove(song);
                                playlistElements.remove(song);
                            }
                        }
                        
                        if(elencoBrani.size() > 0) list.add(new Data(key, elencoBrani.size(), elencoBrani));
                    }
                    else {
                        list.add(new Data(key, raccolta.get(key).size(), raccolta.get(key)));
                    } 
                }
                break;

            
        }

        Table.setItems(list);
    }

    private int FindKey(String str,  ArrayList<String> list) {
        for(String key : list) {
            if(key.equals(str)) return list.indexOf(key);
        }
        return -1;
    }

    @FXML
    public void elementSelected(MouseEvent event) throws Exception 
    {        
        if(state == 0) {
            selected1 = Table.getSelectionModel().getSelectedItem();
        }
        else if (state == 1) {
            selected2 = Table.getSelectionModel().getSelectedItem();
        }

        if(doubleClick() && selected1 == selected2) 
        {
            for(Song s : ((Data) selected1).songs)
                this.window.data.addSong(s);

            this.window.close();
        }
        
    }
    
}
