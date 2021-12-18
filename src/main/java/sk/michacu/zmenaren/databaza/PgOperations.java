package sk.michacu.zmenaren.databaza;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import sk.michacu.zmenaren.model.MenaObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PgOperations {
    private final SessionFactory sessionFactory;

    public PgOperations() {
        Configuration config = new Configuration().addAnnotatedClass(MenaObject.class).configure();
        ServiceRegistry servReg = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        this.sessionFactory = config.buildSessionFactory(servReg);
    }

    public List<MenaObject> findAll() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM MenaObject ");
        List<MenaObject> menaObjectList = query.list();
        session.close();
        System.out.println("Found all records...");
        return menaObjectList;
    }

    public MenaObject findById(Long id) {
        Session session = sessionFactory.openSession();
        MenaObject mena = session.get(MenaObject.class, id);
        session.close();
        System.out.println("Found record with id: " + id);
        return mena;
    }

    private void updateMenaInfo(Long id, boolean activeValue, String description) {
        Session session = sessionFactory.openSession();
        MenaObject menaObject = session.get(MenaObject.class, id);
        Date date = new Date();
        menaObject.setActive(activeValue);
        menaObject.setDescription(description);
        menaObject.setUpdated_at(date);
        session.beginTransaction();
        session.saveOrUpdate(menaObject);
        session.getTransaction().commit();
        session.close();
        System.out.println("Record updated succesfully...");
    }

    public void deleteAll() {
        List<MenaObject> menaElements = findAll();
        if (!menaElements.isEmpty()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            menaElements.forEach(session::delete);
            session.getTransaction().commit();
            session.close();
            System.out.println("Record deleted succesfully...");
        }
    }

    private void deleteMena(Long id) {
        Session session = sessionFactory.openSession();
        MenaObject menaObject = session.get(MenaObject.class, id);
        session.beginTransaction();
        session.delete(menaObject);
        session.getTransaction().commit();
        session.close();
        System.out.println("Record deleted succesfully...");
    }

    public void addMena(MenaObject menaObject) {
        List<MenaObject> menaElements = findAll();

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if (!menaElements.isEmpty()) {
            menaObject.setId(menaElements.get(menaElements.size() - 1).getId() + 1);
        }
        session.save(menaObject);
        session.close();
        System.out.println("Filling object data records...");
    }

    public void fillInitData(List<MenaObject> list) {
        List<MenaObject> menaElements = findAll();
        List<MenaObject> filteredList = new ArrayList<>();
        list.forEach(menaObject -> {
            if (!menaElements.isEmpty()) {
                menaElements.forEach(element -> {
                    if (!Objects.equals(element.getId(), menaObject.getId())) {
                        filteredList.add(menaObject);
                    }
                });
            } else {
                filteredList.add(menaObject);
            }
        });
        if (!filteredList.isEmpty()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            list.forEach(session::save);
            session.getTransaction().commit();
            session.close();
            System.out.println("Filling init data records...");
        }
    }
}
