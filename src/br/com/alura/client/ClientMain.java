package br.com.alura.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) throws Exception {
        System.out.println("Connecting to server...");

        final Socket socket = new Socket("localhost", 12345);

        System.out.println("Connected to server");

        final Thread sendDataThread = new Thread(() -> {
            try {
                System.out.println("Able to send commands to server");
                final PrintStream output = new PrintStream(socket.getOutputStream());
                final Scanner input = new Scanner(System.in);

                while (input.hasNextLine()) {
                    final String command = input.nextLine();

                    if (command.trim().equals("")) {
                        break;
                    }

                    output.println(command);
                }

                output.close();
                input.close();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        });

        final Thread dataReceiverThread = new Thread(() -> {
            try {
                System.out.println("Able to receive commands from server");
                final Scanner server = new Scanner(socket.getInputStream());

                while (server.hasNextLine()) {
                    final String command = server.nextLine();
                    System.out.println("Server: " + command);
                }

                server.close();
                socket.close();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        });

        dataReceiverThread.start();
        sendDataThread.start();

        sendDataThread.join();

        System.out.println("Finishing client");
        socket.close();
    }

}
