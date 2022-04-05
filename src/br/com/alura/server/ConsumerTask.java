package br.com.alura.server;

import java.util.concurrent.BlockingQueue;

public class ConsumerTask implements Runnable {

    private final BlockingQueue<String> commandsQueue;

    public ConsumerTask(final BlockingQueue<String> commandsQueue) {
        this.commandsQueue = commandsQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                final String command = commandsQueue.take();
                System.out.println("Command c3 consumed: " + command + " - " + Thread.currentThread().getName());

                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
