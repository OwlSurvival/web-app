package ru.vsu.zlata.webapp.models.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.zlata.webapp.models.CatRecord;
import ru.vsu.zlata.webapp.models.ChartData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DataBaseServiceImpl implements DataBaseService {

    public static final String TABLE_NAME = "cats_data";
    public static final String SQL_INSERT =
            "INSERT INTO "+ TABLE_NAME +" (id,name,date_time,type_eat,weight_cat,weight_eat,mood_cat)" +
                    "VALUES(?,?,?,?,?,?,?)";
    private static final String SQL_GET_DATA = "select id,name,date_time ,type_eat,weight_cat,weight_eat,mood_cat" +
            " from cats_data WHERE CAST(date_time AS DATE)  = ?";
    private static final String UPDATE =
            "UPDATE cats_data SET NAME = ?, date_time = ?,type_eat = ?,weight_cat = ?,weight_eat = ?,mood_cat = ? \n" +
                    "WHERE ID = ?";

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
                ps.setString(4,records.get(i).getEatName());
                ps.setDouble(5,records.get(i).getWeight());
                ps.setInt(6,records.get(i).getEatWeight());
                ps.setInt(7,records.get(i).getHappiness());
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
                jdbcTemplate.query(SQL_GET_DATA,
                        new Object[]{date},
                        new RowMapper<CatRecord>() {
                            @Override
                            public CatRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                                CatRecord record = new CatRecord();
                                record.setId(rs.getObject(1, UUID.class));
                                record.setName(rs.getString(2));
                                LocalDateTime dt = rs.getObject(3, LocalDateTime.class);
                                record.setDatetime(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dt));
                                record.setEatName(rs.getString(4));
                                record.setWeight(rs.getDouble(5));
                                record.setEatWeight(rs.getInt(6));
                                record.setHappiness(rs.getInt(7));

                                return record;
                            }
                        });
        return result;
    }

    public CatRecord getRecordById(String id) {
        List<CatRecord> result =
                jdbcTemplate.query("select id, name, date_time, type_eat,weight_cat,weight_eat,mood_cat" +
                                " from cats_data WHERE ID  = ?",
                        new Object[]{id},
                        new RowMapper<CatRecord>() {
                            @Override
                            public CatRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                                CatRecord record = new CatRecord();
                                record.setId(rs.getObject(1, UUID.class));
                                record.setName(rs.getString(2));
                                LocalDateTime dt = rs.getObject(3, LocalDateTime.class);
                                record.setDatetime(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dt));
                                record.setEatName(rs.getString(4));
                                record.setWeight(rs.getDouble(5));
                                record.setEatWeight(rs.getInt(6));
                                record.setHappiness(rs.getInt(7));
                                return record;
                            }
                        });
        return result.get(0);
    }

    @Override
    @Transactional
    public void update(CatRecord record) {
        LocalDateTime dateTime = parse(record.getDatetime());
        jdbcTemplate.update(UPDATE, record.getName(), dateTime, record.getEatName(),
                record.getWeight(),record.getEatWeight(),record.getHappiness(), record.getId());
    }

    @Override
    public void deleteRecord(UUID id) {
        jdbcTemplate.update("DELETE FROM "+ TABLE_NAME + " WHERE id = ?", id);
    }

    private static final String SELECT_EATEN=
            "SELECT TO_CHAR(DATE_TIME, 'YYYY-MM-DD') AS YMD,  \n" +
                    "SUM(WEIGHT_EAT) FILTER (WHERE NAME = 'Бетти') AS value_betty,\n" +
                    "SUM(WEIGHT_EAT) FILTER (WHERE NAME = 'Мурзик') AS value_murzik,\n" +
                    "SUM(WEIGHT_EAT) FILTER (WHERE NAME = 'Муся') AS value_musya\n" +
                    "FROM CATS_DATA\n" +
                    "WHERE TO_CHAR(DATE_TIME, 'YYYY-MM') = ?\n" +
                    "GROUP BY YMD ORDER BY YMD";

    @Override
    public Collection<ChartData> getEatenByNameYdm(String yearMonth){

       return jdbcTemplate.query(SELECT_EATEN,
                new Object[]{yearMonth},
                new RowMapper<ChartData>() {
                    @Override
                    public ChartData mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ChartData data = new ChartData();
                        data.setLabel(rs.getString(1));
                        Integer [] values = new Integer[3];
                        values[0]=rs.getInt(2);
                        values[1]=rs.getInt(3);
                        values[2]=rs.getInt(4);
                        data.setValues(values);
                        return data;
                    }
                });
    }

    private static final String SELECT_MOOD=
            "SELECT TO_CHAR(DATE_TIME, 'YYYY-MM-DD') AS YMD,  \n" +
                    "SUM(mood_cat) FILTER (WHERE NAME = 'Бетти') AS value_betty,\n" +
                    "SUM(mood_cat) FILTER (WHERE NAME = 'Мурзик') AS value_murzik,\n" +
                    "SUM(mood_cat) FILTER (WHERE NAME = 'Муся') AS value_musya\n" +
                    "FROM CATS_DATA\n" +
                    "WHERE TO_CHAR(DATE_TIME, 'YYYY-MM') = ?\n" +
                    "GROUP BY YMD ORDER BY YMD";
    @Override
    public Collection<ChartData> getMoodNameYdm(String yearMonth) {
        return jdbcTemplate.query(SELECT_MOOD,
                new Object[]{yearMonth},
                new RowMapper<ChartData>() {
                    @Override
                    public ChartData mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ChartData data = new ChartData();
                        data.setLabel(rs.getString(1));
                        Integer [] values = new Integer[3];
                        values[0]=rs.getInt(2);
                        values[1]=rs.getInt(3);
                        values[2]=rs.getInt(4);
                        data.setValues(values);
                        return data;
                    }
                });
    }

    private LocalDateTime parse(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        //= DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

}
