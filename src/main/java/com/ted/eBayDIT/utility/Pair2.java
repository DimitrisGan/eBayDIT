package com.ted.eBayDIT.utility;

public class Pair2 implements Comparable<Pair2>{



    private Integer index;
    private Double itemScore;

    public Pair2(Integer index, Double itemScore) {
        this.index = index;
        this.itemScore = itemScore;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Double getItemScore() {
        return itemScore;
    }

    public void setItemScore(Double itemScore) {
        this.itemScore = itemScore;
    }

    @Override
    public int compareTo(Pair2 o) {
        return getItemScore().compareTo(o.getItemScore());
    }
}
