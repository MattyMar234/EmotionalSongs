package Java.Graphic_Interface;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    
    @FXML private TableView<CustomSong> SongsTable;
    @FXML private TableColumn<CustomSong, String> Album;
    @FXML private TableColumn<CustomSong, String> Autor;
    @FXML private TableColumn<CustomSong, String> Title;
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


        public CustomSong(Song song, MainPageController_playList classReference) {
            this.MainclassReference = classReference;
            this.song = song;
            this.nome = this.song.getTitle();
            this.classReference = this;
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
            list.add(new CustomSong(song, classeReferences));
        }

        Title.setCellValueFactory(new PropertyValueFactory<CustomSong, String>("nome"));
    //    Autor.setCellValueFactory(new PropertyValueFactory<CustomSong, String>("autor"));
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

                        Image image1;
                        Image image2;

                        try {
                            image1 = new Image(new FileInputStream("data\\image\\trash.png"));
                            image2 = new Image(new FileInputStream("data\\image\\edit.png"));  
                            
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            return;
                        }
                        
                        
                        ImageView deleteIcon = new ImageView(image1); 
                        ImageView editIcon = new ImageView(image2); 

                        deleteIcon.setFitHeight(24); 
                        deleteIcon.setFitWidth(24);
                        editIcon.setFitHeight(24); 
                        editIcon.setFitWidth(24);

                        Button deleteButton = new Button();
                        Button editButton = new Button();


                        deleteButton.setGraphic(deleteIcon);
                        editButton.setGraphic(editIcon);

                        deleteButton.setStyle(
                                " -fx-cursor: hand ;"
                                + "-fx-background-color:#ff00008e;"
                                + "-fx-border-width: 1;"
                                + "-fx-border-color: #E74C3C;"
                                + "-fx-border-radius: 10px;"
                                + "-fx-padding: 8 8 8 8;"
                                + "-fx-border-insets: 10px;"
                                + "-fx-background-insets: 10px;"
                                
                        );
                        editButton.setStyle(
                                " -fx-cursor: hand ;"
                                + "-fx-background-color:#11db0aa4;"
                                + "-fx-border-width: 1;"
                                + "-fx-border-color: #2ECC71;"
                                + "-fx-border-radius: 10px;"
                                + "-fx-padding: 8 8 8 8;"
                                + "-fx-border-insets: 10px;"
                                + "-fx-background-insets: 10px;"

                        );


                        deleteButton.setOnMouseClicked((MouseEvent event) -> {
                        

                        });

                        
                        editButton.setOnMouseClicked((MouseEvent event) -> {
                            

                        });
                        
                        HBox managebtn = new HBox(deleteButton, editButton);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        setGraphic(managebtn);
                        setText(null);
                    }
                }

            };
            return cell;
        };

        Actions.setCellValueFactory(new PropertyValueFactory<CustomSong, CustomSong>("classReference"));
        Actions.setCellFactory(cellFoctory);
        SongsTable.setItems(list);
    
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
