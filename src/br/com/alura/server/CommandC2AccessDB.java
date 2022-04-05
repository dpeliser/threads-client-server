package br.com.alura.server;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class CommandC2AccessDB implements Callable<String> {

    private final PrintStream output;

    public CommandC2AccessDB(final PrintStream output) {
        this.output = output;
    }

    @Override
    public String call() throws Exception {
        System.out.println("Server received command c2 to access database");

        Thread.sleep(10000);

        final int number = new Random().nextInt(100) + 1;

        System.out.println("Command c2 to access database executed successfully");

        return Integer.toString(number);
    }

}
