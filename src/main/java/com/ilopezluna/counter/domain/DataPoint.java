package com.ilopezluna.counter.domain;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by ignasi on 29/5/15.
 */
public class DataPoint {

    private final String key;
    private final LocalDate localDate;
    private BitSet uniques;

    public DataPoint(String key, LocalDate localDate) {
        this.key = key;
        this.localDate = localDate;
        uniques = new BitSet();
    }

    public String getKey() {
        return key;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public BitSet getUniques() {
        return uniques;
    }

    public void setUniques(BitSet uniques) {
        this.uniques = uniques;
    }

    public void hit(int bitIndex) {
        uniques.set(bitIndex);
    }

    public int count() {
        return uniques.cardinality();
    }
}
