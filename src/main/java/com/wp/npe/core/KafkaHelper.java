package com.wp.npe.core;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.wp.npe.client.KafkaProducerAPI;
import com.wp.npe.db.dao.BulkInputDao;
import com.wp.npe.db.dao.StudentDao;
import com.wp.npe.db.entities.Student;
import com.wp.npe.models.Bulk;
import com.wp.npe.models.BulkLightResponse;
import io.dropwizard.hibernate.UnitOfWork;

public class KafkaHelper {

    private BulkInputDao bulkInputDao;
    private KafkaProducerAPI kafkaProducerAPI;
    private StudentDao studentDao;

    @Inject
    public KafkaHelper(BulkInputDao bulkInputDao, KafkaProducerAPI kafkaProducerAPI, StudentDao studentDao) {
        this.bulkInputDao = bulkInputDao;
        this.kafkaProducerAPI = kafkaProducerAPI;
        this.studentDao = studentDao;
    }

    public BulkLightResponse bulkLightProcessing(Bulk bulk){
        BulkLightResponse bulkLightResponse = new BulkLightResponse();
        Boolean isValidRequest = validate(bulk);

        if(!isValidRequest){
            bulkLightResponse.setMessage("retry");
            bulkLightResponse.setStatus(500);
        }

        bulkInputDao.save(bulk);

        kafkaProducerAPI.send(bulk.getStudentId().toString(), bulk.toString());

        bulkLightResponse.setMessage("inserted");
        bulkLightResponse.setStatus(200);

        return bulkLightResponse;
    }

    private Boolean validate(Bulk bulk) {
        if(bulk == null)
            return false;

        if(bulk.getStudentId() == null || bulk.getStudentId().isEmpty())
            return false;

        return true;
    }
}
