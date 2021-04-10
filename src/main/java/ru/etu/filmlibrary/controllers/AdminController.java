package ru.etu.filmlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.etu.filmlibrary.models.data.Figure;
import ru.etu.filmlibrary.models.data.Film;
import ru.etu.filmlibrary.models.data.Genre;
import ru.etu.filmlibrary.models.repositories.*;
import ru.etu.filmlibrary.models.services.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    FigureRepository figureRepository;

    @Autowired
    FigureTypeRepository figureTypeRepository;

    @GetMapping
    public String edit() {
        return "admin";
    }

    @GetMapping(path = "/film/edit/{id}")
    public String getFilmInfo(@PathVariable(value = "id") String id, Model model) {
        model.addAttribute("genres", genreRepository.findAll());
        filmRepository.findById(Integer.parseInt(id)).ifPresent(model::addAttribute);
        return "admin-film-edit";
    }

    @GetMapping(path = "/film/figures/{id}")
    public String getFilm(@PathVariable(value = "id") String id, Model model) {
        Optional<Film> filmOptional = filmRepository.findById(Integer.parseInt(id));
        if (filmOptional.isPresent()) {
            Film film = filmOptional.get();
            List<String> figures = new ArrayList<>();
            for (Figure figure : figureRepository.findAll()) {
                if (!film.getFigures().contains(figure)) {
                    figures.add(figure.getId() + " " + figure.getFullname() + " "
                            + figure.getTypeId().getTitle());
                }
            }

            model.addAttribute("film", film);
            model.addAttribute("figures", figures.toString());
            return "admin-film-figures";
        }
        return "admin-films";
    }

    @GetMapping(path = "/figure/{id}")
    public String getEditedFigure(@PathVariable(value = "id") String id, Model model) {
        Optional<Figure> figureOptional = figureRepository.findById(Integer.parseInt(id));
        if (figureOptional.isPresent()) {
            Figure figure = figureOptional.get();
            model.addAttribute("figuretypes", figureTypeRepository.findAll());
            model.addAttribute("figure", figure);
            model.addAttribute("birthday",
                    new SimpleDateFormat("dd/MM/yyyy").format(figure.getBirthday()));
        }
        return "admin-figure-edit";
    }

    @GetMapping(path = "/films")
    public String getFilms(Model model) {
        model.addAttribute("films", filmRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "admin-films";
    }

    @GetMapping(path = "/figures")
    public String getFigures(Model model) {
        model.addAttribute("figures", figureRepository.findAll());
        model.addAttribute("figuretypes", figureTypeRepository.findAll());
        return "admin-figures";
    }

    @GetMapping(path = "/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin-users";
    }

    @GetMapping(path = "/genres")
    public String getGenres(Model model) {
        model.addAttribute("genres", genreRepository.findAll());
        return "admin-genres";
    }

}
