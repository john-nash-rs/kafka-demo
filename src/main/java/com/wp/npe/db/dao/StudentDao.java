package com.wp.npe.db.dao;

import com.google.inject.Inject;
import com.wp.npe.db.entities.BulkInput;
import com.wp.npe.db.entities.Student;
import com.wp.npe.models.Bulk;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class StudentDao extends AbstractDAO<Student> {

    @Inject
    public StudentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void save(Student student){
        persist(student);
    }
}
