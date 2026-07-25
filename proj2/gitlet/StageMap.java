package gitlet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class StageMap implements Serializable {

    //
    private HashMap<String, String> fileStatus;

    public StageMap(){
        fileStatus = new HashMap<>();
    }

    /** Add a mapping of file or modify the mapping of existed file. */
    public void newMapping(String fileName, String status){
        fileStatus.put(fileName,status);
    }

    public String getStatus(String fileName) {
        return fileStatus.get(fileName);
    }

    public boolean containFilename(String filename) {
        return fileStatus.containsKey(filename);
    }

    public String removeMapping(String fileName) {
        return fileStatus.remove(fileName);
    }

    public Set<String> getFilenameSet() {
        return fileStatus.keySet();
    }


}
