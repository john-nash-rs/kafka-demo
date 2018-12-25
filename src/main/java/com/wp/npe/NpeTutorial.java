package com.wp.npe;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.hubspot.dropwizard.guicier.GuiceBundle;
import com.wp.npe.client.KafkaConsumerAPI;
import com.wp.npe.client.KafkaProducerAPI;
import com.wp.npe.core.KafkaHelper;
import com.wp.npe.db.dao.BulkInputDao;
import com.wp.npe.db.dao.StudentDao;
import com.wp.npe.db.entities.Student;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class NpeTutorial extends Application<NpeConfiguration> {

    private GuiceBundle<NpeConfiguration> guiceBundle;
    private  static HibernateBundle<NpeConfiguration> hibernateBundle =
            new ScanningHibernateBundle<NpeConfiguration>("com.wp") {
                public PooledDataSourceFactory getDataSourceFactory(NpeConfiguration sessionConfiguration) {
                    return sessionConfiguration.getDataSourceFactory();
                }
            };

    public static void main(final String[] args) throws Exception {
        new NpeTutorial().run(args);
//        Injector inject = Guice.createInjector(new NpeModule(hibernateBundle));
//        KafkaHelper kafkaHelper = inject.getInstance(KafkaHelper.class);
//        new KafkaConsumerAPI(kafkaHelper).process();
        StudentDao dao = new StudentDao(hibernateBundle.getSessionFactory());
        KafkaConsumerAPI kafkaConsumerAPI = new UnitOfWorkAwareProxyFactory(hibernateBundle)
                .create(KafkaConsumerAPI.class, StudentDao.class, dao);
        Thread th = new Thread(kafkaConsumerAPI);
        th.start();


    }

    @Override
    public String getName() {
        return "npe-tutorials";
    }

    @Override
    public void initialize(final Bootstrap<NpeConfiguration> bootstrap) {

        guiceBundle = GuiceBundle.defaultBuilder(NpeConfiguration.class)
                .modules(new NpeModule(hibernateBundle))
                .enableGuiceEnforcer(false)
                .build();

        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(guiceBundle);




    }

    @Override
    public void run(final NpeConfiguration configuration,
                    final Environment environment) {
        System.out.println("NpeConfiguration : "+configuration.toString());


        // TODO: implement application
    }



}
