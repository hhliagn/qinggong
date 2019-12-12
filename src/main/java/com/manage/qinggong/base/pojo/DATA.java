package com.manage.qinggong.base.pojo;

public class DATA {
    private String ActIndex;
    private String AcsRes;
    private String Time;

    public DATA() {
    }

    public DATA(String actIndex, String acsRes, String time) {
        ActIndex = actIndex;
        AcsRes = acsRes;
        Time = time;
    }

    public String getActIndex() {
        return ActIndex;
    }

    public void setActIndex(String actIndex) {
        ActIndex = actIndex;
    }

    public String getAcsRes() {
        return AcsRes;
    }

    public void setAcsRes(String acsRes) {
        AcsRes = acsRes;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time){
        this.Time = time;
    }
}
