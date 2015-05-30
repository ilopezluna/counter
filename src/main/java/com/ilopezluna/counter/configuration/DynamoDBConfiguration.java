package com.ilopezluna.counter.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

/**
 * Created by ignasi on 29/5/15.
 */
public class DynamoDBConfiguration {

    public static DynamoDB configure() {
        AWSCredentials awsCredentials = new BasicAWSCredentials("xxx", "xxx");

        AmazonDynamoDBClient client = new AmazonDynamoDBClient(awsCredentials);
        client.setEndpoint("http://localhost:8000");
        client.setRegion(Region.getRegion(Regions.EU_WEST_1));

        DynamoDB dynamoDB = new DynamoDB(client);
        return dynamoDB;
    }
}
