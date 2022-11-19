package pyankov.springprojects.gtdwebapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pyankov.springprojects.gtdwebapp.models.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
}
