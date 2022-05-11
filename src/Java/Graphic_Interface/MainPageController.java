package Java.Graphic_Interface;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Java.Account.RegisteredAccount;
import Java.Account.UnregisteredAccount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class MainPageController extends Controller implements Initializable {

    

    // ========================= Label ========================= //
   

    // ========================= pane ========================= //
    @FXML private AnchorPane SceneContainer;
    @FXML protected BorderPane borderPane;
    


    // ========================= Buttons ========================= //
    @FXML public Button profileButton;
    @FXML public Button reposityButton;
    @FXML public Button playlistButton;
    @FXML public Button optionsButton;
    @FXML public Button CambioButton;
    @FXML public Button ExitButton;


    // ========================= variabili - globali =========================//
    public static MainPageController MainPageControllerReference;


    
    // ========================= variabili - locali =========================//
    
    ArrayList<Button> buttons = new ArrayList<Button>();
    private int SelectedButton = 1;
    private final String ButtonColor = "-fx-background-color: #0bb813;" + "-fx-text-fill:#ffffff;";
    protected int state = 1;
    


    public MainPageController() throws IOException {
        super();
        MainPageControllerReference = this;
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        if(this.windwoPosWidth < 1200.0) {
            this.windwoPosWidth = 1200.0;
            this.application.mainStage.setWidth(this.windwoPosWidth);
        }

        /*
        if(this.windwoPosWidth < (screenSize.getWidth()*100)/80) {
            this.windwoPosWidth = (screenSize.getWidth()*100)/80;
            this.application.mainStage.setWidth(this.windwoPosWidth);
        }*/

        if(this.windwoPosHeight < 800.0) {
            this.windwoPosHeight = 800.0;
            this.application.mainStage.setHeight(this.windwoPosHeight);
        }
        
        
        for(Field f : this.getClass().getFields())
        {
            try {
                Object obj = this.getClass().getField(f.getName()).get(this);

                if(obj instanceof Button) {
                    buttons.add((Button) obj);
                }

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        

        ClearActiveButtons();
        this.buttons.get(1).setStyle(ButtonColor);

        int index =  0;
        for(Button b : this.buttons) 
        {
            final int n = index++;

            b.setOnMouseEntered(e -> {
                if(state == n) {
                    b.setStyle(ButtonColor);//b.setStyle("-fx-background-color: #f18100f6");
                }
                else { 
                    b.setStyle("-fx-background-color: #9c9c9c66;" + "-fx-text-fill:#ffffff;");
                } 
                
                
            });

            b.setOnMouseExited(e -> {
                if(state == n) {
                    b.setStyle(ButtonColor);
                }
                else {
                    b.setStyle("-fx-background-color: transparent;" + "-fx-text-fill:#798AA6");
                }
            });
        }  
        
        if(this.application.ConnectedAccount instanceof UnregisteredAccount) {
            this.playlistButton.setDisable(true);
            this.profileButton.setText("Sign In");
        }
       
        try {
            SetReposityPage();  
        } 
        catch (IOException e) { 
            e.printStackTrace();
        }
    }

    private void ClearActiveButtons() {
        
        //reimposto a tutti lo sfondo
        for(Button b : this.buttons) {
            b.setStyle("-fx-background-color: transparent;");
        }

        
    }


    // -------------------------------- eventi -------------------------------- //
    
    @FXML
    void AccountButtonSelected(ActionEvent event) throws IOException 
    { 
        if(state != 4 ) {
            if(this.application.ConnectedAccount instanceof RegisteredAccount) { 
                state = 4;
                SetProfilePage();
            } 
            else if(this.application.ConnectedAccount instanceof UnregisteredAccount) {

            }
        }
    }

    @FXML
    void setReposity(ActionEvent event) throws IOException 
    {
        if(state != 1 ) {
            state = 1;
            ClearActiveButtons();
            this.buttons.get(1).setStyle(ButtonColor);
            SetReposityPage();
        }
    }

    @FXML
    void SetPlayList(ActionEvent event) throws IOException 
    {
        state = 2;
        ClearActiveButtons();
        this.buttons.get(2).setStyle(ButtonColor);
        SetPlayListPage();
    }

    @FXML
    void SetOptions(ActionEvent event) throws IOException {
        if(state != 3 ) {
            state = 3;
            ClearActiveButtons();
            this.buttons.get(3).setStyle(ButtonColor);
            SetOptionsPage();
        } 
    }


    @FXML
    public void access(MouseEvent event) throws IOException {
        //super.SwitchScene((Stage) icon.getScene().getWindow(), "LoadAccaunt");
     
    }

    @FXML
    void esci(ActionEvent event) {
        application.logout(application.mainStage);
    }


     
    // -------------------------------- Cambio pagine -------------------------------- //

    protected void SetReposityPage() throws IOException
    {       
        AnchorPane view = getScenePage("MainPage_reposity").load();
        borderPane.getChildren().removeAll();
        borderPane.setCenter(view);  
    }

    protected void SetPlayListPage() throws IOException 
    {  
        AnchorPane view = getScenePage("MainPage_PLaylist").load();
        borderPane.getChildren().removeAll();
        borderPane.setCenter(view);  
    }  

    protected void SetOptionsPage() throws IOException 
    {  
        AnchorPane view = getScenePage("MainPage_impostazioni").load();
        borderPane.getChildren().removeAll();
        borderPane.setCenter(view);  
    }  

    protected void SetProfilePage() throws IOException {

    }

    @FXML
    void ChangeAccount(ActionEvent event) throws IOException {
        SwitchScene("AccessPage");
    }
    
}
