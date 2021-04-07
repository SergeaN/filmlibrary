package ru.etu.filmlibrary.models.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.etu.filmlibrary.models.data.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "select u from User u where u.login=?1 and u.password=?2")
    User checkLogin(String login, String password);

    User findByLogin(String login);
    User findByEmail(String email);
}
