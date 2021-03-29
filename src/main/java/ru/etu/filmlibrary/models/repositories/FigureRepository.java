package ru.etu.filmlibrary.models.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.etu.filmlibrary.models.data.Figure;

public interface FigureRepository extends CrudRepository<Figure, Integer> {

}
