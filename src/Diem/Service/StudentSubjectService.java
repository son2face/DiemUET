package Diem.Service;

import Diem.Entity.StudentSubjectEntity;
import Diem.Interface.IStudentSubjectService;
import Diem.Model.StudentSubjectModel;
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
public class StudentSubjectService implements IStudentSubjectService {
    private static SessionFactory factory;
    private static int currentActive;

    public StudentSubjectService(SessionFactory factory) {
        this.factory = factory;
    }

    public StudentSubjectService() {
        if (factory == null || currentActive != DatabaseEntity.Active) {
            IDatabaseService databaseService = new DatabaseService();
            IDatabaseControllService databaseControllService = new DatabaseControllService();
            factory = databaseControllService.createConfiguration(databaseService.get(DatabaseEntity.Active)).buildSessionFactory();
            currentActive = DatabaseEntity.Active;
        }
    }

    public static void setFactory(SessionFactory factory) {
        StudentSubjectService.factory = factory;
    }

    public StudentSubjectModel create(Integer idStudent, String codeSubject) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            StudentSubjectModel studentSubjectModel = new StudentSubjectModel(0, idStudent, codeSubject);
            int id = Integer.valueOf(String.valueOf(session.save(studentSubjectModel.toEntity())));
            tx.commit();
            StudentSubjectModel result = get(id);
            return result;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public StudentSubjectModel get(int id) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<StudentSubjectEntity> criteria = builder.createQuery(StudentSubjectEntity.class);
        Root<StudentSubjectEntity> StudentSubjectEntities = criteria.from(StudentSubjectEntity.class);
        criteria.where(builder.equal(StudentSubjectEntities.get("id"), id));
        try {
            StudentSubjectEntity StudentSubjectEntity = session.createQuery(criteria).getSingleResult();
            return new StudentSubjectModel(StudentSubjectEntity);
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<StudentSubjectModel> get(Integer idStudent) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<StudentSubjectEntity> criteria = builder.createQuery(StudentSubjectEntity.class);
        Root<StudentSubjectEntity> StudentSubjectEntities = criteria.from(StudentSubjectEntity.class);
        criteria.where(builder.equal(StudentSubjectEntities.get("idStudent"), idStudent));
        List<StudentSubjectEntity> studentSubjectEntities = session.createQuery(criteria).getResultList();
        if (!studentSubjectEntities.isEmpty()) {
            List<StudentSubjectModel> studentSubjectModels = new ArrayList<StudentSubjectModel>();
            for (StudentSubjectEntity studentSubjectEntity : studentSubjectEntities) {
                studentSubjectModels.add(new StudentSubjectModel(studentSubjectEntity));
            }
            return studentSubjectModels;
        }
        return null;
    }

    public StudentSubjectModel get(Integer idStudent, String codeSubject) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<StudentSubjectEntity> criteria = builder.createQuery(StudentSubjectEntity.class);
        Root<StudentSubjectEntity> StudentSubjectEntities = criteria.from(StudentSubjectEntity.class);
        criteria.where(
                builder.equal(StudentSubjectEntities.get("idStudent"), idStudent),
                builder.equal(StudentSubjectEntities.get("codeSubject"), codeSubject)
        );
        try {
            StudentSubjectEntity StudentSubjectEntity = session.createQuery(criteria).getSingleResult();
            return new StudentSubjectModel(StudentSubjectEntity);
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
            StudentSubjectEntity studentSubjectEntity = new StudentSubjectEntity();
            studentSubjectEntity.setId(id);
            session.delete(studentSubjectEntity);
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

    public StudentSubjectModel update(int id, Integer idStudent, String codeSubject) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            StudentSubjectModel studentSubjectModel = new StudentSubjectModel(id, idStudent, codeSubject);
            session.update(studentSubjectModel.toEntity());
            tx.commit();
            StudentSubjectModel result = get(id);
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
