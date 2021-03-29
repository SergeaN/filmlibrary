package ru.etu.filmlibrary.models.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.etu.filmlibrary.models.data.Film;

public interface FilmRepository extends CrudRepository<Film, Integer> {
}
