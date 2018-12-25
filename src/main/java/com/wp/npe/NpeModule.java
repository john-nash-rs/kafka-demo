package com.wp.npe;

import com.google.inject.Binder;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.hubspot.dropwizard.guicier.DropwizardAwareModule;
import com.wp.npe.client.KafkaConsumerAPI;
import com.wp.npe.client.KafkaProducerAPI;
import com.wp.npe.core.KafkaHelper;
import com.wp.npe.db.dao.BulkInputDao;
import com.wp.npe.resources.KafkaDemoResource;
import io.dropwizard.hibernate.HibernateBundle;
import org.hibernate.SessionFactory;

public class NpeModule extends DropwizardAwareModule<NpeConfiguration> {

    private final HibernateBundle hibernateBundle;

    public NpeModule(HibernateBundle hibernateBundle) {
        this.hibernateBundle = hibernateBundle;
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(KafkaDemoResource.class).in(Singleton.class);
        binder.bind(KafkaProducerAPI.class).in(Singleton.class);
        binder.bind(BulkInputDao.class).in(Singleton.class);
        binder.bind(KafkaHelper.class).in(Singleton.class);
        binder.bind(KafkaConsumerAPI.class).in(Singleton.class);
    }

    @Singleton
    @Provides
    public SessionFactory sessionFactoryProvider() {
        return hibernateBundle.getSessionFactory();
    }

}
