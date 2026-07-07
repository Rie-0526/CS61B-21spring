package deque;

public class LinkedListDeque<T> {
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
        size = 1;
        addFirst(item);
    }

    public void addFirst(T item){
        Node node = new Node(item);
        node.previous = sentinel;
        node.next = sentinel.next;
        node.previous.next = node;
        node.next.previous = node;
        size++;
    }

    public void addLast(T item){
        Node node = new Node(item);
        node.previous = sentinel.previous;
        node.next = sentinel;
        node.previous.next = node;
        node.next.previous = node;
        size++;
    }

    public boolean isEmpty(){
        if (sentinel.next == sentinel)
            return true;
        else
            return false;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        Node cur = sentinel.next;
        for(int i = 0; i < size; i++){
            System.out.print(cur.item);
            System.out.print(' ');
            cur = cur.next;
        }
        System.out.println();
    }
}
