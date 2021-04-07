package ru.etu.filmlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.etu.filmlibrary.models.data.Genre;
import ru.etu.filmlibrary.models.repositories.FilmRepository;
import ru.etu.filmlibrary.models.repositories.GenreRepository;

@Controller
@RequestMapping(path = "/genres")
public class GenreController {

    @Autowired
    private GenreRepository genreRepository;

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
