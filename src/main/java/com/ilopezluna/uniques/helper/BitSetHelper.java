package com.ilopezluna.uniques.helper;

import java.util.BitSet;

/**
 * Created by ignasi on 31/5/15.
 */
public class BitSetHelper {

    public static BitSet fromByteArray(byte[] bytes) {
        return new BitSet().valueOf(bytes);
    }

    public static BitSet merge(BitSet base, BitSet against, Operation operation) {
        final BitSet result = (BitSet) base.clone();
        switch (operation) {

            case AND:
                result.and(against);
                break;

            case OR:
                result.or(against);
                break;

            default:
                throw new RuntimeException("Operation: " + operation + " not supported");
        }
        return result;
    }

    public static String toString(BitSet bitSet) {
        StringBuffer buffer = new StringBuffer();
        for (byte b : bitSet.toByteArray()) {
            buffer.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        return buffer.toString();
    }
}
