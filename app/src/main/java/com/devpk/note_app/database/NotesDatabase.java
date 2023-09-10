package com.devpk.note_app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.devpk.note_app.NoteDao.NoteDao;
import com.devpk.note_app.entities.Note;

// bump version number if your schema changes
@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {
    protected static NotesDatabase notesDatabase;
    public static synchronized NotesDatabase getNotesDatabase(Context context) {
        if (notesDatabase == null) {
            notesDatabase = Room.
                    databaseBuilder(context, NotesDatabase.class, "notes_db").build();
        }
        return notesDatabase;
    }

    // Declare your data access objects as abstract
    public abstract NoteDao noteDao();
}
