package com.example.kpp_lr6.dbhelper;

public class Wheat {
    private String id;
    private String name;
    private String season;
    private String period;
    private String productivity;

    public Wheat(String id, String name, String season, String period, String productivity) {
        this.id = id;
        this.name = name;
        this.season = season;
        this.period = period;
        this.productivity = productivity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getProductivity() {
        return productivity;
    }

    public void setProductivity(String productivity) {
        this.productivity = productivity;
    }
}
