package com.ilopezluna.uniques.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.ilopezluna.uniques.domain.DataPoint;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                .withKeyComponent(RANGE_KEY, dataPoint.getLocalDate().toString())
                .with(UNIQUES_FIELD, dataPoint.getUniques().toByteArray());
        table.putItem(item);
    }

    public void delete(DataPoint dataPoint) {
        table.deleteItem(PRIMARY_KEY, dataPoint.getKey());
    }

    public DataPoint get(String key) {
        Item item = table.getItem(PRIMARY_KEY, key);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        LocalDate localDate = LocalDate.parse((String) item.get(RANGE_KEY), formatter);
        BitSet uniques = (BitSet) item.get(UNIQUES_FIELD);

        DataPoint dataPoint = new DataPoint(key, localDate);
        dataPoint.setUniques(uniques);
        return dataPoint;
    }
}
