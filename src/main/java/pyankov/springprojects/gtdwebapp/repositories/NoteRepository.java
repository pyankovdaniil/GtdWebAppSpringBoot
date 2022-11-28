package pyankov.springprojects.gtdwebapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pyankov.springprojects.gtdwebapp.models.Note;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> getNotesByName(String name);
}
