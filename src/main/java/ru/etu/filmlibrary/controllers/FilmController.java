package ru.etu.filmlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.etu.filmlibrary.models.data.Figure;
import ru.etu.filmlibrary.models.data.Film;
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


    @GetMapping(value = {"/{id}"})
    public String getFilmInfo(@PathVariable(value = "id") String id, Model model) {
        Optional<Film> filmOptional = filmRepository.findById(Integer.parseInt(id));
        if (filmOptional.isPresent()) {
            Film film = filmOptional.get();
            model.addAttribute("film", film);
            List<Figure> actors = new ArrayList<>();
            for (Figure figure : film.getFigures()) {
                switch (figure.getTypeId().getTitle()) {
                    case "Актёр":
                        actors.add(figure);
                        break;
                    case "Режиссёр":
                        model.addAttribute("director", figure);
                        break;
                    case "Сценарист":
                        model.addAttribute("screenwriter", figure);
                        break;
                    case "Продюссер":
                        model.addAttribute("producer", figure);
                        break;
                    case "Оператор":
                        model.addAttribute("operator", figure);
                        break;
                    case "Художник":
                        model.addAttribute("painter", figure);
                        break;
                    case "Монтажёр":
                        model.addAttribute("editor", figure);
                        break;
                }
            }
            model.addAttribute("actors", actors);
            checkAttributes(model, actors);
            return "film";
        } else return "redirect:/film";
    }

    private void checkAttributes(Model model, List<Figure> actors) {
        Figure notExisting = new Figure();
        notExisting.setFullname("-");
        if (model.getAttribute("director") == null) {
            model.addAttribute("director", notExisting);
        }
        if (model.getAttribute("screenwriter") == null) {
            model.addAttribute("screenwriter", notExisting);
        }
        if (model.getAttribute("producer") == null) {
            model.addAttribute("producer", notExisting);
        }
        if (model.getAttribute("operator") == null) {
            model.addAttribute("operator", notExisting);
        }
        if (model.getAttribute("painter") == null) {
            model.addAttribute("painter", notExisting);
        }
        if (model.getAttribute("editor") == null) {
            model.addAttribute("editor", notExisting);
        }
        if (actors.isEmpty()) actors.add(notExisting);
    }

    @PostMapping(path = "/add")
    public String addFilm(@RequestParam String title,
                          @RequestParam String released,
                          @RequestParam String country,
                          @RequestParam String genre_id,
                          @RequestParam String description,
                          @RequestParam String studio,
                          @RequestParam String photo_href) {

        if (title.length() >= 255 || released.length() != 4 ||
                country.length() >= 255 || description.length() >= 255 ||
                studio.length() >= 255 || photo_href.length() >= 255)
            return "redirect:/admin/films";
        Film film = new Film();
        buildFilm(title, released, country, genre_id,
                description, studio, photo_href, film);
        return "redirect:/admin/films/";
    }

    @PostMapping(path = "/update")
    public String updateFilm(@RequestParam String id,
                             @RequestParam String title,
                             @RequestParam String released,
                             @RequestParam String country,
                             @RequestParam String genre_id,
                             @RequestParam String description,
                             @RequestParam String studio,
                             @RequestParam String photo_href) {

        if (title.length() >= 255 || released.length() != 4 ||
                country.length() >= 255 || description.length() >= 255 ||
                studio.length() >= 255 || photo_href.length() >= 255)
            return "redirect:/admin/film/edit/" + id;
        Optional<Film> filmOptional = filmRepository.findById(Integer.parseInt(id));
        if (filmOptional.isPresent()) {
            Film film = filmOptional.get();
            buildFilm(title, released, country, genre_id,
                    description, studio, photo_href, film);
            return "redirect:/admin/film/edit/" + id;
        }
        return "redirect:/admin/film/edit/" + id;
    }

    private void buildFilm(@RequestParam String title, @RequestParam String released,
                           @RequestParam String country, @RequestParam String genre_id,
                           @RequestParam String description, @RequestParam String studio,
                           @RequestParam String photo_href, Film film) {
        film.setTitle(title);
        film.setReleased(Integer.parseInt(released));
        film.setCountry(country);
        genreRepository.findById(Integer.parseInt(genre_id)).ifPresent(film::setGenreId);
        film.setDescription(description);
        film.setStudio(studio);
        film.setPhotoHref(photo_href);
        filmRepository.save(film);
    }

    @PostMapping(path = "/delete/{id}")
    public String deleteFilm(@PathVariable(value = "id") String id) {
        try {
            filmRepository.deleteById(Integer.parseInt(id));
        } catch (EmptyResultDataAccessException | IllegalArgumentException ignored) {
        }
        return "redirect:/admin/films";
    }

    @PostMapping(path = "/addFigure")
    public String addFigure(@RequestParam String figureId,
                            @RequestParam String filmId) {
        Optional<Figure> optionalFigure = figureRepository.findById(Integer.parseInt(figureId));
        Optional<Film> optionalFilm = filmRepository.findById(Integer.parseInt(filmId));
        if (optionalFigure.isPresent() && optionalFilm.isPresent()) {
            Figure figure = optionalFigure.get();
            Film film = optionalFilm.get();
            film.getFigures().add(figure);
            filmRepository.save(film);
            return "redirect:/admin/film/figures/" + film.getId();
        }
        return "redirect:/admin/film/";
    }

    @PostMapping(path = "/deleteFigure/{figureId}")
    public String deleteFigure(@PathVariable(value = "figureId") String figureId,
                               @RequestParam String filmId) {
        Optional<Figure> optionalFigure = figureRepository.findById(Integer.parseInt(figureId));
        Optional<Film> optionalFilm = filmRepository.findById(Integer.parseInt(filmId));
        if (optionalFigure.isPresent() && optionalFilm.isPresent()) {
            Figure figure = optionalFigure.get();
            Film film = optionalFilm.get();
            film.getFigures().remove(figure);
            filmRepository.save(film);
            return "redirect:/admin/film/figures/" + film.getId();
        }
        return "redirect:/admin/films/";
    }
}
