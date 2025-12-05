package com.jay.template.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class VirtualThreads {
    private static final ContextAwareVirtualThreadFactory FACTORY = new ContextAwareVirtualThreadFactory();

    private VirtualThreads(){}

    public static Thread startVirtualThread(Runnable task) {
        Thread thread = FACTORY.newThread(task);
        thread.start();
        return thread;
    }

    public static ExecutorService newVirtualThreadPerTaskExecutor() {
        return Executors.newThreadPerTaskExecutor(FACTORY);
    }
}
