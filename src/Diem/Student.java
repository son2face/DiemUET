package Diem;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author admin
 */
public class Student implements Serializable {

    String mssv;
    String[] r = new String[3];
    double[] p = new double[3];

    public Student(String mssv, double p1, double p2, double p3) {
        this.mssv = mssv;
        this.p[0] = p1;
        this.p[1] = p2;
        this.p[2] = p3;
        this.r[0] = Double.toString(this.p[0]);
        this.r[1] = Double.toString(this.p[1]);
        this.r[1] = Double.toString(this.p[2]);
    }

    public Student() {
    }

    @Override
    public String toString() {
        String result = "Student{" + "mssv=" + mssv + ", ";
        for (int i = 0; i < 3; i++) {
            result += " r" + i + "=" + r[i];
        }
        for (int i = 0; i < 3; i++) {
            result += " p" + i + "=" + p[i];
        }
        result += '}';
        return result;
    }
}
