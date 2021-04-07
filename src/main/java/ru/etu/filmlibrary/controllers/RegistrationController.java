package ru.etu.filmlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.etu.filmlibrary.models.data.User;
import ru.etu.filmlibrary.models.repositories.UserRepository;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping(path = "/registration")
    public String addUser(@RequestParam String email,
                          @RequestParam String login,
                          @RequestParam String password, Model model) {

        if (userRepository.findByEmail(email) != null) {
            model.addAttribute("emailError",
                    "Пользователь с таким емейлом уже существует");
            return "registration";
        }

        if (userRepository.findByLogin(login) != null) {
            model.addAttribute("loginError",
                    "Пользователь с таким именем уже существует");
            return "registration";
        }

        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(password);

        userRepository.save(user);

        return "redirect:/user";
    }
}
