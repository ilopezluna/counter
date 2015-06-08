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
}