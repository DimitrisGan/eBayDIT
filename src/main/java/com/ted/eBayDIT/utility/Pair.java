package com.ted.eBayDIT.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Pair implements Comparable<Pair> {

    private String e1;
    private Double e2;


    public Pair(String e1, Double e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public String getE1() {
        return e1;
    }

    public void setE1(String e1) {
        this.e1 = e1;
    }

    public Double getE2() {
        return e2;
    }

    public void setE2(Double e2) {
        this.e2 = e2;
    }

    @Override
    public int compareTo(Pair o) {
        return getE2().compareTo(o.getE2());
    }
}