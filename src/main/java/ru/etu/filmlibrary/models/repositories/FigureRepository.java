package ru.etu.filmlibrary.models.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.etu.filmlibrary.models.data.Figure;

import java.util.List;
import java.util.Optional;

public interface FigureRepository extends CrudRepository<Figure, Integer> {

    @Query(value = "SELECT f FROM Figure as f where f.typeId.id=:typeId")
    List<Figure> findFiguresByTypeId(@Param("typeId") Integer typeId);

}
