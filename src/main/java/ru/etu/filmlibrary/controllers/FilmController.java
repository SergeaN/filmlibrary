package ru.etu.filmlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.etu.filmlibrary.models.data.Figure;
import ru.etu.filmlibrary.models.data.Film;
import ru.etu.filmlibrary.models.data.Genre;
import ru.etu.filmlibrary.models.repositories.FigureRepository;
import ru.etu.filmlibrary.models.repositories.FigureTypeRepository;
import ru.etu.filmlibrary.models.repositories.FilmRepository;
import ru.etu.filmlibrary.models.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/film")
public class FilmController {

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    FigureTypeRepository figureTypeRepository;

    @Autowired
    FigureRepository figureRepository;

    @GetMapping(path = "/all")
    public String getFilms(Model model) {
        model.addAttribute("films", filmRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "films";
    }

    @GetMapping(value = {"/{id}"})
    public String getFilmInfo(@PathVariable(value = "id") String id, Model model) {
        Optional<Film> filmOptional = filmRepository.findById(Integer.parseInt(id));
        if (filmOptional.isPresent()) {
            Film film = filmOptional.get();
            model.addAttribute("id", film.getId())
                    .addAttribute("title", film.getTitle())
                    .addAttribute("released", film.getReleased())
                    .addAttribute("country", film.getCountry())
                    .addAttribute("genre", film.getGenreId().getTitle())
                    .addAttribute("studio", film.getStudio())
                    .addAttribute("description", film.getDescription());
            List<String> actors = new ArrayList<>();
            for (Figure figure : film.getFigures()) {
                switch (figure.getTypeId().getTitle()) {
                    case "Актёр":
                        actors.add(figure.getFullname());
                        break;
                    case "Режиссёр":
                        model.addAttribute("director", figure.getFullname());
                        break;
                    case "Сценарист":
                        model.addAttribute("screenwriter", figure.getFullname());
                        break;
                    case "Продюссер":
                        model.addAttribute("producer", figure.getFullname());
                        break;
                    case "Оператор":
                        model.addAttribute("operator", figure.getFullname());
                        break;
                    case "Художник":
                        model.addAttribute("painter", figure.getFullname());
                        break;
                    case "Монтажёр":
                        model.addAttribute("editor", figure.getFullname());
                        break;
                }
            }
            model.addAttribute("actors", actors);
            return "film";
        } else return "redirect:/film/all";
    }

    @PostMapping(path = "/add")
    public String addFilm(@RequestParam String title,
                          @RequestParam String released,
                          @RequestParam String country,
                          @RequestParam String genre_id,
                          @RequestParam String description,
                          @RequestParam String studio) {

        if (title.length() >= 255 || released.length() != 4 ||
                country.length() >= 255 || description.length() >= 255 ||
                studio.length() >= 255) return "redirect:/film/all";
        Film film = new Film();
        film.setTitle(title);
        film.setReleased(Integer.parseInt(released));
        film.setCountry(country);
        genreRepository.findById(Integer.parseInt(genre_id)).ifPresent(film::setGenreId);
        film.setDescription(description);
        film.setStudio(studio);
        filmRepository.save(film);
        return "redirect:/film/" + film.getId();
    }

    @PostMapping(path = "/update")
    public String updateFilm(@RequestParam String id,
                             @RequestParam String title,
                             @RequestParam String released,
                             @RequestParam String country,
                             @RequestParam String genre_id,
                             @RequestParam String description,
                             @RequestParam String studio) {
        if (title.length() >= 255 || released.length() != 4 ||
                country.length() >= 255 || description.length() >= 255 ||
                studio.length() >= 255) return "redirect:/film/all";
        Optional<Film> filmOptional = filmRepository.findById(Integer.parseInt(id));
        if (filmOptional.isPresent()) {
            Film film = filmOptional.get();
            film.setTitle(title);
            film.setReleased(Integer.parseInt(released));
            film.setCountry(country);
            genreRepository.findById(Integer.parseInt(genre_id)).ifPresent(film::setGenreId);
            film.setDescription(description);
            film.setStudio(studio);
            return "redirect:/film/" + film.getId();
        }
        return "redirect:/film/all";
    }

    @PostMapping(path = "/delete")
    public String deleteFilm(@RequestParam String id) {
        try {
            filmRepository.deleteById(Integer.parseInt(id));
        } catch (EmptyResultDataAccessException | IllegalArgumentException ignored) {
        }
        return "redirect:/film/all";
    }

    @PostMapping(path = "/addFigure")
    public String addFigure(@RequestParam String figureId, @RequestParam String filmId) {
        Optional<Figure> optionalFigure = figureRepository.findById(Integer.parseInt(figureId));
        Optional<Film> optionalFilm = filmRepository.findById(Integer.parseInt(filmId));
        if (optionalFigure.isPresent() && optionalFilm.isPresent()) {
            Figure figure = optionalFigure.get();
            Film film = optionalFilm.get();
            film.getFigures().add(figure);
            filmRepository.save(film);
            return "redirect:/film/" + film.getId();
        }
        return "redirect:/film/all";
    }

    @PostMapping(path = "/deleteFigure")
    public String deleteFigure(@RequestParam String figureId, @RequestParam String filmId) {
        Optional<Figure> optionalFigure = figureRepository.findById(Integer.parseInt(figureId));
        Optional<Film> optionalFilm = filmRepository.findById(Integer.parseInt(filmId));
        if (optionalFigure.isPresent() && optionalFilm.isPresent()) {
            Figure figure = optionalFigure.get();
            Film film = optionalFilm.get();
            film.getFigures().remove(figure);
            filmRepository.save(film);
            return "redirect:/film/" + film.getId();
        }
        return "redirect:/film/all";
    }
}
