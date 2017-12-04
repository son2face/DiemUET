package Diem.Model;

import Diem.Entity.StudentSubcribeEntity;

/**
 * Created by Son on 6/14/2017.
 */
public class StudentSubcribeModel {
    public int id;
    public Integer idStudent;
    public String endpoint;

    public StudentSubcribeModel(int id, Integer idStudent, String endpoint) {
        this.id = id;
        this.idStudent = idStudent;
        this.endpoint = endpoint;
    }

    public StudentSubcribeModel(StudentSubcribeEntity studentSubcribeEntity) {
        this.id = studentSubcribeEntity.getId();
        this.idStudent = studentSubcribeEntity.getIdStudent();
        this.endpoint = studentSubcribeEntity.getEndpoint();
    }

    public StudentSubcribeEntity toEntity() {
        StudentSubcribeEntity studentSubcribeEntity = new StudentSubcribeEntity();
        studentSubcribeEntity.setId(id);
        studentSubcribeEntity.setIdStudent(idStudent);
        studentSubcribeEntity.setEndpoint(endpoint);
        return studentSubcribeEntity;
    }
}
