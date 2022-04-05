package br.com.alura;

public class TestServer {

//    private volative boolean running = false;
    private boolean running = false;

    public static void main(final String[] args) throws InterruptedException {
        final TestServer server = new TestServer();
        server.run();
        server.switchRunning();
    }

    private void run() {
        new Thread(() -> {
            System.out.println("Starting server, running = " + running);

            while (!running) ;

            System.out.println("Server running, running = " + running);

            while (running) ;

            System.out.println("Finishing server, running = " + running);
        }).start();
    }

    private void switchRunning() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("Main changing running to true");
        running = true;

        Thread.sleep(5000);
        System.out.println("Main changing running to false");
        running = false;
    }

}