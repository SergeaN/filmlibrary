package ru.etu.filmlibrary.models.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.etu.filmlibrary.models.data.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
