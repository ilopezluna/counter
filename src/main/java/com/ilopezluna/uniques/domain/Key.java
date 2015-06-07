package com.ilopezluna.uniques.domain;

/**
 * Created by ignasi on 1/6/15.
 */
public class Key {

    public final static String DEFAULT_KEY = "u";
    private final static String SEPARATOR = ":";

    private final String path;

    private Key(KeyBuilder builder) {
        this.path = builder.path;
    }

    @Override
    public String toString() {
        return path;
    }

    public static class KeyBuilder {

        private String path = DEFAULT_KEY;

        public KeyBuilder add(String path) {
            this.path = this.path.concat(SEPARATOR).concat(path);
            return this;
        }

        public Key build() {
            return new Key(this);
        }
    }


}
