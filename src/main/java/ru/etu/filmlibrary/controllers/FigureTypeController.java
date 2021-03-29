package ru.etu.filmlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.etu.filmlibrary.models.repositories.FigureTypeRepository;

@Controller
@RequestMapping(path="/figuretypes")
public class FigureTypeController {

    @Autowired
    FigureTypeRepository figureTypeRepository;

    @GetMapping
    public String getFigureTypes(Model model) {
        model.addAttribute("figuretypes", figureTypeRepository.findAll());
        return "figuretypes";
    }
}
