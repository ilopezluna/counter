package com.ilopezluna.counter.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.ilopezluna.counter.domain.DataPoint;

/**
 * Created by ignasi on 31/5/15.
 */
public class DataPointRepositoryImpl implements DataPointRepository {

    private final Table table;

    public DataPointRepositoryImpl(Table table) {
        this.table = table;
    }

    public void save(DataPoint dataPoint) {
        Item item = new Item()
                .withPrimaryKey(PRIMARY_KEY, dataPoint.getKey())
                .withKeyComponent(RANGE_KEY, dataPoint.getLocalDate())
                .with(UNIQUES_FIELD, dataPoint.getUniques());
    }

    public void delete(DataPoint dataPoint) {

    }

    public DataPoint get() {
        return null;
    }
}
