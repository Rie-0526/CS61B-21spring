package deque;
import org.junit.Test;

public class LLDmytest {

    @Test
    public void comprehensiveTest(){
        LinkedListDeque<Integer> lld1 = new LinkedListDeque();
        lld1.addFirst(1);
        lld1.addLast(2);
        lld1.addFirst(0);
        lld1.printDeque();
    }

//    public void main(){
//        comprehensiveTest();
//    }
}
