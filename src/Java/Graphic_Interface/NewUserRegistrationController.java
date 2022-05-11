package Java.Graphic_Interface;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.json.simple.JSONObject;
import Java.Account.RegisteredAccount;
import Java.DataClasses.Common;
import Java.DataClasses.Province;
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
    @FXML public PasswordField password;
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
    

    private class ElementsContainer 
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
                //System.out.println("varName: " + f.getName());
            }
        }

        //creo un un oggetto container che contiene un textField con la sua label
        for(int  i = 0; i < variabiliNome.size(); i++) 
        {
            ElementsContainer container = new ElementsContainer();
            try {
                container.text  = (TextField) this.getClass().getField(variabiliNome.get(i)).get(this);
                container.label = (Label)     this.getClass().getField(LabelsNome.get(i)).get(this);
                //container.clearError();
                contenitori.add(container);
                //if(debug)System.out.println(container);
            } 
            catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
        }    
    }



    @SuppressWarnings("unchecked")
    public void validateNewUser() throws IOException 
    {
        JSONObject UserCostructor = new JSONObject();
        RegisteredAccount testAccount;
        boolean error = false;

        // ================================= 1° verifica ================================= //
        //Verifico se tutti i campi sono stati compilati

        for(int i = 0; i < contenitori.size(); i++) 
        {
            ElementsContainer container = contenitori.get(i);
            String data = container.text.getText();

            if(data == null || data.equals("")) {
                //container.setError("campo non compilato");
                error = true;
            }
            else if(!error) {
                String key = variabiliNome.get(i).toString();
                UserCostructor.put(key, data);
            }
        }

        if(error) {
            return;
        }
        
        // ================================= 2° verifica ================================= //
        //verifica password
        
        if(!password.getText().equals(password2.getText())) {
            contenitori.get(5).setError("Le password non coincidono");
            return;
        }

        // ================================= 3° verifica ================================= //
        //verifica provincia

        Province prov = this.application.locationsManager.FindProvince((String) UserCostructor.get("provincia"));
        if(prov == null) {

            return;
        }

        // ================================= 4° verifica ================================= //
        //verifica comune

        Common common = prov.findCommons((String) UserCostructor.get("comune"));
        if(common == null) {
            
            return;
        }

        // ================================= 5° verifica ================================= //
        //verifica validità email

        String email = (String) UserCostructor.get("email");
        String dominio = "@gmail.com";

        if(!email.endsWith(dominio) && (email.length() - dominio.length()) <= 0) {

            return;
        }


        // ================================= 6° verifica ================================= //
        //verifica esistenza account

        testAccount = new RegisteredAccount(UserCostructor);

        switch(application.AccountsManager.checkAccaunt(testAccount)) 
        {
            case 0:
                application.AccountsManager.appendData(testAccount);
                application.ConnectedAccount = testAccount;
                System.out.println("New Account added");

                Stage Window = (Stage) confirmButton.getScene().getWindow();
                super.SwitchScene("MainPage");
                break;
        
            //Email non valida
            case 1:

                break;

            //cUser ID non valido
            case 2:
                break;
        }
    }

    
    @FXML
    public void typed(KeyEvent event) 
    {
        for(ElementsContainer container : contenitori) {
            //container.clearError();
        }
    }

    @FXML
    public void TurnBack() throws IOException 
    {
        Stage Window = (Stage) BackButton.getScene().getWindow();
        super.SwitchScene("AccessPage");
    }
}
