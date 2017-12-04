package Diem.Service;

import Diem.Entity.StudentSubcribeEntity;
import Diem.Interface.IStudentSubcribeService;
import Diem.Model.StudentSubcribeModel;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Son on 6/15/2017.
 */
public class StudentSubcribeService implements IStudentSubcribeService {
    private static SessionFactory factory;
    private static int currentActive;

    public StudentSubcribeService(SessionFactory factory) {
        this.factory = factory;
    }

    public StudentSubcribeService() {
        if (factory == null || currentActive != DatabaseEntity.Active) {
            IDatabaseService databaseService = new DatabaseService();
            IDatabaseControllService databaseControllService = new DatabaseControllService();
            factory = databaseControllService.createConfiguration(databaseService.get(DatabaseEntity.Active)).buildSessionFactory();
            currentActive = DatabaseEntity.Active;
        }
    }

    public static void setFactory(SessionFactory factory) {
        StudentSubcribeService.factory = factory;
    }

    public StudentSubcribeModel create(Integer idStudent, String endpoint) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            StudentSubcribeModel studentSubcribeModel = new StudentSubcribeModel(0, idStudent, endpoint);
            int id = Integer.valueOf(String.valueOf(session.save(studentSubcribeModel.toEntity())));
            tx.commit();
            StudentSubcribeModel result = get(id);
            return result;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public StudentSubcribeModel get(int id) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<StudentSubcribeEntity> criteria = builder.createQuery(StudentSubcribeEntity.class);
        Root<StudentSubcribeEntity> StudentSubcribeEntities = criteria.from(StudentSubcribeEntity.class);
        criteria.where(builder.equal(StudentSubcribeEntities.get("id"), id));
        try {
            StudentSubcribeEntity studentSubcribeEntities = session.createQuery(criteria).getSingleResult();
            return new StudentSubcribeModel(studentSubcribeEntities);
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<StudentSubcribeModel> get(Integer idStudent) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<StudentSubcribeEntity> criteria = builder.createQuery(StudentSubcribeEntity.class);
        Root<StudentSubcribeEntity> StudentSubcribeEntities = criteria.from(StudentSubcribeEntity.class);
        criteria.where(builder.equal(StudentSubcribeEntities.get("idStudent"), idStudent));
        List<StudentSubcribeEntity> studentSubcribeEntities = session.createQuery(criteria).getResultList();
        if (!studentSubcribeEntities.isEmpty()) {
            List<StudentSubcribeModel> studentSubcribeModels = new ArrayList<StudentSubcribeModel>();
            for (StudentSubcribeEntity studentSubcribeEntity : studentSubcribeEntities) {
                studentSubcribeModels.add(new StudentSubcribeModel(studentSubcribeEntity));
            }
            return studentSubcribeModels;
        }
        return null;
    }

    public StudentSubcribeModel get(Integer idStudent, String endpoint) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<StudentSubcribeEntity> criteria = builder.createQuery(StudentSubcribeEntity.class);
        Root<StudentSubcribeEntity> StudentSubcribeEntities = criteria.from(StudentSubcribeEntity.class);
        criteria.where(
                builder.equal(StudentSubcribeEntities.get("idStudent"), idStudent),
                builder.equal(StudentSubcribeEntities.get("endpoint"), endpoint)
        );
        try {
            StudentSubcribeEntity studentSubcribeEntities = session.createQuery(criteria).getSingleResult();
            return new StudentSubcribeModel(studentSubcribeEntities);
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
            StudentSubcribeEntity studentSubcribeEntity = new StudentSubcribeEntity();
            studentSubcribeEntity.setId(id);
            session.delete(studentSubcribeEntity);
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

    public StudentSubcribeModel update(int id, Integer idStudent, String endpoint) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            StudentSubcribeModel studentSubcribeModel = new StudentSubcribeModel(id, idStudent, endpoint);
            session.update(studentSubcribeModel.toEntity());
            tx.commit();
            StudentSubcribeModel result = get(id);
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
