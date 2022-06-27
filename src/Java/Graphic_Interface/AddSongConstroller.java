package Java.Graphic_Interface;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import Java.Managers.SongManager;
import Java.PlayList_Songs.AddSongWindow;
import Java.PlayList_Songs.Song;
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
    protected HashMap<String, ArrayList<String>> dataFilter;
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
            this.elements = (Elements == 0 ? "-" : Integer.toString(Elements));
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
        this.dataFilter = window.dataFilter;
        this.mode = window.mode;
        this.window = window;

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        SongManager manager = this.window.main.songManager;
        HashMap<String, ArrayList<Song>> raccolta;
        int index;

        name.setCellValueFactory(new PropertyValueFactory<Data, String>("title"));
        number.setCellValueFactory(new PropertyValueFactory<Data, String>("elements"));


        switch (mode) {
            case 1:
                ArrayList<String> songFilter = new ArrayList<String>();
                songFilter.addAll(dataFilter.get("song"));
                
                for(Song song : manager.getList()) 
                {
                    
                    if(songFilter.size() > 0 && (index = FindKey(song.getTitle(), songFilter)) >= 0) 
                    {
                        songFilter.remove(index);
                        continue;                       
                    }
                   
                    ArrayList<Song> s = new ArrayList<Song>();
                    s.add(song);     
                    list.add(new Data(song.getTitle(), 0 , s));
                    
                }
                break;

            case 2:
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

            case 3:
                raccolta = manager.getAutors();
                ArrayList<String> AutorsFilter = new ArrayList<String>();
                AutorsFilter.addAll(dataFilter.get("autor"));

                for(String key : raccolta.keySet()) {
                    
                    if(AutorsFilter.size() > 0 && (index = FindKey(key, AutorsFilter)) >= 0) {
                        AutorsFilter.remove(index);
                        continue; //saltando l'iterazione, non aggiungo l'elemento
                    }

                    list.add(new Data(key, raccolta.get(key).size(), raccolta.get(key)));
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

        if(doubleClick() && selected1 == selected2) {

            switch(mode) {
                case 1: this.dataFilter.get("song").add(((Data) selected1).title); break;
                case 2: this.dataFilter.get("album").add(((Data) selected1).title);break;
                case 3: this.dataFilter.get("autor").add(((Data) selected1).title);break;
            }

            System.out.println("elements: " + this.dataFilter.get("song").size());
            System.out.println("elements: " + this.dataFilter.get("autor").size());
            System.out.println("elements: " + this.dataFilter.get("album").size());



            this.window.returnSelectedData(((Data) selected1).songs);
            this.window.close();
        }
        
    }
    
}
