package deque;
import org.junit.Test;

// 你们不要再看这个测试了，这个测试，这个测试
// 全是我写的。。

public class ArrayDequeTest {

    @Test
    public void createABlankList(){
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.printDeque();

    }


    @Test
    public void onlyAddLast(){
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addLast(0);
        ad.addLast(1);
        ad.addLast(2);
        ad.printDeque();
    }

    @Test
    public void onlyAddFirst() {
        ArrayDeque<Integer> ad = new ArrayDeque<>(4);
        ad.addFirst(3);
        ad.addFirst(2);
        ad.addFirst(1);
        ad.printDeque();
    }

    @Test
    public void onlyAddFirstAndAddLast() {
        ArrayDeque<Integer> ad = new ArrayDeque<>(4);
        ad.addFirst(3);
        ad.addLast(5);
        ad.addFirst(2);
        ad.addLast(6);
        ad.addFirst(1);
        ad.printDeque();
    }

    @Test
    public  void onlyAddLastResize() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < 15; i++){
            ad.addLast(i);
        }
        ad.printDeque();
    }

    @Test
    public  void onlyAddFirstResize() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 14; i >= 0; i--){
            ad.addFirst(i);
        }
        ad.printDeque();
    }

    @Test
    public void AddFirstAndAddLastResize() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 10, j = i + 1; i > 0; i--, j++){
            ad.addFirst(i);
            ad.addLast(j);
        }
        ad.printDeque();
    }
}
