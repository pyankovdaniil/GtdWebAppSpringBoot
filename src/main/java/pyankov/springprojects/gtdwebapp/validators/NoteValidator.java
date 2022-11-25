package pyankov.springprojects.gtdwebapp.validators;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pyankov.springprojects.gtdwebapp.models.Note;
import pyankov.springprojects.gtdwebapp.services.NoteService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class NoteValidator implements Validator {
    private final NoteService noteService;
    @Setter
    private Note noteBeforeChanging;

    @Autowired
    public NoteValidator(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Note.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Note note = (Note) target;

        List<Note> sameNameList = noteService.getNotesByName(note.getName());
        System.out.println(sameNameList);
        for (Note n : sameNameList) {
            if (note.getDescription().equals(n.getDescription()) && !(noteBeforeChanging != null
                    && noteBeforeChanging.getName().equals(n.getName())
                    && noteBeforeChanging.getDescription().equals(n.getDescription()))) {
                errors.rejectValue("name", "", "Заметка с таким названием и описанием уже есть");
                errors.rejectValue("description","", "Заметка с таким названием и описанием уже есть");
            }
        }

        int[] deadlineDates = new int[3];
        String[] datesString = note.getDeadline().split("\\.");
        if (datesString.length != 3) {
            errors.rejectValue("deadline", "", "Некорректный вид дедлайна");
            return;
        }

        for (int i = 0; i < 3; i++) {
            try {
                deadlineDates[i] = Integer.parseInt(datesString[i]);
            } catch (NumberFormatException e) {
                errors.rejectValue("deadline", "", "Некорректные числа в дедлайне");
                return;
            }
        }

        if (!(deadlineDates[1] >= 1 && deadlineDates[1] <= 12 && deadlineDates[2] <= 2050)) {
            errors.rejectValue("deadline", "", "Некорректный номер месяца или года");
            return;
        }

        YearMonth yearMonth = YearMonth.of(deadlineDates[2], deadlineDates[1]);
        if (deadlineDates[0] > yearMonth.lengthOfMonth() || deadlineDates[0] == 0) {
            errors.rejectValue("deadline", "", "Некорректное количество дней в месяце");
            return;
        }

        LocalDate now = LocalDate.now();
        LocalDate deadline = LocalDate.parse(note.getDeadline(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        if (deadline.isBefore(now)) {
            errors.rejectValue("deadline", "", "Такой дедлайн уже истек :(");
        }
    }
}
