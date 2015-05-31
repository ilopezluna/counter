package com.ilopezluna.uniques.helper;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.ilopezluna.uniques.configuration.DynamoDBConfiguration;
import com.ilopezluna.uniques.repository.DataPointRepository;

import java.util.ArrayList;

/**
 * Created by ignasi on 30/5/15.
 */
public class DynamoDBHelper {

    private static DynamoDB dynamoDB = DynamoDBConfiguration.getDynamoDB();

    public static TableDescription getTableDescription(String tableName) {
        return dynamoDB.getTable(tableName).describe();
    }

    public static Table createDataPointTable() throws InterruptedException {
        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName(DataPointRepository.PRIMARY_KEY)
                .withAttributeType("S"));
        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName(DataPointRepository.RANGE_KEY)
                .withAttributeType("S"));

        ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
        keySchema.add(new KeySchemaElement()
                .withAttributeName(DataPointRepository.PRIMARY_KEY)
                .withKeyType(KeyType.HASH));

        keySchema.add(new KeySchemaElement()
                .withAttributeName(DataPointRepository.RANGE_KEY)
                .withKeyType(KeyType.RANGE));

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(DataPointRepository.TABLE_NAME)
                .withKeySchema(keySchema)
                .withAttributeDefinitions(attributeDefinitions)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(1L)
                        .withWriteCapacityUnits(1L));

        System.out.println("Issuing CreateTable request for " + DataPointRepository.TABLE_NAME);
        Table table = dynamoDB.createTable(request);

        System.out.println("Waiting for " + DataPointRepository.TABLE_NAME
                + " to be created...this may take a while...");
        table.waitForActive();
        return table;
    }

}
