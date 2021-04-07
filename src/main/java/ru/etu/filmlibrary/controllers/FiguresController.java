package ru.etu.filmlibrary.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.etu.filmlibrary.models.data.Figure;
import ru.etu.filmlibrary.models.data.FigureType;
import ru.etu.filmlibrary.models.data.Film;
import ru.etu.filmlibrary.models.repositories.FigureRepository;
import ru.etu.filmlibrary.models.repositories.FigureTypeRepository;
import ru.etu.filmlibrary.utils.DateUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/figures")
public class FiguresController {
    @Autowired
    FigureRepository figureRepository;

    @Autowired
    FigureTypeRepository figureTypeRepository;


    @GetMapping(value = {"/{id}"})
    public String getFigureByTypeId(@PathVariable(value = "id") String typeId,
                                    Model model) {

        figureTypeRepository.findById(Integer.parseInt(typeId))
                .ifPresent(figureType -> model.addAttribute("title",
                        figureType.getTitle()));

        model.addAttribute("figures", figureRepository
                .findFiguresByTypeId(Integer.parseInt(typeId)));

        model.addAttribute("figuretypes", figureTypeRepository.findAll().toString());

        return "figures-list";
    }

}
