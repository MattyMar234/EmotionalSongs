package Java.Graphic_Interface;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Java.Account.RegisteredAccount;
import Java.PlayList_Songs.PlayList;
import Java.PlayList_Songs.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;


public class MainPageController_playList extends Controller implements Initializable {

    // ========================= TableView ========================= //
    @FXML private TableView<CustomPlayList> PlaylistsTable;
    @FXML private TableColumn<CustomPlayList, String> PlayListElements;
    @FXML private TableColumn<CustomPlayList, String> PlayListName;
    @FXML private TableColumn<CustomPlayList, String> DataCreazione;
    @FXML private TableColumn<CustomPlayList, String> DeletePlayList;
    @FXML private TableColumn<CustomPlayList, CustomPlayList> EditPlayList;

    private ObservableList<CustomPlayList> userPlayLists = FXCollections.observableArrayList();

    // ========================= Label ========================= //
    @FXML private Label LabelPlaylist;
    

    // ========================= Buttons ========================= //
    @FXML private Button NewPlaylistButton;


    // ========================= variabili =========================//
    public MainPageController mainController;


    public class CustomPlayList 
    {
        public PlayList playList;
        public MainPageController_playList MainclassReference;
        public CustomPlayList classReference;
        public String nome;
        public String CreationDate;
        public String Elements;


        public CustomPlayList(PlayList playList, MainPageController_playList classReference) {
            this.MainclassReference = classReference;
            this.playList = playList;
            this.nome = this.playList.getNome();
            this.Elements = this.playList.getElements();
            this.CreationDate = this.playList.getCreationDate();
            this.classReference = this;
        }

        public PlayList getPlayList() {
            return this.playList;
        }

        public MainPageController_playList getMainclassReference() {
            return this.MainclassReference;
        }

        public CustomPlayList getClassReference() {
            return this;
        }

        public String getElements() {
            return Elements;
        }

        public String getCreationDate() {
            return CreationDate;
        }

        public String getNome() {
            return nome;
        }
    }
 
    
    public MainPageController_playList() throws IOException {
        super();
        
    }

