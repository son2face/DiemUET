package Diem.Interface;

import Diem.Model.WorkModel;

import java.sql.Timestamp;

public interface IWorkService {
    WorkModel create(String work, Timestamp time);

    WorkModel get(int id);

    WorkModel get(String work);

    boolean delete(int id);

    WorkModel update(int id, String work, Timestamp time);

//    JSONObject get(SearchLegalRelationshipModel searchLegalRelationshipModel);
}