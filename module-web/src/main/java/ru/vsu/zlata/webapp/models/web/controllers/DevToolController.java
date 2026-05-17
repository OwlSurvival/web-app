package ru.vsu.zlata.webapp.models.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.zlata.webapp.models.web.services.DevToolService;

@RestController
@RequestMapping(path = "/tools", produces = MediaType.APPLICATION_JSON_VALUE)
public class DevToolController {

    @Autowired
    private DevToolService devToolService;

    @GetMapping(value = "/fill-by-random-data")
    public ResponseEntity<?> fillByRandomData(){

        devToolService.fillByRandomData();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
