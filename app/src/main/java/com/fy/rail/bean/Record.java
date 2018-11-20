package com.fy.rail.bean;

import org.litepal.crud.LitePalSupport;

/**
 * describe： 一条记录 对象
 * Created by fangs on 2018/11/20 16:10.
 */
public class Record extends LitePalSupport {

    private int id;

    private String saveDate = "2000-01-01";//记录日期

    private String direction = "";//行别

    private String numOfKm = "";//千米数

    private String trackNum = "";//轨道号

    private String abnormityDesc = "";//异常描述

    private String otherDesc = "";//其它描述

    public Record() {
    }

    public Record(int id, String saveDate, String direction, String numOfKm, String trackNum, String abnormityDesc, String otherDesc) {
        this.id = id;
        this.saveDate = saveDate;
        this.direction = direction;
        this.numOfKm = numOfKm;
        this.trackNum = trackNum;
        this.abnormityDesc = abnormityDesc;
        this.otherDesc = otherDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(String saveDate) {
        this.saveDate = saveDate;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getNumOfKm() {
        return numOfKm;
    }

    public void setNumOfKm(String numOfKm) {
        this.numOfKm = numOfKm;
    }

    public String getTrackNum() {
        return trackNum;
    }

    public void setTrackNum(String trackNum) {
        this.trackNum = trackNum;
    }

    public String getAbnormityDesc() {
        return abnormityDesc;
    }

    public void setAbnormityDesc(String abnormityDesc) {
        this.abnormityDesc = abnormityDesc;
    }

    public String getOtherDesc() {
        return otherDesc;
    }

    public void setOtherDesc(String otherDesc) {
        this.otherDesc = otherDesc;
    }
}
