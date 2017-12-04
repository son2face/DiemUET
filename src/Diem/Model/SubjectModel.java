package Diem.Model;

import Diem.Entity.SubjectEntity;

/**
 * Created by Son on 6/14/2017.
 */
public class SubjectModel {
    public int id;
    public String name;
    public String code;
    public Integer tc;
    public float gk;
    public float ck;

    public SubjectModel(int id, String name, String code, Integer tc, float gk, float ck) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.tc = tc;
        this.gk = gk;
        this.ck = ck;
    }
    public SubjectModel(int id, String name, String code, Integer tc) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.tc = tc;
    }
    public SubjectModel(SubjectEntity subjectEntity) {
        this.id = subjectEntity.getId();
        this.name = subjectEntity.getName();
        this.code = subjectEntity.getCode();
        this.tc = subjectEntity.getTc();
        this.tc = subjectEntity.getTc();
        this.tc = subjectEntity.getTc();
    }

    public SubjectEntity toEntity() {
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setId(id);
        subjectEntity.setCode(code);
        subjectEntity.setName(name);
        subjectEntity.setTc(tc);
        subjectEntity.setTc(tc);
        subjectEntity.setTc(tc);
        return subjectEntity;
    }
}
