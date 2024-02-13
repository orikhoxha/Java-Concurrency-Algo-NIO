package chapter13.concurrency.jenkov;

import java.util.HashMap;
import java.util.Map;

public class ReadWriteLock {

    private int readers = 0;
    private int writeAccesses = 0;
    private int writeRequests;
    private Thread writingThread;


    private Map<Thread, Integer>  readingThreads = new HashMap<>();

    public synchronized void lockRead() throws InterruptedException {
        Thread callingThread = Thread.currentThread();
        while (!canGrantReadAccess(callingThread)) {
            wait();
        }
        readingThreads.put(callingThread, getReadAccessCount(callingThread) + 1);
    }

    public synchronized void unlockRead() {
        Thread callingThread = Thread.currentThread();
        int accessCount = getReadAccessCount(callingThread);
        if (accessCount == 1) readingThreads.remove(callingThread);
        else readingThreads.compute(callingThread, (key, val) -> val - 1);
        notifyAll();

    }

    public synchronized void lockWrite() throws InterruptedException {
        writeRequests++;

        Thread callingThread = Thread.currentThread();
        while (!canGrantWriteAccess(callingThread)) {
            wait();
        }
        writeRequests--;
        writeAccesses++;
        writingThread = callingThread;
    }

    public synchronized void unlockWrite() throws InterruptedException {
        writeAccesses--;
        if (writeAccesses == 0) writingThread = null;
        notifyAll();
    }

    private boolean canGrantReadAccess(Thread callingThread) {
        if (isWriter(callingThread)) return true;
        if (writeAccesses > 0) return false;
        if (isReader(callingThread)) return true;
        return writeRequests <= 0;
    }

    private boolean isReader(Thread callingThread) {
        return readingThreads.get(callingThread) != null;
    }

    private boolean hasReaders() {
        return readingThreads.size() > 0;
    }

    private int getReadAccessCount(Thread callingThread) {
        return readingThreads.getOrDefault(callingThread, 0);
    }



    private boolean canGrantWriteAccess(Thread callingThread) {
        if (isOnlyReader(callingThread)) return true;
        if (hasReaders()) return false;
        if (writingThread == null) return true;
        return isWriter(callingThread);
    }

    private boolean isWriter(Thread callingThread) {
        return writingThread == callingThread;
    }

    private boolean isOnlyReader(Thread thread) {
        return readingThreads.size() == 1 && readingThreads.get(thread) != null;
    }
}
