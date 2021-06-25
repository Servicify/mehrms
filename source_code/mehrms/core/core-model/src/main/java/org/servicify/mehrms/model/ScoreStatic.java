package org.servicify.mehrms.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class ScoreStatic {

    private List<Integer> lessThan60;

    private List<Integer> lessThan80;

    private List<Integer> lessThan90;

    private List<Integer> lessThan100;

    private ArrayList<Float> avgScore;

    private ArrayList<String> yearsAndMonths;
//    统计最大人数
    private Integer count;

    private List<Integer> bonusList;

    private List<Integer> punishmentsList;

    public List<Integer> getLessThan60() {
        return lessThan60;
    }

    public void setLessThan60(List<Integer> lessThan60) {
        this.lessThan60 = lessThan60;
    }

    public List<Integer> getLessThan80() {
        return lessThan80;
    }

    public void setLessThan80(List<Integer> lessThan80) {
        this.lessThan80 = lessThan80;
    }

    public List<Integer> getLessThan90() {
        return lessThan90;
    }

    public void setLessThan90(List<Integer> lessThan90) {
        this.lessThan90 = lessThan90;
    }

    public List<Integer> getLessThan100() {
        return lessThan100;
    }

    public void setLessThan100(List<Integer> lessThan100) {
        this.lessThan100 = lessThan100;
    }

    public ArrayList<Float> getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(ArrayList<Float> avgScore) {
        this.avgScore = avgScore;
    }

    public ArrayList<String> getYearsAndMonths() {
        return yearsAndMonths;
    }

    public void setYearsAndMonths(ArrayList<String> yearsAndMonths) {
        this.yearsAndMonths = yearsAndMonths;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Integer> getBonusList() {
        return bonusList;
    }

    public void setBonusList(List<Integer> bonusList) {
        this.bonusList = bonusList;
    }

    public List<Integer> getPunishmentsList() {
        return punishmentsList;
    }

    public void setPunishmentsList(List<Integer> punishmentsList) {
        this.punishmentsList = punishmentsList;
    }

}
