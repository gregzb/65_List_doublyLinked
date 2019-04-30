import java.util.Iterator;

public class List_inChainOfNodes<T> implements Iterable<T> {
    private Node headRef = new Node(null);
    private Node tailRef = headRef;

    private int size = 0;

    public int size() {
        return size;
    }

    public int lSize() {
        int count = 0;
        Node current = headRef;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    public void clear() {
        headRef.setNext(null);
        size = 0;
    }

    public boolean addAsHead(T val) {
        add(0, val);
        return true;
    }

    public boolean addAsTail(T val) {
        add(size, val);
        return true;
    }

    public boolean add(int index, T value) {
        Node previous = getNode(index - 1);
        previous.setNext(new Node(value, previous.getNext()));

        if (index == size) {
            tailRef = previous.getNext();
        }

        size++;
        return true;
    }

    private Node getNode(int index) {
        if (index == size - 1) return tailRef;
        Node current = headRef;
        for (int count = -1; count < index; count++, current = current.getNext()) ;
        return current;
    }

    public T get(int index) {
        return getNode(index).getData();
    }

    public T set(int index, T value) {
        Node temp = getNode(index);
        T tempObj = temp.getData();
        temp.setData(value);
        return tempObj;
    }

    public T remove(int index) {
        Node previous = getNode(index - 1);
        T tempObj = previous.getNext().getData();
        previous.setNext(previous.getNext().getNext());
        if (index == size-1) {
            tailRef = previous;
        }

        size--;

        return tempObj;
    }

    public T removeFront() {
        return remove(0);
    }

    public String toString() {
        String stringRep = "";

        Node current = tailRef;

        while (current != headRef) {
            stringRep = current.getData() + "`" + stringRep;
            current = current.getPrev();
        }

        return "tail-first [" + stringRep + "]";
    }

    public void extend(List_inChainOfNodes<T> other) {
        tailRef.setNext(other.headRef.getNext());
        tailRef = other.tailRef;
        size += other.size;
        other.clear();
    }

    public Iterator iterator() {
        return new LinkedListIterator(this);
    }

    class Node {
        T data;
        Node next;
        Node prev;

        public Node(T data) {
            this(data, null);
        }

        public Node(T data, Node next) {
            setData(data);
            setNext(next);
        }

        public Node setNext(Node newNext) {
            Node temp = next;
            next = newNext;
            if (newNext != null) {
                newNext.prev = this;
            } else {

            }
            return temp;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrev() {
            return prev;
        }

        public T setData(T data) {
            T temp = data;
            this.data = data;
            return temp;
        }

        public T getData() {
            return data;
        }

        public String toString() {
            return "Node-" + hashCode() + ": " + getData() + " -> " + (next == null ? "null" : next.hashCode());
        }
    }

    class LinkedListIterator implements Iterator {

        Node current;

        public LinkedListIterator(List_inChainOfNodes<T> list) {
            current = list.getNode(0);
        }

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            T data = current.getData();
            current = current.getNext();
            return data;
        }


    }
}