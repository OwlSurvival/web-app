package ru.vsu.zlata.webapp.models.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.zlata.webapp.models.CatRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class DataBaseServiceImpl implements DataBaseService {

    private static final String SQL_INSERT =
            "INSERT INTO temp_table1 (ID,NAME,CREATED_DATE_TIME)VALUES(?,?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void store(List<CatRecord> records) {

        BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                ps.setString(1, records.get(i).getId().toString());
                ps.setString(2, records.get(i).getName());
                LocalDateTime dateTime = parse(records.get(i).getDatetime());
                ps.setObject(3, dateTime);
            }
            @Override
            public int getBatchSize() {
                return records.size();
            }
        };

        jdbcTemplate.batchUpdate(SQL_INSERT, setter);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CatRecord> getRecords(String date) {
        //select * from TEMP_TABLE1 where  CAST(CREATED_DATE_TIME AS DATE)  = '2026-04-29 '
        List<CatRecord> result =
        jdbcTemplate.query("select ID, NAME, CREATED_DATE_TIME" +
                        " from temp_table1 WHERE CAST(CREATED_DATE_TIME AS DATE)  = ?",
                new Object[]{date},
                new RowMapper<CatRecord>() {
                    @Override
                    public CatRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                        CatRecord record =new CatRecord();
                        record.setId(rs.getObject(1, UUID.class));
                        record.setName(rs.getString(2));
                        LocalDateTime dt = rs.getObject(3, LocalDateTime.class);
                        record.setDatetime(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dt));
                        return record;
                    }
                });
        return result;
    }

    private LocalDateTime parse(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        //= DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

}
