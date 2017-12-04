package Diem.Service;

import Diem.Entity.ResultEntity;
import Diem.Entity.StudentEntity;
import Diem.Interface.IResultService;
import Diem.Model.ResultModel;
import manager.Entity.DatabaseEntity;
import manager.Interface.IDatabaseControllService;
import manager.Interface.IDatabaseService;
import manager.Service.DatabaseControllService;
import manager.Service.DatabaseService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Son on 6/15/2017.
 */
public class ResultService implements IResultService {
    private static SessionFactory factory;
    private static int currentActive;

    public ResultService(SessionFactory factory) {
        this.factory = factory;
    }

    public ResultService() {
        if (factory == null || currentActive != DatabaseEntity.Active) {
            IDatabaseService databaseService = new DatabaseService();
            IDatabaseControllService databaseControllService = new DatabaseControllService();
            factory = databaseControllService.createConfiguration(databaseService.get(DatabaseEntity.Active)).buildSessionFactory();
            currentActive = DatabaseEntity.Active;
        }
    }

    public static void setFactory(SessionFactory factory) {
        ResultService.factory = factory;
    }

    public ResultModel create(String filename) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            ResultModel resultModel = new ResultModel(0, filename);
            int id = Integer.valueOf(String.valueOf(session.save(resultModel.toEntity())));
            tx.commit();
            ResultModel result = get(id);
            return result;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public ResultModel get(int Id) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ResultEntity> criteria = builder.createQuery(ResultEntity.class);
        Root<ResultEntity> ResultEntities = criteria.from(ResultEntity.class);
        criteria.where(builder.equal(ResultEntities.get("id"), Id));
        List<ResultEntity> resultEntities = session.createQuery(criteria).getResultList();
        if (!resultEntities.isEmpty()) {
            return new ResultModel(resultEntities.get(0));
        }
        return null;
    }

    public ResultModel get(String filename) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ResultEntity> criteria = builder.createQuery(ResultEntity.class);
        Root<ResultEntity> ResultEntities = criteria.from(ResultEntity.class);
        criteria.where(builder.like(ResultEntities.get("filename"), filename));
        List<ResultEntity> resultEntities = session.createQuery(criteria).getResultList();
        if (!resultEntities.isEmpty()) {
            return new ResultModel(resultEntities.get(0));
        }
        return null;
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
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setId(id);
            session.delete(resultEntity);
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

    public ResultModel update(int id, String filename) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            ResultModel resultModel = new ResultModel(id, filename);
            session.update(resultModel.toEntity());
            tx.commit();
            ResultModel result = get(id);
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
