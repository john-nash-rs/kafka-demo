package com.wp.npe.db.dao;

import com.google.inject.Inject;
import com.wp.npe.db.entities.BulkInput;
import com.wp.npe.models.Bulk;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class BulkInputDao extends AbstractDAO<BulkInput> {

    @Inject
    public BulkInputDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void save(Bulk bulk){
        BulkInput bulkInput = new BulkInput();
        bulkInput.setInput(bulk.toString());
        bulkInput.setIsProcessed(0);
        persist(bulkInput);
    }
}
