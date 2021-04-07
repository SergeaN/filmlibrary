package ru.etu.filmlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.etu.filmlibrary.models.repositories.FilmRepository;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    FilmRepository filmRepository;

    @GetMapping
    public String home(Model model) {
        int id1 = 1;
        int id2 = 2;
        int id3 = 4;
        filmRepository.findById(id1)
                .ifPresent(film -> model.addAttribute("film1", film));
        filmRepository.findById(id2)
                .ifPresent(film -> model.addAttribute("film2", film));
        filmRepository.findById(id3)
                .ifPresent(film -> model.addAttribute("film3", film));
        return "home";
    }
}