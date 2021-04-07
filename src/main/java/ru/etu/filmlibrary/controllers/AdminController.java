package ru.etu.filmlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.etu.filmlibrary.models.data.Film;
import ru.etu.filmlibrary.models.data.Genre;
import ru.etu.filmlibrary.models.repositories.FigureRepository;
import ru.etu.filmlibrary.models.repositories.FigureTypeRepository;
import ru.etu.filmlibrary.models.repositories.FilmRepository;
import ru.etu.filmlibrary.models.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    FigureRepository figureRepository;

    @Autowired
    FigureTypeRepository figureTypeRepository;


    @GetMapping(path = "/genres")
    public String editGenres(Model model) {
        model.addAttribute("genres", genreRepository.findAll());
        return "admin-genres";
    }

    @GetMapping(path = "/films")
    public String editFilms(Model model) {
        List<String> films = new ArrayList<>();
        for (Film film : filmRepository.findAll()) {
            films.add(film.getId() + " " + film.getTitle() + " "
                    + film.getReleased());
        }
        model.addAttribute("films", films.toString());
        model.addAttribute("genres", genreRepository.findAll());
        return "admin-films";
    }

    @GetMapping(path = "/figures")
    public String editFigure(Model model) {
        model.addAttribute("figures", figureRepository.findAll().toString());
        model.addAttribute("figuretypes", figureTypeRepository.findAll().toString());
        return "admin-figure";
    }

}
