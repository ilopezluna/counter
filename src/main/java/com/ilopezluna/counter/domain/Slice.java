package com.ilopezluna.counter.domain;

import java.util.BitSet;

/**
 * Created by ignasi on 29/5/15.
 */
public class Slice {

    private final int index;

    private BitSet uniques = new BitSet();

    public Slice(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public synchronized void hit(int position) {
        uniques.set( position );
    }

    public int count() {
        return uniques.cardinality();
    }
}
