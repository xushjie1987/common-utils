package org.apache.hadoop.util.bloom;

import java.util.BitSet;

/**
 * Implements a <i>Bloom filter</i>, as defined by Bloom in 1970.
 * <p>
 * The Bloom filter is a data structure that was introduced in 1970 and that has been adopted by the
 * networking research community in the past decade thanks to the bandwidth efficiencies that it
 * offers for the transmission of set membership information between networked hosts. A sender
 * encodes the information into a bit vector, the Bloom filter, that is more compact than a
 * conventional representation. Computation and space costs for construction are linear in the
 * number of elements. The receiver uses the filter to test whether various elements are members of
 * the set. Though the filter will occasionally return a false positive, it will never return a
 * false negative. When creating the filter, the sender can choose its desired point in a trade-off
 * between the false positive rate and the size.
 * <p>
 * Originally created by <a href="http://www.one-lab.org">European Commission One-Lab Project
 * 034819</a>.
 * 
 * @see Filter The general behavior of a filter
 * @see <a href="http://portal.acm.org/citation.cfm?id=362692&dl=ACM&coll=portal">Space/Time
 *      Trade-Offs in Hash Coding with Allowable Errors</a>
 */
public class BloomFilter extends Filter {
    
    private static final byte[] bitvalues = new byte[] {(byte) 0x01, (byte) 0x02, (byte) 0x04, (byte) 0x08, (byte) 0x10, (byte) 0x20, (byte) 0x40, (byte) 0x80};
    
    /** The bit vector. */
    BitSet                      bits;
    
    /** Default constructor - use with readFields */
    public BloomFilter() {
        super();
    }
    
    /**
     * Constructor
     * 
     * @param vectorSize
     *            The vector size of <i>this</i> filter.
     * @param nbHash
     *            The number of hash function to consider.
     * @param hashType
     *            type of the hashing function (see {@link org.apache.hadoop.util.hash.Hash}).
     */
    public BloomFilter(int vectorSize,
                       int nbHash,
                       int hashType) {
        super(vectorSize,
              nbHash,
              hashType);
        
        bits = new BitSet(this.vectorSize);
    }
    
    @Override
    public void add(Key key) {
        if (key == null) {
            throw new NullPointerException("key cannot be null");
        }
        
        int[] h = hash.hash(key);
        hash.clear();
        
        for (int i = 0; i < nbHash; i++) {
            bits.set(h[i]);
        }
    }
    
    @Override
    public void and(Filter filter) {
        if (filter == null ||
            !(filter instanceof BloomFilter) ||
            filter.vectorSize != this.vectorSize ||
            filter.nbHash != this.nbHash) {
            throw new IllegalArgumentException("filters cannot be and-ed");
        }
        
        this.bits.and(((BloomFilter) filter).bits);
    }
    
    @Override
    public boolean membershipTest(Key key) {
        if (key == null) {
            throw new NullPointerException("key cannot be null");
        }
        
        int[] h = hash.hash(key);
        hash.clear();
        for (int i = 0; i < nbHash; i++) {
            if (!bits.get(h[i])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void not() {
        bits.flip(0,
                  vectorSize);
    }
    
    @Override
    public void or(Filter filter) {
        if (filter == null ||
            !(filter instanceof BloomFilter) ||
            filter.vectorSize != this.vectorSize ||
            filter.nbHash != this.nbHash) {
            throw new IllegalArgumentException("filters cannot be or-ed");
        }
        bits.or(((BloomFilter) filter).bits);
    }
    
    @Override
    public void xor(Filter filter) {
        if (filter == null ||
            !(filter instanceof BloomFilter) ||
            filter.vectorSize != this.vectorSize ||
            filter.nbHash != this.nbHash) {
            throw new IllegalArgumentException("filters cannot be xor-ed");
        }
        bits.xor(((BloomFilter) filter).bits);
    }
    
    @Override
    public String toString() {
        return bits.toString();
    }
    
    /**
     * @return size of the the bloomfilter
     */
    public int getVectorSize() {
        return this.vectorSize;
    }
    
    /* @return number of bytes needed to hold bit vector */
    private int getNBytes() {
        return (vectorSize + 7) / 8;
    }
    
}// end class
