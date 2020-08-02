package util;

import java.util.Stack;

/**
 * A stack implementation of a fixed size where new object is replacing the oldest one.
 *
 * @author Nemanja
 */
public class FixedSizeStack<T> extends Stack<T> {

    /**
     * Capacity of the stack.
     */
    private final int maxSize;

    /**
     * Constructor with given capacity.
     *
     * @param size Given capacity
     */
    public FixedSizeStack(int size) {
        super();
        this.maxSize = size;
    }

    /**
     * Pushes object to top of the stack.
     *
     * @param object Object to be pushed
     * @return Pushed object
     */
    @Override
    public T push(T object) {
        if (this.size() == maxSize) {
            this.set(0, object);
            return object;
        }
        return super.push(object);
    }

}

