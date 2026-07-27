package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.*;

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




    public static final String STATUS_DELETE = "deleted";
    public static final String STATUS_NEW = "new";
    public static final String STATUS_MODIFY = "modified";



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


    private static File getStageMapFile(){
        return join(STORING_AREA, "stageMap");
    }

    private static StageMap getStageMapObject() {
        File stageMapFile = join(STORING_AREA, "stageMap");
        return Utils.readObject(stageMapFile, StageMap.class);
    }

    /** Return a Commit object of last commit. */
    private static Commit getLastCommit(){
        // Search for last Commit.
        String currentBranchPath = Utils.readContentsAsString(HEAD);
        File currentBranch = new File(currentBranchPath);
        String lastCommitName = Utils.readContentsAsString(currentBranch);
        File lastCommitFile = join(COMMITS_DIR, lastCommitName);
        Commit lastCommit = readObject(lastCommitFile, Commit.class);
        return lastCommit;

    }

    /** Return a hash value as string as the name of last commit. */
    private static String getLastCommitName(){
        String currentBranchPath = Utils.readContentsAsString(HEAD);
        File currentBranch = new File(currentBranchPath);
        String lastCommitName = Utils.readContentsAsString(currentBranch);
        return lastCommitName;
    }

    private static File getCurrentBranch() {
        String currentBranchPath = Utils.readContentsAsString(HEAD);
        return new File(currentBranchPath);
    }

    public static void add(String filename) throws IOException {

        // Search for the mapping of stage area.
        StageMap stageMap;
        File stageMapFile = getStageMapFile();
        if (stageMapFile.exists()){
            stageMap = readObject(stageMapFile, StageMap.class);
        }
        else {
            stageMapFile.createNewFile();
            stageMap = new StageMap();
        }

        Commit lastCommit = getLastCommit();

        // Search for the file in storing area.
        File storingAreaFile = join(SA_OBJECTS, filename);

        // Search for the contents of the file (of workspace).
        File workspaceFile = new File(filename);

        if (!workspaceFile.exists()) {
            if (lastCommit.containFilename(filename)){    // 文件相较于lc已消失（即删除）
                stageMap.newMapping(filename,STATUS_DELETE);
                storingAreaFile.delete();
            } else if (stageMap.containFilename(filename)) {
                stageMap.removeMapping(filename);
                storingAreaFile.delete();
            } else {
                System.out.println(filename + " doesn't exist!");
            }
            Utils.writeObject(stageMapFile, stageMap);
            System.exit(0);
        }


        String fileContent = Utils.readContentsAsString(workspaceFile);
        // Compute the hash value of the file passed in.
        String fileHashValue = sha1(serialize(fileContent));


        if (lastCommit.containFilename(filename)
            && fileHashValue.equals(lastCommit.getHashValue(filename))) {
                // 文件已被commit追踪
                // 且文件内容相对lc未更改：移除已存在映射，移除暂存区文件
            stageMap.removeMapping(filename);
            storingAreaFile.delete();

//            else {  // 文件内容相对lc已更改
//                //1.之前暂存区里有
//                //2.之前暂存区里没有
//                stageMap.addFile(filename,fileHashValue);
//                storingAreaFile.createNewFile();
//                Utils.writeContents(storingAreaFile, fileContent);
//            }
        }
        else {  //文件相较于last commit被添加/更改:
            // 在映射中添加/更改映射，在暂存区添加/更改文件

            stageMap.newMapping(filename, STATUS_NEW);

            storingAreaFile.createNewFile();
            Utils.writeContents(storingAreaFile, fileContent);

        }

        Utils.writeObject(stageMapFile, stageMap);  // 记得保存stageMap的更改

    }


    public static void commit(String message) throws IOException {
        StageMap stageMap = getStageMapObject();
        Commit newCommit = getLastCommit();

        /* 合并last commit和暂存区的映射 和 暂存区文件迁移： */
        for(String filename : stageMap.getFilenameSet()) {
            String status = stageMap.getStatus(filename);
            if (status.equals(STATUS_NEW)) {
                // 把暂存区文件迁移到objects：
                // 读取暂存区文件，在objects创建名字为hash的副本，暂存区文件删除
                // 并更新commit映射
               File stagingAreaFile = join(SA_OBJECTS, filename);
               String fileContent = Utils.readContentsAsString(stagingAreaFile);
               String fileHashValue = Utils.sha1(fileContent);

               File commitAreaFile = join(OBJECT_DIR,fileHashValue);
               commitAreaFile.createNewFile();
               Utils.writeContents(commitAreaFile, fileContent);

               stagingAreaFile.delete();

               newCommit.newMapping(filename,fileHashValue);
            }
            else if (status.equals(STATUS_DELETE)) {
                newCommit.removeMapping(filename);
            }
        }

        //删除暂存区的stagemap
        File stageMapFile = getStageMapFile();
        stageMapFile.delete();

        // 修改父提交
        newCommit.resetParents(getLastCommitName());

        //修改时间戳
        newCommit.setNowTime();

        //修改提交信息
        newCommit.setMessage(message);


        /* 存储new commit */
        // 获取commit的哈希值作为文件名
        String newCommitHashValue = sha1(serialize(newCommit));

        //在commits文件夹里新建commit哈希名文件
        File newCommitFile = join(COMMITS_DIR,newCommitHashValue);
        newCommitFile.createNewFile();
        Utils.writeObject(newCommitFile,newCommit);

        /* 更改当前分支指针 */
        File currentBranch = getCurrentBranch();
        Utils.writeContents(currentBranch, newCommitHashValue);

    }


    public static void rm(String filename) {

        File stageMapFile = getStageMapFile();  //对stageMap文件修改时使用
        File workspaceFile = new File(filename);
        File storingAreaFile = join(SA_OBJECTS, filename);

        StageMap stageMap = getStageMapObject();
        Commit lastCommit = getLastCommit();


        if ((!stageMap.containFilename(filename)) &&
                !lastCommit.containFilename(filename)) {
            System.out.println("No reason to remove the file.");
        }
        else {

            if (stageMap.containFilename(filename)) {       //文件在暂存区里
                stageMap.removeMapping(filename);
                storingAreaFile.delete();
            }

            if (lastCommit.containFilename(filename)){    // 文件被lc跟踪
                stageMap.newMapping(filename,STATUS_DELETE);
                workspaceFile.delete();
            }

            Utils.writeObject(stageMapFile, stageMap);  // 记得保存stageMap的更改

        }


    }


    /** Read from COMMIT_DIR */
    private static Commit readObjectAsCommit(String commitName) {
        File commitFile = join(COMMITS_DIR, commitName);
        return readObject(commitFile, Commit.class);
    }


    public static void log(){

        Commit curCommit = getLastCommit();
        String parentName = curCommit.getParent(0);

        while (parentName != null) {

            curCommit.print();

            File parentCommitFile = join(COMMITS_DIR, parentName);
            curCommit = Utils.readObject(parentCommitFile, Commit.class);
            parentName = curCommit.getParent(0);
        }

        curCommit.print();


    }


    public static void globalLog() {
        for (String commitName : plainFilenamesIn(COMMITS_DIR)) {
            Commit commit = readObjectAsCommit(commitName);
            commit.print();
        }
    }


    public static void find(String message) {
        boolean isFind = false;

        for (String commitName : plainFilenamesIn(COMMITS_DIR)) {
            Commit commit = readObjectAsCommit(commitName);
            if (message.equals(commit.getMessage())) {
                isFind = true;
                System.out.println(commitName);
            }
        }

        if (!isFind) {
            System.out.println("Found no commit with that message.");
        }
    }


    private static void printTitle(String title) {
        System.out.println("=== " + title + " ===");
    };

    public static void status() {
        File currentBranchFile = getCurrentBranch();
        String currentBranchName = currentBranchFile.getName();

        printTitle("Branches");
        if (currentBranchName.equals("master"))
            System.out.print('*');
        System.out.println("master");
        for (String branchName : Utils.plainFilenamesIn(BRANCH)) {
            if (branchName.equals("master"))
                continue;
            if (currentBranchName.equals(branchName))
                System.out.print('*');
            System.out.println(branchName);
        }
        System.out.println();

        printTitle("Staged Files");
        for (String filename : Utils.plainFilenamesIn(SA_OBJECTS)) {
            System.out.println(filename);
        }
        System.out.println();


        printTitle("Removed Files");
        StageMap stageMap = getStageMapObject();
        ArrayList<String> filelist = new ArrayList<>();

        for (String filename : stageMap.getFilenameSet()) {
            if (stageMap.getStatus(filename).equals(STATUS_DELETE)){
                filelist.add(filename);
            }
        }

        Collections.sort(filelist);
        for (String n : filelist) {
            System.out.println(n);
        }
        System.out.println();


        printTitle("Modifications Not Staged For Commit");
        Commit lc = getLastCommit();
        // stageMap 在前面代码块已载入
        TreeMap<String, String> filesAndStatus = new TreeMap<>();
        List<String> workspaceFileList = plainFilenamesIn(CWD);









//        HashSet<String> fileSet = new HashSet<>();
//        fileSet.addAll(workspaceFileList);
//        fileSet.addAll(lc.getFilenameSet());
//        fileSet.addAll(stageMap.getFilenameSet());
//
//        for (String filename : fileSet) {
//
//            File file = join(CWD, filename);
//            String WSFileHashValue;
//
//
//            /* Tracked in the current commit, changed in the working directory, but not staged; or
//                在当前提交中跟踪，在工作目录中已更改，但未暂存；或
//                Staged for addition, but with different contents than in the working directory; or
//                已暂存以添加，但内容与工作目录中的不同；或
//                Staged for addition, but deleted in the working directory; or
//                已暂存待添加，但在工作目录中被删除；或
//                Not staged for removal, but tracked in the current commit and deleted from the working directory.
//                未暂存以移除，但在当前提交中跟踪且已从工作目录中删除。 */
//
//            if (file.exists()) {
//                WSFileHashValue = sha1(readContentsAsString(file));
//                if (lc.containFilename(filename) && !lc.getHashValue(filename).equals(WSFileHashValue)
//                        && !stageMap.containFilename(filename))
//                {
//                    filesAndStatus.put(filename,STATUS_MODIFY);
//                }
//                // 满足前两项且stagemap里存在非删除状态的映射时，追踪暂存区内容是否不同
//                else if (lc.containFilename(filename) && !lc.getHashValue(filename).equals(WSFileHashValue)
//                        && !stageMap.getStatus(filename).equals(STATUS_DELETE)
//                        && WSFileHashValue.equals(sha1(readContentsAsString(join(SA_OBJECTS,filename)))))
//                {
//                    filesAndStatus.put(filename,STATUS_MODIFY);
//                }
//            }
//            else {
//                /* Staged for addition, but deleted in the working directory; or
//                已暂存待添加，但在工作目录中被删除；或
//                Not staged for removal, but tracked in the current commit and deleted from the working directory.
//                未暂存以移除，但在当前提交中跟踪且已从工作目录中删除。 */
//                if (stageMap.getStatus(filename).equals(STATUS_NEW)) {
//                    filesAndStatus.put(filename,STATUS_DELETE);
//                }
//                else if (lc.containFilename(filename)
//                        && stageMap.getStatus(filename).equals(STATUS_NEW)) {
//                    filesAndStatus.put(filename,STATUS_DELETE);
//                }
//            }
//        }


        for (String filename : lc.getFilenameSet()) {
            File WSfile = join(CWD, filename);
            //文件在工作区存在
            if (WSfile.exists()) {
                String WSfileHashValue = sha1(readContentsAsString(WSfile));
                if (!WSfileHashValue.equals(lc.getHashValue(filename))) { //文件内容和lc中不同
                    //暂存区已存储该文件,且为addition状态
                    if (stageMap.containFilename(filename) && stageMap.getStatus(filename).equals(STATUS_NEW)){
                        File SAfile = join(SA_OBJECTS, filename);
                        String SAfileHashValue = sha1(readContentsAsString(SAfile));
                        if (!SAfileHashValue.equals(WSfileHashValue)) {     //暂存区的文件内容与工作区不同
                            filesAndStatus.put(filename,STATUS_MODIFY);
                        }
                    }
                    //暂存区没有存储该文件
                    else if (!stageMap.containFilename(filename)){
                        filesAndStatus.put(filename,STATUS_MODIFY);
                    }
                    //暂存区已存储，且为removal状态：为未跟踪文件
                }
            }
            // 文件在工作区不存在
            else {
                if (stageMap.containFilename(filename)){    //如果保存在暂存区
                    if ( !stageMap.getStatus(filename).equals(STATUS_DELETE)) {     //如果没有记录成deleted状态
                        filesAndStatus.put(filename,STATUS_DELETE);
                    }
                }
                else {  //在暂存区没有保存
                    filesAndStatus.put(filename, STATUS_DELETE);
                }
            }
        }

        for (String filename : stageMap.getFilenameSet()) {
            switch (stageMap.getStatus(filename)) {
                case STATUS_NEW: {      //如果文件为添加状态，则查看文件是否删除，然后查看文件内容是否更改
                    File WSfile = join(CWD, filename);
                    if (WSfile.exists()) {
                        String WSfileHashValue = sha1(readContentsAsString(WSfile));
                        File SAfile = join(SA_OBJECTS, filename);
                        String SAfileHashValue = sha1(readContentsAsString(SAfile));
                        if (!SAfileHashValue.equals(WSfileHashValue)) {     //暂存区的文件内容与工作区不同
                            filesAndStatus.put(filename,STATUS_MODIFY);
                        }
                    }
                    else {
                        filesAndStatus.put(filename,STATUS_DELETE);
                    }
                }break;
                case STATUS_DELETE:
                    break;
            }
        }



        for (String filename : filesAndStatus.sequencedKeySet()) {
            System.out.print(filename);
            System.out.println('(' + filesAndStatus.get(filename) + ')');
        }

        System.out.println();


        printTitle("Untracked Files");
        for (String filename : workspaceFileList) {
            if (!lc.containFilename(filename) && !stageMap.getStatus(filename).equals(STATUS_NEW)) {
                System.out.println(filename);
            }
        }
        System.out.println();



    }


}
