package pyankov.springprojects.gtdwebapp.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import pyankov.springprojects.gtdwebapp.models.Directory;
import pyankov.springprojects.gtdwebapp.models.Note;
import pyankov.springprojects.gtdwebapp.repositories.DirectoryRepository;
import pyankov.springprojects.gtdwebapp.repositories.NoteRepository;
import pyankov.springprojects.gtdwebapp.services.DirectoryService;
import pyankov.springprojects.gtdwebapp.util.data.DeadlinesChecker;
import pyankov.springprojects.gtdwebapp.util.data.implementations.TodayDeadlinesChecker;
import pyankov.springprojects.gtdwebapp.util.data.implementations.WeekDeadlinesChecker;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DirectoryServiceImplementation implements DirectoryService {
    private final DirectoryRepository directoryRepository;
    private final NoteRepository noteRepository;

    @Autowired
    public DirectoryServiceImplementation(DirectoryRepository directoryRepository, NoteRepository noteRepository) {
        this.directoryRepository = directoryRepository;
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getNotesListById(int id) {
        if (id == 5) {
            List<Note> notes = noteRepository.findAll();
            DeadlinesChecker checker = new TodayDeadlinesChecker();
            return checker.checkDeadlines(notes);
        } else if (id == 6) {
            List<Note> notes = noteRepository.findAll();
            DeadlinesChecker checker = new WeekDeadlinesChecker();
            return checker.checkDeadlines(notes);
        }
        return directoryRepository.findById(id).map(Directory::getNotes).orElse(null);
    }

    @Override
    public List<Directory> getDirectoryList() {
        return directoryRepository.findAll();
    }

    @Override
    @Transactional
    public void addNoteToDirectory(int directoryId, Note note) {
        Optional<Directory> directoryOptional = directoryRepository.findById(directoryId);
        if (directoryOptional.isPresent()) {
            Directory directory = directoryOptional.get();
            note.setDirectories(List.of(directory));
            note.setId(0);
            noteRepository.save(note);
            directory.getNotes().add(note);
            directoryRepository.save(directory);
        }
    }

    @Override
    public int getIdByName(String directoryName) {
        Directory directory = directoryRepository.findDirectoryByName(StringUtils.capitalize(directoryName));
        return directory.getId();
    }
}
