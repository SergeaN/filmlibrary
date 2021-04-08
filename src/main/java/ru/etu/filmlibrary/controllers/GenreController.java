package ru.etu.filmlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.etu.filmlibrary.models.data.Figure;
import ru.etu.filmlibrary.models.data.Genre;
import ru.etu.filmlibrary.models.repositories.FilmRepository;
import ru.etu.filmlibrary.models.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/genres")
public class GenreController {

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    GenreRepository genreRepository;

    @GetMapping(value = {"/{id}"})
    public String getFilms(@PathVariable(value = "id") String genreId, Model model) {

        Optional<Genre> optionalGenre = genreRepository.findById(Integer.parseInt(genreId));

        if (optionalGenre.isPresent()) {
            model.addAttribute("films",
                    filmRepository.findByGenreId(optionalGenre.get()));
        }

        List<Figure> figureList = new ArrayList<>();
        model.addAttribute("figure", figureList);

        return "films-list";
    }

    @PostMapping(path = "/add")
    public String addGenre(@RequestParam String title) {
        Genre genre = new Genre();
        genre.setTitle(title);
        genreRepository.save(genre);
        return "redirect:/admin/genres";
    }

    @PostMapping(path = "/delete")
    public String deleteGenre(@RequestParam String id) {
        try {
            genreRepository.deleteById(Integer.valueOf(id));
            return "redirect:/admin/genres";
        } catch (EmptyResultDataAccessException | IllegalArgumentException e) {
            return "redirect:/admin/genres";
        }
    }

}
