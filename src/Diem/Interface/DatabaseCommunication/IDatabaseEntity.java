package Diem.Interface.DatabaseCommunication;

import java.util.List;

/**
 * Created by Son on 4/10/2017.
 */
public interface IDatabaseEntity {
    void insert() throws Exception;

    void update() throws Exception;

    void delete() throws Exception;

    List<Object> select() throws Exception;

    void insert(List<Object> data) throws Exception;

    void create() throws Exception;

    void truncate() throws Exception;

    void drop() throws Exception;

    List<Object> select(int id) throws Exception;
}
