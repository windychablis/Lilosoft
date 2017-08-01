package com.chablis.lilosoft.model;

/**
 * Created by chablis on 2017/4/7.
 */

public class AppointmentTime {

    /**
     * Region : 08:00-09:00
     * MaxNum : 10
     * SubNum : 0
     * iCount : 10
     */

    private String Region;
    private String MaxNum;
    private String SubNum;
    private String iCount;

    @Override
    public String toString() {
        return "AppointmentTime{" +
                "Region='" + Region + '\'' +
                ", MaxNum=" + MaxNum +
                ", SubNum=" + SubNum +
                ", iCount=" + iCount +
                '}';
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getMaxNum() {
        return MaxNum;
    }

    public void setMaxNum(String maxNum) {
        MaxNum = maxNum;
    }

    public String getSubNum() {
        return SubNum;
    }

    public void setSubNum(String subNum) {
        SubNum = subNum;
    }

    public String getiCount() {
        return iCount;
    }

    public void setiCount(String iCount) {
        this.iCount = iCount;
    }
}
