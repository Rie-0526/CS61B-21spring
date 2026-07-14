package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing<Integer> normallist = new AListNoResizing<>();
        BuggyAList<Integer> buggylist = new BuggyAList<>();
        for (int i = 0; i < 3; i++){
            normallist.addLast(i);
            buggylist.addLast(i);
        }
        for (int i = 0; i < 3; i++){
            assertEquals(normallist.removeLast(), buggylist.removeLast());
        }

    }

}

