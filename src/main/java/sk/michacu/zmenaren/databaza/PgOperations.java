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
import java.util.Optional;

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

    public void updateMenaInfo(String currName,String icon, boolean activeValue, String description) {
        List<MenaObject> menaElements = findAll();
        if (!menaElements.isEmpty()) {
            menaElements.forEach(menaObject -> {
                Optional<MenaObject> tmpObj = menaElements.stream().filter(element -> element.getCurrName().equals(currName)).findFirst();
                if (tmpObj.isPresent()) {
                    MenaObject updatedObject = fillUpdatedObj(tmpObj.get(),icon,activeValue,description);
                    Session session = sessionFactory.openSession();
                    session.beginTransaction();
                    session.saveOrUpdate(updatedObject);
                    session.getTransaction().commit();
                    session.close();
                    System.out.println("Record updated succesfully...");
                }
            });
        }
        System.out.println("Record cant be found...");
    }

    private MenaObject fillUpdatedObj(MenaObject menaObject, String icon, boolean activeValue, String description) {
        menaObject.setIcon(icon);
        menaObject.setActive(activeValue);
        menaObject.setDescription(description);
        menaObject.setUpdated_at(new Date());
        return menaObject;
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

    public void deleteMena(String currencyName) {
        List<MenaObject> menaElements = findAll();
        if (!menaElements.isEmpty()) {
            menaElements.forEach(menaObject -> {
                Optional<MenaObject> tmpObj = menaElements.stream().filter(element -> element.getCurrName().equals(currencyName)).findFirst();
                if (tmpObj.isPresent()) {
                    Session session = sessionFactory.openSession();
                    MenaObject removeObject = session.get(MenaObject.class, tmpObj.get().getId());
                    session.beginTransaction();
                    session.delete(removeObject);
                    session.getTransaction().commit();
                    session.close();
                    System.out.println("Record deleted succesfully...");
                }
            });
        }
        System.out.println("Record cant be found...");
    }

    public void addMena(MenaObject menaObject) {
        List<MenaObject> menaElements = findAll();

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if (!menaElements.isEmpty()) {
            menaObject.setId(menaElements.get(menaElements.size() - 1).getId() + 1);
        }
        session.save(menaObject);
        session.getTransaction().commit();
        session.close();
        System.out.println("Filling object data records...");
    }

    public void fillInitData(List<MenaObject> list) {
        List<MenaObject> menaElements = findAll();
        List<MenaObject> filteredList;
        if (!menaElements.isEmpty()) {
            List<MenaObject> tmpList = new ArrayList<>();
            list.forEach(menaObject -> {
                Optional<MenaObject> tmpObj = menaElements.stream().filter(element -> element.getId().intValue() == menaObject.getId().intValue()).findFirst();
                if (tmpObj.isEmpty()) {
                    tmpList.add(menaObject);
                }
            });
            filteredList = tmpList;
        } else {
            filteredList = list;
        }
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
