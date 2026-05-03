package ru.vsu.zlata.webapp;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import ru.vsu.zlata.webapp.database.generated.jooq.models.Tables;
//import ru.vsu.zlata.webapp.database.generated.jooq.models.tables.daos.TempTable1Dao;
//import ru.vsu.zlata.webapp.database.generated.jooq.models.tables.pojos.TempTable1;
//import ru.vsu.zlata.webapp.database.generated.jooq.models.tables.records.TempTable1Record;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


//@EnableAutoConfiguration
//@ComponentScan({"com.spingboot.examples"})
//@Configuration
//@EnableTransactionManagement - Do not need
@ImportResource(locations = {"classpath:cntx/beans.xml"})
@SpringBootApplication
public class StartApp {

    private static final Logger LOG = LoggerFactory.getLogger(StartApp.class);

    public static void main(String[] args) throws Exception {
    	ApplicationContext context = SpringApplication.run(StartApp.class, args);
        LOG.info("Application webapp has been started!");


       // for(String name: context.getBeanDefinitionNames())
      //  LOG.trace("bean: "+name);

        DSLContext dsl = context.getBean(DSLContext.class);
        Configuration configuration = dsl.configuration();
        //LOG.info("dsl configuration: {}", dsl);


/*

        TempTable1Dao dao =new TempTable1Dao(configuration);
        for(int i=0; i<3;i++) {

            // INSERT NEW ENTITY:
            // 1) Using dao:
            TempTable1 pojo1 = new TempTable1();
            pojo1.setName("NameFromCode_DAO_"+i);
            dao.insert(pojo1);
            LOG.info("add new pojo1: {}", pojo1);
            //pojo id is NOT null;

            // 2) Using record:
            TempTable1 pojo2 = new TempTable1();
            pojo2.setName("NameFromCode_RECORD_"+i);
            //TempTable1Record record =dsl.newRecord(Tables.TEMP_TABLE1, pojo2);
            TempTable1Record record =dsl.newRecord(Tables.TEMP_TABLE1);
            record.from(pojo2);
            record.insert();
            //for update all fields -> record.store();
            LOG.info("add new pojo2: {}", pojo2);
            //pojo id is null

            try {

                List<TempTable1> enities=
                dsl
                //DSL.using(configuration)
                        .select()
                        .from(Tables.TEMP_TABLE1)
                        .fetchInto(TempTable1.class);
                for(TempTable1 entity: enities){
                    LOG.trace("entity: {}", entity);
                }

            } catch (Exception e) {
                LOG.error(e.getLocalizedMessage(), e);
            }


        }
  */
    }
}