package Java.DataClasses;

import java.util.ArrayList;

import Java.PlayList_Songs.Song;
import Java.emotionalsongs.Emotion;

public class Contenitore {
    Commento comm;
    Song canzone;
    ArrayList<Emotion> emozioni=new ArrayList<Emotion>();
    

    public Contenitore(Commento comm,Song canzone,ArrayList<Emotion>emozioni){
        this.comm=comm;
        this.canzone=canzone;
        this.emozioni=emozioni;
    }

    void add_emozioni(ArrayList<Emotion>emozioni,Emotion emozione){
        emozioni.add(emozione);
    }
}
