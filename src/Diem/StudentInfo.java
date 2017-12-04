package Diem;

import Diem.Interface.IStudentService;
import Diem.Interface.IStudentSubjectService;
import Diem.Interface.ISubjectService;
import Diem.Service.StudentService;
import Diem.Service.StudentSubjectService;
import Diem.Service.SubjectService;
import Diem.Service.Tools;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentInfo {

    static {
        Tools.disableSslVerification();
    }

    private String mssv;
    private String hoten;
    private String ngaysinh;
    private String lopkhoahoc;
    private String[] mamonhoc = new String[20];
    private String[] tenmonhoc = new String[20];
    private String[] tc = new String[20];
    private int count = 0;

    StudentInfo(String mssv) {
        try {
//            String urlParameters = "SinhvienLmh%5DmasvTitle%5D=" + mssv + "&SinhvienLmh%5Dterm_id]=022&pageSize=100";
//            byte[] postData = urlParameters.getBytes("UTF-8");
//            int postDataLength = postData.length;
            String request = "https://112.137.129.87/congdaotao/module/qldt/?SinhvienLmh%5BmasvTitle%5D=" + mssv + "&SinhvienLmh%5Bterm_id%5D=022&pageSize=100";
            URL url = new URL(request);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) coc_coc_browser/58.3.138 Chrome/52.3.2743.138 Safari/537.36");
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
//            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
//            wr.write(postData);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            String key = "<td style=\"width: 20px\">";
            int nKey = key.length();
            String[] result = new String[100];
            int kq = 0;
            int length = 0;
            while ((inputLine = in.readLine()) != null) {
                for (int i = 0; i < inputLine.length(); i++) {
                    if (inputLine.charAt(i) == key.charAt(0)) {
                        int check = 1;
                        for (int j = 1; j < nKey; j++) {
                            if (inputLine.charAt(i + j) != key.charAt(j)) {
                                check = 0;
                                break;
                            }
                        }
                        if (check == 1) {
                            if (count == 0) {
                                i += nKey;
                                length += nKey;
                                while (inputLine.charAt(i) != '<') {
                                    i++;
                                    length++;
                                }
                                i += "</td><td style=\"width: 40px\">".length();
                                length += "</td><td style=\"width: 40px\">".length();
                                this.mssv = "";
                                while (inputLine.charAt(i) != '<') {
                                    this.mssv += inputLine.charAt(i);
                                    i++;
                                    length++;
                                }
                                i += "</td><td style=\"width: 100px\">".length();
                                length += "</td><td style=\"width: 100px\">".length();
                                this.hoten = "";
                                while (inputLine.charAt(i) != '<') {
                                    this.hoten += inputLine.charAt(i);
                                    i++;
                                    length++;
                                }
                                i += "</td><td style=\"width: 60px\">".length();
                                length += "</td><td style=\"width: 60px\">".length();
                                this.ngaysinh = "";
                                while (inputLine.charAt(i) != '<') {
                                    this.ngaysinh += inputLine.charAt(i);
                                    i++;
                                    length++;
                                }
                                String[] temp = this.ngaysinh.split("/");
                                this.ngaysinh = "";
                                this.ngaysinh += temp[2] + "-" + temp[1] + "-" + temp[0];
                                i += "</td><td style=\"width: 100px\">".length();
                                length += "</td><td style=\"width: 100px\">".length();
                                this.lopkhoahoc = "";
                                while (inputLine.charAt(i) != '<') {
                                    this.lopkhoahoc += inputLine.charAt(i);
                                    i++;
                                    length++;
                                }
                                i += "</td><td style=\"width: 50px\">".length();
                                length += "</td><td style=\"width: 50px\">".length();
                                this.mamonhoc[0] = "";
                                while (inputLine.charAt(i) != '<') {
                                    this.mamonhoc[0] += inputLine.charAt(i);
                                    i++;
                                }
                                i += "</td><td style=\"width: 160px\">".length();
                                this.tenmonhoc[0] = "";
                                while (inputLine.charAt(i) != '<') {
                                    this.tenmonhoc[0] += inputLine.charAt(i);
                                    i++;
                                }
                                i += "</td><td style=\"width: 15px\">".length();
                                while (inputLine.charAt(i) != '<') {
                                    i++;
                                }
                                i += "</td><td style=\"width: 15px\">".length();
                                this.tc[0] = "";
                                while (inputLine.charAt(i) != '<') {
                                    this.tc[0] += inputLine.charAt(i);
                                    i++;
                                }
                                count++;
                            } else {
                                i += length;
                                this.mamonhoc[count] = "";
                                while (inputLine.charAt(i) != '<') {
                                    this.mamonhoc[count] += inputLine.charAt(i);
                                    i++;
                                }
                                i += "</td><td style=\"width: 160px\">".length();
                                this.tenmonhoc[count] = "";
                                while (inputLine.charAt(i) != '<') {
                                    this.tenmonhoc[count] += inputLine.charAt(i);
                                    i++;
                                }
                                i += "</td><td style=\"width: 15px\">".length();
                                while (inputLine.charAt(i) != '<') {
                                    i++;
                                }
                                i += "</td><td style=\"width: 15px\">".length();
                                this.tc[count] = "";
                                while (inputLine.charAt(i) != '<') {
                                    this.tc[count] += inputLine.charAt(i);
                                    i++;
                                }
                                count++;
                            }
                        }
                    }
                }
            }
            if (count > 0) {
                IStudentService studentService = new StudentService();
                IStudentSubjectService studentSubjectService = new StudentSubjectService();
                ISubjectService subjectService = new SubjectService();
                studentService.create(Integer.valueOf(this.mssv), this.hoten, new Timestamp(Date.valueOf(this.ngaysinh).getTime()), this.lopkhoahoc);
                for (int i = 0; i < count; i++) {
                    if(subjectService.get(this.mamonhoc[i]) == null)
                    subjectService.create(this.tenmonhoc[i], this.mamonhoc[i], Integer.valueOf(this.tc[i]));
                    studentSubjectService.create(Integer.valueOf(this.mssv), this.mamonhoc[i]);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(StudentInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        StudentInfo studentInfo = new StudentInfo("15022886");
        System.out.println(studentInfo.toString());
    }

    String getHoten() {
        return hoten;
    }

    private String fixMaMonHoc(String input) {
        StringBuilder result = new StringBuilder();
        boolean check = false;
        for (int i = 0; i < input.length(); i++) {
            if (isNumber(input.charAt(i))) {
                check = true;
            }
            if (input.charAt(i) == ' ' && !check) {
                continue;
            }
            result.append(input.charAt(i));
        }
        return result.toString();
    }

    private boolean isNumber(char input) {
        return input >= '0' && input <= '9';
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("mssv: " + mssv + "\n"
                + "ho ten: " + hoten + "\n"
                + "ngaysinh: " + ngaysinh + "\n"
                + "lopkhoahoc: " + lopkhoahoc + "\n");
        for (int i = 0; i < count; i++) {
            result.append("ma mon hoc: ").append(mamonhoc[i]).append("\n");
            result.append("ten mon hoc: ").append(tenmonhoc[i]).append("\n");
            result.append("tin chi: ").append(tc[i]).append("\n");
        }
        return result.toString();
    }

}
