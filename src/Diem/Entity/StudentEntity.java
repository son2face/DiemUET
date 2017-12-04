package Diem.Entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Son on 6/14/2017.
 */
@Entity
@Table(name = "student", schema = "diemthiuet", catalog = "")
public class StudentEntity {
    private int mssv;
    private String name;
    private Timestamp birthday;
    private String clas;

    @Id
    @Column(name = "mssv", nullable = false)
    public int getMssv() {
        return mssv;
    }

    public void setMssv(int mssv) {
        this.mssv = mssv;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 1000)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "birthday", nullable = true)
    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "clas", nullable = true, length = 1000)
    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentEntity that = (StudentEntity) o;

        if (mssv != that.mssv) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
        if (clas != null ? !clas.equals(that.clas) : that.clas != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mssv;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (clas != null ? clas.hashCode() : 0);
        return result;
    }
}

