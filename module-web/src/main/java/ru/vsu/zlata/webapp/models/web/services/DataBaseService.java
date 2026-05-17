package ru.vsu.zlata.webapp.models.web.services;

import org.springframework.transaction.annotation.Transactional;
import ru.vsu.zlata.webapp.models.CatRecord;
import ru.vsu.zlata.webapp.models.ChartData;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface DataBaseService {
    void store(List<CatRecord> records);

    List<CatRecord> getRecords(String date);

    CatRecord getRecordById(String id);

    void update(CatRecord record);

    void deleteRecord(UUID id);

    Collection<ChartData> getEatenByNameYdm(String yearMonth);

    Collection<ChartData> getMoodNameYdm(String yearMonth);
}
