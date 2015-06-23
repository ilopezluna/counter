package com.ilopezluna.uniques.service;

import com.amazonaws.util.StringUtils;
import com.ilopezluna.uniques.domain.DataPoint;
import com.ilopezluna.uniques.domain.Key;
import com.ilopezluna.uniques.repository.DataPointRepository;
import com.jcabi.aspects.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Created by ignasi on 2/6/15.
 */
@Service
public class UniquesService {

    @Autowired
    private DataPointRepository dataPointRepository;

    public void hit(String path, int id) {
        Key key = getKey(path);
        hit(LocalDate.now(), id, key);
    }

    public void hit(LocalDate localDate, int id) {
        hit(localDate, id, Key.DEFAULT);
    }

    @Loggable
    public void hit(int id) {
        hit(LocalDate.now(), id, Key.DEFAULT);
    }

    private Key getKey(String path) {
        if ( StringUtils.isNullOrEmpty(path) ) {
            return Key.DEFAULT;
        }
        else {
            return new Key.KeyBuilder()
                    .add(path)
                    .build();
        }
    }

    @Loggable
    private void hit(LocalDate localDate, int id, Key key) {
        DataPoint dataPoint = getOrCreateDataPoint(localDate, key);
        dataPoint.hit(id);
        dataPointRepository.save(dataPoint);
    }

    private DataPoint getOrCreateDataPoint(LocalDate localDate, Key key) {
        DataPoint dataPoint = dataPointRepository.get(key, localDate);
        if (dataPoint == null) {
            dataPoint = new DataPoint(key, localDate);
        }
        return dataPoint;
    }

    @Loggable
    public int count(LocalDate localDate) {
        final DataPoint dataPoint = dataPointRepository.get(Key.DEFAULT, localDate);
        return dataPoint == null ? 0 : dataPoint.count();
    }
}
