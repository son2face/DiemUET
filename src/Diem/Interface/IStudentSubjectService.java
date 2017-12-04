package Diem.Interface;

import Diem.Model.StudentSubjectModel;

import java.util.List;

public interface IStudentSubjectService {
    StudentSubjectModel create(Integer idStudent, String codeSubject);

    StudentSubjectModel get(int id);

    List<StudentSubjectModel> get(Integer idStudent);

    StudentSubjectModel get(Integer idStudent, String codeSubject);

    boolean delete(int id);

    StudentSubjectModel update(int id, Integer idStudent, String codeSubject);

//    JSONObject get(SearchLegalRelationshipModel searchLegalRelationshipModel);
}