package com.wp.npe.client;

import com.google.common.util.concurrent.Runnables;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.wp.npe.NpeModule;
import com.wp.npe.core.KafkaHelper;
import com.wp.npe.db.dao.StudentDao;
import com.wp.npe.db.entities.Student;
import com.wp.npe.models.Bulk;
import io.dropwizard.hibernate.UnitOfWork;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumerAPI implements Runnable {

    Properties props = new Properties();
    KafkaConsumer<String, String> consumer;

    StudentDao studentDao;

    @Inject
    public KafkaConsumerAPI(StudentDao studentDao) {
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer
                <String, String>(props);
        consumer.subscribe(Arrays.asList(KafkaProducerAPI.TOPIC));
        this.studentDao = studentDao;
    }

    public void run(){
        System.out.println("-------------------------Consumer----------------------");
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("Consumer is working : offset = %d, key = %s, value = %s\n",
                        record.offset(), record.key(), record.value());
                processInputAsync(record.value());
            }




        }

    }

    @UnitOfWork
    public void processInputAsync(String record){
        Gson gson = new Gson();
        Bulk bulk = gson.fromJson(record, Bulk.class);

        Student student = new Student();
        student.setIsPresent(Integer.parseInt(bulk.getIsPresent()));
        student.setName(bulk.getName());
        student.setStudentId(Integer.parseInt(bulk.getStudentId()));
        System.out.println("hello - Student DB"+student.getName());
        studentDao.save(student);


    }
}
