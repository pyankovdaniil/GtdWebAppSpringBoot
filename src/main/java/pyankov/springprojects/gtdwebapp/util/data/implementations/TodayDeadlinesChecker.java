package pyankov.springprojects.gtdwebapp.util.data.implementations;

import pyankov.springprojects.gtdwebapp.models.Note;
import pyankov.springprojects.gtdwebapp.util.data.DeadlinesChecker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodayDeadlinesChecker implements DeadlinesChecker {
    @Override
    public List<Note> checkDeadlines(List<Note> notes) {
        List<Note> todayDeadlines = new ArrayList<>();

        SimpleDateFormat formatter= new SimpleDateFormat("dd.MM.yyyy");
        Date now = new Date(System.currentTimeMillis());
        String today = formatter.format(now);

        for (Note note : notes) {
            String deadline = note.getDeadline();
            if (deadline.equals(today)) {
                todayDeadlines.add(note);
            }
        }
        return todayDeadlines;
    }
}
