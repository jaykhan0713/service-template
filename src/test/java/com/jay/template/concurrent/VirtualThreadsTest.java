package com.jay.template.concurrent;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

class VirtualThreadsTest {

    @BeforeEach
    void setUp() {
        MDC.clear();
    }

    @AfterEach
    void tearDown() {
        MDC.clear();
    }

    @Test
    void startVirtualThreadRunsTask() throws InterruptedException {
        AtomicBoolean ran = new AtomicBoolean(false);

        Thread thread = VirtualThreads.startVirtualThread(() -> ran.set(true));
        thread.join();

        assertTrue(ran.get());
    }

    @Test
    void newVirtualThreadPerTaskExecutorRunsTask() throws ExecutionException, InterruptedException {

        try (ExecutorService ex = VirtualThreads.newVirtualThreadPerTaskExecutor()) {
            Future<Boolean> future = ex.submit(() -> Thread.currentThread().isVirtual());
            assertTrue(future.get());
        }
    }

    @Test
    void newVirtualThreadPerTaskExecutorPreservesMdcThroughFactory() throws ExecutionException, InterruptedException {
        MDC.put("mdc-key", "parent");

        try (ExecutorService ex = VirtualThreads.newVirtualThreadPerTaskExecutor()) {
            Future<String> future = ex.submit(() -> MDC.get("mdc-key"));

            assertEquals("parent", future.get());

        }
    }

}