package com.ilopezluna.counter.repository;

import com.ilopezluna.counter.domain.DataPoint;

/**
 * Created by ignasi on 31/5/15.
 */
public interface DataPointRepository {

    String TABLE_NAME = "DataPoint";
    String PRIMARY_KEY = "K";
    String RANGE_KEY = "R";
    String UNIQUES_FIELD = "U";

    void save(DataPoint dataPoint);
    void delete(DataPoint dataPoint);
    DataPoint get();
}
