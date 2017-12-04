package manager.Service;


import Diem.Entity.*;
import manager.Entity.DatabaseEntity;
import manager.Interface.IDatabaseControllService;
import manager.Model.DatabaseModel;
import org.hibernate.cfg.Configuration;

/**
 * Created by Son on 5/12/2017.
 */

public class DatabaseControllService implements IDatabaseControllService {
    public boolean setActive(int id) {
        if (id >= 0 && id < DatabaseEntity.getDatabaseModels().size()) {
            DatabaseEntity.setActive(id);
            return true;
        }
        return false;
    }

    public Configuration createConfiguration(DatabaseModel databaseModel) {
        Configuration cfg;
        switch (databaseModel.typeDB) {
            case 0:
                cfg = new Configuration()
                        .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                        .setProperty("hibernate.connection.url", "jdbc:mysql://" + databaseModel.url + "/" + databaseModel.databaseName)
                        .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                        .setProperty("hibernate.connection.username", databaseModel.userName)
                        .setProperty("hibernate.connection.password", databaseModel.passWord)
                        .addAnnotatedClass(ResultEntity.class)
                        .addAnnotatedClass(StudentEntity.class)
                        .addAnnotatedClass(StudentSubjectEntity.class)
                        .addAnnotatedClass(WorkEntity.class)
                        .addAnnotatedClass(SubcribeEntity.class)
                        .addAnnotatedClass(StudentSubcribeEntity.class)
                        .addAnnotatedClass(SubjectEntity.class);
                break;
            default:
                cfg = new Configuration()
                        .setProperty("hibernate.connection.driver_class", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
                        .setProperty("hibernate.connection.url", "jdbc:sqlserver:://" + databaseModel.url + "/" + databaseModel.databaseName)
                        .setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect")
                        .setProperty("hibernate.connection.username", databaseModel.userName)
                        .setProperty("hibernate.connection.password", databaseModel.passWord)
                        .addAnnotatedClass(ResultEntity.class)
                        .addAnnotatedClass(StudentEntity.class)
                        .addAnnotatedClass(StudentSubjectEntity.class)
                        .addAnnotatedClass(WorkEntity.class)
                        .addAnnotatedClass(StudentSubcribeEntity.class)
                        .addAnnotatedClass(SubcribeEntity.class)
                        .addAnnotatedClass(SubjectEntity.class);
                break;
        }
        return cfg;
    }
}
