package com.ilopezluna.uniques.helper;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.BitSet;


/**
 * Created by ignasi on 8/6/15.
 */
public class BitSetHelperTest {

    private final static Logger logger = LoggerFactory.getLogger(BitSetHelperTest.class);

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
        bitSet.set(1);
        bitSet.set(100);

        BitSet fromByteArray = BitSetHelper.fromByteArray(bitSet.toByteArray());

        Assert.assertEquals(2, fromByteArray.cardinality());
        Assert.assertTrue(fromByteArray.get(1));
        Assert.assertTrue(fromByteArray.get(100));

    }
}