package ru.etu.filmlibrary.models.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.etu.filmlibrary.models.data.Figure;
import ru.etu.filmlibrary.models.data.Film;
import ru.etu.filmlibrary.models.data.Genre;

import java.util.List;

public interface FilmRepository extends CrudRepository<Film, Integer> {

    @Query(value = "SELECT f FROM Film as f where f.title LIKE CONCAT('%',?1,'%')")
    List<Film> findFilmsByQuery(@Param("search") String search);


    List<Film> findByGenreId(Genre genreID);
}
