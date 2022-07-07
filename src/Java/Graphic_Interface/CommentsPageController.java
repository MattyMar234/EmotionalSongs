package Java.Graphic_Interface;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Java.DataClasses.Comment;
import Java.PlayList_Songs.Song;
import Java.emotionalsongs.EmotionalSongs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class CommentsPageController extends Controller implements Initializable {


    @FXML private TableView<Comment> CommentsTable;
    @FXML private TableColumn<Comment, Comment> Commnts;
    @FXML private ImageView BackArrow;
    @FXML private AnchorPane searchPane;
    @FXML private Label pubblica;
    @FXML private Label counter;
    @FXML private TextArea textArea;


    private ObservableList<Comment> commnetsList = FXCollections.observableArrayList();
    private MainPageController mainControllerPage;
    private Song song;

    private final int maxcharacters = 256;
    private int commentLenght = 0;


    public CommentsPageController() {}


    public CommentsPageController(MainPageController controller, Song song) {
        this.mainControllerPage = controller;
        this.song = song;
    }

    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        textArea.setWrapText(true);

        String d = Integer.toString(textArea.getText().length());
        counter.setText("char: " + d + " di 256");

        textArea.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            
            @Override
            public void handle(KeyEvent event) 
            {
                
                
                String character = event.getCharacter();
                boolean validData = false;

                for(int i = 0; i < character.length() && !validData; i++) {
                    char ch = character.charAt(i);
                    //System.out.println((int)ch);

                    //verifica massimo
                    
                    if(ch == 8 && commentLenght >= 1) {
                        commentLenght--;
                        validData = true;
                    }
                    else if(ch >= 32 && ch != 127 && commentLenght <= 255 ) {
                        commentLenght++;
                        validData = true;
                    }
                }

                counter.setText("chars: " + Integer.toString(commentLenght) + " di 256");
        
                if(!validData) {
                    event.consume(); //evita di inserira il dato nella textarea
                }
                //System.out.println(character + " | commlength: " + commentLenght + "  ");                
            }
        });

    
        for(Comment c : this.song.getComments()) {
            commnetsList.add(c);
            System.out.println(c);
        }

        
        Callback<TableColumn<Comment, Comment>, TableCell<Comment, Comment>> cellFoctory = (TableColumn<Comment, Comment> param) -> {
            final TableCell<Comment, Comment> cell = new TableCell<Comment, Comment>() {
                
                @Override
                public void updateItem(Comment item, boolean empty) {
                    super.updateItem(item, empty);

                    
                    
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } 
                    else 
                    {
                        try 
                        {
                            //carico la pagina
                            FXMLLoader XMLloader = new FXMLLoader(getClass().getClassLoader().getResource(application.pageLoaders.get("CommentElement")));
                            
                            XMLloader.setControllerFactory(c -> {    
                                return new CommentElementController(item); // <-- parametri costruttore classe
                            });

                            
                            
                            AnchorPane view = XMLloader.load();
                            setGraphic(view);
                        
                        } catch (IOException e) {
                            System.out.println(e);
                            System.out.println("loading comments error");
                        }
                        setText(null);
                    }
                }
            };
            return cell;
        };

        Commnts.setCellValueFactory(new PropertyValueFactory<Comment, Comment>("classReference"));
        Commnts.setCellFactory(cellFoctory);

        CommentsTable.setItems(commnetsList); 
    }

    public void injectData(MainPageController controller, Song song) {
    }

  
    @FXML
    void turnBack(MouseEvent event) throws IOException {
        this.mainControllerPage.Comment_To_repository();
    }

    @FXML
    void pubblicateComment(MouseEvent event) 
    {
        this.song.addComment(new Comment(textArea.getText()));
        commnetsList.add(this.song.getComments().get(this.song.getComments().size() - 1));
        
        textArea.setText("");
        commentLenght = 0;
        counter.setText("chars: 0 di 256");
    }

    
}
