package com.ilopezluna.uniques.configuration;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

/**
 * Created by ignasi on 29/5/15.
 */
public class DynamoDBConfiguration {

    private static DynamoDB dynamoDB;

    private static void configure() {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient(new BasicAWSCredentials("xxx", "xxx"));
        client.setEndpoint("http://localhost:8000");

        dynamoDB = new DynamoDB(client);
    }

    public static DynamoDB getDynamoDB() {
        if (dynamoDB == null) {
            configure();
        }
        return dynamoDB;
    }
}
