package com.example.notepada.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notepada.Note;
import com.example.notepada.dao.NoteDao;

@Database(entities = {Note.class},version = 1)
public  abstract class DataBaseNote extends RoomDatabase {

    public abstract NoteDao getNoteDao();

    private static DataBaseNote dataBaseNote;

    public static DataBaseNote getInstance(Context context){
        if(dataBaseNote != null){
            return dataBaseNote;
        }

        dataBaseNote = Room.databaseBuilder(context,
                DataBaseNote.class,
                "databse")
                .allowMainThreadQueries().build();

        return dataBaseNote;
    }
}
