package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T> {
    private int size;
    private Node sentinel = new Node(null);

    private class Node{
        private T item;
        private Node previous, next;

        public Node(T i){
            item = i;
        }
    }

    public LinkedListDeque(){
        size = 0;
        sentinel.previous = sentinel;
        sentinel.next = sentinel;
    }

    public LinkedListDeque(T item){
        size = 0;
        sentinel.previous = sentinel;
        sentinel.next = sentinel;
        addFirst(item);
    }

    public Iterator<T> iterator() {
        return new LLDIterator<T>();
    }

    private class LLDIterator<T> implements Iterator<T> {

        Node ptr = sentinel.next;

        @Override
        public boolean hasNext() {
            if (ptr != sentinel) return true;
            return false;
        }

        @Override
        public T next() {
            T value = (T) ptr.item;
            if(hasNext()) {
                ptr = ptr.next;
            }
            return value;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Deque))    return false;
        Deque<T> other = (Deque<T>) o;
        if (size() != other.size())     return false;

        Iterator<T> ptr1 = iterator();
        Iterator<T> ptr2 = other.iterator();
        while (ptr1.hasNext()) {
            if (!(ptr1.next().equals(ptr2.next()))) return false;
        }

        return true;
    }


    @Override
    public void addFirst(T item){
        Node node = new Node(item);
        node.previous = sentinel;
        node.next = sentinel.next;
        node.previous.next = node;
        node.next.previous = node;
        size++;
    }

    @Override
    public void addLast(T item){
        Node node = new Node(item);
        node.previous = sentinel.previous;
        node.next = sentinel;
        node.previous.next = node;
        node.next.previous = node;
        size++;
    }


    @Override
    public int size(){
        return size;
    }


    @Override
    public void printDeque(){
        Node cur = sentinel.next;
        for(int i = 0; i < size; i++){
            System.out.print(cur.item);
            System.out.print(' ');
            cur = cur.next;
        }
        System.out.println();
    }


    @Override
    public T removeFirst(){
        if(size == 0)   return null;
        Node firstNode = sentinel.next;
        Node secondNode = firstNode.next;
        sentinel.next = secondNode;
        secondNode.previous = sentinel;
        size--;
        return firstNode.item;
    }


    @Override
    public T removeLast(){
        if(size == 0)   return null;
        Node lastNode = sentinel.previous;
        Node secondNode = lastNode.previous;
        sentinel.previous = secondNode;
        secondNode.next = sentinel;
        size--;
        return lastNode.item;
    }


    @Override
    public T get(int index){
        Node cur = sentinel.next;
        for(int i = 0; i < index; i++){
            cur = cur.next;
        }
        return cur.item;
    }

    private T supportiveGetRecursive(int index,Node cur){
        if(index == 0){
            return cur.item;
        }
        else {
            return supportiveGetRecursive(--index,cur.next);
        }
    }

    public T getRecursive(int index){
        return supportiveGetRecursive(index,sentinel.next);
    }

}
