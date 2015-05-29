package com.ilopezluna.counter.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ignasi on 29/5/15.
 */
public class DataPoint {

    private static final Integer MAX_SLICE = 100;

    private Map<Integer, Slice> slices = new HashMap<Integer, Slice>();

    public void hit(int id) {
        int sliceId = getSliceId(id);
        int position = getPosition(id);
        hit(sliceId, position);
    }

    public int getSliceId(int id) {
        return id / MAX_SLICE;
    }

    public static int getPosition(long id) {
        return (int) (id % MAX_SLICE);
    }

    private void hit(Integer sliceId, int position) {
        if ( !slices.containsKey(sliceId) ) {
            Slice slice = new Slice(sliceId);
            slices.put(sliceId, slice);
        }
        slices.get(sliceId).hit(position);
    }

    public long count() {
        long count = 0l;
        for (Slice slice : slices.values()) {
            count += slice.count();
        }
        return count;
    }
}
