package Java.Graphic_Interface;

import java.io.IOException;
import java.awt.*;
import Java.emotionalsongs.EmotionalSongs;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public abstract class Controller 
{
    // ========================= variabili =========================//
    protected EmotionalSongs application;
    protected Dimension screenSize;  //java.awt.Toolkit


    //variabili doppio click  
    protected final float maxDt = 0.320f;   //320ms
    protected float fistclick;
    protected float secondClick;
    protected int state = 0;
    protected Object selected1;
    protected Object selected2;

     //variabili finestra (stage)
    protected double windwoPosX;
    protected double windwoPosY;
    protected double windwoPosWidth;
    protected double windwoPosHeight;
    

    public Controller() {
        this.application = EmotionalSongs.classReference;

        this.windwoPosX = application.mainStage.getX();
        this.windwoPosY = application.mainStage.getY();
        this.windwoPosWidth  = application.mainStage.getWidth();
        this.windwoPosHeight = application.mainStage.getHeight();

        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }

    
    public FXMLLoader getScenePage(String name) throws IOException {
        return this.application.getNewStageWindow(name);
    }


    public void SwitchScene(String SceneName) throws IOException {
        //this.application.changeScreen(Window, SceneName);
        AnchorPane view = getScenePage(SceneName).load();
        
        this.application.windowPageReference.anchor.getChildren().removeAll();
        this.application.windowPageReference.anchor.setCenter(view);
    }


    protected boolean doubleClick()
    {
        if(state == 0) {
            this.fistclick = System.nanoTime();
            state++;

            return false;
        }
        else {
            this.secondClick = System.nanoTime();
            state = 0;

            float dt = secondClick - fistclick; //ns
            //System.out.println("dt = " + dt + " ns");
            
            //converto da nano a secondi
            dt = (float) (dt/Math.pow(10, 9));

            //System.out.println("dt = " + dt + " s"); //s
            //System.out.println("dtmx = " + maxDt + " s"); //s
            //System.out.println("" + (this.maxDt - dt));
           
            if(this.maxDt - dt >= 0.0) {
                //se per caso ho cliccato prima
                return true;
            }

            if(dt >= 1.800) {
                return doubleClick();
            }
            else {
                return true;
            }
        }

    }

}
