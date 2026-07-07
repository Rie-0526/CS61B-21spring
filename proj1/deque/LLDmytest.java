package deque;
import org.junit.Test;

public class LLDmytest {

    @Test
    public void comprehensiveTest(){
        LinkedListDeque<Double> lld1 = new LinkedListDeque(1.0);
        lld1.addFirst(0.5);
        lld1.addLast(2.0);
        lld1.addFirst(0.0);
        lld1.printDeque();

        lld1.removeFirst();
        lld1.printDeque();

        lld1.removeLast();
        lld1.printDeque();

    }

    @Test
    public void removeNodeOfSingleList(){
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>(1);
        lld1.removeLast();
        lld1.printDeque();
    }

    @Test
    public void removeNodeOfVoidList(){
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.removeLast();
        lld1.printDeque();
    }

//    public void main(){
//        comprehensiveTest();
//    }
}
