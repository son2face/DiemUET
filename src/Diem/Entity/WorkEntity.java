package Diem.Entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Son on 6/14/2017.
 */
@Entity
@Table(name = "work", schema = "diemthiuet", catalog = "")
public class WorkEntity {
    private int id;
    private String work;
    private Timestamp time;

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
    @Column(name = "work", nullable = true, length = 1000)
    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    @Basic
    @Column(name = "time", nullable = true)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkEntity that = (WorkEntity) o;
        if (id != that.id) return false;
        if (work != null ? !work.equals(that.work) : that.work != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (work != null ? work.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}

