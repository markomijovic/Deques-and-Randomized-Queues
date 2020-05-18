import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }
    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;
        if (size != 0) oldFirst.previous = first; // if first time adding logic
        else           last = first;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldLast;
        if (size != 0) oldLast.next = last; // if first time adding logic
        else           first = last;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next; // first->A->B ==> first->B and A garbage collected
        if (first == null) last = null; //if removed the only element and now first = last = null
        else               first.previous = null;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.previous;
        if (last == null) first = null; //if removed the only element
        else              last.next = null;
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        @Override
        public boolean hasNext() { return current != null; }
        @Override
        public void remove() {throw new UnsupportedOperationException("Remove not implemented");}
        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deq = new Deque<>();
        System.out.println(deq.isEmpty()); // true
        deq.addFirst(1);
        System.out.println(deq.size); // 1
        deq.addFirst(3);
        System.out.println(deq.size); // 2
        deq.addFirst(6);
        System.out.println(deq.size); // 3
        System.out.println(deq.removeFirst()); // 6
        System.out.println(deq.size); // 2
        deq.addLast(12);
        System.out.println(deq.size); // 3
        System.out.println(deq.removeLast()); // 12
        System.out.println(deq.isEmpty()); // false
        Iterator<Integer> test = deq.iterator();
        System.out.println(test.hasNext()); // true
    }
}