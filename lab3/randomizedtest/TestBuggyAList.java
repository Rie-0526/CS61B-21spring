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

    //don't be written by student
    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> L2 = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                L2.addLast(randVal);
//                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int size2 = L2.size();
                assertEquals(size, size2);
//                System.out.println("size: " + size);
            } else if (operationNumber == 2) {
                //getLast
                if(L.size() == 0)   continue;
                int num = L.getLast();
                int num2 = L2.getLast();
                assertEquals(num, num2);
//                System.out.println("last number: " + num);
            } else if (operationNumber == 3) {
                //removeLast
                if(L.size() == 0)   continue;
                int num = L.removeLast();
                int num2 = L2.removeLast();
                assertEquals(num,num2);
//                System.out.println("remove: " + num);
            }
        }
    }
}

