import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;
/**
 * Created by ponomarevandrew on 04.10.16.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;

    private int n;

    // Создает пустую рандомизированную очередь
    public RandomizedQueue() {
        s = (Item[]) new Object[16];
    }

    //  Очередь пустая ?
    public boolean isEmpty() {
        return n == 0;
    }

    // Возвращает число элементов в очереди
    public int size() {
        return n;
    }

    // Добавляет элемент в очередь
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (n == s.length)
            resize(2 * s.length);
        s[n++] = item;
    }

    private void resize(int capcity) {
        Item[] copy = (Item[]) new Object[capcity];
        for (int i = 0; i < n; i++)
            copy[i] = s[i];
        s = copy;
    }

    // Удаляет и возвращает случайный элемент
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int index = StdRandom.uniform(0, n);
        Item item = s[index];
        s[index] = s[--n];
        s[n] = null;
        if (n > 0 && n == s.length/4) resize(s.length/2);
        return item;
    }

    // возвращает но не удаляет случайный элемент
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        int index = StdRandom.uniform(0, n);
        return s[index];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int currentIndex;

        public boolean hasNext() {
            return s[currentIndex] != null;
        }

        public  void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = s[currentIndex];
            int current = ++currentIndex;
            return item;
        }
    }

    public static void main(String[] args) {

    }

}