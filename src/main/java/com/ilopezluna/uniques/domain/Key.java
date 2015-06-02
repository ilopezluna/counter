package com.ilopezluna.uniques.domain;

import com.amazonaws.util.StringUtils;

/**
 * Created by ignasi on 1/6/15.
 */
public class Key {

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

        private String path;

        public KeyBuilder add(String path) {
            if (StringUtils.isNullOrEmpty(this.path) ) {
                this.path = path;
            }
            else {
                this.path = this.path.concat(SEPARATOR).concat(path);
            }

            return this;
        }

        public Key build() {
            return new Key(this);
        }
    }


}
