package ru.etu.filmlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.etu.filmlibrary.models.data.Role;
import ru.etu.filmlibrary.models.data.User;
import ru.etu.filmlibrary.models.repositories.UserRepository;
import ru.etu.filmlibrary.models.services.UserService;

import java.util.Set;

@Controller
@RequestMapping(path = "/profile")
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

   /* @GetMapping(value = {"/{id}"})
    public String getUserInfo(@PathVariable(value = "id") String id, Model model){
        Optional<User> optionalUser = userRepository.findById(Long.parseLong(id));
        if(optionalUser.isPresent()){
            model.addAttribute("user", optionalUser.get().toString());
        }
        return "profile";
    }*/

    @GetMapping
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
}
