package Java.Graphic_Interface;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import org.json.simple.JSONObject;
import Java.Account.RegisteredAccount;
import Java.DataClasses.Common;
import Java.DataClasses.Province;
import Java.DataClasses.Region;
import Java.emotionalsongs.EmotionalSongs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
    //@FXML public TextField cap;
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

    @FXML private ComboBox<String> cap;
    @FXML private ComboBox<String> common;
    @FXML private ComboBox<String> province;

    private AutoCompleteComboBoxListener<String> c1;
    private AutoCompleteComboBoxListener<String> c2;
    private AutoCompleteComboBoxListener<String> c3;



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


    public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

        private ComboBox comboBox;
        private StringBuilder sb;
        private ObservableList<T> data;
        private boolean moveCaretToPos = false;
        private int caretPos;
    
        public AutoCompleteComboBoxListener(final ComboBox comboBox) {
            this.comboBox = comboBox;
            sb = new StringBuilder();
            data = comboBox.getItems();
    
            this.comboBox.setEditable(true);
            this.comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
    
                @Override
                public void handle(KeyEvent t) {
                    comboBox.hide();
                }
            });
            this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
        }
    
        @Override
        public void handle(KeyEvent event) {
    
            if(event.getCode() == KeyCode.UP) {
                caretPos = -1;
                moveCaret(comboBox.getEditor().getText().length());
                return;
            } else if(event.getCode() == KeyCode.DOWN) {
                if(!comboBox.isShowing()) {
                    comboBox.show();
                }
                caretPos = -1;
                moveCaret(comboBox.getEditor().getText().length());
                return;
            } else if(event.getCode() == KeyCode.BACK_SPACE) {
                moveCaretToPos = true;
                caretPos = comboBox.getEditor().getCaretPosition();
            } else if(event.getCode() == KeyCode.DELETE) {
                moveCaretToPos = true;
                caretPos = comboBox.getEditor().getCaretPosition();
            }
    
            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                    || event.isControlDown() || event.getCode() == KeyCode.HOME
                    || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
                return;
            }
    
            ObservableList list = FXCollections.observableArrayList();
            for (int i=0; i<data.size(); i++) {
                if(data.get(i).toString().toLowerCase().startsWith(
                    AutoCompleteComboBoxListener.this.comboBox
                    .getEditor().getText().toLowerCase())) {
                    list.add(data.get(i));
                }
            }
            String t = comboBox.getEditor().getText();
    
            comboBox.setItems(list);
            comboBox.getEditor().setText(t);
            if(!moveCaretToPos) {
                caretPos = -1;
            }
            moveCaret(t.length());
            if(!list.isEmpty()) {
                comboBox.show();
            }
        }
    
        private void moveCaret(int textLength) {
            if(caretPos == -1) {
                comboBox.getEditor().positionCaret(textLength);
            } else {
                comboBox.getEditor().positionCaret(caretPos);
            }
            moveCaretToPos = false;
        }

    }
    


    public NewUserRegistrationController() {
        super();
    }

    @SuppressWarnings("unchecked")
    private Queue<String> BucketSort(Queue<String> l, int lenght, int chars, int offset) 
    {
        Queue<String> [] bucket = (Queue<String> []) new LinkedList[chars];
        String w = "";
        int index = 1;


        //System.out.println(l);

        //ArrayList<String> result = new ArrayList<String>();
        
        
        for(int i = 0; i < chars; i++) {
            bucket[i] = new LinkedList<>();
        }

        for(int i = lenght - 1; i >= 0; i--) {
            while(!l.isEmpty()) 
            {
                w = l.poll();    //ottengo e rimuovo l'head

                //System.out.println("String: " + w);
                //System.out.println("index: " + i);
                //System.out.println((int) w.charAt(i));

                int e = (int) w.charAt(i) - offset;    //determina l’indice e della lista corrispondente alla i-esima lettera di w
                bucket[e].add(w);       //sposto la stringa

                //System.out.println("move " + w + " to bucket[" + e + "]");
            }

            /*if(index-- == 1) {
                for(Queue q : bucket) {
                    System.out.println(q);
                }
            }*/

            //sposto tutte le string in bucket[0] poi in l
            for(int j = 1; j < chars; j++ ) {
                while(bucket[j].size() > 0) 
                {
                    String str = bucket[j].poll();          //ottengo la string della j-esima lista 
                    //System.out.println("move " + w + " to bucket[" + j + "]");
                    bucket[0].add(str);                     //sposto la stringa
                }
            }
            System.out.println(bucket[0].size());
            l = new LinkedList<String>(bucket[0]);
            bucket[0].clear();
        }
        return l;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        EmotionalSongs main = EmotionalSongs.classReference;
        ArrayList<Region> regions = main.locationsManager.getList();

        c1 = new AutoCompleteComboBoxListener<>(cap);
        c2 = new AutoCompleteComboBoxListener<>(common);
        c3 = new AutoCompleteComboBoxListener<>(province);

        
        // reg --> province --> comm

        /*int element = 0;

        for(Region r : regions) { 
            for(Province p : r.getProvincesList()) {
                for(Common c : p.getCommonsList()) {
                    element += c.cap.length;
                }
            }
        }*/

        //int [] caps = new int [element--];

        final int minSize = 5;

        for(Region r : regions) { 
            for(Province p : r.getProvincesList()) {
                while(p.getName().length() < minSize) {
                    p.setName(p.getName() + " ");
                }
                province.getItems().add(p.getName());

                for(Common c : p.getCommonsList()) {
                    while(c.getName().length() < minSize) {
                        c.setName(p.getName() + " ");
                    }
                    common.getItems().add(c.getName());

                    for(int i = 0; i < c.cap.length; i++) {
                        cap.getItems().add(c.cap[i] + " : " + c.getName());
                    }
                }
            }
        }

        Queue<String> q;

        try {
        
            q = new LinkedList<>(province.getItems());
            //System.out.println(q.size());
            province.getItems().clear();
            province.getItems().addAll(BucketSort(q,3,256, 0));
            //System.out.println(province.getItems().size());
        
            q = new LinkedList<>(common.getItems());
            common.getItems().clear();
            common.getItems().addAll(BucketSort(q,3,256 - (int)' ', (int)' '));
        
            q = new LinkedList<>(cap.getItems());
            cap.getItems().clear();
            cap.getItems().addAll(BucketSort(q,5,10, (int)'0')); 

        } catch (Exception e) {
            System.out.println("======================================================================");
            System.out.println(e);
            System.out.println("======================================================================");
            e.printStackTrace();
            
        }
        
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

    @FXML
    void selectCap(ActionEvent event) {
        
        if(common.getSelectionModel().getSelectedItem() == null && province.getSelectionModel().getSelectedItem() == null) {
            String com = cap.getSelectionModel().getSelectedItem().split(" : ")[1];

            //metto il comune
            common.getSelectionModel().select(common.getItems().indexOf(com));

            EmotionalSongs main = EmotionalSongs.classReference;
            ArrayList<Region> regions = main.locationsManager.getList();
            boolean finded = false;

            for(Region r : regions) { 
                if(finded) break;
                
                for(Province p : r.getProvincesList()) {
                    if(finded) break;
                    
                    for(Common c : p.getCommonsList()) 
                    {
                        if(c.getName().equals(com)) {
                            province.getSelectionModel().select(province.getItems().indexOf(p.getName()));
                            finded = !finded;
                            break;
                        }
                    }
                }
            }
        }
    }

    @FXML
    void selectCommon(ActionEvent event) 
    {
        EmotionalSongs main = EmotionalSongs.classReference;
        ArrayList<Region> regions = main.locationsManager.getList();
        String com = common.getSelectionModel().getSelectedItem();

        boolean finded = false;

        //set CAP
        if(cap.getSelectionModel().getSelectedItem() == null) {
            for(Region r : regions) { 
                if(finded) break;

                for(Province p : r.getProvincesList()) {
                    if(finded) break;

                    for(Common c : p.getCommonsList()) 
                    {
                        if(c.getName().equals(com)) 
                        {
                            if(c.cap.length == 1) {
                                cap.getSelectionModel().select(c.cap[0] + " : " + c.getName());  
                            }

                            finded = !finded;
                            break;
                        }
                    }
                }
            }
        }

        //set Province
        if(province.getSelectionModel().getSelectedItem() == null) 
        {
            for(Region r : regions) { 
                for(Province p : r.getProvincesList()) {
                    for(Common c : p.getCommonsList()) 
                    {
                        if(c.getName().equals(com)) {
                            province.getSelectionModel().select(province.getItems().indexOf(p.getName()));
                            finded = !finded;
                            return;
                        }
                    }
                }
            }
        }
    }

    @FXML
    void selectProvince(ActionEvent event) {

    }
}
