package org.apache.hadoop.util;

public class UTF8ByteArrayUtils {
    
    /**
     * Find the first occurrence of the given byte b in a UTF-8 encoded string
     * 
     * @param utf
     *            a byte array containing a UTF-8 encoded string
     * @param start
     *            starting offset
     * @param end
     *            ending position
     * @param b
     *            the byte to find
     * @return position that first byte occures otherwise -1
     */
    public static int findByte(byte[] utf,
                               int start,
                               int end,
                               byte b) {
        for (int i = start; i < end; i++) {
            if (utf[i] == b) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Find the first occurrence of the given bytes b in a UTF-8 encoded string
     * 
     * @param utf
     *            a byte array containing a UTF-8 encoded string
     * @param start
     *            starting offset
     * @param end
     *            ending position
     * @param b
     *            the bytes to find
     * @return position that first byte occures otherwise -1
     */
    public static int findBytes(byte[] utf,
                                int start,
                                int end,
                                byte[] b) {
        int matchEnd = end -
                       b.length;
        for (int i = start; i <= matchEnd; i++) {
            boolean matched = true;
            for (int j = 0; j < b.length; j++) {
                if (utf[i +
                        j] != b[j]) {
                    matched = false;
                    break;
                }
            }
            if (matched) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Find the nth occurrence of the given byte b in a UTF-8 encoded string
     * 
     * @param utf
     *            a byte array containing a UTF-8 encoded string
     * @param start
     *            starting offset
     * @param length
     *            the length of byte array
     * @param b
     *            the byte to find
     * @param n
     *            the desired occurrence of the given byte
     * @return position that nth occurrence of the given byte if exists; otherwise -1
     */
    public static int findNthByte(byte[] utf,
                                  int start,
                                  int length,
                                  byte b,
                                  int n) {
        int pos = -1;
        int nextStart = start;
        for (int i = 0; i < n; i++) {
            pos = findByte(utf,
                           nextStart,
                           length,
                           b);
            if (pos < 0) {
                return pos;
            }
            nextStart = pos + 1;
        }
        return pos;
    }
    
    /**
     * Find the nth occurrence of the given byte b in a UTF-8 encoded string
     * 
     * @param utf
     *            a byte array containing a UTF-8 encoded string
     * @param b
     *            the byte to find
     * @param n
     *            the desired occurrence of the given byte
     * @return position that nth occurrence of the given byte if exists; otherwise -1
     */
    public static int findNthByte(byte[] utf,
                                  byte b,
                                  int n) {
        return findNthByte(utf,
                           0,
                           utf.length,
                           b,
                           n);
    }
    
}
