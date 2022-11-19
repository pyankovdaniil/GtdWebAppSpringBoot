package pyankov.springprojects.gtdwebapp.services;

import org.springframework.stereotype.Service;
import pyankov.springprojects.gtdwebapp.models.Note;
import pyankov.springprojects.gtdwebapp.repositories.NoteRepository;

public interface NoteService {
    Note getNoteById(int id);
    void saveNote(Note note);
    void updateNoteById(int id, Note updatedNote);
    void deleteNoteById(int id);
    void updateDirectory(int newDirectoryId, Note updatedNote);
}
