package Diem.Model;

import Diem.Entity.ResultEntity;

/**
 * Created by Son on 6/14/2017.
 */
public class ResultModel {
    public int id;
    public String filename;

    public ResultModel(int id, String filename) {
        this.id = id;
        this.filename = filename;
    }

    public ResultModel(ResultEntity resultEntity) {
        this.id = resultEntity.getId();
        this.filename = resultEntity.getFilename();
    }

    public ResultEntity toEntity() {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setId(id);
        resultEntity.setFilename(filename);
        return resultEntity;
    }
}
