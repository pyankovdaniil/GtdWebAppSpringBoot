package pyankov.springprojects.gtdwebapp.util.data.implementations;

import pyankov.springprojects.gtdwebapp.models.Note;
import pyankov.springprojects.gtdwebapp.util.data.DeadlinesChecker;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WeekDeadlinesChecker implements DeadlinesChecker {
    @Override
    public List<Note> checkDeadlines(List<Note> notes) {
        List<Note> weekDeadlines = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate today = LocalDate.now();
        today.format(formatter);

        for (Note note : notes) {
            LocalDate noteDeadline = LocalDate.parse(note.getDeadline(), formatter);
            Period period = Period.between(today, noteDeadline);
            if (period.getDays() <= 7 && period.getDays() > 0 && period.getMonths() == 0 &&
                    period.getYears() == 0) {
                weekDeadlines.add(note);
            }
        }
        return weekDeadlines;
    }
}
