package Java.Graphic_Interface;

import java.io.IOException;

import Java.emotionalsongs.EmotionalSongs;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Controller {
    
    protected EmotionalSongs application;

    // ========================= variabili =========================//
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
    }

    
    public FXMLLoader getScenePage(String name) throws IOException {
        return this.application.getNewStageWindow(name);
    }


    public void SwitchScene(Stage Window, String SceneName) throws IOException {
        this.application.changeScreen(Window, SceneName);
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
                return true;
            }

            return false;
        }

    }

}
