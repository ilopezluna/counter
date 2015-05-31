package com.ilopezluna.uniques.helper;

import java.util.BitSet;

/**
 * Created by ignasi on 31/5/15.
 */
public class BitSetHelper {

    public static BitSet fromByteArray(byte[] bytes) {
        BitSet bits = new BitSet();
        for (int i = 0; i < bytes.length * 8; i++) {
            if ((bytes[bytes.length - i / 8 - 1] & (1 << (i % 8))) > 0) {
                bits.set(i);
            }
        }
        return bits;
    }
}
