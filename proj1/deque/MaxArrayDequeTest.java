package deque;

import org.junit.Test;

public class MaxArrayDequeTest {
    @Test
    public void initTest(){
        maxIntComparator c = new maxIntComparator();
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(c);

        for (int i = 4, j = i + 1; i > 0; i--, j++){
            mad.addFirst(i);
            mad.addLast(j);
        }
        mad.printDeque();
        System.out.println();
    }

    @Test
    public void getMaxTest(){
        maxIntComparator c = new maxIntComparator();
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(c);

        for (int i = 4, j = i + 1; i > 0; i--, j++){
            mad.addFirst(i);
            mad.addLast(j);
        }
        mad.printDeque();
        System.out.println();
        System.out.println(mad.max());
    }
}
