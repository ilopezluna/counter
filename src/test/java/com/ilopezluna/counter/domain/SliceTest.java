package com.ilopezluna.counter.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ignasi on 29/5/15.
 */
public class SliceTest {

    private final static int DEFAULT_INDEX = 0;
    private static final int DEFAULT_POSITION = 0;
    private static final int OTHER_POSITION = 1;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetIndex() throws Exception {
        Slice slice = new Slice(DEFAULT_INDEX);
        Assert.assertEquals(DEFAULT_INDEX, slice.getIndex());
    }

    @Test
    public void testHit() throws Exception {
        Slice base = new Slice(DEFAULT_INDEX);
        Slice against = new Slice(DEFAULT_INDEX+1);

        Assert.assertEquals(base.count(), against.count());
        base.hit(DEFAULT_POSITION);
        Assert.assertNotEquals(base.count(), against.count());
        against.hit(DEFAULT_POSITION);
        Assert.assertEquals(base.count(), against.count());

        base.hit(DEFAULT_POSITION);
        Assert.assertEquals(base.count(), against.count());
    }

    @Test
    public void testCount() throws Exception {
        Slice slice = new Slice(DEFAULT_INDEX);
        Assert.assertEquals(0, slice.count());

        slice.hit(DEFAULT_POSITION);
        Assert.assertEquals(1, slice.count());

        slice.hit(DEFAULT_POSITION);
        Assert.assertEquals(1, slice.count());

        slice.hit(OTHER_POSITION);
        Assert.assertEquals(2, slice.count());
    }
}