package Java.Graphic_Interface;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Java.PlayList_Songs.*;
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

public class EditPlaylistController extends Controller implements Initializable {

    
    @FXML private TableView<CustomSong> PLaylistSongs;
    @FXML private TableColumn<CustomSong, String> Album;
    @FXML private TableColumn<CustomSong, String> Autor;
    @FXML private TableColumn<CustomSong, String> Title;
    @FXML private TableColumn<CustomSong, String> SongDate;
    @FXML private TableColumn<CustomSong, CustomSong> Actions;   
    
    @FXML private Button AnnullaPlaylistButton;
    @FXML private Button NewPlaylistButton;

    private MainPageController_playList classeReferences;

    private ObservableList<CustomSong> list = FXCollections.observableArrayList();
    protected PlayList playlist;
    

    public class CustomSong 
    {
        public Song song;
        public MainPageController_playList MainclassReference;
        public CustomSong classReference;
        public String nome;
        public String data;
        public String autor;
        public PlayList playlist;


        public CustomSong(Song song, PlayList playlist, MainPageController_playList classReference) {
            this.MainclassReference = classReference;
            this.song = song;
            this.playlist = playlist;
            this.nome = this.song.getTitle();
            this.classReference = this;
            this.data = song.getYear();
            this.autor = song.getAlbum(); //da cambiare !!!!!
        }

        public Song getSong() {
            return this.song;
        }

        public MainPageController_playList getMainclassReference() {
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
 

    public EditPlaylistController(PlayList list, MainPageController_playList classeReferences) {
        super();
        this.playlist = list;
        this.classeReferences = classeReferences;
        //System.out.println(list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for(Song song : playlist.getSongs()) {
            list.add(new CustomSong(song, this.playlist, classeReferences));
        }

        Title.setCellValueFactory(new PropertyValueFactory<CustomSong, String>("nome"));
        SongDate.setCellValueFactory(new PropertyValueFactory<CustomSong, String>("data"));
        Autor.setCellValueFactory(new PropertyValueFactory<CustomSong, String>("autor"));
    //    Album.setCellValueFactory(new PropertyValueFactory<CustomSong, String>("album"));

        Callback<TableColumn<CustomSong, CustomSong>, TableCell<CustomSong, CustomSong>> cellFoctory = (TableColumn<CustomSong, CustomSong> param) -> {
            final TableCell<CustomSong, CustomSong> cell = new TableCell<CustomSong, CustomSong>() {
                
                @Override
                public void updateItem(CustomSong item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
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
                            "data\\icon\\commentIcon.png",
                            "data\\icon\\EmotionIcon.png"

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

                            


                            buttons[1].setOnMouseClicked((MouseEvent event) -> {
                                try {
                                    item.MainclassReference.mainController.SetCommentsPage(item.getSong(),item.playlist);
                                } catch (IOException e) {
                                    
                                    e.printStackTrace();
                                }

                            });

                            /*
                            buttons[2].setOnMouseClicked((MouseEvent event) -> {
                            

                            });


                            buttons[3].setOnMouseClicked((MouseEvent event) -> {
                            

                            });*/


                        } catch (Exception e) {
                            System.out.println(e);
                            e.printStackTrace();
                            return;
                        }
                        
                        
                        //ImageView deleteIcon = new ImageView(image1); 
                        //ImageView editIcon = new ImageView(image2); 

                        
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
    void addSong(ActionEvent event) {

    }

    @FXML
    void AddAlbum(ActionEvent event) {

    }

    @FXML
    void AddAutor (ActionEvent event) {

    }


    @FXML
    void AddNewPlayList(ActionEvent event) {

    }

    @FXML
    void ConfermeNewPlayList(ActionEvent event) {

    }

    @FXML
    void Selezione(ActionEvent event) {

    }

    @FXML
    void elementSelected(MouseEvent event) {

    }

    @FXML
    void turnBack(ActionEvent event) {

    }

    
}
