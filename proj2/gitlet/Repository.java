package gitlet;

import java.io.File;
import java.io.IOException;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /** The object directory. */
    public static final File OBJECT_DIR = join(GITLET_DIR, "object");
    /** The commits directory. */
    public static final File COMMITS_DIR = join(GITLET_DIR, "commits");
    /** The storing area directory */
    public static final File STORING_AREA = join(GITLET_DIR, "storingArea");
    public static final File SA_OBJECTS = join(STORING_AREA, "objects");

    public static final File HEAD = join(GITLET_DIR, "HEAD");
    /** The branch directory */
    public static final File BRANCH = join(GITLET_DIR, "branch");
    /** The master file of branch directory */
    public static final File master = join(BRANCH, "master");


    /* TODO: fill in the rest of this class. */

    private static void setGitletDir() {
        GITLET_DIR.mkdir();
        OBJECT_DIR.mkdir();
        COMMITS_DIR.mkdir();
        STORING_AREA.mkdir();
        SA_OBJECTS.mkdir();
        BRANCH.mkdir();
    }

    public static void init() throws IOException {

        Commit initialCommit = new Commit();
        initialCommit.setMessage("initial commit");
        initialCommit.setEpochTime();

        String ICfileName = sha1(serialize(initialCommit));
        File ICfile = join(COMMITS_DIR, ICfileName);


        if(ICfile.exists() && HEAD.exists() && master.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }

        setGitletDir();
        ICfile.createNewFile();
        Utils.writeObject(ICfile, initialCommit);
        master.createNewFile();
        Utils.writeContents(master, ICfileName);
        HEAD.createNewFile();
        Utils.writeContents(HEAD, master.getPath());

    }


    public static void add(String filename) throws IOException {

        // Search for the mapping of stage area.
        Stage stageMap;
        File stageMapFile = join(STORING_AREA, "stageMap");
        if (stageMapFile.exists()){
            stageMap = readObject(stageMapFile, Stage.class);
        }
        else {
            stageMapFile.createNewFile();
            stageMap = new Stage();
        }

        // Search for last Commit.
        String currentBranchPath = Utils.readContentsAsString(HEAD);
        File currentBranch = new File(currentBranchPath);
        String lastCommitName = Utils.readContentsAsString(currentBranch);
        File lastCommitFile = join(COMMITS_DIR, lastCommitName);
        Commit lastCommit = readObject(lastCommitFile, Commit.class);

        // Search for the contents of the file (of workspace).
        File workspaceFile = new File(filename);
        String fileContent = Utils.readContentsAsString(workspaceFile);
        // Compute the hash value of the file passed in.
        String fileHashValue = sha1(serialize(fileContent));

        // Search for the file in storing area.
        File storingAreaFile = join(SA_OBJECTS, filename);

        if (lastCommit.containFilename(filename)){  // 文件已被commit追踪
            if (fileHashValue == lastCommit.getHashValue(filename)){    //文件内容相对lc未更改：移除已存在映射，移除暂存区文件
                stageMap.removeFile(filename);
                storingAreaFile.delete();
            }
//            else {  // 文件内容相对lc已更改
//                //1.之前暂存区里有
//                //2.之前暂存区里没有
//                stageMap.addFile(filename,fileHashValue);
//                storingAreaFile.createNewFile();
//                Utils.writeContents(storingAreaFile, fileContent);
//            }
        }
        else {  //文件未被commit追踪（即添加/更改文件）: 在映射中添加/更改映射，在暂存区添加/更改文件

            stageMap.addFile(filename, fileHashValue);

            storingAreaFile.createNewFile();
            Utils.writeContents(storingAreaFile, fileContent);

        }

        Utils.writeObject(stageMapFile, stageMap);  // 记得保存stageMap的更改

    }




}
