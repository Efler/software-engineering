package org.eflerrr.handler;

import org.eflerrr.model.PrintRequest;

public abstract class ChainHandler {

    protected ChainHandler next;

    public ChainHandler setNext(ChainHandler next) {
        this.next = next;
        return next;
    }

    public abstract void handle(PrintRequest req);

    public abstract void shutdownWork();

    public abstract void awaitWork() throws InterruptedException;

}