package ru.etu.filmlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.etu.filmlibrary.models.data.Role;
import ru.etu.filmlibrary.models.data.User;
import ru.etu.filmlibrary.models.repositories.RoleRepository;
import ru.etu.filmlibrary.models.repositories.UserRepository;
import ru.etu.filmlibrary.models.services.UserService;

import java.util.Optional;

@Controller
@RequestMapping
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

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

    private boolean createUser(@RequestParam String email,
                               @RequestParam String username,
                               @RequestParam String password, Model model) {
        if (userRepository.findByEmail(email) != null) {
            model.addAttribute("emailError",
                    "Пользователь с таким емейлом уже существует");
            return true;
        }

        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("loginError",
                    "Пользователь с таким именем уже существует");
            return true;
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        userService.saveUser(user);
        return false;
    }

    @PostMapping(path = "/registration")
    public String registerUser(@RequestParam String email,
                               @RequestParam String username,
                               @RequestParam String password, Model model) {

        if (createUser(email, username, password, model)) return "registration";

        return "redirect:/";
    }

    @PostMapping(path = "/user/add")
    public String addUser(@RequestParam String email,
                          @RequestParam String username,
                          @RequestParam String password, Model model) {

        if (createUser(email, username, password, model)) return "registration";

        return "redirect:/admin/users";
    }

    @PostMapping(path = "/user/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") String id) {
        userService.deleteUser(Long.parseLong(id));
        return "redirect:/admin/users";
    }

    @PostMapping(path = "/user/setadmin/{id}")
    public String setAdminRole(@PathVariable(value = "id") String id) {
        if (Long.parseLong(id) == 4L) return "redirect:/admin/users";
        Optional<User> optionalUser = userRepository.findById(Long.parseLong(id));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Role role = roleRepository.findById(2L).get();
            optionalUser.get().getRoles().add(role);
            userRepository.save(user);
        }
        return "redirect:/admin/users";
    }

    @PostMapping(path = "/user/resetadmin/{id}")
    public String resetAdminRole(@PathVariable(value = "id") String id) {
        if (Long.parseLong(id) == 4L) return "redirect:/admin/users";
        Optional<User> optionalUser = userRepository.findById(Long.parseLong(id));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Role role = roleRepository.findById(2L).get();
            optionalUser.get().getRoles().remove(role);
            userRepository.save(user);
        }
        return "redirect:/admin/users";
    }

}
