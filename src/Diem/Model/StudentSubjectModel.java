package Diem.Model;

import Diem.Entity.StudentSubjectEntity;

/**
 * Created by Son on 6/14/2017.
 */
public class StudentSubjectModel {
    public int id;
    public Integer idStudent;
    public String codeSubject;

    public StudentSubjectModel(int id, Integer idStudent, String codeSubject) {
        this.id = id;
        this.idStudent = idStudent;
        this.codeSubject = codeSubject;
    }

    public StudentSubjectModel(StudentSubjectEntity studentSubjectEntity) {
        this.id = studentSubjectEntity.getId();
        this.idStudent = studentSubjectEntity.getIdStudent();
        this.codeSubject = studentSubjectEntity.getCodeSubject();
    }

    public StudentSubjectEntity toEntity() {
        StudentSubjectEntity studentSubjectEntity = new StudentSubjectEntity();
        studentSubjectEntity.setId(id);
        studentSubjectEntity.setIdStudent(idStudent);
        studentSubjectEntity.setCodeSubject(codeSubject);
        return studentSubjectEntity;
    }
}
