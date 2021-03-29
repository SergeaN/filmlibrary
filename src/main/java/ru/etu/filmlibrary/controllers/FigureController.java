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
@RequestMapping(path = "/figure")
public class FigureController {
    @Autowired
    FigureRepository figureRepository;

    @Autowired
    FigureTypeRepository figureTypeRepository;

    @GetMapping(value = {"/{id}"})
    public String getFigure(@PathVariable(value = "id") String id, Model model) {
        Optional<Figure> optionalFigure = figureRepository.findById(Integer.parseInt(id));
        if (optionalFigure.isPresent()) {
            Figure figure = optionalFigure.get();
            model.addAttribute("id", figure.getId())
                    .addAttribute("type", figure.getId())
                    .addAttribute("fullname", figure.getId())
                    .addAttribute("sex", figure.getId())
                    .addAttribute("birthday", figure.getId());

            List<String> films = new ArrayList<>();
            for (Film film : figure.getFilms()) {
                films.add(film.getTitle() + " " + film.getReleased());
            }
            model.addAttribute("films", films);
            return "figure";
        } else return "redirect:/figure";
    }

    @PostMapping(path = "/add")
    public String addFigure(@RequestParam String type_id,
                            @RequestParam String fullname,
                            @RequestParam String sex,
                            @RequestParam String birthday) {
        Figure figure = new Figure();
        Optional<FigureType> optional = figureTypeRepository.findById(Integer.valueOf(type_id));
        optional.ifPresent(figure::setTypeId);
        figure.setFullname(fullname);
        if (sex.length() == 1 && (sex.contains("М") || sex.contains("Ж"))) figure.setSex(sex);
        else {
            return "redirect:/figure";
        }
        try {
            figure.setBirthday(DateUtil.getDateFromString(birthday));
        } catch (ParseException e) {
            return "redirect:/figure";
        }
        figureRepository.save(figure);
        return "redirect:/figure";
    }

    @PostMapping(path = "/update")
    public String updateFigure(@RequestParam String id,
                               @RequestParam String type_id,
                               @RequestParam String fullname,
                               @RequestParam String sex,
                               @RequestParam String birthday) {
        Optional<Figure> optionalFigure = figureRepository.findById(Integer.parseInt(id));
        Optional<FigureType> optional = figureTypeRepository.findById(Integer.valueOf(type_id));

        if (optionalFigure.isPresent()) {
            Figure figure = optionalFigure.get();
            figure.setFullname(fullname);
            optional.ifPresent(figure::setTypeId);
            if (sex.length() == 1 && (sex.contains("М") || sex.contains("Ж"))) figure.setSex(sex);
            else {
                return "redirect:/figure";
            }
            try {
                figure.setBirthday(DateUtil.getDateFromString(birthday));
            } catch (ParseException ignore) {
            }
            figureRepository.save(figure);
            return "redirect:/figure";
        }
        return "redirect:/figure";
    }

    @PostMapping(path = "/delete")
    public String deleteFigure(@RequestParam String id) {
        try {
            figureRepository.deleteById(Integer.valueOf(id));
        } catch (EmptyResultDataAccessException | IllegalArgumentException ignored) {
        }
        return "redirect:/figure";
    }

    @GetMapping
    public String getFigures(Model model) {
        model.addAttribute("figures", figureRepository.findAll());
        model.addAttribute("figuretypes", figureTypeRepository.findAll());
        return "figures";
    }
}
