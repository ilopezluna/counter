package com.ilopezluna.uniques.repository;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.ilopezluna.uniques.configuration.DynamoDBConfiguration;
import com.ilopezluna.uniques.domain.DataPeriod;
import com.ilopezluna.uniques.domain.DataPoint;
import com.ilopezluna.uniques.domain.Key;
import com.ilopezluna.uniques.helper.DynamoDBHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.BitSet;

/**
 * Created by ignasi on 31/5/15.
 */
public class DataPointRepositoryImplTestIT {

    private final static Key DEFAULT_KEY = new Key.KeyBuilder().add("default").build();
    private DataPointRepository dataPointRepository;

    @Before
    public void setUp() throws Exception {
        DynamoDB dynamoDB = DynamoDBConfiguration.getDynamoDB();
        TableCollection<ListTablesResult> tables = dynamoDB.listTables();
        for (Table table : tables) {
            table.delete();
        }
        Table dataPointTable = DynamoDBHelper.createDataPointTable();
        dataPointRepository = new DataPointRepositoryImpl(dataPointTable);
    }

    @Test
    public void testSave() throws Exception {
        DataPoint dataPoint = new DataPoint(DEFAULT_KEY, LocalDate.now());
        dataPoint.hit(1);
        dataPointRepository.save(dataPoint);
    }

    @Test
    public void testDelete() throws Exception {
        LocalDate now = LocalDate.now();
        DataPoint dataPoint = new DataPoint(DEFAULT_KEY, now);
        dataPoint.hit(1);
        dataPointRepository.save(dataPoint);
        DataPoint fromDynamo = dataPointRepository.get(dataPoint.getKey(), now);
        Assert.assertNotNull(fromDynamo);

        dataPointRepository.delete(dataPoint.getKey(), now);
        fromDynamo = dataPointRepository.get(dataPoint.getKey(), now);
        Assert.assertNull(fromDynamo);

    }

    @Test
    public void testGet() throws Exception {
        LocalDate now = LocalDate.now();
        DataPoint dataPoint = new DataPoint(DEFAULT_KEY, now);
        dataPoint.hit(1);
        dataPointRepository.save(dataPoint);
        DataPoint fromDynamo = dataPointRepository.get(dataPoint.getKey(), now);
        Assert.assertNotNull(fromDynamo);
        Assert.assertEquals(dataPoint.count(), fromDynamo.count());
        BitSet uniques = (BitSet) dataPoint.getUniques().clone();
        uniques.or(fromDynamo.getUniques());
        Assert.assertEquals( 1, uniques.cardinality() );
    }

    @Test
    public void testGetDataPeriod() throws Exception {
        LocalDate from = LocalDate.now().minusDays(10);
        LocalDate to = LocalDate.now();

        DataPeriod dataPeriod = dataPointRepository.getDataPeriod(DEFAULT_KEY, from, to);
        Assert.assertNotNull(dataPeriod);
        Assert.assertEquals(0, dataPeriod.count());

        LocalDate minus4Days = LocalDate.now().minusDays(4);
        DataPoint dataPoint = new DataPoint(DEFAULT_KEY, minus4Days);
        dataPoint.hit(1);
        dataPointRepository.save(dataPoint);

        dataPeriod = dataPointRepository.getDataPeriod(DEFAULT_KEY, from, to);
        Assert.assertNotNull(dataPeriod);
        Assert.assertEquals(1, dataPeriod.count());


        LocalDate minus8Days = LocalDate.now().minusDays(8);
        dataPoint = new DataPoint(DEFAULT_KEY, minus8Days);
        dataPoint.hit(1);
        dataPointRepository.save(dataPoint);

        dataPeriod = dataPointRepository.getDataPeriod(DEFAULT_KEY, from, to);
        Assert.assertNotNull(dataPeriod);
        Assert.assertEquals(1, dataPeriod.count());

        dataPoint = new DataPoint(DEFAULT_KEY, minus4Days);
        dataPoint.hit(2);
        dataPointRepository.save(dataPoint);

        dataPeriod = dataPointRepository.getDataPeriod(DEFAULT_KEY, from, to);
        Assert.assertNotNull(dataPeriod);
        Assert.assertEquals(2   , dataPeriod.count());
    }
}