package Java.Graphic_Interface;

import java.net.URL;
import java.util.ResourceBundle;

import Java.DataClasses.Comment;
import Java.PlayList_Songs.Song;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class CommentElementController extends Controller implements Initializable {

    @FXML private Label LabelName;
    @FXML private Label LabelSurname;
    @FXML private TextArea TextArea;
    
    private Comment comment;

    public CommentElementController() {}

    public CommentElementController(Comment comment) {
        this.comment = comment;
        System.out.println("-----------------\n\n\n\n----------------");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        LabelName.setText("??");
        TextArea.setText(comment.getComment());

        
    }
    
}
