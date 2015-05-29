package com.ilopezluna.counter.domain;

import java.time.LocalDate;
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

    public void hit(int id) {
        DataPoint dataPoint = getOrCreate();
        dataPoint.hit(id);
    }

    private DataPoint getOrCreate() {
        LocalDate now = LocalDate.now();
        if ( !dataPoints.containsKey(now) ) {
            DataPoint dataPoint = new DataPoint();
            dataPoints.put(now, dataPoint);
        }

        return dataPoints.get(now);
    }

    public long count() {
        long count = 0l;
        for (DataPoint dataPoint : dataPoints.values()) {
            count += dataPoint.count();
        }
        return count;
    }
}
