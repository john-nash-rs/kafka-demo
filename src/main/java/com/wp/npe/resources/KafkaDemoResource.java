package com.wp.npe.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.wp.npe.core.KafkaHelper;
import com.wp.npe.models.Bulk;
import com.wp.npe.models.BulkLightResponse;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/kafka-demo-resource")
@Produces(MediaType.APPLICATION_JSON)
public class KafkaDemoResource {

    KafkaHelper kafkaHelper;

    @Inject
    public KafkaDemoResource(KafkaHelper kafkaHelper) {
        this.kafkaHelper = kafkaHelper;
    }

    @POST
    @Timed
    @UnitOfWork
    @Path("/bulk-light")
    public BulkLightResponse bulkLight(Bulk bulk) {
        return kafkaHelper.bulkLightProcessing(bulk);
    }
}
