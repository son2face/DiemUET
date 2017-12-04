package Diem.Service;

import Diem.Entity.StudentEntity;
import Diem.Interface.IStudentService;
import Diem.Model.StudentModel;
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
import java.sql.Timestamp;

/**
 * Created by Son on 6/15/2017.
 */
public class StudentService implements IStudentService {
    private static SessionFactory factory;
    private static int currentActive;

    public StudentService(SessionFactory factory) {
        this.factory = factory;
    }

    public StudentService() {
        if (factory == null || currentActive != DatabaseEntity.Active) {
            IDatabaseService databaseService = new DatabaseService();
            IDatabaseControllService databaseControllService = new DatabaseControllService();
            factory = databaseControllService.createConfiguration(databaseService.get(DatabaseEntity.Active)).buildSessionFactory();
            currentActive = DatabaseEntity.Active;
        }
    }

    public static void setFactory(SessionFactory factory) {
        StudentService.factory = factory;
    }

    public StudentModel create(int mssv, String name, Timestamp birthday, String clas) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            StudentModel studentModel = new StudentModel(mssv, name, birthday, clas);
            int id = Integer.valueOf(String.valueOf(session.save(studentModel.toEntity())));
            tx.commit();
            StudentModel result = get(id);
            return result;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public StudentModel get(int mssv) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> criteria = builder.createQuery(StudentEntity.class);
        Root<StudentEntity> StudentEntities = criteria.from(StudentEntity.class);
        criteria.where(builder.equal(StudentEntities.get("mssv"), mssv));
        try {
            StudentEntity studentEntity = session.createQuery(criteria).getSingleResult();
            return new StudentModel(studentEntity);
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

    public boolean delete(int mssv) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            StudentEntity studentEntity = new StudentEntity();
            studentEntity.setMssv(mssv);
            session.delete(studentEntity);
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

    public StudentModel update(int mssv, String name, Timestamp birthday, String clas) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            StudentModel studentModel = new StudentModel(mssv, name, birthday, clas);
            session.update(studentModel.toEntity());
            tx.commit();
            StudentModel result = get(mssv);
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
