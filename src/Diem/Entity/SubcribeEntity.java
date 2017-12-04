package Diem.Entity;

import javax.persistence.*;

/**
 * Created by Son on 6/14/2017.
 */
@Entity
@Table(name = "subcribe", schema = "diemthiuet", catalog = "")
public class SubcribeEntity {
    private String endpoint;
    private String key;
    private String auth;

    @Id
    @Column(name = "endpoint", nullable = false, length = 300)
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Basic
    @Column(name = "keyy", nullable = true, length = 200)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Basic
    @Column(name = "auth", nullable = true, length = 200)
    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubcribeEntity that = (SubcribeEntity) o;
        if (endpoint.equals(that.endpoint)) return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (auth != null ? !auth.equals(that.auth) : that.auth != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = endpoint.hashCode();
        result = 31 * result + (auth != null ? auth.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }
}
