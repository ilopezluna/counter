package com.ilopezluna.uniques.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.ilopezluna.uniques.domain.DataPoint;
import com.ilopezluna.uniques.helper.BitSetHelper;

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
                .withKeyComponent(RANGE_KEY, dataPoint.getLocalDate().toString())
                .with(UNIQUES_FIELD, dataPoint.getUniques().toByteArray());
        table.putItem(item);
    }

    public void delete(DataPoint dataPoint) {
        table.deleteItem(PRIMARY_KEY, dataPoint.getKey());
    }

    public DataPoint get(String key, LocalDate localDate) {
        KeyAttribute keyAttribute = new KeyAttribute(PRIMARY_KEY, key);
        KeyAttribute rangeAttribute = new KeyAttribute(RANGE_KEY, localDate.toString());

        Item item = table.getItem(keyAttribute, rangeAttribute);
        byte[] bytes = (byte[]) item.get(UNIQUES_FIELD);
        BitSet uniques = BitSetHelper.fromByteArray(bytes);

        DataPoint dataPoint = new DataPoint(key, localDate);
        dataPoint.setUniques(uniques);
        return dataPoint;
    }
}
