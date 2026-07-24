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
    public static final File OBJECT_DIR = join(CWD, "object");
    /** The commits directory. */
    public static final File COMMITS_DIR = join(CWD, "commits");
    /** The storing area directory */
    public static final File STORING_AREA = join(CWD, "storingArea");

    public static final File HEAD = join(CWD, "HEAD");
    /** The branch directory */
    public static final File BRANCH = join(CWD, "branch");
    /** The master file of branch directory */
    public static final File master = join(BRANCH, "master");


    /* TODO: fill in the rest of this class. */
    public void setGitletDir() {
        GITLET_DIR.mkdir();
        OBJECT_DIR.mkdir();
        COMMITS_DIR.mkdir();
        STORING_AREA.mkdir();
        BRANCH.mkdir();
    }



    public void init() throws IOException {

        Commit initialCommit = new Commit();
        initialCommit.setMessage("initial commit");
        initialCommit.setEpochTime();
        initialCommit.setParents(null);

        String ICfileName = sha1(initialCommit);
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


}
