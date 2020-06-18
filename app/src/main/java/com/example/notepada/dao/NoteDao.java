package com.example.notepada.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notepada.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    long insertNote(Note note);

    @Delete
    int deleteNote(Note note);

    @Update
    int updateNote(Note note);

    @Query("select * from noteTable")
    List<Note>getAllNote();

    @Query("select * from noteTable where id like:rowid")
    Note getNoteId(long rowid);


}
