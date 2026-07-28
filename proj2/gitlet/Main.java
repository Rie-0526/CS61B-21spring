package gitlet;

import java.io.IOException;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) throws IOException {
        // TODO: what if args is empty?
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                Repository.init();
                break;
            case "add": {
                // TODO: handle the `add [filename]` command
                String filename = args[1];
                Repository.add(filename);
            } break;
            // TODO: FILL THE REST IN
            case "commit": {
                if (args.length == 1)   {
                    System.out.println("Please enter a commit message.");
                    System.exit(0);
                }
                String message = args[1];
                Repository.commit(message);
            } break;
            case "rm": {
                String filename = args[1];
                Repository.rm(filename);
            } break;
            case "log": {
                Repository.log();
            } break;
            case "global-log": {
                Repository.globalLog();
            } break;
            case "find": {
                String message = args[1];
                Repository.find(message);
            } break;
            case "status": {
                Repository.status();
            } break;
            case "branch": {
                String branchname = args[1];
                Repository.branch(branchname);
            } break;
            case "checkout": {
                switch (args.length) {
                    case 3:{
                        String filename = args[2];
                        Repository.checkout(filename);
                    }break;
                    case 4:{
                        String hashID = args[1];
                        String filename = args[3];
                        Repository.checkout(hashID, filename);
                    }break;
                    case 2:{
                        String branchname = args[1];
                        Repository.checkoutBranch(branchname);
                    }break;
                }
            } break;
        }
    }
}
