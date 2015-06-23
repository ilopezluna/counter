package com.ilopezluna.uniques.helper;

import org.junit.Assert;
import org.junit.Test;

import java.util.BitSet;


/**
 * Created by ignasi on 8/6/15.
 */
public class BitSetHelperTest {

    @Test
    public void testMerge() throws Exception {

        BitSet base = new BitSet();
        BitSet against = new BitSet();

        base.set(1);

        BitSet and = BitSetHelper.merge(base, against, Operation.AND);
        BitSet or = BitSetHelper.merge(base, against, Operation.OR);

        Assert.assertFalse(and.get(1));
        Assert.assertTrue(or.get(1));

        against.set(1);

        and = BitSetHelper.merge(base, against, Operation.AND);
        or = BitSetHelper.merge(base, against, Operation.OR);

        Assert.assertTrue(and.get(1));
        Assert.assertTrue(or.get(1));
    }

    @Test
    public void testFromByteArray() throws Exception {

        BitSet bitSet = new BitSet();
        printBitSet(bitSet);

        bitSet.set(1);
        printBitSet(bitSet);

        bitSet.set(100);
        printBitSet(bitSet);
    }

    private void printBitSet(BitSet bitSet) {
        System.out.print("\nBitSet : ");
        for (byte b : bitSet.toByteArray()) {
            String s1 = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            System.out.print(" " + s1);
        }
    }
}