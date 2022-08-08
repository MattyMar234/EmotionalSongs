package Java.Graphic_Interface;

import java.net.URL;
import java.util.ResourceBundle;

import Java.emotionalsongs.Emotion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;

public class EmotionCreationPageController extends Controller implements Initializable {
   

    @FXML private TableView<EmotionContainer> EmotionTable;
    @FXML private TableColumn<EmotionContainer, Image> emotion;
    @FXML private TableColumn<EmotionContainer, String> name;
    @FXML private TableColumn<EmotionContainer, String> explanation;
    @FXML private TableColumn<EmotionContainer, ComboBox<Integer>> values;

    public ObservableList<EmotionContainer> list = FXCollections.observableArrayList();

    public EmotionCreationPageController() {
        super();
    }


    public class EmotionContainer 
    {
        CheckBox enable;
        Image emotionIMG;
        String name;
        String description;
        ComboBox<Integer> values;

        
        public EmotionContainer(Emotion emotion) {
            
            this.name = emotion.getCategory();
            this.description = emotion.getExplanation();

            values = new ComboBox<Integer>();

            for(int i = 1; i <= 5; i++) {
                values.getItems().add(i);
            }
        }

        public Image getEmotionIMG() {
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

        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
                
        for(int i = 0; i < 9; i++) {
            list.add(new EmotionContainer(Emotion.Emotions[i]));
        }

        name.setCellValueFactory(new PropertyValueFactory<EmotionContainer, String>("name"));
        explanation.setCellValueFactory(new PropertyValueFactory<EmotionContainer, String>("description"));
        values.setCellValueFactory(new PropertyValueFactory<EmotionContainer, ComboBox<Integer>>("values"));
    
        EmotionTable.setItems(list);

        System.out.println("heree");
         
    }
}
