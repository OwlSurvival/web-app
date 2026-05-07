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
    @GetMapping(path = "/record-by-id")
    public CatRecord getRecordeById(@RequestParam(name = "id") String id){
        return dataBaseService.getRecordById(id);
    }

    @PostMapping(path = "/update-record")
    public String saveRecords(@RequestBody CatRecord record){
        dataBaseService.update(record);
        return "{}";
    }

    @GetMapping(path = "/chart-data")
    public String getDataForChart(){
        return "[\n" +
                "    {\"label\": \"Label 1\", \"value\": 10, \"value2\": 5},\n" +
                "    {\"label\": \"Label 1\", \"value\": 11, \"value2\": 5},\n" +
                "    {\"label\": \"Label 1\", \"value\": 12, \"value2\": 8},\n" +
                "    {\"label\": \"Label 1\", \"value\": 13, \"value2\": 6},\n" +
                "    {\"label\": \"Label 1\", \"value\": 10, \"value2\": 4},\n" +
                "    {\"label\": \"Label 1\", \"value\": 5, \"value2\": 1},\n" +
                "    {\"label\": \"Label 1\", \"value\": 6, \"value2\": 6}\n" +
                "  ]";
    }

}
