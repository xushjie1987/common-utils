package org.apache.hadoop.util;

import java.util.logging.Logger;

public class ThreadUtil {
    
    private static final Logger LOG = Logger.getGlobal();
    
    /**
     * Cause the current thread to sleep as close as possible to the provided number of
     * milliseconds. This method will log and ignore any {@link InterruptedException} encountered.
     * 
     * @param millis
     *            the number of milliseconds for the current thread to sleep
     */
    public static void sleepAtLeastIgnoreInterrupts(long millis) {
        long start = Time.now();
        while (Time.now() -
               start < millis) {
            long timeToSleep = millis -
                               (Time.now() - start);
            try {
                Thread.sleep(timeToSleep);
            } catch (InterruptedException ie) {
                LOG.severe("interrupted while sleeping " +
                           ie);
            }
        }
    }
    
}
