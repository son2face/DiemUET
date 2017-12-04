package Diem.Service;

import Diem.Entity.SubcribeEntity;
import Diem.Interface.ISubcribeService;
import Diem.Model.SubcribeModel;
import manager.Entity.DatabaseEntity;
import manager.Interface.IDatabaseControllService;
import manager.Interface.IDatabaseService;
import manager.Service.DatabaseControllService;
import manager.Service.DatabaseService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by Son on 6/15/2017.
 */
public class SubcribeService implements ISubcribeService {
    private static SessionFactory factory;
    private static int currentActive;

    public SubcribeService(SessionFactory factory) {
        this.factory = factory;
    }

    public SubcribeService() {
        if (factory == null || currentActive != DatabaseEntity.Active) {
            IDatabaseService databaseService = new DatabaseService();
            IDatabaseControllService databaseControllService = new DatabaseControllService();
            factory = databaseControllService.createConfiguration(databaseService.get(DatabaseEntity.Active)).buildSessionFactory();
            currentActive = DatabaseEntity.Active;
        }
    }

    public static void setFactory(SessionFactory factory) {
        SubcribeService.factory = factory;
    }

    public SubcribeModel create(String endpoint, String auth, String key) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            SubcribeModel subcribeModel = new SubcribeModel(endpoint, auth, key);
            String endpoints = String.valueOf(session.save(subcribeModel.toEntity()));
            tx.commit();
            SubcribeModel result = get(endpoints);
            return result;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public SubcribeModel get(String endpoint) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<SubcribeEntity> criteria = builder.createQuery(SubcribeEntity.class);
        Root<SubcribeEntity> SubcribeEntities = criteria.from(SubcribeEntity.class);
        criteria.where(builder.equal(SubcribeEntities.get("endpoint"), endpoint));
        try {
            SubcribeEntity SubcribeEntity = session.createQuery(criteria).getSingleResult();
            return new SubcribeModel(SubcribeEntity);
        } catch (NoResultException e) {
            return null;
        }
    }

//    public SubjectModel get(String code) {
//        Session session = factory.openSession();
//        Criteria criteria = session.createCriteria(SubjectEntity.class, "subject");
//        criteria.add(Expression.eq("code", code));
//        List<SubjectEntity> subjectEntities = criteria.list();
//        if (!subjectEntities.isEmpty()) {
//            return new SubjectModel(subjectEntities.get(0));
//        }
//        return null;
//    }
//    public JSONObject get(SearchLegalRelationshipModel searchLegalRelationshipModel) {
//        Session session = factory.openSession();
//        Criteria criteria = session.createCriteria(SearchLegalRelationshipModel.class, "legalrelationship");
//        criteria = searchLegalRelationshipModel.apply(criteria);
//        List<LegalrelationshipEntity> legalrelationshipEntities = criteria.list();
//        List<LegalRelationshipModel> legalRelationshipModels = new ArrayList<>();
//        legalrelationshipEntities.forEach(x -> {
//            legalRelationshipModels.add(new LegalRelationshipModel(x));
//        });
//        JSONObject obj = new JSONObject();
//        int statusCode = 200;
//        JSONArray data = new JSONArray();
//        for (LegalRelationshipModel x : legalRelationshipModels) {
//            data.add(x.toJsonObject());
//        }
//        obj.put("status", statusCode);
//        obj.put("data", data);
//        return obj;
//    }

    public boolean delete(String endpoint) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            SubcribeEntity subcribeEntity = new SubcribeEntity();
            subcribeEntity.setEndpoint(endpoint);
            session.delete(subcribeEntity);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public SubcribeModel update(String endpoint, String auth, String key) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            SubcribeModel subcribeModel = new SubcribeModel(endpoint, auth, key);
            session.update(subcribeModel.toEntity());
            tx.commit();
            SubcribeModel result = get(endpoint);
            return result;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}
