package com.ankit.fruitseller.controller;

import com.ankit.fruitseller.models.Combo;
import com.ankit.fruitseller.service.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ComboController {
    @Autowired
    private ComboService service;

    @PostMapping(value = "/combo", consumes = "application/json", produces = "application/json")
    ResponseEntity<Combo> post(@RequestBody Combo combo) {
        return new ResponseEntity<>(service.save(combo), HttpStatus.CREATED);
    }

    @GetMapping(value = "/combo/{comboId}", produces = "application/json")
    ResponseEntity<Combo> get(@PathVariable UUID comboId) {
        return new ResponseEntity<>(service.get(comboId), HttpStatus.OK);
    }

    @GetMapping(value = "/combo", produces = "application/json")
    ResponseEntity<List<Combo>> get() {
        return new ResponseEntity<>(service.get(), HttpStatus.OK);
    }
}
