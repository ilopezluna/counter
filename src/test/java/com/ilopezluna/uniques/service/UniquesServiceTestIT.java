package com.ilopezluna.uniques.service;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.ilopezluna.uniques.configuration.DynamoDBConfiguration;
import com.ilopezluna.uniques.domain.DataPoint;
import com.ilopezluna.uniques.domain.Key;
import com.ilopezluna.uniques.helper.DynamoDBHelper;
import com.ilopezluna.uniques.repository.DataPointRepositoryImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Random;


/**
 * Created by ignasi on 7/6/15.
 */
public class UniquesServiceTestIT {

    private static final int DEFAULT_ID = 1;
    private static final int OTHER_ID = 2;
    private static final String SAMPLE_PATH = "path";
    private UniquesService uniquesService;
    private DataPointRepositoryImpl dataPointRepository;

    @Before
    public void setUp() throws Exception {
        DynamoDB dynamoDB = DynamoDBConfiguration.getDynamoDB();
        TableCollection<ListTablesResult> tables = dynamoDB.listTables();
        for (Table table : tables) {
            table.delete();
        }
        Table dataPointTable = DynamoDBHelper.createDataPointTable();
        dataPointRepository = new DataPointRepositoryImpl(dataPointTable);
        uniquesService = new UniquesService(dataPointRepository);
    }

    @After
    public void tearDown() throws Exception {

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