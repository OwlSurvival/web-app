package ru.vsu.zlata.webapp.models.web.services;

import org.springframework.transaction.annotation.Transactional;
import ru.vsu.zlata.webapp.models.CatRecord;

import java.util.List;

public interface DataBaseService {
    void store(List<CatRecord> records);

    @Transactional(rollbackFor = Exception.class)
    List<CatRecord> getRecords(String date);
}
