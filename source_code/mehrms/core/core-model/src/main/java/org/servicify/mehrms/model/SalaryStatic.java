package org.servicify.mehrms.model;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class SalaryStatic {

    private List<Integer> lessThan4000;

    private List<Integer> lessThan6000;

    private List<Integer> lessThan8000;

    private List<Integer> lessThan10000;

    private List<Integer> moreThan10000;

    private List<Float> creditEmps;

    private List<Float> creditCom;

    private List<Float> realSalary;

    private List<Float> counts;

    private List<Float> avgSalary;

    private List<String> Months;

    public List<Integer> getLessThan4000() {
        return lessThan4000;
    }

    public void setLessThan4000(List<Integer> lessThan4000) {
        this.lessThan4000 = lessThan4000;
    }

    public List<Integer> getLessThan6000() {
        return lessThan6000;
    }

    public void setLessThan6000(List<Integer> lessThan6000) {
        this.lessThan6000 = lessThan6000;
    }

    public List<Integer> getLessThan8000() {
        return lessThan8000;
    }

    public void setLessThan8000(List<Integer> lessThan8000) {
        this.lessThan8000 = lessThan8000;
    }

    public List<Integer> getLessThan10000() {
        return lessThan10000;
    }

    public void setLessThan10000(List<Integer> lessThan10000) {
        this.lessThan10000 = lessThan10000;
    }

    public List<Integer> getMoreThan10000() {
        return moreThan10000;
    }

    public void setMoreThan10000(List<Integer> moreThan10000) {
        this.moreThan10000 = moreThan10000;
    }

    public List<Float> getCreditEmps() {
        return creditEmps;
    }

    public void setCreditEmps(List<Float> creditEmps) {
        this.creditEmps = creditEmps;
    }

    public List<Float> getCreditCom() {
        return creditCom;
    }

    public void setCreditCom(List<Float> creditCom) {
        this.creditCom = creditCom;
    }

    public List<Float> getRealSalary() {
        return realSalary;
    }

    public void setRealSalary(List<Float> realSalary) {
        this.realSalary = realSalary;
    }

    public List<Float> getCounts() {
        return counts;
    }

    public void setCounts(List<Float> counts) {
        this.counts = counts;
    }

    public List<Float> getAvgSalary() {
        return avgSalary;
    }

    public void setAvgSalary(List<Float> avgSalary) {
        this.avgSalary = avgSalary;
    }

    public List<String> getMonths() {
        return Months;
    }

    public void setMonths(List<String> months) {
        Months = months;
    }
}
