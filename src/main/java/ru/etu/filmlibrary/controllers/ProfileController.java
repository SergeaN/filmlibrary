package ru.etu.filmlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.etu.filmlibrary.models.data.Role;
import ru.etu.filmlibrary.models.data.User;
import ru.etu.filmlibrary.models.repositories.UserRepository;
import ru.etu.filmlibrary.models.services.UserService;

import java.util.Set;

@Controller
@RequestMapping
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/profile")
    public String getUserInfo(Model model) {
        User user = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", user);
        for (Role role : user.getRoles()) {
            if (role.getName().equals("ROLE_ADMIN")) {
                model.addAttribute("role", "ADMIN");
            }
        }
        return "profile";
    }

    @GetMapping(path = "/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping(path = "/registration")
    public String addUser(@RequestParam String email,
                          @RequestParam String username,
                          @RequestParam String password, Model model) {

        if (userRepository.findByEmail(email) != null) {
            model.addAttribute("emailError",
                    "Пользователь с таким емейлом уже существует");
            return "registration";
        }

        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("loginError",
                    "Пользователь с таким именем уже существует");
            return "registration";
        }


        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);


        userService.saveUser(user);

        return "redirect:/film";
    }

    @PostMapping(path = "/delete")
    public String deleteUser(@RequestParam String id) {
        userService.deleteUser(Long.parseLong(id));
        return "redirect:/admin/users";
    }
}
