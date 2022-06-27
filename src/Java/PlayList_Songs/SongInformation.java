package Java.PlayList_Songs;

import java.util.ArrayList;
import Java.DataClasses.Comment;
import Java.emotionalsongs.Emotion;

public class SongInformation {

    protected ArrayList<Emotion> emozioni = new ArrayList<Emotion>();
    protected Comment comment;
    protected Song song;


    // ====================== Costruttori ====================== //
    //1° costruttore
    public SongInformation() {

    }

    //2° costruttore
    public SongInformation(Song song) {
        this.song = song;
    }

    //3° costruttore
    public SongInformation(Song song, Comment comment) {
        this.comment = comment;
        this.song = song;
    }

    //4° costruttore
    public SongInformation(ArrayList<Emotion> emozioni, Song song, Comment comment) {
        this.emozioni = emozioni;
        this.comment = comment;
        this.song = song;
    }

    

    // ====================== funzioni ====================== //
    // ... significa numero parametri variabile
    public void AddEmotion(Emotion... emotion) {
        for (Emotion e : emotion) 
        {
            //verifico se è già presente
            if(!this.emozioni.contains(e)) {
                this.emozioni.add(e);
            }
            
        }
    }


    @Override
    public String toString() {
        String Information = "";

        Information += "Informazioni canzone utente:\n";
        Information += "\t- conzone: \n\t" + this.song.getTitle() + "\n\n";
        Information += "\t- Emozioni:\n";

        for(Emotion e : this.emozioni) {
            Information += "\t\t" + e.toString() + "\n";
        }

        Information += "\n\t- Commento:\n" + getComment() + "\n\n";

        return Information;
    }


    public void removeEmotion(Emotion emotion) {
        if(!this.emozioni.contains(emotion)) {
            this.emozioni.remove(emotion);
        }
    }

    
    public void RemoveAllEmotions() {
        this.emozioni.clear();
    }

    







    // ====================== getters & setters ======================//

    public ArrayList<Emotion> getEmozioni() {
        return emozioni;
    }

    public void setEmozioni(ArrayList<Emotion> emozioni) {
        this.emozioni = emozioni;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public String getComment() {
        return comment.getComment();
    }

    public boolean SetComment(String comment, boolean force) {
        if(force) {
            return this.comment.forceSetComment(comment); 
        }
        else {
            return this.comment.setComment(comment);
        }
    }

}
