package com.twoday.warehouse.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/status")
public class StatusController {

    @GetMapping
    public ResponseEntity<HttpStatus> getStatus() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
