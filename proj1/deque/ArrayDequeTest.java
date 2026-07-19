package deque;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
    public void onlyAddLastAndRemoveLast(){
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addLast(0);
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.removeLast();
        ad.removeLast();
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
    public void onlyAddFirstAndRemoveFirst() {
        ArrayDeque<Integer> ad = new ArrayDeque<>(4);
        ad.addFirst(3);
        ad.addFirst(2);
        ad.addFirst(1);
        ad.removeFirst();
        ad.removeFirst();
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
    public void only_Add_And_Remove_FirstAndAddLas() {
        ArrayDeque<Integer> ad = new ArrayDeque<>(4);
        ad.addFirst(3);
        ad.addLast(5);
        ad.addFirst(2);
        ad.addLast(6);
        ad.addFirst(1);

        ad.removeLast();
        ad.removeFirst();
        ad.printDeque();
    }

    @Test
    public void onlyAddLastResize() {
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

    @Test
    public void Add_Remove_Resize() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 10, j = i + 1; i > 0; i--, j++){
            ad.addFirst(i);
            ad.addLast(j);
        }
        for (int i = 7; i > 0; i--){
            ad.removeFirst();
            ad.removeLast();
        }
        ad.printDeque();
    }

    @Test
    public void Add_Remove_add() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 4, j = i + 1; i > 0; i--, j++){
            ad.addFirst(i);
            ad.addLast(j);
        }
        ad.printDeque();
        System.out.println();
        for (int i = 8; i > 0; i--){
            System.out.println(ad.removeFirst());
        }
        ad.printDeque();
    }


    @Test
    public void getTest(){
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 10, j = i + 1; i > 0; i--, j++){
            ad.addFirst(i);
            ad.addLast(j);
        }
        ad.printDeque();
        System.out.println();

        System.out.println(ad.get(1));
        System.out.println(ad.get(ad.size() - 1));
        System.out.println(ad.get(30));
    }

    @Test
    public void IteratorTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 4, j = i + 1; i > 0; i--, j++){
            ad.addFirst(i);
            ad.addLast(j);
        }
        ad.printDeque();
        System.out.println();

        for (int w : ad) {
            System.out.println(w);
        }
    }

    @Test
    public void AD_DeepEqualsTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 4, j = i + 1; i > 0; i--, j++){
            ad.addFirst(i);
            ad.addLast(j);
        }

        assertEquals(true, ad.equals(ad));
    }

    @Test
    public void ADs_EqualsTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 4, j = i + 1; i > 0; i--, j++){
            ad1.addFirst(i);
            ad1.addLast(j);
        }

        ArrayDeque<Integer> ad2 = new ArrayDeque<>();
        for (int i = 4, j = i + 1; i > 0; i--, j++){
            ad2.addFirst(i);
            ad2.addLast(j);
        }

        assertEquals(true, ad1.equals(ad2));
    }

    @Test
    public void AD_LLD_EqualsTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 4, j = i + 1; i > 0; i--, j++){
            ad.addFirst(i);
            ad.addLast(j);
        }

        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        for (int i = 4, j = i + 1; i > 0; i--, j++){
            lld.addFirst(i);
            lld.addLast(j);
        }

        assertEquals(true, ad.equals(lld));
    }

}
