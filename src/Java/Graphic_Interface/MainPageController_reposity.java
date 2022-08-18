package Java.Graphic_Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Java.PlayList_Songs.PlayList;
import Java.PlayList_Songs.Song;
import Java.PlayList_Songs.SongWindow;
import Java.emotionalsongs.EmotionalSongs;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

public class MainPageController_reposity extends Controller implements Initializable 
{

    // ========================= variabili =========================//
    @FXML private TextField KeywordTextField;
    @FXML private TableView<Container> SongsTable;
    @FXML private TableColumn<Container, Container> Elements;
    
    @FXML private Button CambioButton;
    @FXML private Button playlistButton;
    @FXML private Button profileButton;
    @FXML private Button optionsButton;
    @FXML private Button ExitButton;
    @FXML private Label SerachLabel;
    @FXML private ComboBox<FilterState> filter;

    private ObservableList<Container> list = FXCollections.observableArrayList();
    public MainPageController mainController;
    private int counter = 0;


    private enum FilterState {
        
        ANNO_CRESCENTE, 
        ANNO_DECRESCENTE, 
        COMMENTI_CRESCENTE, 
        COMMENTI_DECRESCENTE, 
        EMOZIONI_CRESCENTE,
        EMOZIONI_DECRESCENTE;

        @Override
        public String toString() {
            String s = super.toString();
            s = "Per " + s.toString().toLowerCase().replace("_", " ");
            
            return s;
        }
    }


    public class Container {

        public Song song;
        public MainPageController_reposity mainController;
        public Container classReference;

        public Container(Song song, MainPageController_reposity mainController) {
            this.song = song;
            this.mainController = mainController;
            classReference = this;
        } 

        public Container getClassReference() {
            return this;
        } 
    }


    // ============================================================ //

    public MainPageController_reposity() throws IOException {
        super();
    }

    @FXML
    void scroll(ScrollEvent event) {
        /*System.out.println("here");
        System.out.println(event.getDeltaX());*/
    }

    


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        for(Song song : application.songManager.getList()) {
            list.add(new Container(song, this));
        }


        /*for(FilterState e : FilterState.values()) {
            //filter.getItems().add("Per " + e.toString().toLowerCase().replace("_", " "));  
        }*/

        filter.getItems().addAll(FilterState.values());
        filter.setValue(filter.getItems().get(0));

        //ScrollBar verticalBar = (ScrollBar) SongsTable.lookup(".scroll-bar:vertical");
        //verticalBar.setValue(0.49);

        //disabilito lo scroll con la rotella
        /*SongsTable.addEventFilter(ScrollEvent.ANY, event -> {
            int d = (int)event.getDeltaY();

            SongsTable.scrollTo(((int)event.getY() + d/10)/100);
            event.consume();
        });

        
        SongsTable.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            
            @Override
            public void handle(ScrollEvent scrollEvent) {
                System.out.println("Scrolled.");
                System.out.println(scrollEvent.getDeltaY());
               ;
               //SongsTable.scrollTo();
            }
     });*/

     


        Callback<TableColumn<Container, Container>, TableCell<Container, Container>> cellFoctory = (TableColumn<Container, Container> param) -> {
            final TableCell<Container, Container> cell = new TableCell<Container, Container>() {
                
                @Override
                public void updateItem(Container item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } 
                    else 
                    {
                        try 
                        {
                            //carico la pagina
                            FXMLLoader XMLloader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/test.fxml"));
                            
                            /*XMLloader.setControllerFactory(c -> {    
                                return new RepositorySongElementController(item); // <-- parametri costruttore classe
                            });*/
                            
                            AnchorPane view = XMLloader.load();

                            //carico i parametri
                            RepositorySongElementController controller = XMLloader.getController();
                            controller.injectData(item.mainController, item.song);
                            //System.out.println(item);

                            setGraphic(view);
                        
                        } catch (IOException e) {
                            System.out.println(e);
                        }  
                        setText(null);
                    }
                }
            };
            return cell;
        };

        Elements.setCellValueFactory(new PropertyValueFactory<Container, Container>("classReference"));
        Elements.setCellFactory(cellFoctory);

        
        FilteredList<Container> filteredData = new FilteredList<Container>(list, b -> true);
        
        KeywordTextField.textProperty().addListener((Observable, oldValue, newValue) -> {
            filteredData.setPredicate(item -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                try {
                    String lowerCaseFilter = newValue.toLowerCase();
                    //varie chiavi (campi) su cui ricercare
                    if(item.song.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } 
                    /*else if(item.song.getAlbum().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }*/
                    else if(item.song.getAutor().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    /*else if(item.song.getDuration().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }*/
                    else if(item.song.getYear().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                } catch (NullPointerException e) {
                    return false;
                }
                return false;
            });  
        });
        
        SortedList<Container> sortedData = new SortedList<Container>(filteredData);
        sortedData.comparatorProperty().bind(SongsTable.comparatorProperty());
        SongsTable.setItems(sortedData);
  
    }

    @FXML
    public void elementSelected(MouseEvent event) throws Exception 
    {        
        
    }

    @FXML
    public void Selezione(ActionEvent event) {
        System.out.println("pressed");

    }

    private int sortGetData(FilterState F, Song S) {
        switch (F) 
        {
            case ANNO_CRESCENTE, ANNO_DECRESCENTE:
                return Integer.parseInt(S.getYear());

            case COMMENTI_DECRESCENTE, COMMENTI_CRESCENTE:
                return S.getComments().size();
      

            case EMOZIONI_CRESCENTE, EMOZIONI_DECRESCENTE:
                return S.getEmotions().size();

            default: return 0;
        }
    }

    @FXML
    void update(ActionEvent event) {
        
        FilterState state = filter.getValue();
        System.out.println(state);

        int N = list.size();
        int min = sortGetData(state, list.get(0).song);
        int max = sortGetData(state, list.get(0).song);
        int range;
        int count[];

        Container a[] = new Container[list.size()];

        int counter = 0;
        for(Container c : list) {
            a[counter++] = c;
        }

        for(Container c : a) 
        {
            int v = sortGetData(state, c.song);
            
            if(v <  min) {
                min = v;
            }
            else if(v > max) {
                max = v;
            }
        }

        range = max - min + 1;

        count = new int[range];
        for(int i = 0; i < range; i++) count[i] = 0;

        //freq aasolute
        for(int i = 0; i < N; i++) count[sortGetData(state, a[i].song) - min]++;
        
        //freq culumalte
        for(int i = 1; i < range; i++) count[i] += count[i - 1];


        Container [] b = new Container[N];
        

        if (state == FilterState.ANNO_CRESCENTE || state == FilterState.COMMENTI_CRESCENTE || state == FilterState.EMOZIONI_CRESCENTE) {
            for(int i = 0; i < N; i++) {
                int freq = count[sortGetData(state, a[i].song) - min]--;
                b[N - (freq - 1) - 1] = a[i];
            }
        }
        else {
            for(int i = 0; i < N; i++) {
                int freq = count[sortGetData(state, a[i].song) - min]--;
                b[(freq - 1)] = a[i];
            }
        }

        

        list.clear();
        list.addAll(b);

        



        

        
    }

}
