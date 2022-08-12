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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class EmotionCreationPageController extends Controller implements Initializable {
   
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

    private MainPageController classeReferences;
    private PlayList playlist;
    private Song song;

    public ObservableList<EmotionContainer> list = FXCollections.observableArrayList();
    public ObservableList<EmotionContainer> Addedlist = FXCollections.observableArrayList();

    public EmotionCreationPageController(MainPageController classeReferences, PlayList playlist, Song song) {
        super();
        this.classeReferences = classeReferences;
        this.playlist = playlist;
        this.song = song;
        //this.songCopy = new Song(song.toJSON());
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
                enable.setText("Includi Emozione");
            }
            else {
                enable = new Button();
                enable.setId("cance_Button");
                enable.setText("Escludi Emozione");
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
        ArrayList<Emotion> userEmotion = song.getUserEmotions(super.application.ConnectedAccount.getID());
        boolean [] missingEmotion = new boolean[9];


        if(userEmotion.size() != 0) {

            for(int  i = 0; i < 9; i++) {
                missingEmotion[i] = true;
            }

            for(Emotion e : userEmotion) {
                EmotionContainer c = new EmotionContainer(e, 2, this);
                c.valuetext = Integer.toString(e.getScore());
                Addedlist.add(c);

                missingEmotion[Emotion.getEmotionID(e)] = false; // return n : 0 -> 8 e imposto false
            
            }

            for(int  i = 0; i < 9; i++) {
                if(missingEmotion[i]) {
                    //inserisco l'emozioni che mi mancano
                    list.add(new EmotionContainer(Emotion.Emotions[i], 1, this));
                }
            }
        }
        else {
            for(int i = 0; i < 9; i++) {
                list.add(new EmotionContainer(Emotion.Emotions[i], 1, this));
            }
        }

        name.setCellValueFactory(new PropertyValueFactory<EmotionContainer, String>("name"));
        explanation.setCellValueFactory(new PropertyValueFactory<EmotionContainer, String>("description"));
        values.setCellValueFactory(new PropertyValueFactory<EmotionContainer, ComboBox<Integer>>("values"));
        emotion.setCellValueFactory(new PropertyValueFactory<EmotionContainer, ImageView>("emotionIMG"));
        emotionSelection.setCellValueFactory(new PropertyValueFactory<EmotionContainer, Button>("enable"));

        name1.setCellValueFactory(new PropertyValueFactory<EmotionContainer, String>("name"));
        explanation1.setCellValueFactory(new PropertyValueFactory<EmotionContainer, String>("description"));
        values1.setCellValueFactory(new PropertyValueFactory<EmotionContainer, String>("valuetext"));
        emotion1.setCellValueFactory(new PropertyValueFactory<EmotionContainer, ImageView>("emotionIMG"));
        emotionSelection1.setCellValueFactory(new PropertyValueFactory<EmotionContainer, Button>("enable"));

        EmotionTable.setRowFactory(tv -> new TableRow<EmotionContainer>() {
            
            @Override
            protected void updateItem(EmotionContainer item, boolean empty) {
                super.updateItem(item, empty);
                
                if (item == null)
                    setStyle("");
                else if (item.selected)
                    setStyle("-fx-background-color: #baffba;");
                else
                    setStyle("");
            }
        });

        EmotionTable.setItems(list);
        EmotionTable.setSelectionModel(null);

        AddedEmotionTable.setItems(Addedlist);
        AddedEmotionTable.setSelectionModel(null);
 
    }

    public void move(int state, EmotionContainer emotion) {
        
        //add
        if(state == 1) {
            list.remove(emotion);
            EmotionContainer p = new EmotionContainer(emotion.emotion, 2, emotion.classRef);
            p.valuetext = Integer.toString(emotion.values.getValue());
            Addedlist.add(p);

            song.getEmotions().add(new Emotion(p.emotion, emotion.values.getValue(), (RegisteredAccount)application.ConnectedAccount));
      
        }
        //remove
        else {
            Addedlist.remove(emotion);
            EmotionContainer p = new EmotionContainer(emotion.emotion, 1, emotion.classRef);
            list.add(p);

            song.getEmotions().remove(emotion.emotion);
        
            
        }
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
