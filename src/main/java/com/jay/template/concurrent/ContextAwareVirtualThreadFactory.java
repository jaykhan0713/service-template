package com.jay.template.concurrent;

import java.util.concurrent.ThreadFactory;

import com.jay.template.concurrent.mdc.MdcContext;

public final class ContextAwareVirtualThreadFactory implements ThreadFactory {

    private final ThreadFactory delegate;

    public ContextAwareVirtualThreadFactory() {
        this.delegate = Thread.ofVirtual().factory();
    }

    @Override
    public Thread newThread(Runnable task) {
        return delegate.newThread(MdcContext.wrap(task));
    }
}
