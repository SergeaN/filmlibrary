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


    @Autowired
    private UserService userService;

    @GetMapping
    public String edit(){
        return "admin";
    }

    @GetMapping(path = "/genres")
    public String editGenres(Model model) {
        model.addAttribute("genres", genreRepository.findAll());
        return "admin-genres";
    }

    @GetMapping(path = "/filmsedit")
    public String editFilmsList(Model model) {
        model.addAttribute("films", filmRepository.findAll());
        return "admin-films-edit";
    }

    @GetMapping(path = "/film/{id}")
    public String editFilm(@PathVariable(value = "id") String id, Model model) {
        Optional<Film> filmOptional = filmRepository.findById(Integer.parseInt(id));
        if (filmOptional.isPresent()) {
            Film film = filmOptional.get();
            String filmInfo = film.getId() + " " + film.getTitle() + " " +
                    film.getReleased() + " " + film.getGenreId().getTitle();
            List<String> filmFigures = new ArrayList<>();
            for (Figure figure : film.getFigures()) {
                filmFigures.add(figure.getTypeId().getTitle() + " " + figure.getId() + " " +
                        figure.getFullname() + " " + new SimpleDateFormat("dd/MM/yyyy")
                                .format(figure.getBirthday()));
            }

            List<String> figures = new ArrayList<>();
            for(Figure figure: figureRepository.findAll()){
                if(!film.getFigures().contains(figure)){
                    figures.add(figure.getTypeId().getTitle() + " " + figure.getId() + " " +
                            figure.getFullname() + " " + new SimpleDateFormat("dd/MM/yyyy")
                            .format(figure.getBirthday()));
                }
            }

            model.addAttribute("id", film.getId());
            model.addAttribute("film", filmInfo);
            model.addAttribute("filmfigures", filmFigures.toString());
            model.addAttribute("figures", figures.toString());

            return "admin-film";
        }
        return "admin-film";
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

    @GetMapping(path = "/users")
    public String editUsers(Model model){
        model.addAttribute("users", userRepository.findAll().toString());
        return "admin-users";
    }

}
