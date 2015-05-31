package com.ilopezluna.uniques.domain;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by ignasi on 29/5/15.
 */
public class DataPointTest {

    private final static String DEFAULT_KEY = "default";
    private final static String OTHER_KEY = "other";
    private final static Integer DEFAULT_ID= 0;
    private final static Integer OTHER_ID= 1;
    private final static LocalDate localDate= LocalDate.now();

    @Test
    public void testHit() throws Exception {
        DataPoint base = new DataPoint(DEFAULT_KEY, localDate);
        DataPoint against = new DataPoint(OTHER_KEY, localDate);

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
        DataPoint dataPoint = new DataPoint(DEFAULT_KEY, localDate);
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