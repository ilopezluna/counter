package com.ilopezluna.counter.domain;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ignasi on 29/5/15.
 */
public class DataPointTest {

    private static final Integer DEFAULT_SLICE = 0;
    private static final Integer OTHER_SLICE = 1;
    private static final int DEFAULT_POSITION = 0;
    private static final Integer OTHER_POSITION = 1;

    @Test
    public void testHit() throws Exception {
        DataPoint base = new DataPoint();
        DataPoint against = new DataPoint();

        Assert.assertEquals(base.count(), against.count());
        base.hit(DEFAULT_SLICE, DEFAULT_POSITION);
        Assert.assertNotEquals(base.count(), against.count());
        against.hit(DEFAULT_SLICE, DEFAULT_POSITION);
        Assert.assertEquals(base.count(), against.count());

        base.hit(DEFAULT_SLICE, DEFAULT_POSITION);
        Assert.assertEquals(base.count(), against.count());
    }

    @Test
    public void testCount() throws Exception {
        DataPoint dataPoint = new DataPoint();
        Assert.assertEquals(0, dataPoint.count());

        dataPoint.hit(DEFAULT_SLICE, DEFAULT_POSITION);
        Assert.assertEquals(1, dataPoint.count());

        dataPoint.hit(DEFAULT_SLICE, DEFAULT_POSITION);
        Assert.assertEquals(1, dataPoint.count());

        dataPoint.hit(DEFAULT_SLICE, OTHER_POSITION);
        Assert.assertEquals(2, dataPoint.count());

        dataPoint.hit(OTHER_SLICE, DEFAULT_POSITION);
        Assert.assertEquals(3, dataPoint.count());

        dataPoint.hit(OTHER_SLICE, OTHER_POSITION);
        Assert.assertEquals(4, dataPoint.count());

        dataPoint.hit(OTHER_SLICE, OTHER_POSITION);
        Assert.assertEquals(4, dataPoint.count());
    }

}