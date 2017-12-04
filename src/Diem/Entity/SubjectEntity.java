package Diem.Entity;

/**
 * Created by Son on 6/14/2017.
 */

import javax.persistence.*;

/**
 * Created by Son on 6/14/2017.
 */
@Entity
@Table(name = "subject", schema = "diemthiuet", catalog = "")
public class SubjectEntity {
    private int id;
    private String name;
    private String code;
    private Integer tc;
    private Float gk;
    private Float ck;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "code", nullable = true, length = 100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "gk", nullable = true)
    public Float getGk() {
        return gk;
    }

    public void setGk(Float gk) {
        this.gk = gk;
    }

    @Basic
    @Column(name = "ck", nullable = true)
    public Float getCk() {
        return ck;
    }

    public void setCk(Float ck) {
        this.ck = ck;
    }

    @Basic
    @Column(name = "tc", nullable = true)
    public Integer getTc() {
        return tc;
    }

    public void setTc(Integer tc) {
        this.tc = tc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectEntity that = (SubjectEntity) o;
        if (id != that.id) return false;
        if (ck != that.ck) return false;
        if (gk != that.gk) return false;
        if (tc != null ? !tc.equals(that.tc) : that.tc != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (gk != null ? gk.hashCode() : 0);
        result = 31 * result + (ck != null ? ck.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tc != null ? tc.hashCode() : 0);
        return result;
    }
}