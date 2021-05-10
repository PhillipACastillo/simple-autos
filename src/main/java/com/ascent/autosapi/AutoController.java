package com.ascent.autosapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/autos")
public class AutoController {
    private AutoService autoService;

    public AutoController (AutoService autoservice) {
        this.autoService = autoservice;
    }

    @GetMapping("")
    public ArrayList<Automobile> returnAutomobiles(@RequestParam(required = false) String color) {
        return color == null ? autoService.getAllAutos() : autoService.getAllAutosWithCriteria(color);
    }
}
