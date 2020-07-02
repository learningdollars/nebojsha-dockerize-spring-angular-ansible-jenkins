package com.example.demo.controllers;

import com.example.demo.services.FormulaOneDriversService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/formula_one/drivers", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormulaOneDriversController {
    FormulaOneDriversService service;

    public FormulaOneDriversController(FormulaOneDriversService driversService) {
        this.service = driversService;
    }

    @GetMapping("/list/{limit}/{language}")
    public ResponseEntity<String> getDrivers(@PathVariable Integer limit, @PathVariable String language) {
        return ResponseEntity.ok(this.service.getListOfDrivers(limit, language));
    }

    @GetMapping("/search/{name}/{limit}/{language}")
    public ResponseEntity<String> searchDrivers(@PathVariable String name, @PathVariable Integer limit, @PathVariable String language){
        return ResponseEntity.ok(this.service.search(name,language,limit));
    }

    @GetMapping("/details/basic/{fullName}/{language}")
    public ResponseEntity<String> getBasicDriverDetails(@PathVariable String fullName, @PathVariable String language) {
        return ResponseEntity.ok(this.service.getDriverDetails(fullName,language));
    }
}
