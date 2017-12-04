package Diem.Entity;

import javax.persistence.*;

/**
 * Created by Son on 6/14/2017.
 */
@Entity
@Table(name = "student_subcribe", schema = "diemthiuet", catalog = "")
public class StudentSubcribeEntity {
    private int id;
    private Integer idStudent;
    private String endpoint;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "endpoint", nullable = true, length = 300)
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentSubcribeEntity that = (StudentSubcribeEntity) o;
        if (id != that.id) return false;
        if (endpoint != null ? !endpoint.equals(that.endpoint) : that.endpoint != null) return false;
        if (idStudent != null ? !idStudent.equals(that.idStudent) : that.idStudent != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (endpoint != null ? endpoint.hashCode() : 0);
        result = 31 * result + (idStudent != null ? idStudent.hashCode() : 0);
        return result;
    }
}
