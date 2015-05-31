package com.ilopezluna.uniques.helper;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.ilopezluna.uniques.configuration.DynamoDBConfiguration;
import com.ilopezluna.uniques.repository.DataPointRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ignasi on 30/5/15.
 */
public class DynamoDBHelperTestIT {

    private DynamoDB dynamoDB;

    @Before
    public void before() {
        dynamoDB = DynamoDBConfiguration.getDynamoDB();
        TableCollection<ListTablesResult> tables = dynamoDB.listTables();
        for (Table table : tables) {
            table.delete();
        }
    }

    @Test
    public void testGetTableInformation() throws Exception {

        DynamoDBHelper.createDataPointTable();
        TableDescription tableDescription = DynamoDBHelper.getTableDescription(DataPointRepository.TABLE_NAME);
        Assert.assertEquals(DataPointRepository.TABLE_NAME, tableDescription.getTableName());
        Assert.assertEquals(1, (long)tableDescription.getProvisionedThroughput().getReadCapacityUnits());
        Assert.assertEquals(1, (long) tableDescription.getProvisionedThroughput().getWriteCapacityUnits());
    }

    @Test
    public void testCreateTable() throws Exception {
        DynamoDBHelper.createDataPointTable();
        TableCollection<ListTablesResult> tables = dynamoDB.listTables();
        for (Table table : tables) {
            Assert.assertEquals(DataPointRepository.TABLE_NAME, table.getTableName());
        }
    }
}