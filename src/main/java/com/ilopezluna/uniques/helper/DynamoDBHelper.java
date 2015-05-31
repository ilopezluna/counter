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

    private static void deleteTable(String tableName) {
        Table table = dynamoDB.getTable(tableName);
        try {
            System.out.println("Issuing DeleteTable request for " + tableName);
            table.delete();
            System.out.println("Waiting for " + tableName
                    + " to be deleted...this may take a while...");
            table.waitForDelete();

        } catch (Exception e) {
            System.err.println("DeleteTable request failed for " + tableName);
            System.err.println(e.getMessage());
        }
    }

    public static void createTable(
            String tableName, long readCapacityUnits, long writeCapacityUnits,
            String hashKeyName, String hashKeyType) throws InterruptedException {

        createTable(tableName, readCapacityUnits, writeCapacityUnits,
                hashKeyName, hashKeyType, null, null);
    }

    private static void createTable(
        String tableName, long readCapacityUnits, long writeCapacityUnits,
        String hashKeyName, String hashKeyType,
        String rangeKeyName, String rangeKeyType) throws InterruptedException {

        ArrayList<KeySchemaElement> keySchema = getKeySchemaElements(hashKeyName, rangeKeyName);
        ArrayList<AttributeDefinition> attributeDefinitions = getAttributeDefinitions(hashKeyName, hashKeyType, rangeKeyName, rangeKeyType);
        ProvisionedThroughput provisionedThroughput = getProvisionedThroughput(readCapacityUnits, writeCapacityUnits);
        CreateTableRequest request = getCreateTableRequest(tableName, keySchema, attributeDefinitions, provisionedThroughput);


        System.out.println("Issuing CreateTable request for " + tableName);
        Table table = dynamoDB.createTable(request);
        System.out.println("Waiting for " + tableName
                + " to be created...this may take a while...");
        table.waitForActive();
    }

    private static ArrayList<KeySchemaElement> getKeySchemaElements(String hashKeyName, String rangeKeyName) {
        ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
        keySchema.add(new KeySchemaElement()
                .withAttributeName(hashKeyName)
                .withKeyType(KeyType.HASH));
        if (rangeKeyName != null) {
            keySchema.add(new KeySchemaElement()
                    .withAttributeName(rangeKeyName)
                    .withKeyType(KeyType.RANGE));
        }
        return keySchema;
    }

    private static ArrayList<AttributeDefinition> getAttributeDefinitions(String hashKeyName, String hashKeyType, String rangeKeyName, String rangeKeyType) {
        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName(hashKeyName)
                .withAttributeType(hashKeyType));

        if (rangeKeyName != null) {
            attributeDefinitions.add(new AttributeDefinition()
                    .withAttributeName(rangeKeyName)
                    .withAttributeType(rangeKeyType));
        }
        return attributeDefinitions;
    }

    private static CreateTableRequest getCreateTableRequest(String tableName, ArrayList<KeySchemaElement> keySchema, ArrayList<AttributeDefinition> attributeDefinitions, ProvisionedThroughput provisionedThroughput) {
        return new CreateTableRequest()
                        .withTableName(tableName)
                        .withKeySchema(keySchema)
                        .withAttributeDefinitions(attributeDefinitions)
                        .withProvisionedThroughput(provisionedThroughput);
    }

    private static ProvisionedThroughput getProvisionedThroughput(long readCapacityUnits, long writeCapacityUnits) {
        return new ProvisionedThroughput()
                        .withReadCapacityUnits(readCapacityUnits)
                        .withWriteCapacityUnits(writeCapacityUnits);
    }

    public static Table createDataPointTable() {

        try {

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
                            .withReadCapacityUnits(5L)
                            .withWriteCapacityUnits(6L));

            System.out.println("Issuing CreateTable request for " + DataPointRepository.TABLE_NAME);
            Table table = dynamoDB.createTable(request);

            System.out.println("Waiting for " + DataPointRepository.TABLE_NAME
                    + " to be created...this may take a while...");
            table.waitForActive();
            return table;

        } catch (Exception e) {
            System.err.println("CreateTable request failed for " + DataPointRepository.TABLE_NAME);
            System.err.println(e.getMessage());
        }

        return null;
    }

}
