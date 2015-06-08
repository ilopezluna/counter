package com.ilopezluna.uniques.helper;

import com.ilopezluna.uniques.domain.DataPoint;
import com.ilopezluna.uniques.domain.Key;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by ignasi on 8/6/15.
 */
public class DataPointHelperTest {

    @Test
    public void testMerge() throws Exception {
        DataPoint base = new DataPoint(Key.DEFAULT, LocalDate.now());
        DataPoint against = new DataPoint(Key.DEFAULT, LocalDate.now());

        DataPoint and = DataPointHelper.merge(base, against, Operation.AND);
        DataPoint or = DataPointHelper.merge(base, against, Operation.OR);
        Assert.assertEquals(0, and.count());
        Assert.assertEquals(0, or.count());

        base.hit(1);
        and = DataPointHelper.merge(base, against, Operation.AND);
        or = DataPointHelper.merge(base, against, Operation.OR);

        Assert.assertEquals(0, and.count());
        Assert.assertEquals(1, or.count());
        Assert.assertTrue(or.getUniques().get(1));

        against.hit(1);
        and = DataPointHelper.merge(base, against, Operation.AND);
        or = DataPointHelper.merge(base, against, Operation.OR);

        Assert.assertEquals(1, and.count());
        Assert.assertEquals(1, or.count());
        Assert.assertTrue(and.getUniques().get(1));
        Assert.assertTrue(or.getUniques().get(1));


    }
}