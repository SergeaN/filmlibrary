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
import java.text.SimpleDateFormat;
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
            String birthday = new SimpleDateFormat("dd/MM/yyyy")
                    .format(figure.getBirthday());
            model.addAttribute("figure", figure);
            model.addAttribute("birthday", birthday);

            return "figure";
        } else return "redirect:/figure";
    }

    @PostMapping(path = "/add")
    public String addFigure(@RequestParam String type_id,
                            @RequestParam String fullname,
                            @RequestParam String sex,
                            @RequestParam String birthday,
                            @RequestParam String photo_href) {
        Figure figure = new Figure();
        Optional<FigureType> optional = figureTypeRepository.findById(Integer.valueOf(type_id));
        optional.ifPresent(figure::setTypeId);
        figure.setFullname(fullname);
        figure.setPhotoHref(photo_href);
        if (sex.length() == 1 && (sex.contains("М") || sex.contains("Ж"))) figure.setSex(sex);
        else {
            return "redirect:/admin/figures";
        }
        try {
            figure.setBirthday(DateUtil.getDateFromString(birthday));
        } catch (ParseException e) {
            return "redirect:/admin/figures";
        }
        figureRepository.save(figure);
        return "redirect:/admin/figures";
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
        return "redirect:/admin/figures";
    }

    @GetMapping
    public String getFigures(Model model) {
        model.addAttribute("title", "Все фигур");
        model.addAttribute("figures", figureRepository.findAll());
        model.addAttribute("figuretypes", figureTypeRepository.findAll());
        return "figures";
    }

}
