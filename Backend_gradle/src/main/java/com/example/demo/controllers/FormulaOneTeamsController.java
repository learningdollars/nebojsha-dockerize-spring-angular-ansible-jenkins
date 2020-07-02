package com.example.demo.controllers;

import com.example.demo.services.FormulaOneTeamsService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/formula_one/teams", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormulaOneTeamsController {

    private FormulaOneTeamsService service;

    public FormulaOneTeamsController(FormulaOneTeamsService teamsService) { this.service = teamsService; }

    @GetMapping("/list/{limit}/{language}")
    public ResponseEntity<String> getDrivers(@PathVariable Integer limit, @PathVariable String language) {
        return ResponseEntity.ok(this.service.getListOfTeams(limit, language));
    }

    @GetMapping("/search/{name}/{limit}/{language}")
    public ResponseEntity<String> searchDrivers(@PathVariable String name, @PathVariable Integer limit, @PathVariable String language){
        return ResponseEntity.ok(this.service.search(name,language,limit));
    }
}
