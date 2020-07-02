package com.example.demo.controllers;

import com.example.demo.services.FormulaOneService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/formula_one", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormulaOneController {
    FormulaOneService service;

    public FormulaOneController(FormulaOneService service) {
        this.service = service;
    }

    @GetMapping("/abstract/{language}")
    public ResponseEntity<String> getAbstract(@PathVariable String language) {
        return ResponseEntity.ok(this.service.getAbstract(language));
    }
}
