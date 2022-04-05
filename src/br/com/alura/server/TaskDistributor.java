package br.com.alura.server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TaskDistributor implements Runnable {

    private final ExecutorService threadPool;
    private final Socket socket;
    private final ServerMain server;
    private final BlockingQueue<String> commandsQueue;

    public TaskDistributor(
            final ExecutorService threadPool,
            final Socket socket,
            final ServerMain server,
            final BlockingQueue<String> commandsQueue
    ) {
        this.threadPool = threadPool;
        this.socket = socket;
        this.server = server;
        this.commandsQueue = commandsQueue;
    }

    @Override
    public void run() {
        try {
            System.out.println("Distributing task to " + socket);
            final Scanner input = new Scanner(socket.getInputStream());
            final PrintStream output = new PrintStream(socket.getOutputStream());

            while (input.hasNextLine()) {
                final String command = input.nextLine();
                System.out.println("Received command from client: " + command);

                switch (command) {
                    case "c1": {
                        output.println("Confirmation of command c1");
                        final CommandC1 c1 = new CommandC1(output);
                        this.threadPool.execute(c1);
                        break;
                    }
                    case "c2": {
                        output.println("Confirmation of command c2");
                        final CommandC2CallWS c2CallWS = new CommandC2CallWS(output);
                        final CommandC2AccessDB c2AccessDB = new CommandC2AccessDB(output);
                        final Future<String> futureCallWS = this.threadPool.submit(c2CallWS);
                        final Future<String> futureAccessDB = this.threadPool.submit(c2AccessDB);

                        this.threadPool.submit(new ResultCommandC2(futureCallWS, futureAccessDB, output));
                        break;
                    }
                    case "c3": {
                        output.println("Confirmation of command c3");
                        commandsQueue.put(command);
                        output.println("Command c3 added to queue");
                        break;
                    }
                    case "end": {
                        output.println("Finishing the server");
                        server.stop();
                        break;
                    }
                    default: {
                        output.println("Command not found");
                    }
                }
            }

            output.close();
            input.close();
            Thread.sleep(2000);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

}
