package Java.Graphic_Interface;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Java.DataClasses.Comment;
import Java.PlayList_Songs.Song;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class CommentElementController extends Controller implements Initializable {

    @FXML private Label LabelName;
    @FXML private Label LabelSurname;
    @FXML private TextArea TextArea;
    @FXML private ImageView DeleteIcon;

    @FXML private VBox LabelContainer;
    
    private Comment comment;
    private Boolean EnableDeleteIcon;
    private CommentsPageController controllerClass;
    private int elementIndex;

    public CommentElementController() {}


    public CommentElementController(Comment comment, CommentsPageController controllerClass, boolean editable, int index) {
        this.comment = comment;
        this.controllerClass = controllerClass;
        EnableDeleteIcon = editable;
        elementIndex = index;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        LabelName.setText(comment.getAutor().getName() + " " + comment.getAutor().getSurname());
        LabelSurname.setText(comment.getAutor().getID());
        //TextArea.setText(comment.getComment());
        //TextArea.setEditable(false);

        ArrayList<String> texts = new ArrayList<String>();
        String str = comment.getComment();
        String s = "";
        int lenghtLimit = 64;

        for(int i = 0; i < str.length(); i++) 
        {
            if(str.charAt(i) == '\n')
                continue;
                
            s += str.charAt(i);


            if(s.length() == lenghtLimit) {
                texts.add(new String(s));
                s = "";
            }
        }

        texts.add(new String(s));

        for(String st : texts) {
            Label l = new Label(st);
            System.out.println("line: " + st);
            l.setId("text");
            LabelContainer.getChildren().addAll(l);
        }

        

        

        if(!EnableDeleteIcon) {
            DeleteIcon.setDisable(true);
            DeleteIcon.setVisible(false);
        }
    }


    @FXML
    void removeComment(MouseEvent event) {
        controllerClass.removeComment(elementIndex);
    }
    
}
