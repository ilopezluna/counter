package com.ilopezluna.uniques.helper;

import com.ilopezluna.uniques.domain.DataPeriod;
import com.ilopezluna.uniques.domain.DataPoint;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by ignasi on 9/6/15.
 */
public class DataPeriodHelper {

    public static DataPeriod merge(DataPeriod base, DataPeriod against, Operation operation) {
        if ( canBeMerged(base, against)) {
            final DataPeriod result = new DataPeriod(base.getFrom(), base.getTo());
            Map<LocalDate, DataPoint> dataPoints = base.getDataPoints();
            for (LocalDate localDate : dataPoints.keySet()) {
                DataPoint merged = DataPointHelper.merge(base.getDataPoint(localDate), against.getDataPoint(localDate), operation);
                result.addDataPoint(merged);
            }

            return result;
        }
        throw new RuntimeException("Unable to merge given dataPeriods");
    }

    private static boolean canBeMerged(DataPeriod base, DataPeriod against) {
        return
                base.getFrom() != null
                && base.getFrom().equals(against.getFrom())
                && base.getTo() != null
                && base.getTo().equals(against.getTo());
    }
}
