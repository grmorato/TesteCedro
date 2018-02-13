package grmorato.testecedro.Models;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by grmorato on 09/02/2018.
 */

public class Pais implements Serializable
{
    private boolean select;
    private String name;
    private String flag;
    private String alpha2Code;
    private String area;
    private String population;
    private String capital;
    private String DateVisit;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getDateVisit() {
        return DateVisit;
    }

    public void setDateVisit(String dateVisit) {
        DateVisit = dateVisit;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

}
