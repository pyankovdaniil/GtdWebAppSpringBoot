package pyankov.springprojects.gtdwebapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pyankov.springprojects.gtdwebapp.models.Directory;
import pyankov.springprojects.gtdwebapp.models.Note;
import pyankov.springprojects.gtdwebapp.services.DirectoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/directories")
public class DirectoryController {
    private final DirectoryService directoryService;

    @Autowired
    public DirectoryController(DirectoryService directoryService) {
        this.directoryService = directoryService;
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
                             @ModelAttribute("note") Note note,
                             HttpServletRequest request) {
        int id = directoryService.getIdByName(directoryName);
        directoryService.addNoteToDirectory(id, note);

        return "redirect:/directories/" + directoryName;
    }

    @ModelAttribute("directoryList")
    public List<Directory> getDirectoryList() {
        return directoryService.getDirectoryList();
    }
}
