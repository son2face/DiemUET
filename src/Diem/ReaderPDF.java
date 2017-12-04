package Diem;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Diem.Model.DiemModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author admin
 */
public class ReaderPDF implements Serializable {

    String url;
    String input;
    int current = 0;
    int length;
    DiemModel diem = new DiemModel();
    Student[] student = new Student[120];
    int numberStudent = 0;

    public ReaderPDF(File files) {
        try {
            PDDocument document = null;
            document = PDDocument.load(files);
            document.getClass();
            url = files.getPath();
            if (!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper Tstripper = new PDFTextStripper();
                input = Tstripper.getText(document);
                input = input.replace(",", ".");
                length = input.length();
//                System.out.println("-----" + input);
                findHeSo();
                findDataStudent();
                print();
                fixStringPoint();
                fixHeSo();
                System.out.println(diem.giuaki);
                System.out.println(diem.cuoiki);
                for (int i = 0; i < numberStudent; i++) {
                    fixDiem(student[i]);
                }
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ReaderPDF readFromFile(File file) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            ReaderPDF result = (ReaderPDF) objectInputStream.readObject();
            objectInputStream.close();
            return result;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReaderPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReaderPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReaderPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ReaderPDF readFromFile(String filename) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename));
            ReaderPDF result = (ReaderPDF) objectInputStream.readObject();
            objectInputStream.close();
            return result;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReaderPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReaderPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReaderPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void printRawInput() {
        System.out.println(input);
    }

    public void print() {
        String check = "(";
        check += url + ")";
        check += diem.giuaki + " " + diem.cuoiki + "\n";
        System.out.println(check);
        for (int i = 0; i < numberStudent; i++) {
            System.out.println(student[i]);
        }
    }

    public boolean compare(String a, String b) {
        if (a.charAt(2) == '.' && b.charAt(2) == '.') {
            int count = 0;
            if (a.charAt(0) == b.charAt(0)) {
                count++;
            }
            if (a.charAt(1) == b.charAt(1)) {
                count++;
            }
            if (a.charAt(3) == b.charAt(3)) {
                count++;
            }
            return count > 2;
        }
        return (a.charAt(0) == b.charAt(0) || a.charAt(2) == b.charAt(2)) || (a.charAt(0) == b.charAt(0) || a.charAt(2) == b.charAt(2));
    }

    public void fixDiem(Student student) {
        if (isTruePoints(student.r[0]) && isTruePoints(student.r[1]) && isTruePoints(student.r[2])) {
            student.p[1] = Double.parseDouble(student.r[1]);
            student.p[0] = Double.parseDouble(student.r[0]);
            student.p[2] = Double.parseDouble(student.r[2]);
            if (student.p[0] * diem.giuaki + student.p[1] * diem.cuoiki != student.p[2]) {
                if ((student.p[0] - (int) student.p[0]) != 0 || (student.p[0] - (int) student.p[0]) != 0.5) {
                    double temp1 = (double) Math.round((student.p[2] - student.p[1] * diem.cuoiki) / diem.giuaki * 10) / 10;
                    String t1 = Double.toString(temp1);
                    if (compare(t1, student.r[0])) {
                        student.r[0] = t1;
                        student.p[0] = temp1;
                        return;
                    }
                } else if ((student.p[1] - (int) student.p[1]) != 0 || (student.p[1] - (int) student.p[1]) != 0.5) {
                    double temp2 = (double) Math.round((student.p[2] - student.p[0] * diem.giuaki) / diem.cuoiki * 10) / 10;
                    String t2 = Double.toString(temp2);
                    if (compare(t2, student.r[1])) {
                        student.r[1] = t2;
                        student.p[1] = temp2;
                        return;
                    }
                }
                double temp = (double) Math.round((student.p[0] * diem.giuaki + student.p[1] * diem.cuoiki) * 10) / 10;
                String t = Double.toString(temp);
                if (compare(t, student.r[2])) {
                    student.r[2] = t;
                    student.p[2] = temp;
                } else {
                    double temp1 = (double) Math.round((student.p[2] - student.p[1] * diem.cuoiki) / diem.giuaki * 10) / 10;
                    String t1 = Double.toString(temp1);
                    if (compare(t1, student.r[0])) {
                        student.r[0] = t1;
                        student.p[0] = temp1;
                    } else {
                        double temp2 = (double) Math.round((student.p[2] - student.p[0] * diem.giuaki) / diem.cuoiki * 10) / 10;
                        String t2 = Double.toString(temp2);
                        if (compare(t2, student.r[1])) {
                            student.r[1] = t2;
                            student.p[1] = temp2;
                        }
                    }
                }
            }
        } else {
            if (isTruePoints(student.r[0]) == false) {
                if (isTruePoints(student.r[1]) && isTruePoints(student.r[2])) {
                    student.p[1] = Double.parseDouble(student.r[1]);
                    student.p[2] = Double.parseDouble(student.r[2]);
                    student.p[0] = ((double) Math.round((student.p[2] - student.p[1] * diem.cuoiki) / diem.giuaki * 10)) / 10;
                    student.r[0] = Double.toString(student.p[0]);
                }
            }
            if (isTruePoints(student.r[1]) == false) {
                if (isTruePoints(student.r[0]) && isTruePoints(student.r[2])) {
                    student.p[0] = Double.parseDouble(student.r[0]);
                    student.p[2] = Double.parseDouble(student.r[2]);
                    student.p[1] = ((double) Math.round((student.p[2] - student.p[0] * diem.giuaki) / diem.cuoiki * 10)) / 10;
                    student.r[1] = Double.toString(student.p[1]);
                }
            }
            if (isTruePoints(student.r[2]) == false) {
                if (isTruePoints(student.r[0]) && isTruePoints(student.r[1])) {
                    student.p[1] = Double.parseDouble(student.r[1]);
                    student.p[0] = Double.parseDouble(student.r[0]);
                    student.p[2] = ((double) Math.round((student.p[0] * diem.giuaki + student.p[1] * diem.cuoiki) * 10)) / 10;
                    student.r[2] = Double.toString(student.p[2]);
                }
            }
        }

    }

    public void fixHeSo() {
        int check = 0;
        int check1 = 0;
        double gk = 0;
        double ck = 0;
//        System.out.println("+++++" + diem.giuaki + " " + diem.cuoiki + " +++++=");
//        System.out.println("*******");
        for (int i = 0; i < numberStudent; i++) {
            if (isTruePoints(student[i].r[0]) && isTruePoints(student[i].r[1]) && isTruePoints(student[i].r[2])) {
//                System.out.println(student[i].toString());
                double r0 = Double.parseDouble(student[i].r[0]);
                double r1 = Double.parseDouble(student[i].r[1]);
                double r2 = Double.parseDouble(student[i].r[2]);
                if (r0 == 0 && r1 == 0 && r2 == 0) continue;
                double t1 = (r0 * diem.giuaki + r1 * diem.cuoiki);
                t1 = (double) Math.round(t1 * 10) / 10;
                double t2 = (r0 * gk + r1 * ck);
                t2 = (double) Math.round(t2 * 10) / 10;
                if (t1 == r2) {
                    check++;
                } else if (t2 == r2) {
                    check1++;
                } else {
                    gk = (r2 - r1) / (r0 - r1);
                    ck = 1 - gk;
                    ck = (double) Math.round(ck * 10) / 10;
                    gk = (double) Math.round(gk * 10) / 10;
                    check1 = 0;
//                    System.out.println("gk " + gk + " ck " + ck);
                }
            }
            if (check == 2) {
                return;
            }
            if (check1 > 2) {
//                System.out.println("");
                diem.giuaki = gk;
                diem.cuoiki = ck;
                return;
            }
        }
//        System.out.println("**********");
    }

    public void fixStringPoint() {
        for (int i = 0; i < numberStudent; i++) {
            for (int j = 0; j < 3; j++) {
                if (student[i].r[j] != null) {
                    int key = student[i].r[j].indexOf(".");
                    String temp = "";
                    if (key - 2 > -1) {
                        if (student[i].r[j].charAt(key - 2) == '1') {
                            temp += "1";
                        }
                    }
                    if (key - 1 > -1) {
                        temp += student[i].r[j].charAt(key - 1);
                    }
                    temp += ".";
                    if (student[i].r[j].length() > key + 1) {
                        temp += student[i].r[j].charAt(key + 1);
                    }
                    student[i].r[j] = temp;
                }
            }
        }
    }

    public void addStudent(Student in) {
        student[numberStudent] = in;
        numberStudent++;
    }

    public void fixStudent(Student fix, double p0, double p1, double p2) {
        fix.p[0] = p0;
        fix.p[1] = p1;
        fix.p[2] = p2;
        fix.r[0] = Double.toString(fix.p[0]);
        fix.r[1] = Double.toString(fix.p[1]);
        fix.r[1] = Double.toString(fix.p[2]);
    }

    public boolean isTruePoints(String input) {
        if (input == null) {
            return false;
        }
        if (input.equals("10.0")) {
            return true;
        }
        if (input.length() < 3) {
            return false;
        }
        return isNumber(input.charAt(0)) && isNumber(input.charAt(2));
    }

    public void findDataStudent() {
        while (current < length) {
            String line = getLineAndFix();
            if (isLineData(line)) {
                String[] words = getWords(line);
                int numWords = words.length;
                for (int i = 0; i < numWords; i++) {
                    if (isMSSV(words[i])) {
                        student[numberStudent] = new Student();
                        String ms = words[i];
                        int k = i + 1;
                        while (ms.length() < 8) {
                            for (int l = 0; l < words[k].length(); l++) {
                                if (ms.length() < 8) {
                                    ms += words[k].charAt(l);
                                }
                            }
                            k++;
                        }
                        student[numberStudent].mssv = fixMSSV(ms);
                        if (isAndDoPoints(words, student[numberStudent])) {
                            numberStudent++;
                        } else {
                            if (findPointNextLine(student[numberStudent])) {
                                numberStudent++;
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    public boolean isAndDoPoints(String[] input, Student student) {
        int numWords = input.length;
        String[] point = new String[3];
        int count = 0;
        boolean conti = false;
        for (int i = 0; i < numWords && count < 3; i++) {
            if (input[i].length() > 2) {
                if (input[i].contains("QH") || input[i].contains("CQ")) {
                    conti = false;
                    continue;
                }
                if (input[i].contains(".")) {
                    if (conti == false) {
                        if (count < 2) {
                            count = 0;
                        }
                    }
                    point[count] = input[i];
                    count++;
                    conti = true;
                } else {
                    conti = false;
                }
            }
        }
        student.r[0] = point[0];
        student.r[1] = point[1];
        student.r[2] = point[2];
        return count == 3;
    }

    public boolean findPointNextLine(Student student) {
        String[] temp = new String[4];
        int cc = 0;
        temp[0] = "";
        int current = this.current;
        while (current < length && cc < 3) {
            if (input.charAt(current) != ' ' && input.charAt(current) != '\n') {
                temp[cc] += input.charAt(current);
            } else {
                cc++;
                temp[cc] = "";
            }
            current++;
        }
        int count = 0;
        while (temp[count].contains(".")) {
            this.current += temp[count].length() + 1;
            count++;
        }
        if (count == 3) {
            for (int j = 0; j < 3; j++) {
                student.r[j] = temp[j];
            }
            return true;
        }
        for (int i = 0; i < 3; i++) {
            if (student.r[i] == null) {
                if (i + count < 2) {
                    return false;
                }
                for (int j = 0; j < count && i + j < 3; j++) {
                    student.r[i + j] = temp[j];
                }
                return true;
            }
        }
        return true;
    }

    public boolean isMSSV(String input) {
        int count = 0;
        int length = input.length();
        for (int i = 0; i < input.length(); i++) {
            if (isNumber(input.charAt(i))) {
                count++;
            }
            if (input.charAt(i) == '/') {
                return false;
            }
            if (input.charAt(i) == '.' || input.charAt(i) == ',') {
                length--;
            }
        }
        return count > 4 && length < 9;
    }

    public String fixMSSV(String input) {
        String result = "";
        int i = 0;
        if (input.charAt(0) == '7') {
            result += '1';
            i++;
        }
        for (; i < input.length(); i++) {
            if (input.charAt(i) != '.' && input.charAt(i) != ',') {
                result += input.charAt(i);
            }
        }
        return result;
    }

    public String getLineAndFix() {
        String result = "";
        while (current < length) {
            if (input.charAt(current) != '\n') {
                result += input.charAt(current);
                current++;
            } else {
                current++;
                break;
            }
        }
        input = input.replace('t', '1');
        input = input.replace('B', '8');
        input = input.replace('O', '0');
        input = input.replace('$', '8');
        return result;
    }

    public String[] getWords(String input) {
        String[] temp = new String[300];
        int cc = 0;
        temp[0] = "";
        int current = 0;
        int length = input.length();
        while (current < length) {
            if (input.charAt(current) != ' ') {
                temp[cc] += input.charAt(current);
            } else {
                cc++;
                temp[cc] = "";
            }
            current++;
        }
        String[] result = new String[cc + 1];
        for (int i = 0; i <= cc; i++) {
            result[i] = temp[i];
        }
        return result;
    }

    public boolean isLineData(String input) {
        return input.contains("QH");
    }

    public boolean isToken(char input) {
        return input == ' ' || input == 13 || input == 10;
    }

    public void findHeSo() {
        int i = 0;
        int begin = 0;
        int end = 0;
        while (current < length) {
            if (input.charAt(current) == '.') {
                if (input.charAt(current) == 'Q' && input.charAt(current + 1) == 'H') {
                    current -= 50;
                    break;
                }
                if (i == 0) {
                    if (isNumber(input.charAt(current + 1))) {
                        begin = current - 5;
                        diem.giuaki = 0;
                        diem.giuaki = diem.giuaki + (input.charAt(current + 1) - '0') / 10.0;
                        i++;
                    }
                } else {
                    if (isNumber(input.charAt(current + 1))) {
                        end = current + 3;
                        diem.cuoiki = 0;
                        diem.cuoiki = diem.cuoiki + (input.charAt(current + 1) - '0') / 10.0;
                        i++;
                    }
                    break;
                }
            }
            current++;
        }
    }

    private boolean isNumber(char input) {
        return input >= '0' && input <= '9';
    }

    @Override
    public String toString() {
        return diem.giuaki + " " + diem.cuoiki + ")";
    }

    public void writeToFile(String filename) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename));
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReaderPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReaderPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Student findStudent(String mssv) {
        Student[] result = new Student[15];
        int count = 0;
        boolean check = false;
        for (int i = 0; i < numberStudent; i++) {
            int cur = 0;
            for (int j = 0; j < student[i].mssv.length(); j++) {
                if (isNumber(student[i].mssv.charAt(j))) {
                    check = false;
                    while (cur < mssv.length()) {
                        if (student[i].mssv.charAt(j) == mssv.charAt(cur)) {
                            cur++;
                            check = true;
                            break;
                        }
                        cur++;
                    }
                }
            }
            if (check == true) {
                result[count] = student[i];
                count++;
            }
        }
        if (count != 0) {
            for (int i = 0; i < count; i++) {
                System.out.println(result[i]);
            }
            String comp = "";
            for (int i = mssv.length() - 5; i < mssv.length(); i++) {
                comp += mssv.charAt(i);
            }
            System.out.println("comp " + comp);
            Student kq = result[0];
            for (int i = 1; i < count; i++) {
                int a = computeSame(comp, kq.mssv);
                int b = computeSame(comp, result[i].mssv);
                System.out.println("a " + a + " b " + b);
                if (a < b) {
                    kq = result[i];
                }
            }
            return kq;
        }
        return null;
    }

    public int computeSame(String a, String b) {
        int result = a.length() - 1;
        for (int i = b.length() - 1; i >= 0; i--) {
            if (result == -1) {
                break;
            }
            if (isNumber(b.charAt(i))) {
                if (a.charAt(result) == b.charAt(i)) {
                    result--;
                } else {
                    break;
                }
            }

        }
        return a.length() - result;
    }
    /**
     * @param args the command line arguments
     */
}
