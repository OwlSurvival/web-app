package ru.vsu.zlata.webapp.models;

import lombok.Data;

import java.util.UUID;

@Data
public class CatRecord {
    private UUID id;
    private String name;
    private String datetime;
    private double weight;
    private String eatName;
    private int eatWeight;
    private int happiness;
}