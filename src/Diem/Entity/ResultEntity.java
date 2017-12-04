package Diem.Entity;

import javax.persistence.*;

/**
 * Created by Son on 6/14/2017.
 */
@Entity
@Table(name = "result", schema = "diemthiuet", catalog = "")
public class ResultEntity {
    private int id;
    private String filename;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "filename", nullable = true, length = 1000)
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResultEntity that = (ResultEntity) o;

        if (id != that.id) return false;
        if (filename != null ? !filename.equals(that.filename) : that.filename != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        return result;
    }
}

