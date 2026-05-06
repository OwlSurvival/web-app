package ru.vsu.zlata.webapp.models.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.vsu.zlata.webapp.models.CatRecord;
import ru.vsu.zlata.webapp.models.web.services.DataBaseService;

import java.util.List;


@RestController
@RequestMapping(path = "/cats", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatsDataController {

    @Autowired
    DataBaseService dataBaseService;

    @GetMapping(path = "/test")
    public String test(){
        return "{\"status\":200}";
    }

    @PostMapping(path = "/insert-cat-records")
    public String saveRecords(@RequestBody List<CatRecord> records){
        dataBaseService.store(records);
        return "{}";
    }

    @GetMapping(path = "/records")
    public List<CatRecord> getForDate(@RequestParam(name = "date") String date){
        return dataBaseService.getRecords(date);
    }
}
