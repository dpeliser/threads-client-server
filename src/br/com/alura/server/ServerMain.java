package br.com.alura.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerMain {

    private final ServerSocket server;
    private final ExecutorService threadPool;
    private final AtomicBoolean running;
    private final ArrayBlockingQueue<String> commandsQueue;
    private final int numberOfConsumers = 2;

    public ServerMain() throws IOException {
        this.server = new ServerSocket(12345);
        this.threadPool = Executors.newCachedThreadPool(new ThreadTaskDistributorFactory(Executors.defaultThreadFactory()));
        this.running = new AtomicBoolean(true);
        this.commandsQueue = new ArrayBlockingQueue<>(2);
        startConsumers();
    }

    public static void main(final String[] args) throws Exception {
        final ServerMain server = new ServerMain();

        System.out.println("Server started");

        server.run();
        server.stop();
//        Set<Thread> allThreads = Thread.getAllStackTraces().keySet();
//
//        for (final Thread thread : allThreads) {
//            System.out.println(thread.getName());
//        }
    }

    public void run() throws IOException {
        while (running.get()) {
            try {
                final Socket socket = server.accept();
                System.out.println("New client connected in port: " + socket.getPort());

                final TaskDistributor taskDistributor = new TaskDistributor(threadPool, socket, this, commandsQueue);
                threadPool.execute(taskDistributor);
            } catch (final SocketException e) {
                System.out.println("SocketException, is it running? " + this.running);
            }
        }
    }

    public void stop() throws IOException {
        running.set(false);
        server.close();
        threadPool.shutdown();
    }

    private void startConsumers() {
        for (int i = 0; i < numberOfConsumers; i++) {
            final ConsumerTask task = new ConsumerTask(commandsQueue);
            threadPool.execute(task);
        }
    }

}
