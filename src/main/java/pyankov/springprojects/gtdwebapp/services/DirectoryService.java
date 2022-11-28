package pyankov.springprojects.gtdwebapp.services;

import pyankov.springprojects.gtdwebapp.models.Directory;
import pyankov.springprojects.gtdwebapp.models.Note;

import java.util.List;

public interface DirectoryService {
    List<Note> getNotesListById(int id);
    List<Directory> getDirectoryList();
    void addNoteToDirectory(int directoryId, Note note);
    int getIdByName(String directoryName);
}
