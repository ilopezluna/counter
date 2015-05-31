package com.ilopezluna.uniques.helper;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.ilopezluna.uniques.configuration.DynamoDBConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ignasi on 30/5/15.
 */
public class DynamoDBHelperTestIT {

    private static final String TABLE_NAME = "table";
    private static final String PRIMARY_KEY = "hashKeyName";
    private static final String HASH_KEY_TYPE = "N";

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

        DynamoDBHelper.createTable(TABLE_NAME, 1, 1, PRIMARY_KEY, HASH_KEY_TYPE);
        TableDescription tableDescription = DynamoDBHelper.getTableDescription(TABLE_NAME);
        Assert.assertEquals(TABLE_NAME, tableDescription.getTableName());
        Assert.assertEquals(1, (long)tableDescription.getProvisionedThroughput().getReadCapacityUnits());
        Assert.assertEquals(1, (long) tableDescription.getProvisionedThroughput().getWriteCapacityUnits());
    }

    @Test
    public void testCreateTable() throws Exception {
        DynamoDBHelper.createTable(TABLE_NAME, 1, 1, PRIMARY_KEY, HASH_KEY_TYPE);
        TableCollection<ListTablesResult> tables = dynamoDB.listTables();
        for (Table table : tables) {
            Assert.assertEquals(TABLE_NAME, table.getTableName());
        }
    }
}