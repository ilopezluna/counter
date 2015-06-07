package com.ilopezluna.uniques.domain;

import java.time.LocalDate;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ignasi on 29/5/15.
 */
public class DataPeriod {

    private final LocalDate from;
    private final LocalDate to;

    private final Map<LocalDate, DataPoint> dataPoints = new HashMap<LocalDate, DataPoint>();

    public DataPeriod(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public int count() {
        BitSet bitSet = new BitSet();
        for (DataPoint dataPoint : dataPoints.values()) {
            bitSet.or(dataPoint.getUniques());
        }
        return bitSet.cardinality();
    }

    public void addDataPoint(DataPoint dataPoint) {
        dataPoints.put(dataPoint.getLocalDate(), dataPoint);
    }
}
