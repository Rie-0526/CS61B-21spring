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

    public void modifyFile(String fileName, String status){
        fileStatus.put(fileName,status);
    }

    public String getStatus(String fileName) {
        return fileStatus.get(fileName);
    }

    public boolean containFilename(String filename) {
        return fileStatus.containsKey(filename);
    }

    public String removeFile(String fileName) {
        return fileStatus.remove(fileName);
    }

    public Set<String> getFilenameSet() {
        return fileStatus.keySet();
    }


}
