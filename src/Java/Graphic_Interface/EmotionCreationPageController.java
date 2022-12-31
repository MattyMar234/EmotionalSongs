package Java.Graphic_Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

import Java.Account.RegisteredAccount;
import Java.DataClasses.Emotion;
import Java.PlayListSongs.PlayList;
import Java.PlayListSongs.Song;
import Java.emotionalsongs.EmotionalSongs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class EmotionCreationPageController extends Controller implements Initializable {

    private final static String [][] matrice = {
        {"seleziona", "select"},     
        {"Emozione", "Emotion"},  
        {"Nome", "Name"},
        {"Spiegazione", "Explanation"},
        {"Valore", "Value"}       
    };
   
    
    @FXML private Button Back;
    @FXML private TableView<Container> EmotioTable;
    @FXML private TableColumn<Container, Container> EmotionElement;

    @FXML
    private ImageView IMG1;

    private ObservableList<Container> list = FXCollections.observableArrayList();
    private MainPageController classeReferences;
    private PlayList playlist;
    private Song song;

    

    public EmotionCreationPageController(MainPageController classeReferences, PlayList playlist, Song song) {
        super();
        this.classeReferences = classeReferences;
        this.playlist = playlist;
        this.song = song;
        //this.songCopy = new Song(song.toJSON());
    }

    public class Container {

        public Song song;
        public PlayList playlist;
        public Emotion emotion;
        public EmotionCreationPageController mainController;
        public Container classReference;

        public Container(Song song, PlayList playlist, Emotion emotion, EmotionCreationPageController mainController) {
            this.song = song;
            this.playlist = playlist;
            this.emotion = emotion;
            this.mainController = mainController;
            classReference = this;
        } 

        public Container getClassReference() {
            return this;
        } 
    }


    

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        super.setImage(IMG1);

        Back.setText(EmotionalSongs.language == 0 ? "indietro" : "back");


        for(Emotion e : Emotion.Emotions) {
            list.add(new Container(song,playlist, e, this));
        }


        Callback<TableColumn<Container, Container>, TableCell<Container, Container>> cellFoctory = (TableColumn<Container, Container> param) -> {
            final TableCell<Container, Container> cell = new TableCell<Container, Container>() {
                
                @Override
                public void updateItem(Container item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } 
                    else 
                    {
                        try 
                        {
                            //carico la pagina
                            String path = EmotionalSongs.XML_Paths[18]; 
                            FXMLLoader XMLloader = new FXMLLoader(getClass().getClassLoader().getResource(path));
                            AnchorPane view = XMLloader.load();

                            //carico i parametri
                            EmotioTableElementController controller = XMLloader.getController();
                            controller.injectData(item);
                            //System.out.println(item);

                            setGraphic(view);
                        
                        } catch (IOException e) {
                            System.out.println(e);
                        }  
                        setText(null);
                    }
                }
            };
            return cell;
        };

        EmotionElement.setCellValueFactory(new PropertyValueFactory<Container, Container>("classReference"));
        EmotionElement.setCellFactory(cellFoctory);

        EmotioTable.setItems(list);
        EmotioTable.setSelectionModel(null);

        
 
    }

    

    

    

    @FXML
    void turnBack(ActionEvent event) throws IOException {
        System.out.println("hereee");
        classeReferences.SetPlaylistEditPage(playlist, classeReferences);
    }
}
