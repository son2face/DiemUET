package Diem.Interface;

import Diem.Model.SubjectModel;

/**
 * Created by Son on 6/15/2017.
 */
public interface ISubjectService {
    SubjectModel update(int id, String name, String code, Integer tc);

    boolean delete(int mssv);

    SubjectModel get(int id);

    SubjectModel create(String name, String code, Integer tc);

    SubjectModel get(String code);
//    JSONObject get(SearchLegalRelationshipModel searchLegalRelationshipModel);
}