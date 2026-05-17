package ru.vsu.zlata.webapp.models;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
public class ChartData {
    private String label;
    private Integer[] values;
}
