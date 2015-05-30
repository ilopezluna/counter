package com.ilopezluna.counter.helper;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.ilopezluna.counter.configuration.DynamoDBConfiguration;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ignasi on 30/5/15.
 */
public class DynamoDBHelperTestIT {

    private static final String TABLE_NAME = "table";
    private static final String PRIMARY_KEY = "hashKeyName";
    private static final String HASH_KEY_TYPE = "N";
    private static final int PRIMARY_KEY_VALUE = 1;

    @Test
    public void testGetTableInformation() throws Exception {

        DynamoDB dynamoDB = DynamoDBConfiguration.getDynamoDB();
        Assert.assertNotNull(dynamoDB);

        TableCollection<ListTablesResult> tables = dynamoDB.listTables();
        for (Table table : tables) {
            table.delete();
        }
        DynamoDBHelper.createTable(TABLE_NAME, 1, 1, PRIMARY_KEY, HASH_KEY_TYPE);
        Table table = dynamoDB.getTable(TABLE_NAME);

        Item item = new Item()
                .withPrimaryKey(PRIMARY_KEY, PRIMARY_KEY_VALUE);
        table.putItem(item);

        QuerySpec spec = new QuerySpec()
                .withHashKey(PRIMARY_KEY, PRIMARY_KEY_VALUE);

        ItemCollection<QueryOutcome> items = table.query(spec);
        for (Item item1 : items) {
            System.out.println(item1.toJSON());
            Assert.assertEquals("{\"hashKeyName\":1}", item1.toJSON());
        }
        Assert.assertEquals(1, items.getTotalCount());
        table.delete();
    }
}