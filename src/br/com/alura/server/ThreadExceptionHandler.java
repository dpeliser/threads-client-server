package br.com.alura.server;

import java.lang.Thread.UncaughtExceptionHandler;

public class ThreadExceptionHandler implements UncaughtExceptionHandler {

    @Override
    public void uncaughtException(final Thread t, final Throwable e) {
        System.out.println("Exception in Thread " + t.getName() + " - " + e.getMessage());
    }

}
