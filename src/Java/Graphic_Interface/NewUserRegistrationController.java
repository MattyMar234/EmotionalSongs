package Java.Graphic_Interface;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.jar.Attributes.Name;

import Java.Account.Account;
import Java.Account.RegisteredAccount;
import Java.emotionalsongs.EmotionalSongs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class NewUserRegistrationController extends Controller implements Initializable {
    
    private boolean debug = true;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private Button confirmButton;
    @FXML private Button BackButton;
    @FXML public TextField name;
    @FXML public TextField surname;
    @FXML public TextField userID;
    @FXML public TextField email;
    @FXML public PasswordField password1;
    @FXML public PasswordField password2;
    @FXML public TextField civicNumber;
    @FXML public TextField cap;
    @FXML public TextField comune;
    @FXML public TextField provincia;
    @FXML public TextField codiceFiscale;
    @FXML public TextField viaPiazza;

    @FXML public Label label1;
    @FXML public Label label2;
    @FXML public Label label3;
    @FXML public Label label4;
    @FXML public Label label5;
    @FXML public Label label6;
    @FXML public Label label7;
    @FXML public Label label8;
    @FXML public Label label9;
    @FXML public Label label10;
    @FXML public Label label11;
    @FXML public Label label12;



    Field [] variabili = this.getClass().getFields();      //tutte le variabili public
    ArrayList<String> variabiliNome = new  ArrayList<String>();
    ArrayList<String> LabelsNome = new  ArrayList<String>();
    ArrayList<ElementsContainer> contenitori = new ArrayList<ElementsContainer>();
    
    public class ElementsContainer 
    {
        public TextField text;
        public Label label;

        //costruttore 1
        public ElementsContainer(TextField text, Label label) {
            this.text = text;
            this.label = label;
        }

        //costruttore 2
        public ElementsContainer() {

        }

        @Override
        public String toString() {
            return new String(text + " with " + label);
        }

        public void setError(String error) {
            this.text.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            this.label.setVisible(true);
            this.label.setText(error);
        }

        public void clearError() {
            //this.text.setStyle("");
            this.label.setVisible(false);
        }
    }


    

    
    public NewUserRegistrationController() {
        super();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        //creo gli array
        for(Field f : variabili) {
            if(f.getName().startsWith("label")) {
                LabelsNome.add(f.getName());
            }
            else {
                variabiliNome.add(f.getName());
            }
        }

        //creo un un oggetto container che contiene un textField con la sua label
        for(int  i = 0; i < variabiliNome.size(); i++) 
        {
            ElementsContainer container = new ElementsContainer();
            try {
                container.text  = (TextField) this.getClass().getField(variabiliNome.get(i)).get(this);
                container.label = (Label)     this.getClass().getField(LabelsNome.get(i)).get(this);
                container.clearError();
                contenitori.add(container);
                if(debug)System.out.println(container);
            } 
            catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
            
        }    
    }


    public void validateNewUser() throws IOException 
    {
        System.out.println("validete fields");
        HashMap <String, String> UserCostructor = new HashMap<String, String>();
        boolean error = false;
        
        //Verifico se tutti i campi sono stati compilati
        for(int i = 0; i < contenitori.size(); i++) 
        {
            ElementsContainer container = contenitori.get(i);
            String data = container.text.getText();

            if(data == null || data.equals("")) {
                container.setError("campo non compilato");
                error = true;
            }

            if(!error) {
                UserCostructor.put(variabiliNome.get(i), data);
            }
        }

        if(error) {
            return;
        }

        //verifica password
        if(!password1.getText().equals(password2.getText())) {
            contenitori.get(5).setError("le password non coincidono");
            return;
        }


        RegisteredAccount testAccount = new RegisteredAccount(UserCostructor);// = new Account(UserCostructor);


        switch(application.AccountsManager.checkAccaunt(testAccount)) 
        {
            case 0:
                application.AccountsManager.Users.add(testAccount);
                application.ConnectedAccount = testAccount;
                Stage Window = (Stage) confirmButton.getScene().getWindow();
                super.SwitchScene(Window, "MainPage");
                break;
        
            //accaount già esistnte
            case 1:

                break;

            //comune non valido
            case 2:
                break;

            //provincia non valida 
            case 3:
                break;

            //cap non valido
            case 4:
                break;
            
            //userID non valido
            case 5:
                break;

        }
  
    }

    
    @FXML
    public void typed(KeyEvent event) 
    {
        for(ElementsContainer container : contenitori) {
            container.clearError();
        }
    }

    @FXML
    public void TurnBack() throws IOException 
    {
        Stage Window = (Stage) BackButton.getScene().getWindow();
        super.SwitchScene(Window, "AccessPage");
    }
}
