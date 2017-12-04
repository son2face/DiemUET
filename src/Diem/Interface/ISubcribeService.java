package Diem.Interface;

import Diem.Model.SubcribeModel;

/**
 * Created by Son on 6/15/2017.
 */
public interface ISubcribeService {
    SubcribeModel update(String endpoint, String auth, String key);

    boolean delete(String endpoint);

    SubcribeModel get(String endpoint);

    SubcribeModel create(String endpoint, String auth, String key);

//    JSONObject get(SearchLegalRelationshipModel searchLegalRelationshipModel);
}