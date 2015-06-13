package com.ilopezluna.uniques.service;

import com.ilopezluna.uniques.Application;
import com.ilopezluna.uniques.domain.DataPoint;
import com.ilopezluna.uniques.domain.Key;
import com.ilopezluna.uniques.repository.DataPointRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Random;


/**
 * Created by ignasi on 7/6/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UniquesServiceTestIT {

    private static final int DEFAULT_ID = 1;
    private static final int OTHER_ID = 2;
    private static final String SAMPLE_PATH = "path";

    @Autowired
    private DataPointRepository dataPointRepository;

    @Autowired
    private UniquesService uniquesService;

    @Before
    public void before() {
        final LocalDate now = LocalDate.now();
        final Key key = new Key.KeyBuilder().build();

        dataPointRepository.delete(key, now);
        dataPointRepository.delete(Key.DEFAULT, now);
    }

    @Test
    public void testHit() throws Exception {
        final LocalDate now = LocalDate.now();
        final Key key = new Key.KeyBuilder().build();

        DataPoint dataPoint = dataPointRepository.get(key, now);
        Assert.assertNull(dataPoint);

        uniquesService.hit(now, DEFAULT_ID);
        dataPoint = dataPointRepository.get(key, now);
        Assert.assertNotNull(dataPoint);
        Assert.assertEquals(1, dataPoint.count());

        uniquesService.hit(now, DEFAULT_ID);
        dataPoint = dataPointRepository.get(key, now);
        Assert.assertNotNull(dataPoint);
        Assert.assertEquals(1, dataPoint.count());

        uniquesService.hit(now, OTHER_ID);
        dataPoint = dataPointRepository.get(key, now);
        Assert.assertNotNull(dataPoint);
        Assert.assertEquals(2, dataPoint.count());
    }

    @Test
    public void testHit1() throws Exception {
        final LocalDate now = LocalDate.now();

        DataPoint dataPoint = dataPointRepository.get(Key.DEFAULT, now);
        Assert.assertNull(dataPoint);

        uniquesService.hit(now, DEFAULT_ID);
        dataPoint = dataPointRepository.get(Key.DEFAULT, now);
        Assert.assertNotNull(dataPoint);
        Assert.assertEquals(1, dataPoint.count());

        uniquesService.hit(now, DEFAULT_ID);
        dataPoint = dataPointRepository.get(Key.DEFAULT, now);
        Assert.assertNotNull(dataPoint);
        Assert.assertEquals(1, dataPoint.count());

        uniquesService.hit(now, OTHER_ID);
        dataPoint = dataPointRepository.get(Key.DEFAULT, now);
        Assert.assertNotNull(dataPoint);
        Assert.assertEquals(2, dataPoint.count());
    }

    @Test
    public void testHit2() throws Exception {
        final LocalDate now = LocalDate.now();

        DataPoint dataPoint = dataPointRepository.get(Key.DEFAULT, now);
        Assert.assertNull(dataPoint);

        Key key = new Key.KeyBuilder().add(SAMPLE_PATH).build();
        uniquesService.hit(SAMPLE_PATH, DEFAULT_ID);
        dataPoint = dataPointRepository.get(key, now);
        Assert.assertNotNull(dataPoint);
        Assert.assertEquals(1, dataPoint.count());

        uniquesService.hit(SAMPLE_PATH, DEFAULT_ID);
        dataPoint = dataPointRepository.get(key, now);
        Assert.assertNotNull(dataPoint);
        Assert.assertEquals(1, dataPoint.count());

        uniquesService.hit(SAMPLE_PATH, OTHER_ID);
        dataPoint = dataPointRepository.get(key, now);
        Assert.assertNotNull(dataPoint);
        Assert.assertEquals(2, dataPoint.count());
    }

    @Test
    public void firstPerformanceTest() throws Exception {
        final LocalDate now = LocalDate.now();
        DataPoint dataPoint = dataPointRepository.get(Key.DEFAULT, now);
        Assert.assertNull(dataPoint);

        long startTime = System.currentTimeMillis();
        Random random = new Random();
        for (int i =0; i < 1000;i++) {
            uniquesService.hit(random.nextInt(1000));
//            uniquesService.hit(i);
        }

        long estimatedTime = (System.currentTimeMillis() - startTime)/1000;
        System.out.println("Elapsed time: " + estimatedTime + " seconds.");
        System.out.println("Total uniques:" + uniquesService.count(now));
    }
}