package com.ilopezluna.counter.domain;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ignasi on 29/5/15.
 */
public class DataPointTest {

    private final static Integer DEFAULT_ID= 0;
    private final static Integer OTHER_ID= 1;

    @Test
    public void testHit() throws Exception {
        DataPoint base = new DataPoint();
        DataPoint against = new DataPoint();

        Assert.assertEquals(base.count(), against.count());
        base.hit(DEFAULT_ID);
        Assert.assertNotEquals(base.count(), against.count());
        against.hit(DEFAULT_ID);
        Assert.assertEquals(base.count(), against.count());

        base.hit(DEFAULT_ID);
        Assert.assertEquals(base.count(), against.count());
    }

    @Test
    public void testCount() throws Exception {
        DataPoint dataPoint = new DataPoint();
        Assert.assertEquals(0, dataPoint.count());

        dataPoint.hit(DEFAULT_ID);
        Assert.assertEquals(1, dataPoint.count());

        dataPoint.hit(DEFAULT_ID);
        Assert.assertEquals(1, dataPoint.count());

        dataPoint.hit(OTHER_ID);
        Assert.assertEquals(2, dataPoint.count());

        dataPoint.hit(OTHER_ID);
        Assert.assertEquals(2, dataPoint.count());
    }

}