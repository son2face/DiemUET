package Diem.Model;

import Diem.Entity.WorkEntity;

import java.sql.Timestamp;

/**
 * Created by Son on 6/14/2017.
 */
public class WorkModel {
    public int id;
    public String work;
    public Timestamp time;

    public WorkModel(int id, String work, Timestamp time) {
        this.id = id;
        this.work = work;
        this.time = time;
    }

    public WorkModel(WorkEntity workEntity) {
        this.id = workEntity.getId();
        this.work = workEntity.getWork();
        this.time = workEntity.getTime();
    }

    public WorkEntity toEntity() {
        WorkEntity workEntity = new WorkEntity();
        workEntity.setId(id);
        workEntity.setTime(time);
        workEntity.setWork(work);
        return workEntity;
    }
}
