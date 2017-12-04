package Diem.Interface;

import Diem.Model.ResultModel;

public interface IResultService {
    ResultModel create(String filename);

    ResultModel get(int id);

    boolean delete(int id);

    ResultModel update(int id, String filename);

    ResultModel get(String filename);
//    JSONObject get(SearchLegalRelationshipModel searchLegalRelationshipModel);
}