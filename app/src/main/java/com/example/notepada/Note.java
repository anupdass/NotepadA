package com.example.notepada;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
@Entity(tableName = "noteTable")
public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String noteText;
    private String date;

    public Note(String noteText, String date) {
        this.noteText = noteText;
        this.date = date;
    }
    @Ignore
    public Note(long id, String noteText, String date) {
        this.id = id;
        this.noteText = noteText;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
