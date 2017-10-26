/**
 * Created by ponomarevandrew on 04.10.16.
 *
 */

import edu.princeton.cs.algs4.StdIn;

public class Subset {

    public static void main(String[] args) {
        int k = Integer.valueOf(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        String inputString = StdIn.readString();
        String[] input = inputString.split(" ");
        for (String item : input)
            queue.enqueue(item);
        for (int i = 0; i < k; i++)
            System.out.println(queue.dequeue());
    }
}
