package deque;

public class maxIntComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2){
        return o1 - o2;
    }
}
