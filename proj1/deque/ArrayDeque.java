package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {

    // 规定：当size == 0时，firstIndex == lastIndex
    private int size = 0;
    private int firstIndex = 0;
    private int lastIndex = 0;

    private T[] array = (T[]) new Object[8];


    public ArrayDeque(){
    }

    public ArrayDeque(T item){
        addLast(item);
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator<T> implements Iterator<T> {

        int ptr = firstIndex;
        int nextTimes = 0;

        @Override
        public boolean hasNext(){
            if (firstIndex < lastIndex){
                if (ptr + 1 <= lastIndex)
                    return true;
                else
                    return false;
            }
            else if (firstIndex > lastIndex) {
                if ((ptr > lastIndex && ptr < firstIndex) || nextTimes == size)
                    return false;
                else
                    return true;
            }
            return false;
        }

        @Override
        public T next() {
            T value = (T) array[ptr];
            if (!hasNext()) return value;
            ptr++;
            if (ptr == array.length) {
                ptr = 0;
            }
            nextTimes++;
            return value;
        }
    }

    @Override
    public boolean equals(Object o){

        if (!(o instanceof Deque))    return false;
        Deque<T> other = (Deque<T>) o;
        if (size() != other.size())     return false;

        for(int i = 0; i < size(); i++) {
            if (!get(i).equals(other.get(i))) return false;
        }

        return true;
    }


    private void resizeArray(int capacity){
        T[] temp = (T[]) new Object[capacity];
        // Copy the array
        int j = 0;
        if (firstIndex <= lastIndex){
            for(int i = firstIndex; i <= lastIndex; i++){
                temp[j] = array[i];
                j++;
            }
        }
        else {
            for (int i = firstIndex; i < array.length; i++){
                temp[j] = array[i];
                j++;
            }
            for (int i = 0; i <= lastIndex; i++){
                temp[j] = array[i];
                j++;
            }
        }

        array = temp;
        firstIndex = 0;
        lastIndex = j - 1;

    }


    @Override
    public void addFirst(T item){
        if(size == array.length){
            resizeArray(size * 2);
        }
        if (size != 0){
            --firstIndex;
            if (firstIndex < 0) {
                firstIndex = array.length - 1;
            }
        }
        array[firstIndex] = item;
        size++;
    }

    @Override
    public void addLast(T item){
        if(size == array.length){
            resizeArray(size * 2);
        }
        if (size != 0) {
            ++lastIndex;
            if (lastIndex >= array.length) {
                lastIndex = 0;
            }
        }
        array[lastIndex] = item;
        size++;
    }

    // 感觉最好写一个firstIndex/lastIndex增加/减少方法
    // 你这不纯迭代器的超级无敌劣质low版本


    @Override
    public int size(){
        return size;
    }

    @Override
    public void printDeque(){
        if (size == 0)  {
            System.out.println();
            return;
        }

        if (firstIndex <= lastIndex){
            for(int i = firstIndex; i <= lastIndex; i++){
                System.out.println(array[i]);
            }
        }
        else {
            for (int i = firstIndex; i < array.length; i++){
                System.out.println(array[i]);
            }
            for (int i = 0; i <= lastIndex; i++){
                System.out.println(array[i]);
            }
        }
    }

    @Override
    public T removeFirst(){
        if (size == 0)  {return null;}

        T removeValue = array[firstIndex];
        array[firstIndex] = null;

        if (size > 1){
            firstIndex++;
            if (firstIndex > array.length - 1) {
                firstIndex = 0;
            }
        }

        size--;

        if (size < array.length / 4){
            resizeArray(array.length / 4);
        }

        return removeValue;
    }

    @Override
    public T removeLast(){
        if (size == 0)  {return null;}

        T removeValue = array[lastIndex];
        array[lastIndex] = null;

        if (size > 1){
            lastIndex--;
            if (lastIndex < 0) {
                lastIndex = array.length - 1;
            }
        }

        size--;

        if (size < array.length / 4){
            resizeArray(array.length / 4);
        }

        return removeValue;
    }

    @Override
    public T get(int index){

        if (index >= size)  {return null;}

        int realIndex = index + firstIndex;
        if (realIndex >= array.length){
            realIndex -= array.length;
        }
        return array[realIndex];

    }



}
