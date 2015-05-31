package com.ilopezluna.counter.configuration;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ignasi on 30/5/15.
 */
public class DynamoDBConfigurationTestIT {

    @Test
    public void testConfigure() throws Exception {
        DynamoDB dynamoDB = DynamoDBConfiguration.getDynamoDB();
        Assert.assertNotNull(dynamoDB);
    }
}