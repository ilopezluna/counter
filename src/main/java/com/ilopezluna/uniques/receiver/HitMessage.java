package com.ilopezluna.uniques.receiver;

import java.time.LocalDate;

/**
 * Created by ignasi on 23/6/15.
 */
public class HitMessage {
    private final int id;
    private String path;
    private LocalDate localDate;

    public HitMessage(int id) {
        this(id, null);
    }

    public HitMessage(int id, String path) {
        this(id, path, null);
    }

    public HitMessage(int id, String path, LocalDate localDate) {
        this.id = id;
        this.path = path;
        this.localDate = localDate;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", localDate=" + localDate +
                '}';
    }
}
