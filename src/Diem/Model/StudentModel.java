package Diem.Model;

import Diem.Entity.StudentEntity;

import java.sql.Timestamp;

/**
 * Created by Son on 6/14/2017.
 */
public class StudentModel {
    public String name;
    public Timestamp birthday;
    public String clas;
    public int mssv;

    public StudentModel(int mssv, String name, Timestamp birthday, String clas) {
        this.name = name;
        this.birthday = birthday;
        this.clas = clas;
        this.mssv = mssv;
    }

    public StudentModel(StudentEntity studentEntity) {
        this.name = studentEntity.getName();
        this.birthday = studentEntity.getBirthday();
        this.clas = studentEntity.getClas();
        this.mssv = studentEntity.getMssv();
    }

    public StudentEntity toEntity() {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setMssv(mssv);
        studentEntity.setBirthday(birthday);
        studentEntity.setName(name);
        studentEntity.setClas(clas);
        return studentEntity;
    }
}
