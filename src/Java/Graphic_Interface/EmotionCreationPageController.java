package Java.Graphic_Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

import Java.Account.RegisteredAccount;
import Java.PlayList_Songs.PlayList;
import Java.PlayList_Songs.Song;
import Java.emotionalsongs.Emotion;
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
   
    @FXML private TableView   <EmotionContainer> EmotionTable;
    @FXML private TableView   <EmotionContainer> AddedEmotionTable;
    //@FXML private TableColumn <EmotionContainer, CheckBox> emotionSelection;
    @FXML private TableColumn <EmotionContainer, Button> emotionSelection;
    @FXML private TableColumn <EmotionContainer, ImageView> emotion;
    @FXML private TableColumn <EmotionContainer, String> name;
    @FXML private TableColumn <EmotionContainer, String> explanation;
    @FXML private TableColumn <EmotionContainer, ComboBox<Integer>> values;

    @FXML private TableColumn <EmotionContainer, Button> emotionSelection1;
    @FXML private TableColumn <EmotionContainer, ImageView> emotion1;
    @FXML private TableColumn <EmotionContainer, String> name1;
    @FXML private TableColumn <EmotionContainer, String> explanation1;
    @FXML private TableColumn <EmotionContainer, String> values1;
    @FXML private Button Back;


    @FXML private TableView<Container> EmotioTable;
    @FXML private TableColumn<Container, Container> EmotionElement;

    private MainPageController classeReferences;
    private PlayList playlist;
    private Song song;

    private ObservableList<Container> list = FXCollections.observableArrayList();
    public ObservableList<EmotionContainer> Addedlist = FXCollections.observableArrayList();

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


    public class EmotionContainer 
    {
        ImageView emotionIMG;
        String name;
        String description;
        String valuetext;
        int state;
        ComboBox<Integer> values;
        //CheckBox enable;
        Button enable;
        Emotion emotion;

        EmotionCreationPageController classRef;
        EmotionContainer thisclass;

        boolean selected = false;

        
        public EmotionContainer(Emotion emotion, int state, EmotionCreationPageController classRef) {
            
            this.name = emotion.getCategory();
            this.description = emotion.getExplanation();
            this.emotionIMG = new ImageView(Emotion.EmotionHashMap.get(this.name));
            this.emotion = emotion;
            this.state = state;
            this.classRef = classRef;
            this.thisclass = this;

            emotionIMG.setFitHeight(48);
            emotionIMG.setFitWidth(48);

            values = new ComboBox<Integer>();
            //enable = new CheckBox();

            if(state == 1) {
                enable = new Button();
                enable.setId("save_Button");
                enable.setText(EmotionalSongs.language == 0 ? "Includi Emozione" : "Include Emotion");
            }
            else {
                enable = new Button();
                enable.setId("cance_Button");
                enable.setText(EmotionalSongs.language == 0 ? "Escludi Emozione" : "Exclude Emotion");
            }

            enable.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    classRef.move(state, thisclass);
                }
            });

            for(int i = 1; i <= 5; i++) {
                values.getItems().add(i);
                values.setValue(1);
            }
        }
        

        public Button getEnable() {
            return enable;
        }
        public ImageView getEmotionIMG() {
            return emotionIMG;
        }
        public String getName() {
            return name;
        }
        public String getDescription() {
            return description;
        }
        public ComboBox<Integer> getValues() {
            return values;
        }
        public String getValuetext() {
            return valuetext;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {


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

    public void move(int state, EmotionContainer emotion) {
        
        
    }

    @FXML
    void clicked(MouseEvent event) {

        /*
        if(state == 0) {
            selected1 = EmotionTable.getSelectionModel().getSelectedItem();
            state++;
        }
        else {
            selected2 = EmotionTable.getSelectionModel().getSelectedItem();
            state = 0;
            if(selected2 == selected1) {
                EmotionContainer p = EmotionTable.getSelectionModel().getSelectedItem();
                p.selected = !p.selected;
            }
        }*/
    }

    @FXML
    void save(ActionEvent event) 
    {
        /*for(EmotionContainer row : list) {
            if(row.enable.isSelected()) {
                song.getEmotions().add(new Emotion(row.emotion, row.values.getValue()));
            }
        }*/
        this.song.getEmotions().clear();
        
        for(Emotion e : this.song.getEmotions()) {
            this.song.getEmotions().add(e);
        }
    }

    @FXML
    void turnBack(ActionEvent event) throws IOException {
        classeReferences.SetPlaylistEditPage(playlist, classeReferences);
    }
}
