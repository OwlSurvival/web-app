package ru.vsu.zlata.webapp.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/cats", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatsDataController {

    @GetMapping(path = "/test")
    public String test(){
        return "{\"status\":200}";
    }

}
