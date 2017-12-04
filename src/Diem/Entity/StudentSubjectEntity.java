package Diem.Entity;

import javax.persistence.*;

/**
 * Created by Son on 6/14/2017.
 */
@Entity
@Table(name = "student_subject", schema = "diemthiuet", catalog = "")
public class StudentSubjectEntity {
    private int id;
    private Integer idStudent;
    private String codeSubject;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "idStudent", nullable = true)
    public Integer getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Integer idStudent) {
        this.idStudent = idStudent;
    }

    @Basic
    @Column(name = "codeSubject", nullable = true, length = 100)
    public String getCodeSubject() {
        return codeSubject;
    }

    public void setCodeSubject(String codeSubject) {
        this.codeSubject = codeSubject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentSubjectEntity that = (StudentSubjectEntity) o;
        if (id != that.id) return false;
        if (codeSubject != null ? !codeSubject.equals(that.codeSubject) : that.codeSubject != null) return false;
        if (idStudent != null ? !idStudent.equals(that.idStudent) : that.idStudent != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (codeSubject != null ? codeSubject.hashCode() : 0);
        result = 31 * result + (idStudent != null ? idStudent.hashCode() : 0);
        return result;
    }
}
