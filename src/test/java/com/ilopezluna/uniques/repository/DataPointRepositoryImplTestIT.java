package com.ilopezluna.uniques.repository;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.ilopezluna.uniques.configuration.DynamoDBConfiguration;
import com.ilopezluna.uniques.domain.DataPoint;
import com.ilopezluna.uniques.helper.DynamoDBHelper;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by ignasi on 31/5/15.
 */
public class DataPointRepositoryImplTestIT {

    private static final String DEFAULT_KEY = "key";
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

    }

    @Test
    public void testGet() throws Exception {

    }
}