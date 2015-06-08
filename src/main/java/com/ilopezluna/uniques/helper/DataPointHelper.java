package com.ilopezluna.uniques.helper;

import com.ilopezluna.uniques.domain.DataPoint;

import java.util.BitSet;

/**
 * Created by ignasi on 8/6/15.
 */
public class DataPointHelper {

    public static DataPoint merge(DataPoint base, DataPoint against, Operation operation) {
        if ( canBeMerged(base, against)) {
            final DataPoint result = new DataPoint(base.getKey(), base.getLocalDate());
            final BitSet merged = BitSetHelper.merge(base.getUniques(), against.getUniques(), operation);
            result.setUniques(merged);
            return result;
        }
        throw new RuntimeException("Unable to merge given dataPoints");
    }

    private static boolean canBeMerged(DataPoint base, DataPoint against) {
        return
                base.getKey() != null
                && base.getKey().equals(against.getKey())
                && base.getLocalDate() != null
                && base.getLocalDate().equals(against.getLocalDate());
    }
}
