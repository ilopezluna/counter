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

    private final static int DEFAULT_INDEX = 1;

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
}