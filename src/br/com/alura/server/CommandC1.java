package br.com.alura.server;

import java.io.PrintStream;

public class CommandC1 implements Runnable {

    private final PrintStream output;

    public CommandC1(final PrintStream output) {
        this.output = output;
    }

    @Override
    public void run() {
        System.out.println("Running command c1");

        try {
            Thread.sleep(5000);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Command c1 executed successfully");
    }

}
