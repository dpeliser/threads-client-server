package br.com.alura.server;

import java.util.concurrent.ThreadFactory;

public class ThreadTaskDistributorFactory implements ThreadFactory {

    private final ThreadFactory defaultFactory;

    public ThreadTaskDistributorFactory(final ThreadFactory defaultFactory) {
        this.defaultFactory = defaultFactory;
    }

    @Override
    public Thread newThread(final Runnable r) {
        final Thread thread = defaultFactory.newThread(r);
        thread.setUncaughtExceptionHandler(new ThreadExceptionHandler());
        return thread;
    }

}
