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
    private ArrayList<String> parents = new ArrayList<>();;

    /** The map of file names to blob references */
    private HashMap<String, String> fileMap = new HashMap<>();

    /* TODO: fill in the rest of this class. */

    public Commit() {};

    public void setMessage(String text){
        message = text;
    }

    public void setNowTime(){
        time = Instant.now();
    }

    public void setEpochTime(){
        time = Instant.ofEpochSecond(0);
    }

    // p is a hash value of parent commit.
    public void resetParents(String p) {
        parents.clear();
        parents.add(p);
    }
    public void addParents(String p){
        parents.add(p);
    }


    /** Add a mapping of file or modify the mapping of existed file. */
    public void newMapping(String fileName, String hashValue){
        fileMap.put(fileName,hashValue);
    }

//    public void modifyMapping(String fileName, String hashValue){
//        /* add Or Modify Mapping Of Existed File */
//        fileMap.put(fileName,hashValue);
//    }

    public void removeMapping(String filename) {
        fileMap.remove(filename);
    }

    public String getHashValue(String fileName) {
        return fileMap.get(fileName);
    }

    public boolean containFilename(String filename) {
        return fileMap.containsKey(filename);
    }







}