    public void SetMainControllerReference(MainPageController mainController) {
        this.mainController = mainController;
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        if(application.ConnectedAccount == null) return;
        //System.out.println("loding playlists data...");

        if(application.ConnectedAccount instanceof RegisteredAccount) 
        {
            for(PlayList playlist : ((RegisteredAccount) application.ConnectedAccount).getPlayLists()) {
                userPlayLists.add(new CustomPlayList(playlist, this));

                //System.out.println(playlist);
            }

            PlayListName.setCellValueFactory(new PropertyValueFactory<CustomPlayList, String>("nome"));
            PlayListElements.setCellValueFactory(new PropertyValueFactory<CustomPlayList, String>("Elements"));
            DataCreazione.setCellValueFactory(new PropertyValueFactory<CustomPlayList, String>("CreationDate"));

            Callback<TableColumn<CustomPlayList, CustomPlayList>, TableCell<CustomPlayList, CustomPlayList>> cellFoctory = (TableColumn<CustomPlayList, CustomPlayList> param) -> {
                final TableCell<CustomPlayList, CustomPlayList> cell = new TableCell<CustomPlayList, CustomPlayList>() {
                    
                    @Override
                    public void updateItem(CustomPlayList item, boolean empty) {
                        super.updateItem(item, empty);
                        //that cell created only on non-empty rows
                        if (empty) {
                            setGraphic(null);
                            setText(null);

                        } else {

                            //crash ??
                            //FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                            //FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                            Image image1;
                            Image image2;

                            try {
                                image1 = new Image(new FileInputStream("data\\image\\trash.png"));
                                image2 = new Image(new FileInputStream("data\\image\\edit.png"));  
                                
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                return;
                            }
                            
                            
                            ImageView deleteIcon = new ImageView(image1); 
                            ImageView editIcon = new ImageView(image2); 

                            deleteIcon.setFitHeight(24); 
                            deleteIcon.setFitWidth(24);
                            editIcon.setFitHeight(24); 
                            editIcon.setFitWidth(24);

                            Button deleteButton = new Button();
                            Button editButton = new Button();


                            deleteButton.setGraphic(deleteIcon);
                            editButton.setGraphic(editIcon);


                        
                            deleteButton.setStyle(
                                    " -fx-cursor: hand ;"
                                    + "-fx-background-color:#ff00008e;"
                                    + "-fx-border-width: 1;"
                                    + "-fx-border-color: #E74C3C;"
                                    + "-fx-border-radius: 10px;"
                                    + "-fx-padding: 8 8 8 8;"
                                    + "-fx-border-insets: 10px;"
                                    + "-fx-background-insets: 10px;"
                                    
                            );
                            editButton.setStyle(
                                    " -fx-cursor: hand ;"
                                    + "-fx-background-color:#11db0aa4;"
                                    + "-fx-border-width: 1;"
                                    + "-fx-border-color: #2ECC71;"
                                    + "-fx-border-radius: 10px;"
                                    + "-fx-padding: 8 8 8 8;"
                                    + "-fx-border-insets: 10px;"
                                    + "-fx-background-insets: 10px;"

                            );


                            deleteButton.setOnMouseClicked((MouseEvent event) -> {
                                
                                //PlayList playlist = PlaylistsTable.getSelectionModel().getSelectedItem();
                                //funziona se solo prima selezioni l'elemento
                                
                                ((RegisteredAccount)application.ConnectedAccount).remove_playlist(item.getPlayList());
                                userPlayLists.remove(item);
                                PlaylistsTable.refresh();

                                //System.out.println(item); //item è il riferimento della playlist
 
                            });

                            editButton.setOnMouseClicked((MouseEvent event) -> {
                                
                                PlayList p = item.getPlayList();
                                //System.out.println(l);
                                
                                try {
                                    item.MainclassReference.mainController.SetPlaylistEditPage(p, item.MainclassReference.mainController);
                                
                                    //System.out.println(item.getPlayList());
                                } catch (IOException e) {
                                    System.out.println(e);
                                    e.printStackTrace();
                                }
                            });

                            
                            HBox managebtn = new HBox(deleteButton, editButton);
                            managebtn.setStyle("-fx-alignment:center");
                            HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                            HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                            setGraphic(managebtn);
                            setText(null);

                        }
                    }

                };

                return cell;
            };

            //call getReference ???
            EditPlayList.setCellValueFactory(new PropertyValueFactory<CustomPlayList, CustomPlayList>("classReference"));
            EditPlayList.setCellFactory(cellFoctory);

            PlaylistsTable.setItems(userPlayLists);

        }

    }


    @FXML
    public void access(MouseEvent event) throws IOException {
        //super.SwitchScene((Stage) icon.getScene().getWindow(), "LoadAccaunt");
     
    }

    @FXML
    public void elementSelected(MouseEvent event) throws Exception 
    {        
        if(state == 0) {
            selected1 = PlaylistsTable.getSelectionModel().getSelectedItem();
        }
        else if (state == 1) {
            selected2 = PlaylistsTable.getSelectionModel().getSelectedItem();
        }

        if(doubleClick() && (selected2 == selected1) && selected1 instanceof Song) {
            //System.out.println("song selected");
            //SongWindow windowSong = new SongWindow(application, (Song) selected1);
        }
        else if(doubleClick() && (selected2 == selected1) && selected1 instanceof PlayList) {
            //System.out.println("song selected");
            //SongWindow windowSong = new SongWindow(application, selected1);
        }

    }

    @FXML
    public void Selezione(ActionEvent event) {
        
        
    }

    @FXML
    void AddNewPlayList(ActionEvent event) throws IOException {
        mainController.NewPlaylistPage();
    }

    
    

}




