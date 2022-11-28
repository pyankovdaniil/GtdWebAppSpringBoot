package pyankov.springprojects.gtdwebapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pyankov.springprojects.gtdwebapp.models.Note;
import pyankov.springprojects.gtdwebapp.services.NoteService;
import pyankov.springprojects.gtdwebapp.validators.NoteValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    private final NoteValidator noteValidator;

    @Autowired
    public NoteController(NoteService noteService, NoteValidator noteValidator) {
        this.noteService = noteService;
        this.noteValidator = noteValidator;
    }

    @PatchMapping("/{id}")
    public String moveNote(@PathVariable("id") int id,
                             @ModelAttribute Note note,
                             HttpServletRequest request) {
        Note updatedNote = noteService.getNoteById(id);
        noteService.updateDirectory(note.getId(), updatedNote);
        noteService.updateNoteById(id, updatedNote);

        String fullPageAddress = request.getHeader("referer");
        String page = fullPageAddress.substring(fullPageAddress.lastIndexOf('/') + 1);
        return "redirect:/directories/" + page;
    }

    @PatchMapping("/{directoryName}/{id}/edit")
    public String updateNote(@PathVariable("id") int id,
                             @PathVariable("directoryName") String directoryName,
                             @ModelAttribute("note") @Valid Note note,
                             BindingResult bindingResult) {
        noteValidator.validate(note, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/notes/editNoteForm";
        }

        noteService.updateNoteInfoById(note.getId(), note);

        return "redirect:/directories/" + directoryName;
    }

    @GetMapping("/{id}/edit")
    public String getUpdateNoteForm(@PathVariable("id") int id,
                             Model model,
                                    HttpServletRequest request) {
        Note note = noteService.getNoteById(id);
        noteValidator.setNoteBeforeChanging(note);
        model.addAttribute("note", note);

        String fullPageAddress = request.getHeader("referer");
        String page = fullPageAddress.substring(fullPageAddress.lastIndexOf('/') + 1);

        model.addAttribute("directoryName", page);
        return "/notes/editNoteForm";
    }

    @DeleteMapping("/{id}")
    public String deleteNote(@PathVariable("id") int id,
                             HttpServletRequest request) {
        noteService.deleteNoteById(id);
        String fullPageAddress = request.getHeader("referer");
        String page = fullPageAddress.substring(fullPageAddress.lastIndexOf('/') + 1);
        return "redirect:/directories/" + page;
    }

    @GetMapping("/new")
    public String getNewNoteForm(Model model, HttpServletRequest request) {
        model.addAttribute("note", new Note());

        String fullPageAddress = request.getHeader("referer");
        String page = fullPageAddress.substring(fullPageAddress.lastIndexOf('/') + 1);

        model.addAttribute("directoryName", page);
        return "/notes/newNoteForm";
    }
}
