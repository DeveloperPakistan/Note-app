package com.devpk.note_app.listeners;

import com.devpk.note_app.entities.Note;

public interface NotesListener {

    void onNoteClicked(Note note, int position);

}
