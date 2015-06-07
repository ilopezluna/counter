package com.ilopezluna.uniques.repository;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.ilopezluna.uniques.domain.DataPeriod;
import com.ilopezluna.uniques.domain.DataPoint;
import com.ilopezluna.uniques.domain.Key;
import com.ilopezluna.uniques.helper.BitSetHelper;
import com.jcabi.aspects.Loggable;

import java.time.LocalDate;
import java.util.BitSet;
import java.util.Iterator;

/**
 * Created by ignasi on 31/5/15.
 */
public class DataPointRepositoryImpl implements DataPointRepository {

    private final Table table;

    public DataPointRepositoryImpl(Table table) {
        this.table = table;
    }

    @Loggable
    public void save(DataPoint dataPoint) {
        Item item = new Item()
                .withPrimaryKey(PRIMARY_KEY, dataPoint.getKey().toString())
                .withKeyComponent(RANGE_KEY, dataPoint.getLocalDate().toString())
                .with(UNIQUES_FIELD, dataPoint.getUniques().toByteArray());
        table.putItem(item);
    }

    public void delete(Key key, LocalDate localDate) {
        KeyAttribute keyAttribute = new KeyAttribute(PRIMARY_KEY, key.toString());
        KeyAttribute rangeAttribute = new KeyAttribute(RANGE_KEY, localDate.toString());

        table.deleteItem(keyAttribute, rangeAttribute);
    }

    public DataPoint get(Key key, LocalDate localDate) {
        KeyAttribute keyAttribute = new KeyAttribute(PRIMARY_KEY, key.toString());
        KeyAttribute rangeAttribute = new KeyAttribute(RANGE_KEY, localDate.toString());

        Item item = table.getItem(keyAttribute, rangeAttribute);
        if (item != null) {
            return getDataPointFromItem(key, item);
        }
        return null;
    }

    private DataPoint getDataPointFromItem(Key key, Item item) {
        LocalDate localDate = LocalDate.parse((String) item.get(RANGE_KEY));
        byte[] bytes = (byte[]) item.get(UNIQUES_FIELD);
        BitSet uniques = BitSetHelper.fromByteArray(bytes);

        DataPoint dataPoint = new DataPoint(key, localDate);
        dataPoint.setUniques(uniques);
        return dataPoint;
    }

    public DataPeriod getDataPeriod(Key key, LocalDate from, LocalDate to) {
        final DataPeriod dataPeriod = new DataPeriod(from, to);

        RangeKeyCondition rangeKeyCondition = new RangeKeyCondition(RANGE_KEY);
        rangeKeyCondition.between(from.toString(), to.toString());

        QuerySpec spec = new QuerySpec()
                .withHashKey(PRIMARY_KEY, key.toString())
                .withRangeKeyCondition(rangeKeyCondition);

        ItemCollection<QueryOutcome> items = table.query(spec);
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            DataPoint dataPoint = getDataPointFromItem(key, iterator.next());
            dataPeriod.addDataPoint(dataPoint);
        }
        return dataPeriod;
    }
}
