package com.ilopezluna.uniques.configuration;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.ilopezluna.uniques.repository.DataPointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;


/**
 * Created by ignasi on 29/5/15.
 */
@Configuration
public class DynamoDBConfiguration {

    private final static Logger logger = LoggerFactory.getLogger(DynamoDBConfiguration.class);

    @Autowired
    private Environment env;

//    @Value("${username}")
//    String name;

    @Bean
    public DynamoDB dynamoDB() {

        logger.info("Initializing DynamoDB client...");
        logger.info("Username: " + env.getProperty("username"));
//        logger.info("Username: " + name);

        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(env.getProperty("username"), env.getProperty("password"));
        AmazonDynamoDBClient client = new AmazonDynamoDBClient(basicAWSCredentials);
        client.setEndpoint(env.getProperty("endpoint"));
        return new DynamoDB(client);
    }

    @Bean
    public Table table(DynamoDB dynamoDB) throws InterruptedException {
        ArrayList<AttributeDefinition> attributeDefinitions = getAttributeDefinitions();
        ArrayList<KeySchemaElement> keySchema = getKeySchemaElements();

        Table table = dynamoDB.getTable(DataPointRepository.TABLE_NAME);
        if (table != null) {
            logger.info("Creating table: " + DataPointRepository.TABLE_NAME);
            CreateTableRequest request = getCreateTableRequest(attributeDefinitions, keySchema);
            table = dynamoDB.createTable(request);
            logger.info("Waiting for " + DataPointRepository.TABLE_NAME
                    + " to be created...this may take a while...");
            table.waitForActive();
        }
        return table;
    }

    private CreateTableRequest getCreateTableRequest(ArrayList<AttributeDefinition> attributeDefinitions, ArrayList<KeySchemaElement> keySchema) {
        return new CreateTableRequest()
                .withTableName(DataPointRepository.TABLE_NAME)
                .withKeySchema(keySchema)
                .withAttributeDefinitions(attributeDefinitions)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(1L)
                        .withWriteCapacityUnits(1L));
    }

    private ArrayList<KeySchemaElement> getKeySchemaElements() {
        ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
        keySchema.add(new KeySchemaElement()
                .withAttributeName(DataPointRepository.PRIMARY_KEY)
                .withKeyType(KeyType.HASH));

        keySchema.add(new KeySchemaElement()
                .withAttributeName(DataPointRepository.RANGE_KEY)
                .withKeyType(KeyType.RANGE));
        return keySchema;
    }

    private ArrayList<AttributeDefinition> getAttributeDefinitions() {
        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName(DataPointRepository.PRIMARY_KEY)
                .withAttributeType("S"));
        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName(DataPointRepository.RANGE_KEY)
                .withAttributeType("S"));
        return attributeDefinitions;
    }
}
