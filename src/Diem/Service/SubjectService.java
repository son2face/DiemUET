package Diem.Service;

import Diem.Entity.SubjectEntity;
import Diem.Interface.ISubjectService;
import Diem.Model.SubjectModel;
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
public class SubjectService implements ISubjectService {
    private static SessionFactory factory;
    private static int currentActive;

    public SubjectService(SessionFactory factory) {
        this.factory = factory;
    }

    public SubjectService() {
        if (factory == null || currentActive != DatabaseEntity.Active) {
            IDatabaseService databaseService = new DatabaseService();
            IDatabaseControllService databaseControllService = new DatabaseControllService();
            factory = databaseControllService.createConfiguration(databaseService.get(DatabaseEntity.Active)).buildSessionFactory();
            currentActive = DatabaseEntity.Active;
        }
    }

    public static void setFactory(SessionFactory factory) {
        SubjectService.factory = factory;
    }

    public SubjectModel create(String name, String code, Integer tc) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            SubjectModel subjectModel = new SubjectModel(0, name, code, tc);
            int id = Integer.valueOf(String.valueOf(session.save(subjectModel.toEntity())));
            tx.commit();
            SubjectModel result = get(id);
            return result;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public SubjectModel get(int id) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<SubjectEntity> criteria = builder.createQuery(SubjectEntity.class);
        Root<SubjectEntity> SubjectEntities = criteria.from(SubjectEntity.class);
        criteria.where(builder.equal(SubjectEntities.get("id"), id));
        try {
            SubjectEntity SubjectEntity = session.createQuery(criteria).getSingleResult();
            return new SubjectModel(SubjectEntity);
        } catch (NoResultException e) {
            return null;
        }
    }

    public SubjectModel get(String code) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<SubjectEntity> criteria = builder.createQuery(SubjectEntity.class);
        Root<SubjectEntity> SubjectEntities = criteria.from(SubjectEntity.class);
        criteria.where(builder.equal(SubjectEntities.get("code"), code));
        try {
            SubjectEntity SubjectEntity = session.createQuery(criteria).getSingleResult();
            return new SubjectModel(SubjectEntity);
        } catch (NoResultException e) {
            return null;
        }
    }
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

    public boolean delete(int id) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            SubjectEntity subjectEntity = new SubjectEntity();
            subjectEntity.setId(id);
            session.delete(subjectEntity);
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

    public SubjectModel update(int id, String name, String code, Integer tc) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            SubjectModel subjectModel = new SubjectModel(id, name, code, tc);
            session.update(subjectModel.toEntity());
            tx.commit();
            SubjectModel result = get(id);
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
