package Diem.Interface;

import Diem.Model.StudentSubcribeModel;

import java.util.List;

/**
 * Created by Son on 6/15/2017.
 */
public interface IStudentSubcribeService {
    StudentSubcribeModel create(Integer idStudent, String endpoint);

    StudentSubcribeModel get(int id);

    List<StudentSubcribeModel> get(Integer idStudent);

    StudentSubcribeModel get(Integer idStudent, String endpoint);

    boolean delete(int id);

    StudentSubcribeModel update(int id, Integer idStudent, String endpoint);
//    JSONObject get(SearchLegalRelationshipModel searchLegalRelationshipModel);
}