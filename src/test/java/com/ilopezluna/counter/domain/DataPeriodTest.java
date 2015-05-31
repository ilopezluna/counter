package com.ilopezluna.counter.domain;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by ignasi on 29/5/15.
 */
public class DataPeriodTest {

    private final static String DEFAULT_KEY = "default";
    private final static LocalDate DEFAULT_FROM = LocalDate.now();
    private final static LocalDate DEFAULT_TO = LocalDate.now().plusDays(1);
    private final static Integer DEFAULT_ID= 0;
    private final static Integer OTHER_ID= 1;

    @Test
    public void testGetFrom() throws Exception {
        DataPeriod dataPeriod = new DataPeriod(DEFAULT_KEY, DEFAULT_FROM, DEFAULT_TO);
        Assert.assertEquals(DEFAULT_FROM, dataPeriod.getFrom());
    }

    @Test
    public void testGetTo() throws Exception {
        DataPeriod dataPeriod = new DataPeriod(DEFAULT_KEY, DEFAULT_FROM, DEFAULT_TO);
        Assert.assertEquals(DEFAULT_TO, dataPeriod.getTo());
    }

    @Test
    public void testHit() throws Exception {
        DataPeriod base = new DataPeriod(DEFAULT_KEY, DEFAULT_FROM, DEFAULT_TO);
        DataPeriod against = new DataPeriod(DEFAULT_KEY, DEFAULT_FROM, DEFAULT_TO);
        Assert.assertEquals(against.count(), base.count());
        base.hit(DEFAULT_ID);
        against.hit(DEFAULT_ID);
        Assert.assertEquals(against.count(), base.count());

        base.hit(DEFAULT_ID);
        Assert.assertEquals(against.count(), base.count());

        base.hit(OTHER_ID);
        Assert.assertNotEquals(against.count(), base.count());
    }

    @Test
    public void testCount() throws Exception {
        DataPeriod dataPeriod = new DataPeriod(DEFAULT_KEY, DEFAULT_FROM, DEFAULT_TO);
        Assert.assertEquals(0, dataPeriod.count());
        dataPeriod.hit(DEFAULT_ID);
        Assert.assertEquals(1, dataPeriod.count());

        dataPeriod.hit(DEFAULT_ID);
        Assert.assertEquals(1, dataPeriod.count());

        dataPeriod.hit(OTHER_ID);
        Assert.assertEquals(2, dataPeriod.count());
    }
}