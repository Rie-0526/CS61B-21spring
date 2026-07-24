package gitlet;

import java.io.Serializable;
import java.util.HashMap;

public class Stage implements Serializable {

    private HashMap<String, String> fileMap;

    public Stage(){
        fileMap = new HashMap<>();
    }

    public void addFile(String fileName, String hashValue){
        fileMap.put(fileName,hashValue);
    }

    public String getHashValue(String fileName) {
        return fileMap.get(fileName);
    }

    public boolean containFilename(String filename) {
        return fileMap.containsKey(filename);
    }

    public String removeFile(String fileName) {
        return fileMap.remove(fileName);
    }


}
