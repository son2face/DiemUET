package Diem.Interface;

import Diem.Model.StudentModel;

import java.sql.Timestamp;

/**
 * Created by Son on 6/15/2017.
 */
public interface IStudentService {
    StudentModel update(int mssv, String name, Timestamp birthday, String clas);

    boolean delete(int mssv);

    StudentModel get(int mssv);

    StudentModel create(int mssv, String name, Timestamp birthday, String clas);

//    JSONObject get(SearchLegalRelationshipModel searchLegalRelationshipModel);
}