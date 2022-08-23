package Java.Graphic_Interface;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Java.PlayList_Songs.*;
import Java.emotionalsongs.EmotionalSongs;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class EditPlaylistController extends Controller implements Initializable 
{

    @FXML private TableView<CustomSong> PLaylistSongs;
    @FXML private TableColumn<CustomSong, String> Album;
    @FXML private TableColumn<CustomSong, String> Autor;
    @FXML private TableColumn<CustomSong, String> Title;
    @FXML private TableColumn<CustomSong, String> SongDate;
    @FXML private TableColumn<CustomSong, CustomSong> Actions;   
    
    @FXML private Button addSong;
    @FXML private Button AddSongAutor;
    @FXML private Button Back;
    @FXML private Button salva;

    @FXML private Label titoloEditPlaylist;

    
    public ObservableList<CustomSong> list = FXCollections.observableArrayList();
    private MainPageController classeReferences;
    private AddSongWindow addSongWindow;
    private PlayList playlistCopy;
    private PlayList playlist;
    

    public class CustomSong 
    {
        public Song song;
        public MainPageController MainclassReference;
        public EditPlaylistController controller;
        public CustomSong classReference;
        public String nome;
        public String data;
        public String autor;
        public PlayList playlist;


        public CustomSong(Song song, PlayList playlist, MainPageController classReference, EditPlaylistController controller) {
            this.MainclassReference = classReference;
            this.song = song;
            this.playlist = playlist;
            this.nome = this.song.getTitle();
            this.classReference = this;
            this.data = song.getYear();
            this.autor = song.getAutor(); 
            this.controller = controller;  
        }

        public Song getSong() {
            return this.song;
        }
        public MainPageController getMainclassReference() {
            return this.MainclassReference;
        }
        public CustomSong getClassReference() {
            return this;
        }
        public String getNome() {
            return nome;
        }
        public String getData() {
            return data;
        }
        public String getAutor() {
            return autor;
        }
    }
 

    public EditPlaylistController(PlayList list, MainPageController classeReferences) {
        super();
        this.playlist = list;
        this.classeReferences = classeReferences;
    }

    public void updateTable() {
        list.clear();

        for(Song song : playlistCopy.getSongs()) {
            list.add(new CustomSong(song, this.playlistCopy, classeReferences, this));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        playlistCopy = playlist.copy();

        titoloEditPlaylist.setText(EmotionalSongs.language == 0 ? "MODIFICA PLAYLIST" : "EDIT PLAYLIST");
        salva.setText(EmotionalSongs.language == 0 ? "Salva" : "Save");

        Back.setText(EmotionalSongs.language == 0 ? "Indietro" : "Turn Back");
        Title.setText(EmotionalSongs.language == 0 ? "Titolo" : "Title");
        Autor.setText(EmotionalSongs.language == 0 ? "Autore" : "Autor");
        SongDate.setText(EmotionalSongs.language == 0 ? "Data" : "Date");
        Actions.setText(EmotionalSongs.language == 0 ? "Azioni" : "Actions");

        addSong.setText(EmotionalSongs.language == 0 ? "Aggiungi Canzone" : "Add Song");
        AddSongAutor.setText(EmotionalSongs.language == 0 ? "Aggiungi Canzoni Autore" : "Add Author's songs");



        for(Song song : playlistCopy.getSongs()) {
            list.add(new CustomSong(song, this.playlistCopy, classeReferences,this));
        }

        Title.setCellValueFactory(new PropertyValueFactory<CustomSong, String>("nome"));
        SongDate.setCellValueFactory(new PropertyValueFactory<CustomSong, String>("data"));
        Autor.setCellValueFactory(new PropertyValueFactory<CustomSong, String>("autor"));
        //Album.setCellValueFactory(new PropertyValueFactory<CustomSong, String>("album"));

        Callback<TableColumn<CustomSong, CustomSong>, TableCell<CustomSong, CustomSong>> cellFoctory = (TableColumn<CustomSong, CustomSong> param) -> {
            final TableCell<CustomSong, CustomSong> cell = new TableCell<CustomSong, CustomSong>() {
                
                @Override
                public void updateItem(CustomSong item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        //crash ??
                        //FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        //FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        //Image [] imgs = new Image[4];
                        ImageView [] icons = new ImageView[3];
                        Button[] buttons = new Button[icons.length];
                        String[] IDs = {
                            "delete", "comment","emotions"
                        };

                        String[] path = {
                            "data\\image\\trash.png",
                            //"data\\image\\edit.png",
                            "data\\icon\\commentIcon3.png",
                            "data\\icon\\EmotionIcon2.png"

                        };
                        
                        //Image image1;
                        //Image image2;

                        try {
                            for( int i = 0; i < icons.length; i++) {
                                icons[i] = new ImageView(new Image(new FileInputStream (path[i])));

                                icons[i].setFitHeight(24);
                                icons[i].setFitWidth(24);

                                buttons[i] = new Button();
                                buttons[i].setGraphic(icons[i]);

                                buttons[i].setId(IDs[i]);
 
                            }

                            HBox managebtn = new HBox(buttons);

                            for( int i = 0; i < icons.length; i++) {
                                HBox.setMargin(buttons[i], new Insets(2, 2, 0, 3));
                            }
                            
                                
                            managebtn.setStyle("-fx-alignment:center");
                            
                            setGraphic(managebtn);
                            setText(null);


                            buttons[0].setOnMouseClicked((MouseEvent event) -> {
                                item.controller.list.remove(item);
                                item.controller.playlistCopy.removeSong(item.getSong());
                            
                            });

                            


                            buttons[1].setOnMouseClicked((MouseEvent event) -> {
                                try {
                                    item.MainclassReference.SetCommentsPage(item.getSong(),item.playlist);
                                } catch (IOException e) {
                                    
                                    e.printStackTrace();
                                }

                            });

                            
                            buttons[2].setOnMouseClicked((MouseEvent event) -> {
                                try {
                                    item.MainclassReference.SetAddEmotionPage(item.playlist, item.song);
                                } catch (IOException e) {
                                    
                                    e.printStackTrace();
                                }
                                
                            });

                            /*


                            buttons[3].setOnMouseClicked((MouseEvent event) -> {
                            

                            });*/


                        } catch (Exception e) {
                            System.out.println(e);
                            e.printStackTrace();
                            return;
                        } 
                    }
                }
            };
            return cell;
        };

        Actions.setCellValueFactory(new PropertyValueFactory<CustomSong, CustomSong>("classReference"));
        Actions.setCellFactory(cellFoctory);
        PLaylistSongs.setItems(list);

    }

    
    
    @FXML
    void elementSelected(MouseEvent event) {
        
    }
    
    @FXML
    void addSong(ActionEvent event) throws Exception {
        addSongWindow = new AddSongWindow(this, EmotionalSongs.classReference, 1, playlistCopy);
    }

    @FXML
    void AddAutor (ActionEvent event) throws Exception {
        addSongWindow = new AddSongWindow(this, EmotionalSongs.classReference, 3, playlistCopy);
    }


    @FXML
    void turnBack(ActionEvent event) throws IOException {
        classeReferences.SetPlayListPage();
    }

    @FXML
    void save(ActionEvent event) 
    {
        playlist.clearPlayList();

        for(Song song : playlistCopy.getSongs()) {
            playlist.addSong(song);
        }
    }

    
}
