package pyankov.springprojects.gtdwebapp.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pyankov.springprojects.gtdwebapp.models.Directory;
import pyankov.springprojects.gtdwebapp.models.Note;
import pyankov.springprojects.gtdwebapp.repositories.DirectoryRepository;
import pyankov.springprojects.gtdwebapp.repositories.NoteRepository;
import pyankov.springprojects.gtdwebapp.services.NoteService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class NoteServiceImplementation implements NoteService {
    private final NoteRepository noteRepository;
    private final DirectoryRepository directoryRepository;

    @Autowired
    public NoteServiceImplementation(NoteRepository noteRepository,
                                     DirectoryRepository directoryRepository) {
        this.noteRepository = noteRepository;
        this.directoryRepository = directoryRepository;
    }


    @Override
    public Note getNoteById(int id) {
        return noteRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void saveNote(Note note) {
        noteRepository.save(note);
    }

    @Override
    @Transactional
    public void updateNoteById(int id, Note updatedNote) {
        updatedNote.setId(id);
        noteRepository.save(updatedNote);
    }

    @Override
    @Transactional
    public void deleteNoteById(int id) {
        noteRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateDirectory(int newDirectoryId, Note updatedNote) {
        List<Directory> directoryList = updatedNote.getDirectories();
        for (int i = 0; i < directoryList.size(); i++) {
            if (directoryList.get(i).getId() != 5 && directoryList.get(i).getId() != 6) {
                updatedNote.getDirectories().set(0,
                        directoryRepository.findById(newDirectoryId).get());
            }
        }
    }
}
