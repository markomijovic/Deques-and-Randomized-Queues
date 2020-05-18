import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int N; // number of elements
    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[4]; // initially 4 long
        N = 0;
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int k = 0; k < N; k++) temp[k] = s[k];
        s = temp;
    }
    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (N == s.length) resize(s.length * 2);
        if (N == 0) {
            s[N++] = item;
            return;
        }
        int randIndex = StdRandom.uniform(N);
        Item holder = s[randIndex]; // holds the value where we are going to push in our new item
        s[randIndex] = item;
        s[N++] = holder; // places the value replaced by item at the end of the array
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        if (N > 0 && N == s.length/4) resize(s.length / 2);
        int randIndex = StdRandom.uniform(N);
        Item item = s[randIndex];
        // pop the last item despite random behaviour to retain consistent size
        s[randIndex] = s[--N]; // changing reference of popped item to the last item instead. --N since N!=0
        s[N] = null; // popping the reference to the last item. garbage collected
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int randIndex = StdRandom.uniform(N);
        return s[randIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {
        private int i;
        private int[] randomIndices; // random indices stored in array used to access the randomized en/deque array
        public RandomArrayIterator() {
            i = 0;
            randomIndices = new int[N];
            for (int j = 0; j < N; j++) {
                randomIndices[j] = j;
            }
            StdRandom.shuffle(randomIndices);
        }
        @Override
        public boolean hasNext() {return i < N;}
        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            else            return s[randomIndices[i++]];
        }
        @Override
        public void remove()     {throw new UnsupportedOperationException();}
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> myQ = new RandomizedQueue();
        System.out.println(myQ.isEmpty()); // true
        myQ.enqueue(1);
        myQ.enqueue(2);
        myQ.enqueue(3);
        myQ.enqueue(4);
        myQ.enqueue(5);
        System.out.println(myQ.sample()); // sample from 1 to 5
        System.out.println(myQ.size()); // 5
        System.out.println(myQ.dequeue()); // any number from 1 to 5
        System.out.println(myQ.size()); // 4
        System.out.println(myQ.dequeue());
        System.out.println(myQ.dequeue());
        System.out.println(myQ.dequeue());
        System.out.println(myQ.dequeue());
        System.out.println(myQ.size()); // 0
        System.out.println(myQ.isEmpty()); // true
    }

}