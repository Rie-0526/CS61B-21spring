package gitlet;

// TODO: any imports you need here

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;

    /** The time of this Commit. */
    private Instant time;

    /** The hash value(commit file name) of parents of this Commit. */
    private ArrayList<String> parents = null;

    /** The map of file names to blob references */
    private HashMap<String, String> fileMap = null;

    /* TODO: fill in the rest of this class. */
    public void setMessage(String text){
        message = text;
    }

    public void setNowTime(){
        time = Instant.now();
    }

    public void setEpochTime(){
        time = Instant.ofEpochSecond(0);
    }

    public void setParents(ArrayList<String> p){
        for (String i : p) {
            parents.add(i);
        }
    }







}
