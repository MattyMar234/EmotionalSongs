package Java.Graphic_Interface;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Java.Account.RegisteredAccount;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AccountDataController extends Controller implements Initializable  {

    private ArrayList<DataContainer> dataFields = new ArrayList<DataContainer>();
    
    @FXML private AnchorPane basicInfoContainer;
    @FXML private GridPane  grid1;
    @FXML private GridPane  grid2;
    @FXML private VBox elements;

    private static final boolean [] setButton = {
        true,true,false,false,false, true,true,true,true,true,
    };
    
    private static final String [][] testi = {
        
        {"Nome"     , "Name"},
        {"Cognome"  , "Surname"}, 
        {"Email"    , "Email"},
        {"Password" , "Password"},
        {"UserID"   , "UserID"},

        {"CodiceFiscale" , "fiscalcode"},
        {"provincia"     , "province"},
        {"comune"        , "common"},
        {"via"           , "vie"},
        {"cap"           , "cap"}

    };


    public class DataContainer extends Controller{

        public Label label; 
        public TextField tf;
        public Button bt;
        
        private boolean edit = false;
        private int number;
        public  boolean editableButton;
        
        public DataContainer(Label label, TextField tf, Button bt, boolean editable, int number) {
            this.label = label;
            this.tf = tf;
            this.bt = bt;
            this.editableButton = editable;
            this.number = number;

            tf.setEditable(false);
            tf.setStyle("-fx-background-color: #949494");
            label.setId("edit_String");
            
            bt.setId("edit2");
            bt.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override
                public void handle(ActionEvent event) {
                    tf.setEditable(!tf.isEditable());

                    if(tf.isEditable()) {
                        tf.setStyle("-fx-background-color: #FFFFFF"); 
                    }
                    else {
                        tf.setStyle("-fx-background-color: #949494"); 
                        RegisteredAccount r = ((RegisteredAccount)application.ConnectedAccount);

                        
                        switch(number) {
                            case 0: r.setName(tf.getText());            break;
                            case 1: r.setSurname(tf.getText());         break;
                            case 5: r.SetFiscalCode(tf.getText());      break;
                            case 6: r.setProvincia(tf.getText());       break;
                            case 7: r.setComune(tf.getText());          break;
                            case 8: r.setViaPiazza(tf.getText());       break;
                            case 9: r.setCap(tf.getText());             break;
                        }
                        r.updateHashMap();
                    }
                }    
            });
        }
    }


    public AccountDataController(){
        super();
    }

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        RegisteredAccount account = (RegisteredAccount)super.application.ConnectedAccount;
       
        grid1.setAlignment(Pos.CENTER);
        grid2.setAlignment(Pos.CENTER);

        elements.setStyle(
              "-fx-border-radius: 12px;" 
              + "-fx-border-color: #F0F0F0;"
              //+ "-fx-background-color: transparent;"
              + "-fx-background-color: #21253a"
        );
        
        //-fx-background-color: #21253a;

        for(int i = 0; i < testi.length; i++) 
        {
            Label label = new Label(testi[i][0] + " :\t\t\t");
            TextField tf = new TextField(account.accountKeys.get(testi[i][1].toLowerCase()));
            Button bt = new Button("Modifica");

            label.setStyle("-fx-text-fill: rgb(2, 253, 199);");

            dataFields.add(new DataContainer(label, tf, bt, AccountDataController.setButton[i], i));
            GridPane.setHalignment(bt, HPos.CENTER);
            GridPane.setHalignment(tf, HPos.CENTER);
            //GridPane.setHalignment(label, HPos.RIGHT);

            

            
            if(i <= 4) {
                grid1.add(label , 0, i%5);
                grid1.add(tf    , 1, i%5);
                
                if(dataFields.get(i).editableButton)
                    grid1.add(bt    , 2, i%5);
            }
            else {
                grid2.add(label , 0, i%5);
                grid2.add(tf    , 1, i%5);
                
                if(dataFields.get(i).editableButton)
                    grid2.add(bt    , 2, i%5);
            }

            
            
        }
        
    }

    
    

}
