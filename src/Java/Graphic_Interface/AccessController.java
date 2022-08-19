package Java.Graphic_Interface;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import Java.Account.Account;
import Java.Account.RegisteredAccount;
import Java.Account.UnregisteredAccount;
import Java.PlayList_Songs.PlayList;
import Java.emotionalsongs.EmotionalSongs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AccessController extends Controller implements Initializable {

    @FXML private Button LoginButton;
    @FXML private Label NewAccount;
    @FXML private Button NoAccountButton;
    @FXML private AnchorPane labelButton;
    @FXML private AnchorPane pane1;
    @FXML private PasswordField password;
    @FXML private TextField userName;

    @FXML private ComboBox<ImageView> flags;
    private ObservableList<ImageView> imgs = FXCollections.observableArrayList();

    @FXML private Label LabelName;
    @FXML private Label labelPassword;


    private final static String [][] matrice = {
        {"Accedi all'Account", "Login"},     //LoginButton
        {"Continua senza Account", "Continue without account"}, //  //NoAccountButton
        {"Crea un Account", "Create Account"}           //NewAccount
    };

    public class StatusListCell extends ListCell<ImageView> 
    {
        protected void updateItem(ImageView item, boolean empty) {
            
            super.updateItem(item, empty);
            setGraphic(null);
            setText(null);

            if(item != null){
                ImageView imageView = item;
                imageView.setFitWidth(40);
                imageView.setFitHeight(40);
                setGraphic(imageView);
                //setText();
            }
        }
    
    }

    class IconTextCellClass extends ListCell<ImageView> {
        @Override
        protected void updateItem(ImageView item, boolean empty) {
            super.updateItem(item, empty);
            
            if (item != null) {
                setGraphic(item);
            }
        }
    }

    

    
    
    public AccessController() {
        super();
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {

        updatePageText();

        File folder = new File(EmotionalSongs.flagFolder);
        File[] listOfFiles = folder.listFiles();

        Queue<File> queue = new LinkedList<File>();
        for(File f : listOfFiles) queue.add(f);

        int index = 1;
        while(queue.size() > 0) 
        {
            File f = queue.poll(); //ottengo e rimuovo

            //se non è un file, viene comunque rimosso
            if(f.isFile()) 
            {   
                //se l'immagine cossiponde a quella che cerco
                int number = Integer.parseInt(f.getName().split("_")[0]);
                
                if(number == index) 
                {
                    //creo la nuova immagine e l'aggiungo
                    try {
                        ImageView img = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(f), null));

                        img.setFitHeight(36);
                        img.setFitWidth(36);

                        imgs.add(img);

                        flags.getItems().add(img);
                        index++;
                    } 
                    catch (IOException e) 
                    {
                        System.out.println("=========================================");
                        System.out.println(e);
                        System.out.println("=========================================");
                        e.printStackTrace();
                        return;
                    }
           
                }
                else {
                    queue.add(f);
                }
            }
        }

        flags.setCellFactory(new Callback<ListView<ImageView>, ListCell<ImageView>>() {

            @Override public ListCell<ImageView> call(ListView<ImageView> p) {
                return new ListCell<ImageView>() {
                    
                    @Override protected void updateItem(ImageView item, boolean empty) {
                        super.updateItem(item, empty);
        
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            ImageView v = new ImageView(item.getImage());
                            v.setFitWidth(36);
                            v.setFitHeight(36);

                            setGraphic(v);
                        }
                   }
              };
          }
        });

        flags.getSelectionModel().selectFirst();
            
        clearError();
       
        //non funzionano ??? 
        if(this.windwoPosWidth < 1200.0) {
            this.windwoPosWidth = 1200.0;
            this.application.mainStage.setWidth(this.windwoPosWidth);
        }


        if(this.windwoPosHeight < 1000.0) {
            this.windwoPosHeight = 1000.0;
            this.application.mainStage.setHeight(this.windwoPosHeight);
        }
    }

    @FXML
    void changeLanguage(ActionEvent event) {

        EmotionalSongs.language = flags.getSelectionModel().getSelectedIndex();
        flags.getSelectionModel().select(EmotionalSongs.language);

        updatePageText();
        
    
        

        /*
        flags.getSelectionModel().select(null);
        flags.getItems().clear();
        flags.getItems().addAll(imgs);
        flags.getSelectionModel().select(imgs.get(EmotionalSongs.language));
        */

        

        /*for(int  i = 0; i < flags.getItems().size(); i++) {
            flags.getItems().get(0).setImage(imgs.get(i));
        }*/


    }

    private void updatePageText() {
        LoginButton.setText(AccessController.matrice[0][EmotionalSongs.language]);
        NoAccountButton.setText(AccessController.matrice[1][EmotionalSongs.language]);
        NewAccount.setText(AccessController.matrice[2][EmotionalSongs.language]);
    }

    @FXML
    void NoAccount(ActionEvent event) throws IOException {

        this.application.ConnectedAccount = new UnregisteredAccount();
        Stage Window = (Stage) NoAccountButton.getScene().getWindow();
        super.SwitchScene("MainPage");
    }

    @FXML
    void CreateNewAccount(MouseEvent event) throws IOException {
        Stage Window = (Stage) NoAccountButton.getScene().getWindow();
        super.SwitchScene("UserRegistration");
    }

    @FXML
    void searchAccount(ActionEvent event) throws IOException 
    {
        RegisteredAccount TempAccount = new RegisteredAccount();
        boolean error = false;

        clearError();

        //verifico validità del campo
        if(userName == null || userName.getText().length() == 0) {
            this.LabelName.setText("missing data");
            this.userName.setStyle("-fx-border-color: #a50303;");
            this.LabelName.setVisible(true);
            error = true;
        }
        else {
            //verifica email
            if(userName.getText().contains("@")) {
                TempAccount = application.AccountsManager.SearchByEmail(userName.getText());
                this.LabelName.setText("invalid email");
            }
            //verifica userID
            else {
                TempAccount = application.AccountsManager.SearchByID(userName.getText());
                this.LabelName.setText("invalid user ID");
            }

            if(TempAccount == null) {
                this.userName.setStyle("-fx-border-color: #a50303;");
                this.LabelName.setVisible(true);
                error = true;
            }
        }

        //verifico validità del campo
        if(password == null || password.getText().length() == 0) {
            this.labelPassword.setText("missing data");
            this.password.setStyle("-fx-border-color: #a50303;");
            this.labelPassword.setVisible(true);
            error = true;
        }


        if(error) {
            return;
        }

        if(!TempAccount.getPassword().equals(password.getText())) {
            this.labelPassword.setText("incorrect password");
            this.password.setStyle("-fx-border-color: #a50303;");
            this.password.clear();
            this.labelPassword.setVisible(true);
            return;
        }
            

        /*****test*****/
        /* PlayList p = new PlayList("prova");
        p.addSong(application.songManager.getElement(20));
        p.addSong(application.songManager.getElement(287));
        p.addSong(application.songManager.getElement(60));
        TempAccount.addPlaylist(p);*/

        application.ConnectedAccount = TempAccount;

        Stage Window = (Stage) NoAccountButton.getScene().getWindow();
        super.SwitchScene("MainPage");
        

    }  



    private void clearError() {
        this.LabelName.setVisible(false);
        this.labelPassword.setVisible(false);
        this.userName.setStyle("-fx-border-color: transparent;");
        this.password.setStyle("-fx-border-color: transparent;");
    }




}