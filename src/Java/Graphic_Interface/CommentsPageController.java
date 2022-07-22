package Java.Graphic_Interface;

import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Java.DataClasses.Comment;
import Java.PlayList_Songs.PlayList;
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

public class CommentsPageController extends Controller implements Initializable 
{

    @FXML private TableView<Comment> CommentsTable;
    @FXML private TableColumn<Comment, Comment> Commnts;
    @FXML private ImageView BackArrow;
    @FXML private AnchorPane searchPane;
    @FXML private Label pubblica;
    @FXML volatile private Label counter;
    @FXML private TextArea textArea;

    @FXML private AnchorPane writerElement;


    private ObservableList<Comment> commnetsList = FXCollections.observableArrayList();
    private MainPageController mainControllerPage;
    private Song song;
    private PlayList playlist;
    
    private final int maxcharacters = 256;
    private int commentLenght = 0;
    private int texAreaBaseHeight;
    private CharsCounter listener;

    public class CharsCounter extends Thread {

        volatile private CommentsPageController controller;
        volatile private boolean run = true;
        

        public CharsCounter(CommentsPageController controller) {
            this.controller = controller;

        }

        public void end() {
            this.run = false;
        }

        public void run()
        {
            while(run) 
            {
                //System.out.println(controller.textArea.getText());
                controller.update();
                try {
                    sleep(20);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            }
        }

    }


    public CommentsPageController() {}


    public CommentsPageController(MainPageController controller, Song song) {
        this(controller, song, null);
    }

    public CommentsPageController(MainPageController controller, Song song, PlayList playlist) {
        this.mainControllerPage = controller;
        this.song = song;
        this.playlist = playlist;
    }

    public void update() {
        
        String d = Integer.toString(textArea.getText().length());
        counter.setText("char: " + d + " di 256");
        System.out.println(textArea.getText().length());
    }

    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        if(playlist == null) {
            writerElement.setVisible(false);
        }
        
        textArea.setWrapText(true);
        textArea.setStyle("-fx-font-family: 'monospaced';");
        texAreaBaseHeight = (int)textArea.getHeight();
        textArea.setMinHeight(texAreaBaseHeight + 3 * 16);
        textArea.setMaxHeight(400);

        String d = Integer.toString(textArea.getText().length());
        counter.setText("char: " + d + " di 256");
        //counter.setVisible(false);



        textArea.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            
           @Override
            public void handle(KeyEvent event) 
            {
                for(int  i = 0; i < event.getCharacter().length(); i++) {
                    char ch = event.getCharacter().charAt(i);
                    
                    if(commentLenght >= 256 && (ch >= 32 || commentLenght != 127)) {
                        event.consume();
                    }
                }
                
                
                /*String character = event.getCharacter();
                boolean validData = false;

                commentLenght = textArea.getText().length();

                counter.setText("chars: " + Integer.toString(commentLenght) + " di 256");
        
                //System.out.println(character + " | commlength: " + commentLenght + "  ");                
            */
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

        listener = new CharsCounter(this);
        //listener.start();
    }

    public void injectData(MainPageController controller, Song song) {
    }

  
    @FXML
    void turnBack(MouseEvent event) throws IOException {
        if(playlist == null) {
            this.mainControllerPage.Comment_To_repository();
        }
        else {
            this.mainControllerPage.SetPlayListPage();

            MainPageController_playList controller = (MainPageController_playList)this.mainControllerPage.currentLoader.getController();
            this.mainControllerPage.SetPlaylistEditPage(playlist, controller);
        }
    }

    @FXML
    void pubblicateComment(MouseEvent event) 
    {
        this.song.addComment(new Comment(textArea.getText(), application.ConnectedAccount));
        commnetsList.add(this.song.getComments().get(this.song.getComments().size() - 1));
        
        textArea.setText("");
        commentLenght = 0;
        counter.setText("chars: 0 di 256");
    }

    @FXML
    void checkTextArea(KeyEvent event) {

        commentLenght = textArea.getText().length();

        if(commentLenght > 256) {
            String txt = textArea.getText();

            String out = "";

            for(int i = 0; i < 256; i++) {
                out += txt.charAt(i);
            }
            


            textArea.setText(out);
            textArea.positionCaret(256);
            //textArea.Line
            commentLenght = textArea.getText().length();
        }

        //update size

        int Newline = 0;
        for(int i = 0; i < textArea.getText().length(); i++) {
            if(textArea.getText().charAt(i) == '\n') Newline++;
        }

        int  line = commentLenght/67  + Newline + 3;
        int value = texAreaBaseHeight + line * 16;

        if(value > 400) value = 400;
        
        textArea.setMinHeight(value);


        counter.setText("chars: " + Integer.toString(commentLenght) + " di 256");
        System.out.println("lines: " + line);
    }

    
}
