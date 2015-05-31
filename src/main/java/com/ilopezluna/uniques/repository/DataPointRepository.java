package com.ilopezluna.uniques.repository;

import com.ilopezluna.uniques.domain.DataPoint;

import java.time.LocalDate;

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
    DataPoint get(String key, LocalDate localDate);
}
