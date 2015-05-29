package com.ilopezluna.counter.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ignasi on 29/5/15.
 */
public class DataPoint {

    private Map<Integer, Slice> slices = new HashMap<Integer, Slice>();

    public void hit(Integer key, int position) {
        if ( !slices.containsKey(key) ) {
            Slice slice = new Slice(key);
            slices.put(key, slice);
        }
        slices.get(key).hit(position);
    }

    public long count() {
        long count = 0l;
        for (Slice slice : slices.values()) {
            count += slice.count();
        }
        return count;
    }
}
