package com.ilopezluna.counter.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.ilopezluna.counter.domain.DataPoint;

import java.time.LocalDate;
import java.util.BitSet;

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
        table.putItem(item);
    }

    public void delete(DataPoint dataPoint) {
        table.deleteItem(PRIMARY_KEY, dataPoint.getKey());
    }

    public DataPoint get(String key) {
        Item item = table.getItem(PRIMARY_KEY, key);
        LocalDate localDate = (LocalDate) item.get(RANGE_KEY);
        BitSet uniques = (BitSet) item.get(UNIQUES_FIELD);

        DataPoint dataPoint = new DataPoint(key, localDate);
        dataPoint.setUniques(uniques);
        return dataPoint;
    }
}
