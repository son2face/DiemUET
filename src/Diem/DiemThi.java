package Diem;

import Diem.Interface.*;
import Diem.Model.*;
import Diem.Service.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiemThi extends HttpServlet {
    public static String pathResult = "/webapps/grade/";
    private static HashMap<String, ReaderPDF> data = new HashMap<String, ReaderPDF>();
    private static int dem = 0;

    public static void makeData(File files) {
        File[] files1 = files.listFiles();
        if (files1 == null) return;
        for (final File fileEntry : files1) {
            if (fileEntry.isDirectory()) {
                makeData(fileEntry);
            } else {
                String[] name = fileEntry.getName().split("\\.");
                String fullUrl = Tools.FullPath + pathResult + name[0] + ".txt";
                File f = new File(fullUrl);
                if (f.exists()) {
                    data.put(fileEntry.getName(), ReaderPDF.readFromFile(fullUrl));
                } else {
                    ReaderPDF x = new ReaderPDF(fileEntry);
                    x.writeToFile(fullUrl);
                    data.put(fileEntry.getName(), x);
                }
            }
        }
    }

    public static JSONArray score(String mssv) throws IOException {
        IStudentService studentService = new StudentService();
        IWorkService workService = new WorkService();
        ISubjectService subjectService = new SubjectService();
        IResultService resultService = new ResultService();
        IStudentSubjectService studentSubjectService = new StudentSubjectService();
        dem++;
        try {
            FileWriter log = new FileWriter("log.txt", true);
            log.write(Integer.toString(dem) + " ");
            log.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DiemThi.class.getName()).log(Level.SEVERE, null, ex);
        }
        WorkModel workModel = workService.get("update");
        if (workModel != null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if ((now.getTime() - workModel.time.getTime()) > 3600000) {
                if (GetPDF.getPDF(data)) {
                    workService.update(workModel.id, workModel.work, now);
                }
            }
        } else {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (GetPDF.getPDF(data)) {
                workService.create("update", now);
            }
        }
        System.out.println(dem);
        StudentModel studentModel = studentService.get(Integer.valueOf(mssv));
        boolean check = true;
        if (studentModel == null) {
            StudentInfo student = new StudentInfo(mssv);
            if (student.getHoten() == null || student.getHoten().equals("")) {
                check = false;
            }
        }
        JSONArray result = new JSONArray();
        if (check) {
            boolean ch = false;
            List<StudentSubjectModel> studentSubjectModels = studentSubjectService.get(Integer.valueOf(mssv));
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (StudentSubjectModel studentSubjectModel : studentSubjectModels) {
                String compare = studentSubjectModel.codeSubject;
                compare = compare.toLowerCase();
                compare = compare.replace(' ', '-');
                JSONObject jsonObject = new JSONObject();
                SubjectModel subjectModel = subjectService.get(studentSubjectModel.codeSubject);
                jsonObject.put("ten", subjectModel.name);
                jsonObject.put("mamonhoc", subjectModel.code);
                jsonObject.put("tinchi", subjectModel.tc);
                ResultModel resultModel = resultService.get(compare);
                if (resultModel != null) {
                    String link = resultModel.filename;
                    ReaderPDF res = data.get(link);
//                        System.out.println(link);
                    if (res != null) {
                        link = link.replace("&", "&#38");
                        Student ress = res.findStudent(mssv);
                        double hs4 = 0;
                        if (ress == null) {
                            ress = new Student(mssv, 0, 0, 0);
                        } else {
                            if (ress.p[2] < 4) {
                                hs4 = 0;
                            } else if (ress.p[2] < 5) {
                                hs4 = 1;
                            } else if (ress.p[2] < 5.5) {
                                hs4 = 1.5;
                            } else if (ress.p[2] < 6.5) {
                                hs4 = 2;
                            } else if (ress.p[2] < 7) {
                                hs4 = 2.5;
                            } else if (ress.p[2] < 8) {
                                hs4 = 3;
                            } else if (ress.p[2] < 8.5) {
                                hs4 = 3.5;
                            } else if (ress.p[2] < 9) {
                                hs4 = 3.7;
                            } else {
                                hs4 = 4;
                            }
                        }
                        jsonObject.put("gk", ress.p[0]);
                        jsonObject.put("ck", ress.p[1]);
                        jsonObject.put("hs4", hs4);
                        jsonObject.put("tk", ress.p[2]);
                        jsonObject.put("link", "result/" + link);
                        if (!ch) {
                            ch = true;
                        }
                    }
                    result.add(jsonObject);
                } else {
                    jsonObjectList.add(jsonObject);
                }
            }
            for (JSONObject jsonObject : jsonObjectList) {
                result.add(jsonObject);
            }
        }
        return result;
    }

    public JSONObject report(String mssv, String url) {
        try {
            FileWriter log = new FileWriter("error.txt", true);
            log.write(mssv + " " + url + "\n");
            log.close();
        } catch (IOException ex) {
            Logger.getLogger(DiemThi.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", "success");
        return jsonObject;
    }
}
