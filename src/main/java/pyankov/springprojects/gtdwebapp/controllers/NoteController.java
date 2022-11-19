package pyankov.springprojects.gtdwebapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pyankov.springprojects.gtdwebapp.models.Note;
import pyankov.springprojects.gtdwebapp.services.NoteService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PatchMapping("/{id}")
    public String updateNode(@PathVariable("id") int id,
                             @ModelAttribute Note note,
                             HttpServletRequest request) {
        Note updatedNote = noteService.getNoteById(id);
        noteService.updateDirectory(note.getId(), updatedNote);
        noteService.updateNoteById(id, updatedNote);

        String fullPageAddress = request.getHeader("referer");
        String page = fullPageAddress.substring(fullPageAddress.lastIndexOf('/') + 1);
        return "redirect:/directories/" + page;
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
