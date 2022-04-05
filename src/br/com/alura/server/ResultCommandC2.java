package br.com.alura.server;

import java.io.PrintStream;
import java.util.concurrent.*;

public class ResultCommandC2 implements Callable<Void> {

    private final Future<String> futureCallWS;
    private final Future<String> futureAccessDB;
    private final PrintStream output;

    public ResultCommandC2(final Future<String> futureCallWS, final Future<String> futureAccessDB, final PrintStream output) {
        this.futureCallWS = futureCallWS;
        this.futureAccessDB = futureAccessDB;
        this.output = output;
    }

    @Override
    public Void call() {
        System.out.println("Waiting results from Future CallWS and AccessDB");

        try {
            final String resultCallWs = futureCallWS.get(15, TimeUnit.SECONDS);
            final String resultAccessDB = futureAccessDB.get(15, TimeUnit.SECONDS);
            output.println("Result of command c2: " + resultCallWs + " - " + resultAccessDB);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("Timeout of command c2, canceling it");
            output.println("Timeout running command c2");

            futureCallWS.cancel(true);
            futureAccessDB.cancel(true);
        }

        System.out.println("Finished getting result of command c2");
        return null;
    }

}
