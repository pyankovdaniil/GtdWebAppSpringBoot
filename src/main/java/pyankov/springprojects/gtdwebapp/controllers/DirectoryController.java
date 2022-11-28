package pyankov.springprojects.gtdwebapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pyankov.springprojects.gtdwebapp.models.Directory;
import pyankov.springprojects.gtdwebapp.models.Note;
import pyankov.springprojects.gtdwebapp.services.DirectoryService;
import pyankov.springprojects.gtdwebapp.validators.NoteValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/directories")
public class DirectoryController {
    private final DirectoryService directoryService;
    private final NoteValidator noteValidator;

    @Autowired
    public DirectoryController(DirectoryService directoryService, NoteValidator noteValidator) {
        this.directoryService = directoryService;
        this.noteValidator = noteValidator;
    }

    @GetMapping("/")
    public String navigationPage() {
        return "redirect:/directories/inbox";
    }

    @GetMapping("/inbox")
    public String inboxDirectory(Model model) {
        model.addAttribute("notes", directoryService.getNotesListById(1));
        model.addAttribute("note", new Note());
        return "/directories/inboxDirectory";
    }

    @GetMapping("/todo")
    public String toDoDirectory(Model model) {
        model.addAttribute("notes", directoryService.getNotesListById(2));
        model.addAttribute("note", new Note());
        return "/directories/toDoDirectory";
    }

    @GetMapping("/info")
    public String infoDirectory(Model model) {
        model.addAttribute("notes", directoryService.getNotesListById(3));
        model.addAttribute("note", new Note());
        return "/directories/infoDirectory";
    }

    @GetMapping("/maybe")
    public String maybeDirectory(Model model) {
        model.addAttribute("notes", directoryService.getNotesListById(4));
        model.addAttribute("note", new Note());
        return "/directories/maybeDirectory";
    }

    @GetMapping("/today")
    public String todayDeadlinesDirectory(Model model) {
        model.addAttribute("notes", directoryService.getNotesListById(5));
        model.addAttribute("note", new Note());
        return "/directories/todayDeadlinesDirectory";
    }

    @GetMapping("/week")
    public String weekDeadlinesDirectory(Model model) {
        model.addAttribute("notes", directoryService.getNotesListById(6));
        model.addAttribute("note", new Note());
        return "/directories/weekDeadlinesDirectory";
    }

    @PostMapping("/{directoryName}")
    public String addNewNote(@PathVariable("directoryName") String directoryName,
                             @ModelAttribute("note") @Valid Note note,
                             BindingResult result) {
        noteValidator.validate(note, result);
        if (result.hasErrors()) {
            return "/notes/newNoteForm";
        }

        int id = directoryService.getIdByName(directoryName);
        directoryService.addNoteToDirectory(id, note);

        return "redirect:/directories/" + directoryName;
    }

    @ModelAttribute("directoryList")
    public List<Directory> getDirectoryList() {
        return directoryService.getDirectoryList();
    }
}
