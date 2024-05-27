package org.danieska.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GenericDAOImpl <T> implements IGenericDao<T>{
    private SessionFactory sessionFactory;
    public GenericDAOImpl(Class<T> cl, SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
        throw new RuntimeException("La fabrica de session es nula ! ! !");
    }
    @Override
    public T get(Class<T> cl, Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        T element = session.get(cl, id);
        session.getTransaction().commit();
        return element;
    }
    @Override
    public T get(Class<T> cl, Long id) {
        return null;
    }

    @Override
    public T save(T object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
        return null;
    }

    @Override
    public void update(T object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(object);
        session.getTransaction().commit();

    }

    @Override
    public void delete(T object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();

    }

    @Override
    public List<T> query(String hsql, Map<String, Objects> params) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery(hsql);
        if(params != null){
            for(String i: params.keySet()){
                query.setParameter(i, params.get(i));
            }
        }
        List<T>resuelt = null;
            if((!hsql.toUpperCase().contains("DELETE"))
                    && (!hsql.toUpperCase().contains("UPDATE"))
                    && (!hsql.toUpperCase().contains("INSERT"))
            ){
             resuelt = query.list();
           }
           session.getTransaction().commit();
       return resuelt;



    }
}