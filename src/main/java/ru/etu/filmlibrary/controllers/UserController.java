package ru.etu.filmlibrary.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.etu.filmlibrary.models.data.User;
import ru.etu.filmlibrary.models.repositories.UserRepository;
import ru.etu.filmlibrary.models.repositories.UserTypeRepository;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTypeRepository userTypeRepository;

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll().toString());
        model.addAttribute("usertypes", userTypeRepository.findAll().toString());
        return "admin-users";
    }

    @PostMapping(path = "/add")
    public String addUser(
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String type_id) {

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        userTypeRepository.findById(Integer.parseInt(type_id)).ifPresent(user::setUserType);

        userRepository.save(user);

        return "redirect:/user";
    }

    @PostMapping(path = "/delete")
    public String deleteUser(@RequestParam String id) {
        try {
            userRepository.deleteById(Integer.valueOf(id));
        } catch (EmptyResultDataAccessException | IllegalArgumentException ignored) {
        }
        return "redirect:/user";
    }

    @PostMapping(path = "/login")
    public @ResponseBody
    String login(@RequestParam String login, @RequestParam String password) {
        User user = userRepository.checkLogin(login, password);
        if (user != null) {
            return "Success login";
        }
        else return "Login error";
    }

}
