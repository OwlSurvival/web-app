package ru.vsu.zlata.webapp.models.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.vsu.zlata.webapp.models.CatRecord;
import ru.vsu.zlata.webapp.models.ChartData;
import ru.vsu.zlata.webapp.models.web.services.DataBaseService;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


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
    @GetMapping(path = "/record-by-id")
    public CatRecord getRecordeById(@RequestParam(name = "id") String id){
        return dataBaseService.getRecordById(id);
    }

    @PostMapping(path = "/update-record")
    public String saveRecords(@RequestBody CatRecord record){
        dataBaseService.update(record);
        return "{}";
    }

    @DeleteMapping(path = "/record-remove")
    public String deleteRecord(@RequestParam(name= "id") UUID id){
        dataBaseService.deleteRecord(id);
        return "{}";
    }

    @GetMapping(path = "/chart-data/eaten")
    public Collection<ChartData>  getDataForChart(@RequestParam(name= "year-month") String yearMonth){
        return dataBaseService.getEatenByNameYdm(yearMonth);
    }

    @GetMapping(path = "/chart-data/mood")
    public Collection<ChartData>  getMoodForChart(@RequestParam(name= "year-month") String yearMonth){
        return dataBaseService.getMoodNameYdm(yearMonth);
    }
}
