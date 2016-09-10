package org.apache.hadoop.util;

/**
 * Generic ID generator used for generating various types of number sequences.
 */
public interface IdGenerator {
    
    /** Increment and then return the next value. */
    public long nextValue();
    
}
