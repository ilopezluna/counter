package com.ilopezluna.uniques.service;

import com.amazonaws.util.StringUtils;
import com.ilopezluna.uniques.domain.DataPoint;
import com.ilopezluna.uniques.domain.Key;
import com.ilopezluna.uniques.repository.DataPointRepository;

import java.time.LocalDate;

/**
 * Created by ignasi on 2/6/15.
 */
public class UniquesService {

    private static final String EMPTY = "";
    private final DataPointRepository dataPointRepository;

    public UniquesService(DataPointRepository dataPointRepository) {
        this.dataPointRepository = dataPointRepository;
    }

    public void hit(String path, LocalDate localDate, int id) {
        Key key = getKey(path);
        hit(localDate, id, key);
    }

    public void hit(String path, int id) {
        LocalDate now = LocalDate.now();
        Key key = getKey(path);
        hit(now, id, key);
    }

    public void hit(LocalDate localDate, int id) {
        Key key = getKey(EMPTY);
        hit(localDate, id, key);
    }

    private Key getKey(String path) {
        if ( StringUtils.isNullOrEmpty(path) ) {
            return new Key.KeyBuilder()
                    .build();
        }
        else {
            return new Key.KeyBuilder()
                    .add(path)
                    .build();
        }
    }

    private void hit(LocalDate localDate, int id, Key key) {
        DataPoint dataPoint = getOrCreateDataPoint(localDate, key);
        dataPoint.hit(id);
        dataPointRepository.save(dataPoint);
        DataPoint dataPoint1 = dataPointRepository.get(key, localDate);
        dataPoint1.count();
    }

    private DataPoint getOrCreateDataPoint(LocalDate localDate, Key key) {
        DataPoint dataPoint = dataPointRepository.get(key, localDate);
        if (dataPoint == null) {
            dataPoint = new DataPoint(key, localDate);
        }
        return dataPoint;
    }
}
