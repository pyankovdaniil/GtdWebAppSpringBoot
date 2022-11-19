package pyankov.springprojects.gtdwebapp.util.data;

import pyankov.springprojects.gtdwebapp.models.Note;

import java.util.List;

public interface DeadlinesChecker {
    List<Note> checkDeadlines(List<Note> notes);
}
