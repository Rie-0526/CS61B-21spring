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

    @Test
    public void getFunctionTest(){
        LinkedListDeque<Double> lld1 = new LinkedListDeque(1.0);
        lld1.addFirst(0.5);
        lld1.addLast(2.0);
        lld1.addFirst(0.0);
        lld1.printDeque();

        System.out.println(lld1.get(0));
        System.out.println(lld1.get(3));

    }

    @Test
    public void getRecursiveFunctionTest(){
        LinkedListDeque<Double> lld1 = new LinkedListDeque(1.0);
        lld1.addFirst(0.5);
        lld1.addLast(2.0);
        lld1.addFirst(0.0);
        lld1.printDeque();

        System.out.println(lld1.getRecursive(0));
        System.out.println(lld1.getRecursive(3));

    }

    @Test
    public void IteratorTest() {
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        for (int i = 4, j = i + 1; i > 0; i--, j++){
            lld.addFirst(i);
            lld.addLast(j);
        }
        lld.printDeque();
        System.out.println();

        for (int w : lld) {
            System.out.println(w);
        }
    }
}
