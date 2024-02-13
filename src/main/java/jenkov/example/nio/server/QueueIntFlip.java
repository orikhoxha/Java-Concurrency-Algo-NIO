package jenkov.example.nio.server;

public class QueueIntFlip {

    public int[] elements;

    public int capacity;
    public int writePos;
    public int readPos;
    public boolean flipped;

    public QueueIntFlip(int capacity) {
        this.capacity = capacity;
        this.elements = new int[capacity];
    }

    public void reset() {
        this.writePos = 0;
        this.readPos = 0;
        this.flipped = false;
    }

    public int available() {
        if (!flipped) {
            return writePos - readPos;
        }
        return capacity - readPos + writePos;
    }

    public int remainingCapacity() {
        if (!flipped) {
            return capacity - writePos;
        } else {
            return readPos - writePos;
        }
    }

    public boolean put(int element) {
        if (!flipped) {
            if (writePos == capacity) {
                writePos = 0;
                flipped = true;

                if (writePos < readPos) {
                    elements[writePos++] = element;
                    return true;
                } else {
                    return false;
                }
            } else {
                elements[writePos++] = element;
            }
        } else {
            if (writePos < readPos) {
                elements[writePos++] = element;
                return true;
            }
        }
        return false;
    }

    public int put(int[] newElements, int length) {
        int newElementsReadPo = 0;
        if (!flipped) {
            // readPos lower than writePos - free sections are:
            // 1) from writePos to capacity
            // 2) from 0 to readPos

            if (length <= capacity - writePos) {
                for (; newElementsReadPo < length; newElementsReadPo++) {
                    this.elements[this.writePos++] = newElements[newElementsReadPo];
                }
                return newElementsReadPo;
            } else {
                // divide top and bottom of elements array

                // writing to top
                for (; this.writePos < capacity; this.writePos++) {
                    this.elements[this.writePos] = newElements[newElementsReadPo++];
                }

                // writing to bottom
                this.writePos = 0;
                this.flipped = true;

                // if more elements to write than current read pos, remaining will not be written
                int endPos = Math.min(this.readPos, length - newElementsReadPo);
                for (; this.writePos < endPos; this.writePos++) {
                    this.elements[writePos] = newElements[newElementsReadPo++];
                }

                return newElementsReadPo;
            }

        } else {

            // readPos higher than writePos - free sections are:
            // 1) from writePos to readPos

            // if more elements to write than current read pos, remaining will not be written
            int endPos = Math.min(readPos, this.writePos + length);
            for (; this.writePos < endPos; writePos++) {
                this.elements[writePos] = newElements[newElementsReadPo++];
            }
            return newElementsReadPo;
        }
    }

    public int take() {
        if (!flipped) {
            if (readPos < writePos) {
                return elements[readPos++];
            } else {
                return -1;
            }
        } else {
            if (readPos == capacity) {
                readPos = 0;
                flipped = false;

                if (readPos < writePos) {
                    return elements[readPos++];
                } else {
                    return -1;
                }
            } else {
                return elements[readPos++];
            }
        }
    }

    public int take(int[] into, int length) {
        int intoWritePos = 0;
        if (!flipped) {

            //writePos higher than readPos - available section is writePos - readPos
            int endPos = Math.min(this.writePos, this.readPos + length);
            for (; this.readPos < endPos; this.readPos++) {
                into[intoWritePos++] = this.elements[this.readPos++];
            }
            return intoWritePos;
        } else {
            // readPos higher than writePos - available sections are top + bottom of elements array

            if (length <= capacity - readPos) {
                // length lower than elements available at the top of the elements array - copy directly
                for (; readPos < capacity; this.readPos++) {
                    into[intoWritePos++] = elements[readPos];
                }

                return intoWritePos;
            } else {
                // length is higher than elements available at the top of the elements array
                // split copy into a copy from both top and boottom of elements array

                // copy from top
                for (; this.readPos < capacity; readPos++) {
                    into[intoWritePos++] = elements[this.readPos];
                }

                // copy from bottom
                this.readPos = 0;
                this.flipped = false;
                int endPos = Math.min(this.writePos, length - intoWritePos);
                for (; this.readPos < endPos; this.readPos++) {
                    into[intoWritePos++] = elements[this.readPos];
                }
                return intoWritePos;
            }
        }
    }

}
