package test;

import Diem.DiemThi;
import Diem.GetPDF;
import Diem.Interface.IResultService;
import Diem.Interface.IWorkService;
import Diem.Model.ResultModel;
import Diem.ReaderPDF;
import Diem.Service.ResultService;
import Diem.Service.WorkService;
import manager.Entity.DatabaseEntity;
import manager.Model.DatabaseModel;
import org.apache.tomcat.jni.Time;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 * Created by Son on 4/10/2017.
 */

public class TestDB {
    private static HashMap<String, ReaderPDF> data = new HashMap<String, ReaderPDF>();
    public static void main(String[] args) {
        DatabaseModel databaseModel = new DatabaseModel(0, 0, "localhost", "test", "root", "root");
//        DatabaseEntity.setFileDir("F:/apache-tomcat-9.0.0.M19/bin/database.txt");
//        DatabaseEntity.loadData();
//        DatabaseControllService databaseControllService = new DatabaseControllService(); // khởi tạo service làm việc
//        LegalInfoService.setFactory(databaseControllService.createConfiguration(databaseModel).buildSessionFactory());
//        testGet();
//        GetPDF.getPDF(data);
//        testGetId();
//        testCreateData();
//        testEditData();
//        testDeleteData();
    }

    public static void testGet(){
        try {
            System.out.println(DiemThi.score("15021317").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testGetResult() {
        IResultService resultService = new ResultService();
        ResultModel x = resultService.get("int3403-2");
        System.out.println(x.filename);
    }

    public static void testCreateWork() {
        IWorkService workService = new WorkService();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        workService.create("update", now);
    }
}
