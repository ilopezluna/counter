package com.ilopezluna.counter.configuration;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.amazonaws.services.dynamodbv2.model.ScalarAttributeType.*;

/**
 * Created by ignasi on 30/5/15.
 */
public class DynamoDBConfigurationTestIT {

    private final static String TABLE_NAME = "table";
    private AmazonDynamoDBClient client;

    @Before
    public void setUp() throws Exception {
        client = new AmazonDynamoDBClient(new BasicAWSCredentials("xxx", "xxx"));
        client.setEndpoint("http://localhost:8000");
    }

    @After
    public void tearDown() throws Exception {
        client.shutdown();
    }

    @Test
    public void testConfigure() throws Exception {

        Assert.assertEquals(0, client.listTables().getTableNames().size());

        AttributeDefinition attributeDefinition = new AttributeDefinition()
                .withAttributeName("name")
                .withAttributeType(S);

        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList();
        attributeDefinitions.add(attributeDefinition);

        KeySchemaElement keySchemaElement = new KeySchemaElement("name", KeyType.HASH);
        ArrayList<KeySchemaElement> keySchemaElements = new ArrayList();
        keySchemaElements.add(keySchemaElement);

        CreateTableResult productCatalog = client.createTable(
                attributeDefinitions, TABLE_NAME, keySchemaElements,
                new ProvisionedThroughput(1l, 1l));

        Assert.assertEquals(1, client.listTables().getTableNames().size());

        client.deleteTable(TABLE_NAME);

        Assert.assertEquals(0, client.listTables().getTableNames().size());

    }
}