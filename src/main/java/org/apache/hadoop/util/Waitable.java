package org.apache.hadoop.util;

import java.util.concurrent.locks.Condition;

/**
 * Represents an object that you can wait for.
 */
public class Waitable<T> {
    
    private T               val;
    private final Condition cond;
    
    public Waitable(Condition cond) {
        this.val = null;
        this.cond = cond;
    }
    
    public T await() throws InterruptedException {
        while (this.val == null) {
            this.cond.await();
        }
        return this.val;
    }
    
    public void provide(T val) {
        this.val = val;
        this.cond.signalAll();
    }
    
    public boolean hasVal() {
        return this.val != null;
    }
    
    public T getVal() {
        return this.val;
    }
    
}
