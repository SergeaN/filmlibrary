package ru.etu.filmlibrary.models.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.etu.filmlibrary.models.data.UserType;

public interface UserTypeRepository extends CrudRepository<UserType, Integer> {

    @Query(value = "select u from UserType u where u.title=:title")
    UserType findByTitle(@Param("title") String title);
}
