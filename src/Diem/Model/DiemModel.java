package Diem.Model;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class DiemModel implements Serializable {

    public double giuaki = 10;
    public double cuoiki = 10;

    public DiemModel() {
    }

    public DiemModel(double giuaki, double cuoiki) {
        this.giuaki = giuaki;
        this.cuoiki = cuoiki;
    }

}
