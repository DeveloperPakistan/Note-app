package com.devpk.note_app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.devpk.note_app.NoteDao.NoteDao;
import com.devpk.note_app.entities.Note;

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class DeleteDatabase extends RoomDatabase {
    protected static DeleteDatabase deleteDatabase;
    public static DeleteDatabase getNotesDatabase(Context context) {

        if (deleteDatabase == null) {
            deleteDatabase = Room
                    .databaseBuilder(context, DeleteDatabase.class, "delete_db").build();
        }
        return deleteDatabase;
    }
    // Declare your data access objects as abstract
    public abstract NoteDao noteDao();
}
