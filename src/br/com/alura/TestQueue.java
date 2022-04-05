package br.com.alura;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TestQueue {

    public static void main(final String[] args) throws InterruptedException {
        final BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        queue.put("c1");
        queue.put("c2");
        queue.put("c3");
        queue.put("c4");

        System.out.println(queue.size());

        System.out.println(queue.take());

        System.out.println(queue.peek());
        System.out.println(queue.take());

        System.out.println(queue.peek());
        System.out.println(queue.take());

        System.out.println(queue.size());
    }

}
