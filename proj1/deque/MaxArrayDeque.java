package deque;

public class MaxArrayDeque<T> extends ArrayDeque<T>{

    Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c){
        comparator = c;
    }

    public T max(Comparator<T> c) {
        T mostItem = get(0);
        int cmpResult;
        for (int i = 1; i < size(); i++){
            cmpResult = c.compare(get(i), mostItem);
            if (cmpResult > 0) {
                mostItem = get(i);
            }
        }
        return mostItem;
    }

    public T max() {
        return max(comparator);
    }

}
