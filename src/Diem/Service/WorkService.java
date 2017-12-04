package Diem.Service;
import Diem.Entity.WorkEntity;
import Diem.Interface.IWorkService;
import Diem.Model.WorkModel;
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
public class WorkService implements IWorkService {
    private static SessionFactory factory;
    private static int currentActive;

    public WorkService(SessionFactory factory) {
        this.factory = factory;
    }

    public WorkService() {
        if (factory == null || currentActive != DatabaseEntity.Active) {
            IDatabaseService databaseService = new DatabaseService();
            IDatabaseControllService databaseControllService = new DatabaseControllService();
            factory = databaseControllService.createConfiguration(databaseService.get(DatabaseEntity.Active)).buildSessionFactory();
            currentActive = DatabaseEntity.Active;
        }
    }

    public static void setFactory(SessionFactory factory) {
        WorkService.factory = factory;
    }

    public WorkModel create(String work, Timestamp time) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            WorkModel workModel = new WorkModel(0, work, time);
            int id = Integer.valueOf(String.valueOf(session.save(workModel.toEntity())));
            tx.commit();
            WorkModel result = get(id);
            return result;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public WorkModel get(int id) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<WorkEntity> criteria = builder.createQuery(WorkEntity.class);
        Root<WorkEntity> WorkEntities = criteria.from(WorkEntity.class);
        criteria.where(builder.equal(WorkEntities.get("id"), id));
        try {
            WorkEntity WorkEntity = session.createQuery(criteria).getSingleResult();
            return new WorkModel(WorkEntity);
        } catch (NoResultException e) {
            return null;
        }
    }

    public WorkModel get(String work) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<WorkEntity> criteria = builder.createQuery(WorkEntity.class);
        Root<WorkEntity> WorkEntities = criteria.from(WorkEntity.class);
        criteria.where(builder.equal(WorkEntities.get("work"), work));
        try {
            WorkEntity WorkEntity = session.createQuery(criteria).getSingleResult();
            return new WorkModel(WorkEntity);
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
            WorkEntity workEntity = new WorkEntity();
            workEntity.setId(id);
            session.delete(workEntity);
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

    public WorkModel update(int id, String work, Timestamp time) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            WorkModel workModel = new WorkModel(id, work, time);
            session.update(workModel.toEntity());
            tx.commit();
            WorkModel result = get(id);
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
