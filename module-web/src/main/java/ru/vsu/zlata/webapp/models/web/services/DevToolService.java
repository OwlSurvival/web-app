package ru.vsu.zlata.webapp.models.web.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.zlata.webapp.models.CatRecord;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Log4j2
public class DevToolService {


    @Autowired
    private DataBaseService dataBaseService;
    private boolean debugMode;


    public static void main(String[] args) {
        DevToolService service = new DevToolService();
        service.setDebugMode(true);
        service.fillByRandomData();
    }

    private void setDebugMode(boolean debugMode) {
      this.debugMode = debugMode;
    }


    @Transactional
    public void fillByRandomData() {

        String[] cats = {"Муся", "Бетти", "Мурзик"};
        double[] catsWeight = {2.500, 3.000, 1.000};
        String[] eatType = {"Felix", "Perfect", "Kitekat"};
        LocalDate today = LocalDate.now();
        LocalDate currentDate = today.minusDays(365);
        Random random = new Random();
        int eatWeight, mood, eatTypeIndex, prevEatTypeIndex = 0;
        CatRecord rec = null;
        LocalDateTime[] dayEatTime = new LocalDateTime[3];
        List<CatRecord> listOfRecords = new ArrayList<>();

        while (currentDate.isBefore(today)) {

            dayEatTime[0] = currentDate.atTime(8, 0);
            dayEatTime[1] = currentDate.atTime(12, 0);
            dayEatTime[2] = currentDate.atTime(19, 0);

            for (int dayEatTimeIndex = 0; dayEatTimeIndex < dayEatTime.length; dayEatTimeIndex++) {
                eatTypeIndex = random.nextInt(0, 3);
                for (int catIndex = 0; catIndex < cats.length; catIndex++) {
                    //Perfect is better than others for the cat mood
                    if (prevEatTypeIndex == 1) {
                        mood = random.nextInt(4, 6);
                        if (catIndex == 2) {//cat is too small it can't eat to mach
                            eatWeight = random.nextInt(20, 51);
                        } else {
                            eatWeight = random.nextInt(50, 81);
                        }
                    } else {
                        mood = random.nextInt(1, 6);
                        if (catIndex == 2) {//cat is too small it can't eat to mach
                            eatWeight = random.nextInt(10, 41);
                        } else {
                            eatWeight = random.nextInt(0, 51);
                        }
                    }

                    catsWeight[catIndex] += random.nextDouble(-0.001, 0.002);
                    rec = new CatRecord();
                    rec.setId(UUID.randomUUID());
                    rec.setName(cats[catIndex]);
                    rec.setDatetime(dayEatTime[dayEatTimeIndex].toString());
                    rec.setEatName(eatType[eatTypeIndex]);
                    rec.setWeight(roundDouble3Scale(catsWeight[catIndex]));
                    rec.setEatWeight(eatWeight);
                    rec.setHappiness(mood);
                    if(debugMode) {
                      System.out.println("rec: " + rec);
                    }

                    listOfRecords.add(rec);
                }
                prevEatTypeIndex = eatTypeIndex;
            }

            if(!debugMode)
             dataBaseService.store(listOfRecords);
            listOfRecords.clear();
            currentDate = currentDate.plusDays(1);
        }
    }

    public static double roundDouble3Scale(double src) {
        return BigDecimal.valueOf(src)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
