package ru.etu.filmlibrary.models.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.etu.filmlibrary.models.data.FigureType;

public interface FigureTypeRepository extends CrudRepository<FigureType, Integer> {
}
