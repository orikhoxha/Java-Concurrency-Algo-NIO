package jenkov.example.nio.server;

public class RingBufferFillCount {

    public Object[] elements;

    private int capacity;
    private int writePos;
    private int available;

    public RingBufferFillCount(int capacity) {
        this.capacity = capacity;
        this.elements  = new Object[capacity];
    }

    public void reset() {
        this.writePos = 0;
        this.available = 0;
    }

    public int capacity() { return  capacity; }
    public int available() { return available; } // fillCount

    public int remainingCapacity() {
        return this.capacity - this.available; // 10 - 4 = 6
    }

    public boolean put(Object element) {
        if (available < capacity) {
            if (writePos >= capacity) {
                writePos = 0;
            }

            elements[writePos] = element;
            writePos++;
            available++;
            return true;
        }
        return false;
    }

    public Object take() {
        if (available == 0) {
            return null;
        }

        int nextSlot = writePos - available;

        if (nextSlot < 0) {
            nextSlot += capacity;
        }

        Object nextObj = elements[nextSlot];
        available--;
        return nextObj;
    }
}
