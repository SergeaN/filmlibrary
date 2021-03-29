package ru.etu.filmlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.etu.filmlibrary.models.data.UserType;
import ru.etu.filmlibrary.models.repositories.UserTypeRepository;

@Controller
@RequestMapping(path="/usertypes")
public class UserTypeController {
    @Autowired
    private UserTypeRepository userRepository;

    @GetMapping
    public String getUserTypes(Model model) {
        model.addAttribute("usertypes", userRepository.findAll());
        return "usertypes";
    }
}