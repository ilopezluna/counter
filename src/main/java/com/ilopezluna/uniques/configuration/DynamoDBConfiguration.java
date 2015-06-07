package com.ilopezluna.uniques.configuration;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

import static com.ilopezluna.uniques.configuration.UniquesProperties.getProperty;

/**
 * Created by ignasi on 29/5/15.
 */
public class DynamoDBConfiguration {

    private static DynamoDB dynamoDB;

    private static void configure() {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient(getAwsCredentials());
        client.setEndpoint(getProperty("endpoint"));

        dynamoDB = new DynamoDB(client);
    }

    private static BasicAWSCredentials getAwsCredentials() {
        return new BasicAWSCredentials(getProperty("username"), getProperty("password"));
    }

    public static DynamoDB getDynamoDB() {
        if (dynamoDB == null) {
            configure();
        }
        return dynamoDB;
    }
}
