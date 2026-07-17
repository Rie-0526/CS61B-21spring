package deque;

public class ArrayDeque<T> {

    int size = 0;
    int firstIndex = 0;
    int lastIndex = 0;

    T[] array = (T[]) new Object[8];


    public ArrayDeque(){
    }

    public ArrayDeque(T item){
        addLast(item);
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

    public boolean isEmpty(){
        if (size == 0)  return true;
        else return false;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
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

    public T removeFirst(){
        if (size == 0)  {return null;}

        return null;
    }

    public T removeLast(){
        return null;
    }

    public T get(int index){
        return null;
    }



}
