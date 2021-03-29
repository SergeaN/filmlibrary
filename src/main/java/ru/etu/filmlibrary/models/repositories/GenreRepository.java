package ru.etu.filmlibrary.models.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.etu.filmlibrary.models.data.Genre;

public interface GenreRepository extends CrudRepository<Genre, Integer> {

}
