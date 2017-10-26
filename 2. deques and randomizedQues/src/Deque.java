import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by ponomarevandrew on 30.09.16.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first;

    private Node last;

    private int count;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    //дека пустая?
    public boolean isEmpty() {
        return count == 0;
    }

    // возвращает кол-во элементов в деке
    public int size() {
        return count;
    }

    // добавляет элемент в начало
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node oldFirst = first;
        first = new Node();
        first.next = oldFirst;
        first.item = item;
        if (isEmpty()) last = first;
        else           oldFirst.prev = first;
        count++;
    }

    // добавляет элемент в конец
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node oldLast = last;
        last = new Node();
        last.prev = oldLast;
        last.item = item;
        if (isEmpty()) first = last;
        else           oldLast.next = last;
        count++;
    }

    // удаляет и возвращает элемент из начала
    public Item removeFirst() {
        boolean fromFront = true;
        return remove(fromFront);
    }

    // удаляет и вохвращает элемент из конца
    public Item removeLast() {
        boolean fromFront = false;
        return remove(fromFront);
    }

    private Item remove(boolean fromFront) {
        if (isEmpty())
            throw new NoSuchElementException();
        count--;
        Item item = null;
        if (fromFront) {
            item = first.item;
            first = first.next;
            if (isEmpty()) last = null;
            else first.prev = null;
        } else {
            item = last.item;
            last = last.prev;
            if (isEmpty()) first = null;
            else last.next = null;
        }
        return item;
    }

//    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        for (Item item : this) {
//            builder.append(item.toString());
//            builder.append(" ");
//        }
//        return builder.toString();
//    }

    // Возвращает итератор над элементами в порядке из начала в конец
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public  void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
//        int r = 0;
//        int num = 0;
//        Deque<Integer> deque = new Deque<>();
//        for (int i = 0; i < 500; i++){
//            try {
//                System.out.println(deque);
//                r = StdRandom.uniform(0, 3);
//                num = StdRandom.uniform(0, 100);
//                if (r == 0)
//                    deque.addFirst(num);
//                if (r == 1)
//                    deque.removeFirst();
//                if (r == 2)
//                    deque.isEmpty();
//                System.out.println(deque);
//            } catch(NoSuchElementException e) {
//                continue;
//            }
//        }
    }
}
